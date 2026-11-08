package com.nthexperience.desktop;

import android.content.*;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.*;
import android.text.*;
import android.view.*;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import java.util.*;
import org.json.*;

/** Full-screen Eight Start, a separate Apps view, and real local scoped search. */
final class EightStartScreen extends LinearLayout {
    enum Surface { START, ALL_APPS, SEARCH, OVERVIEW }
    final StartMenu.Actions actions;
    final DesktopStore store;
    final Runnable surfaceChanged;
    final AeroWallpaper wallpaper;
    final List<AppCatalog.Entry> all=new ArrayList<>();
    final FrameLayout viewport;
    final LinearLayout header,commands,bodyRow,searchSide,scopeChoices;final ScrollView searchSideScroll;
    final TextView heading,identity;final View updatePower,updateSearch;
    final EditText search;
    final Handler handler=new Handler(Looper.getMainLooper());
    EightStartData data;
    EightProfile profile;
    Surface surface=Surface.START;
    String selected,scope="Apps";
    private String firstSearchResult;
    int lastWidth,lastHeight;
    HorizontalScrollView horizontal;
    final ScaleGestureDetector zoom;
    private final List<TileGroup> dragGroups=new ArrayList<>();
    private final Map<String,View> tileViews=new HashMap<>();
    final EightLiveTiles liveTiles;
    private final Map<String,EightLiveTileView> liveViews=new HashMap<>();
    private final Map<String,TextView> smallBadges=new HashMap<>();
    private boolean foreground=true;
    private String armedTile,weatherKey="";private float pressX,pressY;
    private TileDrag dragging;private View dragSource;private DropPreview dropPreview;
    private EightStartData.Group dropGroup;private int dropIndex,edgeSpeed,newGroupIndex=-1;private View dragSpace;
    private float dragX,dragY,downX,downY;private boolean pendingRender,swipeCandidate,swiping,swipeToApps;
    private final Runnable edgeScroll=new Runnable(){public void run(){if(dragging==null||horizontal==null)return;horizontal.scrollBy(edgeSpeed,0);updateDrop(dragX,dragY);if(edgeSpeed!=0)handler.postDelayed(this,16);}};
    private static final class TileGroup{final EightStartData.Group group;final View grid;final List<EightTileLayout.Cell> cells;final int unit;TileGroup(EightStartData.Group g,View v,List<EightTileLayout.Cell> c,int u){group=g;grid=v;cells=c;unit=u;}}
    private final class DropPreview extends View{
        final Paint paint=new Paint(3);final RectF target=new RectF();String label="";
        DropPreview(Context c){super(c);setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);}
        protected void onDraw(Canvas c){if(dropGroup==null&&newGroupIndex<0)return;paint.setColor(newGroupIndex>=0?0x55ffffff:Color.WHITE);paint.setStrokeWidth(dp(2));paint.setStyle(newGroupIndex>=0?Paint.Style.FILL:Paint.Style.STROKE);c.drawRect(target,paint);paint.setStyle(Paint.Style.FILL);paint.setColor(0xe632165e);c.drawRect(0,getHeight()-dp(34),getWidth(),getHeight(),paint);paint.setColor(Color.WHITE);paint.setTextSize(dp(14));c.drawText(label,dp(16),getHeight()-dp(11),paint);}
    }
    final Runnable live=new Runnable(){public void run(){if(isShown()&&foreground){refreshLiveTiles();if(surface==Surface.START)surfaceChanged.run();handler.postDelayed(this,2000);}}};

    EightStartScreen(Context context,StartMenu.Actions actions,DesktopStore store,AeroWallpaper wallpaper,Runnable surfaceChanged){
        super(context);this.actions=actions;this.store=store;this.surfaceChanged=surfaceChanged;this.wallpaper=wallpaper;
        liveTiles=new EightLiveTiles(store,context);profile=EightDefaults.profile(store);data=new EightStartData(store,profile);
        setOrientation(VERTICAL);setFocusableInTouchMode(true);setElevation(dp(24));
        setContentDescription("Eight Start screen");TenAppearance.preserve(this);
        header=new LinearLayout(context);header.setGravity(Gravity.CENTER_VERTICAL);header.setPadding(dp(24),dp(8),dp(20),0);
        heading=label("Start",36);header.addView(heading,new LayoutParams(0,-1,1));
        identity=label("",14);identity.setGravity(Gravity.END|Gravity.CENTER_VERTICAL);identity.setMaxLines(2);identity.setFocusable(true);identity.setContentDescription("Open profile and shell settings");identity.setOnClickListener(v->actions.open("@settings"));header.addView(identity,new LayoutParams(dp(150),-1));
        updatePower=new TenStyle.Glyph(context,"power",Color.WHITE);updatePower.setContentDescription("Start power control");updatePower.setFocusable(true);updatePower.setOnClickListener(v->actions.open("@power"));header.addView(updatePower,new LayoutParams(dp(38),dp(38)));
        updateSearch=new TenStyle.Glyph(context,"search",Color.WHITE);updateSearch.setContentDescription("Start search control");updateSearch.setFocusable(true);updateSearch.setOnClickListener(v->showSearch("Everywhere"));header.addView(updateSearch,new LayoutParams(dp(38),dp(38)));
        addView(header,new LayoutParams(-1,dp(86)));
        search=new EditText(context);search.setSingleLine();search.setTextSize(17);search.setTextColor(Color.WHITE);search.setHintTextColor(0xffc7bfde);search.setBackgroundColor(0x30333333);search.setPadding(dp(20),0,dp(20),0);search.setHint("Search apps");search.setContentDescription("Eight search query");search.setVisibility(GONE);addView(search,new LayoutParams(-1,dp(48)));
        search.setImeOptions(android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH);
        search.setOnEditorActionListener((v,action,event)->{
            boolean enter=event!=null&&(event.getKeyCode()==KeyEvent.KEYCODE_ENTER||event.getKeyCode()==KeyEvent.KEYCODE_NUMPAD_ENTER);
            if(enter&&event.getAction()!=KeyEvent.ACTION_DOWN)return true;
            if(enter||action==android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH||action==android.view.inputmethod.EditorInfo.IME_ACTION_GO){if(firstSearchResult!=null)actions.open(firstSearchResult);return true;}return false;
        });
        search.addTextChangedListener(new TextWatcher(){public void beforeTextChanged(CharSequence s,int a,int c,int n){}public void afterTextChanged(Editable e){}public void onTextChanged(CharSequence s,int a,int b,int c){if(surface==Surface.SEARCH)renderContent();}});
        bodyRow=new LinearLayout(context);viewport=new FrameLayout(context);bodyRow.addView(viewport,new LayoutParams(0,-1,1));searchSide=Aero.column(context);searchSide.setPadding(dp(16),dp(14),dp(16),dp(14));searchSide.setContentDescription("Eight Search charm pane");searchSide.setBackgroundColor(0xff352467);TextView searchTitle=label("Search",28);searchSide.addView(searchTitle,new LayoutParams(-1,dp(58)));scopeChoices=new LinearLayout(context);searchSideScroll=new ScrollView(context);searchSideScroll.setFillViewport(true);searchSideScroll.addView(searchSide,new ScrollView.LayoutParams(-1,-2));bodyRow.addView(searchSideScroll,new LayoutParams(dp(280),-1));searchSideScroll.setVisibility(GONE);searchSide.setVisibility(GONE);addView(bodyRow,new LayoutParams(-1,0,1));
        HorizontalScrollView commandScroll=new HorizontalScrollView(context);commandScroll.setHorizontalScrollBarEnabled(false);commandScroll.setFillViewport(true);commandScroll.setBackgroundColor(0xff32165e);
        commands=new LinearLayout(context);commands.setGravity(Gravity.CENTER_VERTICAL);commands.setPadding(dp(12),0,dp(12),0);commandScroll.addView(commands,new HorizontalScrollView.LayoutParams(-2,-1));addView(commandScroll,new LayoutParams(-1,dp(56)));
        zoom=new ScaleGestureDetector(context,new ScaleGestureDetector.SimpleOnScaleGestureListener(){public boolean onScale(ScaleGestureDetector detector){if(detector.getScaleFactor()<.92f&&surface==Surface.START){surface=Surface.OVERVIEW;selected=null;post(()->render());return true;}return false;}});
        viewport.addOnLayoutChangeListener((v,l,t,r,b,ol,ot,or,ob)->{if(r-l!=lastWidth||b-t!=lastHeight){lastWidth=r-l;lastHeight=b-t;renderContent();}});
        viewport.setOnDragListener((v,event)->tileDrag(event));
        render();
    }
    private int dp(int value){return Aero.dp(getContext(),value);}
    private android.graphics.drawable.StateListDrawable selection(int base){android.graphics.drawable.StateListDrawable states=new android.graphics.drawable.StateListDrawable();int highlighted=TenStyle.mix(store.prefs.getInt("eight_start_color",0xff21164f),Color.WHITE,.18f);for(int state:new int[]{android.R.attr.state_pressed,android.R.attr.state_hovered,android.R.attr.state_focused})states.addState(new int[]{state},new android.graphics.drawable.ColorDrawable(highlighted));states.addState(new int[]{},new android.graphics.drawable.ColorDrawable(base));return states;}
    private TextView label(String text,int size){TextView view=Aero.text(getContext(),text,size,Color.WHITE);view.setTypeface(Aero.face(getContext()),Typeface.NORMAL);return view;}
    private TextView command(String text,String description,Runnable action){TextView view=label(text,14);view.setGravity(Gravity.CENTER);view.setPadding(dp(16),0,dp(16),0);view.setMinHeight(dp(48));view.setFocusable(true);view.setContentDescription(description);view.setBackground(selection(selected==null?Color.TRANSPARENT:0xff32165e));view.setOnClickListener(v->action.run());commands.addView(view,new LayoutParams(-2,-1));return view;}
    void reloadLayout(){if(dragging!=null)return;data=new EightStartData(store,profile);render();}
    void entries(List<AppCatalog.Entry> values){all.clear();all.addAll(values);if(isShown())renderContent();}
    void refreshName(){identity.setText(store.prefs.getString("desktop_name","Nth Experience")+"\n"+profile.label);}
    void refreshHome(){if(isShown())appearanceChanged();}
    void appearanceChanged(){EightProfile next=EightDefaults.profile(store);if(next!=profile){profile=next;data=new EightStartData(store,profile);selected=null;scope=profile==EightProfile.EIGHT?"Apps":"Everywhere";}render();}
    private void render(){
        if(dragging!=null){pendingRender=true;return;}
        boolean update=profile==EightProfile.EIGHT_POINT_ONE&&store.prefs.getBoolean("eight_81_update",false)&&surface==Surface.START;updatePower.setVisibility(update?VISIBLE:GONE);updateSearch.setVisibility(update?VISIBLE:GONE);
        refreshName();boolean wallpaper=profile==EightProfile.EIGHT_POINT_ONE&&store.prefs.getBoolean("eight_start_wallpaper",false);
        setBackground(new EightBackdrop(store.prefs.getInt("eight_start_color",0xff21164f),wallpaper?this.wallpaper:null));
        heading.setText(surface==Surface.ALL_APPS?"Apps":surface==Surface.SEARCH?"Search":surface==Surface.OVERVIEW?"Groups":"Start");
        search.setVisibility(surface==Surface.SEARCH?VISIBLE:GONE);renderContent();renderCommands();
    }
    void showApps(){surface=Surface.ALL_APPS;selected=null;render();}
    void toggleApps(){surface=surface==Surface.ALL_APPS?Surface.START:Surface.ALL_APPS;selected=null;render();}
    private void renderCommands(){
        commands.removeAllViews();((View)commands.getParent()).setBackgroundColor(selected==null?Color.TRANSPARENT:0xff32165e);
        if(selected!=null){
            EightStartData.Item item=data.item(selected);
            command(item==null?"Pin to Start":"Unpin from Start",item==null?"Pin selected app to Start":"Unpin selected tile",()->{if(item==null){if(!data.pin(selected))Toast.makeText(getContext(),"This app cannot be pinned.",Toast.LENGTH_SHORT).show();}else data.unpin(selected);selected=null;render();});
            if(item!=null){command("Resize","Resize selected tile",()->Aero.dialog(getContext()).setTitle("Tile size").setItems(profile.sizes(),(d,n)->{item.size=profile.sizes()[n];data.save();render();}).show());
                command("Move earlier","Move selected tile earlier",()->moveSelected(-1));command("Move later","Move selected tile later",()->moveSelected(1));
                command("Move to group","Move selected tile to group",()->Aero.dialog(getContext()).setTitle("Move to group").setItems(data.groups.stream().map(g->g.name).toArray(String[]::new),(d,n)->{EightStartData.Group group=data.groups.get(n);data.move(selected,group,group.items.size());render();}).show());}
            if(item!=null&&EightLiveTiles.supports(selected)){String id=selected;command(liveTiles.individual(id)?"Turn live tile off":"Turn live tile on","Toggle live content for selected tile",()->{liveTiles.toggle(id);render();surfaceChanged.run();});if(id.equals("@photos"))command("Choose photos","Choose live tile photos",()->actions.open("eight-tile:photos"));}
            command("Done","Finish tile selection",()->{selected=null;render();});return;
        }
        command(surface==Surface.START?(profile==EightProfile.EIGHT?"All apps":"↓  Apps"):"←  Start","Toggle All apps",()->{surface=surface==Surface.START?Surface.ALL_APPS:Surface.START;render();});
        if(surface==Surface.START||surface==Surface.OVERVIEW){command(surface==Surface.OVERVIEW?"Tiles":"Groups","Toggle group overview",()->{surface=surface==Surface.OVERVIEW?Surface.START:Surface.OVERVIEW;render();});if(surface==Surface.OVERVIEW)command("+ Group","Add tile group",()->nameGroup(null));}
        if(surface==Surface.ALL_APPS&&profile==EightProfile.EIGHT_POINT_ONE)command("Sort apps","Sort Apps view",()->Aero.dialog(getContext()).setTitle("Sort apps").setItems(new String[]{"Name","Date installed","Most used","Category"},(d,n)->{store.prefs.edit().putString("eight_apps_sort",new String[]{"Name","Date installed","Most used","Category"}[n]).apply();render();}).show());
        command("⋯","More Start commands",()->Aero.dialog(getContext()).setTitle("Start commands").setItems(new String[]{"Search","Charms","Switch apps"},(d,n)->{if(n==0)showSearch(profile==EightProfile.EIGHT?"Apps":"Everywhere");else actions.open(n==1?"@charms":"@eight-switcher");}).show());
    }
    private void renderContent(){
        if(dragging!=null){pendingRender=true;return;}
        armedTile=null;dragGroups.clear();tileViews.clear();liveViews.clear();smallBadges.clear();dockSearch();int scroll=horizontal==null?0:horizontal.getScrollX();viewport.removeAllViews();
        if(surface==Surface.SEARCH){horizontal=null;renderSearch();return;}
        horizontal=new HorizontalScrollView(getContext());horizontal.setFillViewport(false);horizontal.setHorizontalScrollBarEnabled(true);horizontal.setContentDescription(surface==Surface.ALL_APPS?"All apps horizontal view":"Horizontal Start groups");
        LinearLayout groups=new LinearLayout(getContext());groups.setPadding(dp(24),dp(8),dp(24),dp(20));horizontal.addView(groups,new HorizontalScrollView.LayoutParams(-2,-1));viewport.addView(horizontal,new FrameLayout.LayoutParams(-1,-1));
        if(surface==Surface.ALL_APPS)renderApps(groups);else for(EightStartData.Group group:data.groups)renderGroup(groups,group,surface==Surface.OVERVIEW);
        HorizontalScrollView rendered=horizontal;rendered.post(()->{if(horizontal==rendered)rendered.scrollTo(scroll,0);});refreshLiveTiles();
    }
    private void renderGroup(LinearLayout parent,EightStartData.Group group,boolean overview){
        LinearLayout block=Aero.column(getContext());LayoutParams blockParams=new LayoutParams(-2,-1);blockParams.rightMargin=dp(34);parent.addView(block,blockParams);
        TextView title=label(group.name,17);title.setGravity(Gravity.CENTER_VERTICAL);title.setFocusable(true);title.setContentDescription("Tile group "+group.name);title.setOnLongClickListener(v->{nameGroup(group);return true;});block.addView(title,new LayoutParams(-1,dp(38)));
        int height=Math.max(dp(100),lastHeight-dp(66));int rows=Math.max(4,Math.min(8,height/dp(54)));rows-=rows%2;int unit=Math.max(1,Math.min(dp(62),height/rows));if(overview)unit=Math.max(1,unit/2);
        List<String> sizes=new ArrayList<>();for(EightStartData.Item item:group.items)sizes.add(item.size);
        List<EightTileLayout.Cell> cells=EightTileLayout.place(sizes,rows,profile);FrameLayout grid=new FrameLayout(getContext());int width=unit*2;
        for(int i=0;i<group.items.size();i++){
            EightStartData.Item item=group.items.get(i);EightTileLayout.Cell cell=cells.get(i);StartSearch.Result resolved=resolve(item.id);
            View tile=tile(resolved,item.size,overview);FrameLayout.LayoutParams position=new FrameLayout.LayoutParams(Math.max(1,cell.w*unit-dp(5)),Math.max(1,cell.h*unit-dp(5)));position.leftMargin=cell.col*unit;position.topMargin=cell.row*unit;grid.addView(tile,position);width=Math.max(width,(cell.col+cell.w)*unit);
            tileViews.put(item.id,tile);
        }
        block.addView(grid,new LayoutParams(overview?Math.max(dp(110),width):width,rows*unit));
        dragGroups.add(new TileGroup(group,grid,cells,unit));
        if(overview){title.setOnClickListener(v->{surface=Surface.START;render();int index=data.groups.indexOf(group);post(()->{if(horizontal!=null&&horizontal.getChildCount()>0){LinearLayout content=(LinearLayout)horizontal.getChildAt(0);if(index<content.getChildCount())horizontal.smoothScrollTo(content.getChildAt(index).getLeft(),0);}});});title.setOnLongClickListener(v->{groupOptions(group);return true;});}
    }
    private View tile(StartSearch.Result item,String size,boolean overview){
        FrameLayout tile=new FrameLayout(getContext());int color=color(item.id);android.graphics.drawable.GradientDrawable fill=new android.graphics.drawable.GradientDrawable();fill.setColor(color);if(item.id.equals(selected))fill.setStroke(dp(3),Color.WHITE);tile.setBackground(item.id.equals("@desktop")&&!item.id.equals(selected)?new EightBackdrop(0xff174266,wallpaper):fill);tile.setForeground(item.id.equals(selected)?tileOutline():null);tile.setFocusable(true);tile.setContentDescription(item.title+", "+size+" Start tile");
        int iconSize=dp(overview?20:size.equals("Small")?22:38);FrameLayout.LayoutParams iconParams=new FrameLayout.LayoutParams(iconSize,iconSize,Gravity.CENTER);if(!overview&&!size.equals("Small"))iconParams.bottomMargin=dp(16);if(!item.id.equals("@desktop"))tile.addView(icon(item,Color.WHITE),iconParams);
        if(!size.equals("Small")&&!overview){TextView text=label(item.title,13);text.setMaxLines(2);text.setPadding(dp(9),0,dp(6),dp(6));tile.addView(text,new FrameLayout.LayoutParams(-1,-2,Gravity.BOTTOM));}
        if(!overview&&!size.equals("Small")&&EightLiveTiles.supports(item.id)){EightLiveTileView liveView=new EightLiveTileView(getContext(),item.title,size);liveView.setBackgroundColor(color);tile.addView(liveView,new FrameLayout.LayoutParams(-1,-1));liveViews.put(item.id,liveView);}
        if(!overview&&size.equals("Small")&&EightLiveTiles.supports(item.id)){TextView badge=label("",12);badge.setGravity(Gravity.CENTER);badge.setBackgroundColor(0x99000000);badge.setVisibility(GONE);tile.addView(badge,new FrameLayout.LayoutParams(dp(28),dp(22),Gravity.BOTTOM|Gravity.END));smallBadges.put(item.id,badge);}
        tile.setOnHoverListener((v,event)->{boolean over=event.getActionMasked()!=MotionEvent.ACTION_HOVER_EXIT;if(!item.id.equals(selected))tile.setForeground(over?hoverOutline():null);return false;});
        tile.setOnFocusChangeListener((v,focused)->{if(!item.id.equals(selected))tile.setForeground(focused?hoverOutline():null);});
        tile.setOnClickListener(v->{if(overview){surface=Surface.START;render();}else if(selected!=null){selected=selected.equals(item.id)?null:item.id;render();}else actions.open(item.id);});
        tile.setOnContextClickListener(v->{if(profile==EightProfile.EIGHT_POINT_ONE&&store.prefs.getBoolean("eight_81_update",false))tileMenu(tile,item);else{selected=item.id;render();}return true;});
        tile.setOnLongClickListener(v->{if(overview)return false;selected=item.id;armedTile=item.id;swipeCandidate=false;pauseLiveTiles();tile.getParent().requestDisallowInterceptTouchEvent(true);updateSelection();renderCommands();return true;});
        tile.setOnTouchListener((v,event)->{if(event.getActionMasked()==MotionEvent.ACTION_DOWN){if(EightSurfaceMotion.enabled(store))tile.animate().scaleX(.965f).scaleY(.965f).setDuration(85).start();pressX=event.getX();pressY=event.getY();armedTile=null;}else if(event.getActionMasked()==MotionEvent.ACTION_MOVE&&item.id.equals(armedTile)&&Math.hypot(event.getX()-pressX,event.getY()-pressY)>dp(8)){armedTile=null;tile.animate().cancel();tile.setScaleX(1);tile.setScaleY(1);getParent().requestDisallowInterceptTouchEvent(true);return tile.startDragAndDrop(ClipData.newPlainText("Eight tile",item.id),new View.DragShadowBuilder(tile),new TileDrag(item.id),0);}else if(event.getActionMasked()==MotionEvent.ACTION_UP||event.getActionMasked()==MotionEvent.ACTION_CANCEL){armedTile=null;tile.animate().scaleX(1).scaleY(1).setDuration(EightSurfaceMotion.enabled(store)?100:0).start();}return false;});
        tile.setOnKeyListener((v,key,event)->{if(event.getAction()==KeyEvent.ACTION_DOWN&&event.isCtrlPressed()&&(key==KeyEvent.KEYCODE_DPAD_LEFT||key==KeyEvent.KEYCODE_DPAD_RIGHT)){selected=item.id;moveSelected(key==KeyEvent.KEYCODE_DPAD_LEFT?-1:1);return true;}return false;});
        tile.setAccessibilityDelegate(new View.AccessibilityDelegate(){public void onInitializeAccessibilityNodeInfo(View v,AccessibilityNodeInfo node){super.onInitializeAccessibilityNodeInfo(v,node);node.addAction(new AccessibilityNodeInfo.AccessibilityAction(0x01020051,"Move tile earlier"));node.addAction(new AccessibilityNodeInfo.AccessibilityAction(0x01020052,"Move tile later"));}public boolean performAccessibilityAction(View v,int action,Bundle args){if(action==0x01020051||action==0x01020052){selected=item.id;moveSelected(action==0x01020051?-1:1);return true;}return super.performAccessibilityAction(v,action,args);}});
        return tile;
    }
    private void tileMenu(View anchor,StartSearch.Result item){DesktopContextMenu menu=new DesktopContextMenu(getContext(),"8.1 Update tile menu");if(EightLiveTiles.supports(item.id)){menu.item(liveTiles.individual(item.id)?"Turn live tile off":"Turn live tile on","appearance",()->{liveTiles.toggle(item.id);render();surfaceChanged.run();});if(item.id.equals("@photos")){menu.item("Choose photos","pictures",()->actions.open("eight-tile:photos"));menu.item("Clear tile photos","delete",()->{store.prefs.edit().remove("eight_tile_photos").apply();render();});}}menu.item("Open","programs",()->actions.open(item.id));menu.item("Unpin from Start","delete",()->{data.unpin(item.id);selected=null;render();});menu.item("Resize","appearance",()->Aero.dialog(getContext()).setTitle("Tile size").setItems(profile.sizes(),(d,n)->{EightStartData.Item tile=data.item(item.id);if(tile!=null){tile.size=profile.sizes()[n];data.save();render();}}).show());List<String> pins=store.quickLaunch();menu.item(pins.contains(item.id)?"Unpin from taskbar":"Pin to taskbar","programs",()->{if(pins.contains(item.id))pins.remove(item.id);else if(pins.size()<DesktopStore.MAX_QUICK)pins.add(item.id);store.quickLaunch(pins);surfaceChanged.run();});ContextMenus.centered(anchor,(x,y)->ContextMenus.show(menu,anchor,x,y));}
    private static final class TileDrag{final String id;TileDrag(String id){this.id=id;}}
    private android.graphics.drawable.Drawable hoverOutline(){android.graphics.drawable.GradientDrawable line=new android.graphics.drawable.GradientDrawable();line.setColor(Color.TRANSPARENT);line.setStroke(dp(2),0x99ffffff);return line;}
    private android.graphics.drawable.Drawable tileOutline(){android.graphics.drawable.GradientDrawable line=new android.graphics.drawable.GradientDrawable();line.setColor(Color.TRANSPARENT);line.setStroke(dp(3),Color.WHITE);return line;}
    private void updateSelection(){for(Map.Entry<String,View> entry:tileViews.entrySet()){entry.getValue().setForeground(entry.getKey().equals(selected)?tileOutline():null);android.graphics.drawable.GradientDrawable fill=new android.graphics.drawable.GradientDrawable();fill.setColor(color(entry.getKey()));if(entry.getKey().equals(selected))fill.setStroke(dp(3),Color.WHITE);entry.getValue().setBackground(entry.getKey().equals("@desktop")&&!entry.getKey().equals(selected)?new EightBackdrop(0xff174266,wallpaper):fill);}}
    private boolean tileDrag(DragEvent event){
        if(!(event.getLocalState() instanceof TileDrag))return false;
        switch(event.getAction()){
            case DragEvent.ACTION_DRAG_STARTED:pauseLiveTiles();dragging=(TileDrag)event.getLocalState();dragSource=tileViews.get(dragging.id);if(dragSource!=null)dragSource.setAlpha(.35f);newGroupIndex=-1;if(horizontal!=null&&horizontal.getChildCount()>0){dragSpace=new View(getContext());dragSpace.setContentDescription("New column drop space");((LinearLayout)horizontal.getChildAt(0)).addView(dragSpace,new LayoutParams(Math.max(dp(180),viewport.getWidth()/2),-1));}dropPreview=new DropPreview(getContext());viewport.addView(dropPreview,new FrameLayout.LayoutParams(-1,-1));swipeCandidate=false;return true;
            case DragEvent.ACTION_DRAG_LOCATION:dragX=event.getX();dragY=event.getY();edgeSpeed=dragX<dp(56)?-Math.max(dp(2),(int)((dp(56)-dragX)/5)):dragX>viewport.getWidth()-dp(56)?Math.max(dp(2),(int)((dragX-viewport.getWidth()+dp(56))/5)):0;edgeSpeed=Math.max(-dp(14),Math.min(dp(14),edgeSpeed));handler.removeCallbacks(edgeScroll);updateDrop(dragX,dragY);if(edgeSpeed!=0)handler.post(edgeScroll);return true;
            case DragEvent.ACTION_DRAG_EXITED:edgeSpeed=0;handler.removeCallbacks(edgeScroll);dropGroup=null;newGroupIndex=-1;if(dropPreview!=null)dropPreview.invalidate();return true;
            case DragEvent.ACTION_DROP:edgeSpeed=0;handler.removeCallbacks(edgeScroll);updateDrop(event.getX(),event.getY());if(newGroupIndex>=0){if(!data.moveToNewGroup(dragging.id,newGroupIndex)){Toast.makeText(getContext(),"Start can contain up to 16 groups.",Toast.LENGTH_SHORT).show();return false;}pendingRender=true;selected=null;return true;}if(dropGroup!=null){data.move(dragging.id,dropGroup,dropIndex);pendingRender=true;selected=null;return true;}return false;
            case DragEvent.ACTION_DRAG_ENDED:finishDrag();return true;
            default:return true;
        }
    }
    private void finishDrag(){handler.removeCallbacks(edgeScroll);edgeSpeed=0;dragging=null;dropGroup=null;newGroupIndex=-1;if(dragSource!=null)dragSource.setAlpha(1);dragSource=null;if(dropPreview!=null)viewport.removeView(dropPreview);dropPreview=null;if(dragSpace!=null&&dragSpace.getParent() instanceof ViewGroup)((ViewGroup)dragSpace.getParent()).removeView(dragSpace);dragSpace=null;boolean refresh=pendingRender;pendingRender=false;if(refresh)render();else{updateSelection();renderCommands();}}
    private void updateDrop(float x,float y){
        if(dragging==null||dropPreview==null||dragGroups.isEmpty())return;
        newGroupIndex=-1;dropGroup=null;
        int[] origin=new int[2],point=new int[2];viewport.getLocationOnScreen(origin);TileGroup nearest=null;float best=Float.MAX_VALUE;
        // New columns/groups are real drop destinations, including the scrollable space after the last group.
        float previousRight=0;for(int n=0;n<dragGroups.size();n++){
            TileGroup candidate=dragGroups.get(n);candidate.grid.getLocationOnScreen(point);float groupLeft=point[0]-origin[0],groupTop=point[1]-origin[1],groupRight=groupLeft+candidate.grid.getWidth();
            if((n==0&&x<groupLeft-dp(10))||(n>0&&x>previousRight+dp(7)&&x<groupLeft-dp(7))){newGroupIndex=n;float center=n==0?groupLeft-dp(14):(previousRight+groupLeft)/2;dropPreview.target.set(center-dp(8),groupTop,center+dp(8),Math.min(viewport.getHeight()-dp(38),groupTop+candidate.grid.getHeight()));break;}
            if(n==dragGroups.size()-1&&x>groupRight+dp(10)){newGroupIndex=n+1;float center=groupRight+dp(20);dropPreview.target.set(center-dp(8),groupTop,center+dp(8),Math.min(viewport.getHeight()-dp(38),groupTop+candidate.grid.getHeight()));break;}
            previousRight=groupRight;
        }
        if(newGroupIndex>=0){dropPreview.label="New column";dropPreview.setContentDescription("Create new Start column");dropPreview.invalidate();return;}
        for(TileGroup group:dragGroups){group.grid.getLocationOnScreen(point);float left=point[0]-origin[0],distance=Math.max(left-x,Math.max(x-left-group.grid.getWidth(),0));if(distance<best){best=distance;nearest=group;}}
        if(nearest==null)return;nearest.grid.getLocationOnScreen(point);float left=point[0]-origin[0],top=point[1]-origin[1];dropGroup=nearest.group;dropIndex=0;int target=-1;boolean after=false;best=Float.MAX_VALUE;int index=0;
        for(int i=0;i<nearest.cells.size();i++){if(nearest.group.items.get(i).id.equals(dragging.id))continue;EightTileLayout.Cell cell=nearest.cells.get(i);float l=left+cell.col*nearest.unit,t=top+cell.row*nearest.unit,r=l+cell.w*nearest.unit,b=t+cell.h*nearest.unit;float dx=Math.max(l-x,Math.max(x-r,0)),dy=Math.max(t-y,Math.max(y-b,0));float distance=dx*dx+dy*dy;if(distance<best){best=distance;target=i;after=x>r||x>=l&&y>(t+b)/2;dropIndex=index+(after?1:0);}index++;}
        if(target<0){dropPreview.target.set(left,top,left+nearest.unit*2,top+nearest.unit*2);dropPreview.label="Move to "+nearest.group.name;}
        else{EightTileLayout.Cell cell=nearest.cells.get(target);float l=left+cell.col*nearest.unit,t=top+(cell.row+(after?cell.h:0))*nearest.unit;dropPreview.target.set(l,t-dp(2),l+cell.w*nearest.unit-dp(5),t+dp(2));dropPreview.label=(after?"After ":"Before ")+resolve(nearest.group.items.get(target).id).title+" · "+nearest.group.name;}
        dropPreview.setContentDescription(dropPreview.label);dropPreview.invalidate();
    }
    private WeatherClient.Snapshot cachedWeather(){try{return WeatherClient.Snapshot.parse(store.prefs.getString("weather_json",""));}catch(Exception ignored){return null;}}
    void weatherChanged(){String key=store.prefs.getString("weather_json","")+store.prefs.getString("weather_city","")+store.prefs.getBoolean("weather_f",false);if(key.equals(weatherKey))return;weatherKey=key;if(isShown())refreshLiveTiles();}
    private void moveSelected(int delta){for(EightStartData.Group group:data.groups){for(int i=0;i<group.items.size();i++)if(group.items.get(i).id.equals(selected)){data.move(selected,group,i+delta);render();return;}}}
    private void nameGroup(EightStartData.Group group){EditText input=new EditText(getContext());input.setSingleLine();input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});input.setText(group==null?"":group.name);android.app.AlertDialog dialog=Aero.dialog(getContext()).setTitle(group==null?"New group":"Name group").setView(input).setPositiveButton("Save",null).setNegativeButton("Cancel",null).create();dialog.setOnShowListener(d->dialog.getButton(-1).setOnClickListener(v->{if(data.name(group,input.getText().toString())){dialog.dismiss();render();}else input.setError("Choose a unique name (1–40 characters).");}));dialog.show();}
    private void groupOptions(EightStartData.Group group){Aero.dialog(getContext()).setTitle(group.name).setItems(new String[]{"Rename","Move earlier","Move later"},(d,n)->{if(n==0){nameGroup(group);return;}int at=data.groups.indexOf(group),to=Math.max(0,Math.min(data.groups.size()-1,at+(n==1?-1:1)));data.groups.remove(group);data.groups.add(to,group);data.save();render();}).show();}
    private void renderApps(LinearLayout parent){
        List<StartSearch.Result> values=builtins();for(AppCatalog.Entry app:all)values.add(new StartSearch.Result(app.id,app.label,"Installed apps","programs",app.icon,true));
        String order=store.prefs.getString("eight_apps_sort","Name");
        if(profile==EightProfile.EIGHT_POINT_ONE&&!order.equals("Name")){if(order.equals("Category"))values.sort(Comparator.comparing((StartSearch.Result i)->i.group).thenComparing(i->i.title.toLowerCase(Locale.ROOT)));else if(order.equals("Most used")){values.sort(Comparator.comparingInt((StartSearch.Result i)->-store.prefs.getInt("eight_launches_"+i.id,0)).thenComparing(i->i.title.toLowerCase(Locale.ROOT)));}else if(order.equals("Date installed")){Map<String,Long> dates=new HashMap<>();for(StartSearch.Result i:values)try{android.content.ComponentName component=android.content.ComponentName.unflattenFromString(i.id);dates.put(i.id,getContext().getPackageManager().getPackageInfo(component==null?getContext().getPackageName():component.getPackageName(),0).firstInstallTime);}catch(Exception ignored){}values.sort(Comparator.comparingLong(i->-dates.getOrDefault(i.id,0L)));}else if(order.equals("Recent")){List<String> recent=store.recents();values.sort(Comparator.comparingInt(i->{int at=recent.indexOf(i.id);return at<0?Integer.MAX_VALUE:at;}));}}
        else values.sort(Comparator.comparing(i->i.title.toLowerCase(Locale.ROOT)));int rows=Math.max(1,(lastHeight-dp(32))/dp(60));LinearLayout column=null;
        for(int i=0;i<values.size();i++){if(i%rows==0){column=Aero.column(getContext());LayoutParams lp=new LayoutParams(dp(230),-1);lp.rightMargin=dp(20);parent.addView(column,lp);}column.addView(appRow(values.get(i)),new LayoutParams(-1,dp(60)));}
    }
    private View appRow(StartSearch.Result item){LinearLayout row=new LinearLayout(getContext());row.setGravity(Gravity.CENTER_VERTICAL);row.setPadding(dp(6),0,dp(8),0);row.addView(icon(item,Color.WHITE),new LayoutParams(dp(32),dp(32)));TextView text=label(item.title,14);text.setMaxLines(2);text.setPadding(dp(10),0,0,0);row.addView(text,new LayoutParams(0,-2,1));row.setContentDescription("Open "+item.title);row.setFocusable(true);row.setBackground(selection(Color.TRANSPARENT));row.setOnClickListener(v->actions.open(item.id));if(item.shortcut)row.setOnLongClickListener(v->{selected=item.id;renderCommands();return true;});return row;}
    void showSearch(String scope){if(getVisibility()!=VISIBLE)show(true);this.scope=scope;surface=Surface.SEARCH;selected=null;render();search.setHint("Search "+scope.toLowerCase(Locale.ROOT));search.requestFocus();}
    private void dockSearch(){boolean right=surface==Surface.SEARCH&&getWidth()>=dp(600);ViewGroup parent=(ViewGroup)search.getParent();boolean move=right?parent!=searchSide:parent!=this,focused=search.hasFocus();int caret=search.getSelectionStart();if(move){parent.removeView(search);if(right)searchSide.addView(search,1,new LayoutParams(-1,dp(48)));else addView(search,1,new LayoutParams(-1,dp(48)));}searchSide.setVisibility(right?VISIBLE:GONE);searchSideScroll.setVisibility(right?VISIBLE:GONE);search.setVisibility(surface==Surface.SEARCH?VISIBLE:GONE);if(move&&focused){search.requestFocus();search.setSelection(Math.max(0,Math.min(caret,search.length())));post(()->{if(surface==Surface.SEARCH)search.requestFocus();});}}

    private void renderSearch(){
        firstSearchResult=null;
        LinearLayout content=Aero.column(getContext());content.setPadding(dp(24),dp(8),dp(20),0);viewport.addView(content,new FrameLayout.LayoutParams(-1,-1));
        boolean right=searchSide.getVisibility()==VISIBLE;LinearLayout filters=scopeChoices;if(filters.getParent() instanceof ViewGroup)((ViewGroup)filters.getParent()).removeView(filters);filters.removeAllViews();filters.setOrientation(right?VERTICAL:HORIZONTAL);if(right)searchSide.addView(filters,new LayoutParams(-1,-2));else{HorizontalScrollView scrollFilters=new HorizontalScrollView(getContext());scrollFilters.addView(filters);content.addView(scrollFilters,new LayoutParams(-1,dp(48)));}
        String[] scopes=profile==EightProfile.EIGHT?new String[]{"Apps","Settings","Files"}:new String[]{"Everywhere","Apps","Settings","Files"};
        for(String filter:scopes){TextView button=label(filter,14);button.setGravity(Gravity.CENTER);button.setPadding(dp(12),0,dp(12),0);button.setContentDescription("Search scope "+filter);button.setBackgroundColor(filter.equals(scope)?0xff604398:Color.TRANSPARENT);button.setFocusable(true);button.setOnClickListener(v->{scope=filter;renderSearchContent();});filters.addView(button,new LayoutParams(right?-1:-2,right?dp(48):-1));}
        ScrollView listScroll=new ScrollView(getContext());LinearLayout list=Aero.column(getContext());listScroll.addView(list);content.addView(listScroll,new LayoutParams(-1,0,1));
        String query=search.getText().toString().trim(),q=query.toLowerCase(Locale.ROOT);List<StartSearch.Result> results=new ArrayList<>();
        if(scope.equals("Apps")||scope.equals("Everywhere")){for(StartSearch.Result item:builtins())if(q.isEmpty()||item.title.toLowerCase(Locale.ROOT).contains(q))results.add(item);for(AppCatalog.Entry app:all)if(q.isEmpty()||app.label.toLowerCase(Locale.ROOT).contains(q))results.add(new StartSearch.Result(app.id,app.label,"Installed apps","programs",app.icon,true));}
        if(scope.equals("Settings")||scope.equals("Everywhere"))for(String[] entry:EightPCSettings.categories(store))if(q.isEmpty()||entry[1].toLowerCase(Locale.ROOT).contains(q))results.add(new StartSearch.Result("eight-setting:"+entry[0],entry[1],"PC settings","settings",null,false));
        if(scope.equals("Settings")||scope.equals("Everywhere"))for(EightSettingsCatalog.Page entry:EightSettingsCatalog.PAGES)if(q.isEmpty()||entry.title.toLowerCase(Locale.ROOT).contains(q))results.add(new StartSearch.Result("eight-desktop:"+entry.id,entry.title,"Control Panel","settings",null,false));
        if(scope.equals("Files")||scope.equals("Everywhere")){for(StartSearch.Result item:StartSearch.find(store,all,query))if(item.id.startsWith("search-file:")||item.id.startsWith("recent-file:")||item.group.equals("Folders"))results.add(item);if(query.isEmpty()){JSONArray files=LinkedFiles.list(store);for(int i=0;i<files.length();i++){JSONObject file=files.optJSONObject(i);if(file!=null)results.add(new StartSearch.Result("search-file:"+file.optString("uri"),file.optString("name"),"Linked files","documentfile",null,false));}}}
        Set<String> seen=new HashSet<>();for(StartSearch.Result item:results)if(seen.add(item.id)){if(firstSearchResult==null)firstSearchResult=item.id;list.addView(appRow(item),new LayoutParams(-1,dp(56)));}
        if(seen.isEmpty()){TextView empty=label(scope.equals("Files")?"No matching linked files. Link files in File Explorer to search them here.":"No matching results.",15);empty.setPadding(0,dp(20),0,0);list.addView(empty);}
    }
    private void renderSearchContent(){renderContent();}
    private List<StartSearch.Result> builtins(){List<StartSearch.Result> result=new ArrayList<>();for(String[] entry:new String[][]{{"@mail","Mail","mail"},{"@people","People","contacts"},{"@maps","Maps","network"},{"@paint","Paint","appearance"},{"@wordpad","WordPad","document"},{"@characters","Character Map","fonts"},{"@help","Help+Tips","information"},{"@readinglist","Reading List","document"},{"@reader","Reader","documentfile"},{"@alarms","Alarms","clock"},{"@soundrecorder","Sound Recorder","music"},{"@mobility","Windows Mobility Center","computer"},{"@sticky","Sticky Notes","notes"},{"@news","News","news"},{"@desktop","Desktop","desktop"},{"@nota","Nth Nota","notes"},{"@files","File Explorer","files"},{"@browser",BrowserPanel.NAME,"browser"},{"@calendar","Calendar","calendar"},{"@weather","Weather","weather"},{"@photos","Photos","pictures-app"},{MediaLibrary.PLAYER,MediaLibrary.PLAYER_TITLE,"mediaplayer"},{"@notes","Notepad","notes"},{"@gadget:calculator","Calculator","calculator"},{"@settings","PC settings","control"},{"@legacycontrol","Control Panel","control"},{"@taskmanager","Task Manager","taskmanager"},{"@recycle","Recycle Bin","recycle"}})result.add(new StartSearch.Result(entry[0],entry[1],"Nth tools",entry[2],null,true));if(store.prefs.getBoolean("eight_admin_tools",false))for(String[] entry:new String[][]{{"@management","Computer Management"},{"@events","Event Viewer"},{"@devices","Device Manager"}})result.add(new StartSearch.Result(entry[0],entry[1],"Administrative tools","computer",null,true));JSONObject sites=EightWebTiles.list(store);java.util.Iterator<String> webIds=sites.keys();while(webIds.hasNext()){String id=webIds.next();result.add(new StartSearch.Result(id,sites.optJSONObject(id).optString("title"),"Websites","browser",null,true));}return result;}
    private StartSearch.Result resolve(String id){if(EightWebTiles.validId(id)){JSONObject page=EightWebTiles.get(store,id);return new StartSearch.Result(id,page==null?"Unavailable website":page.optString("title"),"Websites","browser",null,page!=null);}for(StartSearch.Result item:builtins())if(item.id.equals(id))return item;for(AppCatalog.Entry app:all)if(app.id.equals(id))return new StartSearch.Result(id,app.label,"Installed apps","programs",app.icon,true);return new StartSearch.Result(id,"Unavailable app","Missing app","programs",null,false);}
    private View icon(StartSearch.Result item,int ink){if(item.icon!=null){ImageView image=new ImageView(getContext());image.setImageDrawable(item.icon);return image;}if(EightWebTiles.validId(item.id))return new EightTileGlyph(getContext(),"@browser");if(item.id.equals("@nota"))return new TenStyle.Glyph(getContext(),"nota",ink);if(item.id.equals("@paint")||item.id.equals("@wordpad"))return new AeroIcon(getContext(),item.id.substring(1));if(item.id.equals("@settings")||EightTools.utility(item.id)||EightAppSpec.get(item.id)!=null)return new EightTileGlyph(getContext(),item.id);return new AeroIcon(getContext(),item.kind);}
    private int color(String id){switch(id){case "@mail":return 0xff0078a4;case "@people":return 0xffd34c16;case "@maps":return 0xff70439a;case "@desktop":return 0xff007ca8;case "@nota":return 0xff6b3f98;case "@browser":return 0xff007db5;case "@weather":return 0xff006c90;case "@calendar":return 0xff64448e;case "@photos":return 0xffa12a75;case "@settings":return 0xff3a5c99;case "@notes":return 0xff9a4c08;default:int[] colors={0xff00885c,0xff824095,0xffad3f20,0xff2f5698};return colors[Math.floorMod(id.hashCode(),colors.length)];}}
    private String liveText(StartSearch.Result item){if(!liveTiles.enabled(item.id))return item.title;if(item.id.equals("@calendar"))return new java.text.SimpleDateFormat("EEEE d",Locale.getDefault()).format(new Date());if(item.id.equals("@weather")){WeatherClient.Snapshot forecast=cachedWeather();return store.prefs.getString("weather_city","Weather · Add a location")+(forecast==null?"":"\n"+forecast.condition());}return item.title;}
    void show(boolean keyboard){surface=keyboard?Surface.SEARCH:Surface.START;selected=null;if(keyboard)scope=profile==EightProfile.EIGHT?"Apps":"Everywhere";setVisibility(VISIBLE);bringToFront();appearanceChanged();search.setText("");requestFocus();if(keyboard)search.requestFocus();handler.removeCallbacks(live);handler.post(live);surfaceChanged.run();}
    void hide(){if(dragging!=null){cancelDragAndDrop();finishDrag();}handler.removeCallbacks(live);pauseLiveTiles();((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindowToken(),0);search.clearFocus();setVisibility(GONE);surfaceChanged.run();}
    boolean back(){if(selected!=null){selected=null;render();return true;}if(surface!=Surface.START){surface=Surface.START;search.setText("");render();return true;}return false;}
    JSONObject state(){JSONObject result=new JSONObject();GadgetLayout.put(result,"visible",getVisibility()==VISIBLE);GadgetLayout.put(result,"surface",surface.name());GadgetLayout.put(result,"scope",scope);GadgetLayout.put(result,"query",search.getText().toString());GadgetLayout.put(result,"scroll",horizontal==null?0:horizontal.getScrollX());return result;}
    void restore(JSONObject state){if(state==null||!state.optBoolean("visible")){hide();return;}show(false);try{surface=Surface.valueOf(state.optString("surface","START"));}catch(IllegalArgumentException ignored){surface=Surface.START;}scope=state.optString("scope","Apps");search.setText(state.optString("query"));render();post(()->{if(horizontal!=null)horizontal.scrollTo(state.optInt("scroll"),0);});}
    // Route native typing before focus transfer completes on older Android versions.
    boolean searchKey(KeyEvent event){
        if(event.getAction()==KeyEvent.ACTION_DOWN&&dragging==null&&!event.isCtrlPressed()&&!event.isAltPressed()&&!event.isMetaPressed()){
            int character=event.getUnicodeChar();
            if(event.isPrintingKey()&&character!=0&&(character&KeyCharacterMap.COMBINING_ACCENT)==0){
                if(surface==Surface.START||surface==Surface.ALL_APPS){showSearch(profile==EightProfile.EIGHT?"Apps":"Everywhere");search.setText(new String(Character.toChars(character)));search.setSelection(search.length());return true;}
                if(surface==Surface.SEARCH)return search.dispatchKeyEvent(event);
            }
        }
        return false;
    }
    @Override public boolean dispatchTouchEvent(MotionEvent event){
        int action=event.getActionMasked();if(action==MotionEvent.ACTION_DOWN){downX=event.getX();downY=event.getY();swipeCandidate=dragging==null&&(surface==Surface.START||surface==Surface.ALL_APPS)&&downY<getHeight()-dp(56);swiping=false;swipeToApps=false;}
        if(event.getPointerCount()>1)swipeCandidate=false;
        if(action==MotionEvent.ACTION_MOVE&&swipeCandidate&&dragging==null){float dx=event.getX()-downX,dy=event.getY()-downY;if(Math.abs(dx)>dp(28)&&Math.abs(dx)>Math.abs(dy))swipeCandidate=false;else if((dy>dp(48)||profile==EightProfile.EIGHT_POINT_ONE&&surface==Surface.START&&dy<-dp(48))&&Math.abs(dy)>Math.abs(dx)*1.5f){swipeToApps=dy<0;MotionEvent cancel=MotionEvent.obtain(event);cancel.setAction(MotionEvent.ACTION_CANCEL);super.dispatchTouchEvent(cancel);cancel.recycle();swiping=true;swipeCandidate=false;}}
        if(swiping){if(action==MotionEvent.ACTION_UP){swiping=false;if(swipeToApps)showApps();else actions.open("@desktop");}else if(action==MotionEvent.ACTION_CANCEL)swiping=false;return true;}
        zoom.onTouchEvent(event);return super.dispatchTouchEvent(event);
    }
    void foreground(boolean value){foreground=value;handler.removeCallbacks(live);if(value&&isShown())handler.post(live);else pauseLiveTiles();}
    private void pauseLiveTiles(){for(EightLiveTileView view:liveViews.values())view.pause();}
    void refreshLiveTiles(){
        if(!foreground||!isShown()||surface!=Surface.START||dragging!=null||selected!=null){pauseLiveTiles();return;}
        boolean motion=!store.prefs.getBoolean("reduce_motion",false)&&android.animation.ValueAnimator.areAnimatorsEnabled();long now=System.currentTimeMillis();
        for(Map.Entry<String,TextView> entry:smallBadges.entrySet()){EightTileSnapshot s=liveTiles.snapshot(entry.getKey(),now);entry.getValue().setText(s==null?"":s.badgeText());entry.getValue().setVisibility(s!=null&&!s.expired(now)&&s.badge>0?VISIBLE:GONE);}
        for(Map.Entry<String,EightLiveTileView> entry:liveViews.entrySet()){
            EightTileSnapshot snapshot=liveTiles.snapshot(entry.getKey(),now);EightLiveTileView view=entry.getValue();boolean valid=snapshot!=null&&!snapshot.expired(now);view.setVisibility(valid?VISIBLE:GONE);
            android.graphics.Rect visible=new android.graphics.Rect();if(valid)view.show(snapshot,motion?(int)(android.os.SystemClock.elapsedRealtime()/8000+Math.floorMod(entry.getKey().hashCode(),5)):0,motion&&view.getGlobalVisibleRect(visible));else view.pause();
        }
    }
    @Override protected void onDetachedFromWindow(){handler.removeCallbacksAndMessages(null);super.onDetachedFromWindow();}
}
