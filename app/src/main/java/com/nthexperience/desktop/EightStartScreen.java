package com.nthexperience.desktop;

import android.content.*;
import android.graphics.Color;
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
    final LinearLayout header,commands;
    final TextView heading,identity;
    final EditText search;
    final Handler handler=new Handler(Looper.getMainLooper());
    EightStartData data;
    EightProfile profile;
    Surface surface=Surface.START;
    String selected,scope="Apps";
    int lastWidth,lastHeight;
    HorizontalScrollView horizontal;
    final ScaleGestureDetector zoom;
    final Runnable live=new Runnable(){public void run(){if(isShown()){render();handler.postDelayed(this,60000-System.currentTimeMillis()%60000);}}};

    EightStartScreen(Context context,StartMenu.Actions actions,DesktopStore store,AeroWallpaper wallpaper,Runnable surfaceChanged){
        super(context);this.actions=actions;this.store=store;this.surfaceChanged=surfaceChanged;this.wallpaper=wallpaper;
        profile=EightDefaults.profile(store);data=new EightStartData(store,profile);
        setOrientation(VERTICAL);setFocusableInTouchMode(true);setElevation(dp(24));
        setContentDescription("Eight Start screen");TenAppearance.preserve(this);
        header=new LinearLayout(context);header.setGravity(Gravity.CENTER_VERTICAL);header.setPadding(dp(24),dp(8),dp(20),0);
        heading=label("Start",36);header.addView(heading,new LayoutParams(0,-1,1));
        identity=label("",14);identity.setGravity(Gravity.END|Gravity.CENTER_VERTICAL);identity.setMaxLines(2);identity.setFocusable(true);identity.setContentDescription("Open profile and shell settings");identity.setOnClickListener(v->actions.open("@settings"));header.addView(identity,new LayoutParams(dp(150),-1));
        addView(header,new LayoutParams(-1,dp(86)));
        search=new EditText(context);search.setSingleLine();search.setTextSize(17);search.setTextColor(Color.WHITE);search.setHintTextColor(0xffc7bfde);search.setBackgroundColor(0x30333333);search.setPadding(dp(20),0,dp(20),0);search.setHint("Search apps");search.setContentDescription("Eight search query");search.setVisibility(GONE);addView(search,new LayoutParams(-1,dp(48)));
        search.addTextChangedListener(new TextWatcher(){public void beforeTextChanged(CharSequence s,int a,int c,int n){}public void afterTextChanged(Editable e){}public void onTextChanged(CharSequence s,int a,int b,int c){if(surface==Surface.SEARCH)renderContent();}});
        viewport=new FrameLayout(context);addView(viewport,new LayoutParams(-1,0,1));
        HorizontalScrollView commandScroll=new HorizontalScrollView(context);commandScroll.setHorizontalScrollBarEnabled(false);commandScroll.setFillViewport(true);commandScroll.setBackgroundColor(0xff32165e);
        commands=new LinearLayout(context);commands.setGravity(Gravity.CENTER_VERTICAL);commands.setPadding(dp(12),0,dp(12),0);commandScroll.addView(commands,new HorizontalScrollView.LayoutParams(-2,-1));addView(commandScroll,new LayoutParams(-1,dp(56)));
        zoom=new ScaleGestureDetector(context,new ScaleGestureDetector.SimpleOnScaleGestureListener(){public boolean onScale(ScaleGestureDetector detector){if(detector.getScaleFactor()<.92f&&surface==Surface.START){surface=Surface.OVERVIEW;selected=null;post(()->render());return true;}return false;}});
        viewport.addOnLayoutChangeListener((v,l,t,r,b,ol,ot,or,ob)->{if(r-l!=lastWidth||b-t!=lastHeight){lastWidth=r-l;lastHeight=b-t;renderContent();}});
        render();
    }
    private int dp(int value){return Aero.dp(getContext(),value);}
    private TextView label(String text,int size){TextView view=Aero.text(getContext(),text,size,Color.WHITE);view.setTypeface(Aero.face(getContext()),Typeface.NORMAL);return view;}
    private TextView command(String text,String description,Runnable action){TextView view=label(text,14);view.setGravity(Gravity.CENTER);view.setPadding(dp(16),0,dp(16),0);view.setMinHeight(dp(48));view.setFocusable(true);view.setContentDescription(description);view.setBackground(TenStyle.hover(getContext(),0xff32165e));view.setOnClickListener(v->action.run());commands.addView(view,new LayoutParams(-2,-1));return view;}
    void entries(List<AppCatalog.Entry> values){all.clear();all.addAll(values);if(isShown())renderContent();}
    void refreshName(){identity.setText(store.prefs.getString("desktop_name","Nth Experience")+"\n"+profile.label);}
    void refreshHome(){if(isShown())appearanceChanged();}
    void appearanceChanged(){EightProfile next=EightDefaults.profile(store);if(next!=profile){profile=next;data=new EightStartData(store,profile);selected=null;scope=profile==EightProfile.EIGHT?"Apps":"Everywhere";}render();}
    private void render(){
        refreshName();boolean wallpaper=profile==EightProfile.EIGHT_POINT_ONE&&store.prefs.getBoolean("eight_start_wallpaper",false);
        setBackground(new EightBackdrop(store.prefs.getInt("eight_start_color",0xff21164f),wallpaper?this.wallpaper:null));
        heading.setText(surface==Surface.ALL_APPS?"Apps":surface==Surface.SEARCH?"Search":surface==Surface.OVERVIEW?"Groups":"Start");
        search.setVisibility(surface==Surface.SEARCH?VISIBLE:GONE);renderContent();renderCommands();
    }
    private void renderCommands(){
        commands.removeAllViews();
        if(selected!=null){
            EightStartData.Item item=data.item(selected);
            command(item==null?"Pin to Start":"Unpin from Start",item==null?"Pin selected app to Start":"Unpin selected tile",()->{if(item==null){if(!data.pin(selected))Toast.makeText(getContext(),"This app cannot be pinned.",Toast.LENGTH_SHORT).show();}else data.unpin(selected);selected=null;render();});
            if(item!=null){command("Resize","Resize selected tile",()->Aero.dialog(getContext()).setTitle("Tile size").setItems(profile.sizes(),(d,n)->{item.size=profile.sizes()[n];data.save();render();}).show());
                command("Move earlier","Move selected tile earlier",()->moveSelected(-1));command("Move later","Move selected tile later",()->moveSelected(1));
                command("Move to group","Move selected tile to group",()->Aero.dialog(getContext()).setTitle("Move to group").setItems(data.groups.stream().map(g->g.name).toArray(String[]::new),(d,n)->{EightStartData.Group group=data.groups.get(n);data.move(selected,group,group.items.size());render();}).show());}
            command("Done","Finish tile selection",()->{selected=null;render();});return;
        }
        command(surface==Surface.START?(profile==EightProfile.EIGHT?"All apps":"↓  Apps"):"←  Start","Toggle All apps",()->{surface=surface==Surface.START?Surface.ALL_APPS:Surface.START;render();});
        if(surface==Surface.START||surface==Surface.OVERVIEW){command(surface==Surface.OVERVIEW?"Tiles":"Groups","Toggle group overview",()->{surface=surface==Surface.OVERVIEW?Surface.START:Surface.OVERVIEW;render();});if(surface==Surface.OVERVIEW)command("+ Group","Add tile group",()->nameGroup(null));}
        if(surface==Surface.ALL_APPS&&profile==EightProfile.EIGHT_POINT_ONE)command("Sort apps","Sort Apps view",()->Aero.dialog(getContext()).setTitle("Sort apps").setItems(new String[]{"Name","Category","Recent"},(d,n)->{store.prefs.edit().putString("eight_apps_sort",new String[]{"Name","Category","Recent"}[n]).apply();render();}).show());
        command("Search","Search charm",()->showSearch(profile==EightProfile.EIGHT?"Apps":"Everywhere"));
        command("Charms","Open Charms",()->actions.open("@charms"));command("Switch apps","Open app switcher",()->actions.open("@eight-switcher"));
    }
    private void renderContent(){
        int scroll=horizontal==null?0:horizontal.getScrollX();viewport.removeAllViews();
        if(surface==Surface.SEARCH){horizontal=null;renderSearch();return;}
        horizontal=new HorizontalScrollView(getContext());horizontal.setFillViewport(false);horizontal.setHorizontalScrollBarEnabled(true);horizontal.setContentDescription(surface==Surface.ALL_APPS?"All apps horizontal view":"Horizontal Start groups");
        LinearLayout groups=new LinearLayout(getContext());groups.setPadding(dp(24),dp(8),dp(24),dp(20));horizontal.addView(groups,new HorizontalScrollView.LayoutParams(-2,-1));viewport.addView(horizontal,new FrameLayout.LayoutParams(-1,-1));
        if(surface==Surface.ALL_APPS)renderApps(groups);else for(EightStartData.Group group:data.groups)renderGroup(groups,group,surface==Surface.OVERVIEW);
        horizontal.post(()->{if(horizontal!=null)horizontal.scrollTo(scroll,0);});
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
            tile.setOnDragListener((v,e)->{if(!(e.getLocalState() instanceof TileDrag))return false;if(e.getAction()==DragEvent.ACTION_DROP){data.move(((TileDrag)e.getLocalState()).id,group,group.items.indexOf(item));selected=null;post(this::render);}return true;});
        }
        block.addView(grid,new LayoutParams(overview?Math.max(dp(110),width):width,rows*unit));
        block.setOnDragListener((v,e)->{if(!(e.getLocalState() instanceof TileDrag))return false;if(e.getAction()==DragEvent.ACTION_DROP){data.move(((TileDrag)e.getLocalState()).id,group,group.items.size());selected=null;post(this::render);}return true;});
        if(overview){title.setOnClickListener(v->{surface=Surface.START;render();int index=data.groups.indexOf(group);post(()->{if(horizontal!=null&&horizontal.getChildCount()>0){LinearLayout content=(LinearLayout)horizontal.getChildAt(0);if(index<content.getChildCount())horizontal.smoothScrollTo(content.getChildAt(index).getLeft(),0);}});});title.setOnLongClickListener(v->{groupOptions(group);return true;});}
    }
    private View tile(StartSearch.Result item,String size,boolean overview){
        FrameLayout tile=new FrameLayout(getContext());int color=color(item.id);android.graphics.drawable.GradientDrawable fill=new android.graphics.drawable.GradientDrawable();fill.setColor(color);if(item.id.equals(selected))fill.setStroke(dp(3),Color.WHITE);tile.setBackground(item.id.equals("@desktop")&&!item.id.equals(selected)?new EightBackdrop(0xff174266,wallpaper):fill);tile.setFocusable(true);tile.setContentDescription(item.title+", "+size+" Start tile");
        int iconSize=dp(overview?20:size.equals("Small")?22:38);FrameLayout.LayoutParams iconParams=new FrameLayout.LayoutParams(iconSize,iconSize,Gravity.CENTER);if(!overview&&!size.equals("Small"))iconParams.bottomMargin=dp(16);if(!item.id.equals("@desktop"))tile.addView(icon(item,Color.WHITE),iconParams);
        if(!size.equals("Small")&&!overview){TextView text=label(liveText(item),13);text.setMaxLines(2);text.setPadding(dp(9),0,dp(6),dp(6));tile.addView(text,new FrameLayout.LayoutParams(-1,-2,Gravity.BOTTOM));}
        tile.setOnClickListener(v->{if(overview){surface=Surface.START;render();}else if(selected!=null){selected=selected.equals(item.id)?null:item.id;render();}else actions.open(item.id);});
        tile.setOnContextClickListener(v->{selected=item.id;render();return true;});
        tile.setOnLongClickListener(v->{if(item.id.equals(selected)){tile.startDragAndDrop(ClipData.newPlainText("Eight tile",item.id),new View.DragShadowBuilder(tile),new TileDrag(item.id),0);}else{selected=item.id;render();}return true;});
        tile.setOnKeyListener((v,key,event)->{if(event.getAction()==KeyEvent.ACTION_DOWN&&event.isCtrlPressed()&&(key==KeyEvent.KEYCODE_DPAD_LEFT||key==KeyEvent.KEYCODE_DPAD_RIGHT)){selected=item.id;moveSelected(key==KeyEvent.KEYCODE_DPAD_LEFT?-1:1);return true;}return false;});
        tile.setAccessibilityDelegate(new View.AccessibilityDelegate(){public void onInitializeAccessibilityNodeInfo(View v,AccessibilityNodeInfo node){super.onInitializeAccessibilityNodeInfo(v,node);node.addAction(new AccessibilityNodeInfo.AccessibilityAction(0x01020051,"Move tile earlier"));node.addAction(new AccessibilityNodeInfo.AccessibilityAction(0x01020052,"Move tile later"));}public boolean performAccessibilityAction(View v,int action,Bundle args){if(action==0x01020051||action==0x01020052){selected=item.id;moveSelected(action==0x01020051?-1:1);return true;}return super.performAccessibilityAction(v,action,args);}});
        return tile;
    }
    private static final class TileDrag{final String id;TileDrag(String id){this.id=id;}}
    private void moveSelected(int delta){for(EightStartData.Group group:data.groups){for(int i=0;i<group.items.size();i++)if(group.items.get(i).id.equals(selected)){data.move(selected,group,i+delta);render();return;}}}
    private void nameGroup(EightStartData.Group group){EditText input=new EditText(getContext());input.setSingleLine();input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});input.setText(group==null?"":group.name);android.app.AlertDialog dialog=Aero.dialog(getContext()).setTitle(group==null?"New group":"Name group").setView(input).setPositiveButton("Save",null).setNegativeButton("Cancel",null).create();dialog.setOnShowListener(d->dialog.getButton(-1).setOnClickListener(v->{if(data.name(group,input.getText().toString())){dialog.dismiss();render();}else input.setError("Choose a unique name (1–40 characters).");}));dialog.show();}
    private void groupOptions(EightStartData.Group group){Aero.dialog(getContext()).setTitle(group.name).setItems(new String[]{"Rename","Move earlier","Move later"},(d,n)->{if(n==0){nameGroup(group);return;}int at=data.groups.indexOf(group),to=Math.max(0,Math.min(data.groups.size()-1,at+(n==1?-1:1)));data.groups.remove(group);data.groups.add(to,group);data.save();render();}).show();}
    private void renderApps(LinearLayout parent){
        List<StartSearch.Result> values=builtins();for(AppCatalog.Entry app:all)values.add(new StartSearch.Result(app.id,app.label,"Installed apps","programs",app.icon,true));
        String order=store.prefs.getString("eight_apps_sort","Name");
        if(profile==EightProfile.EIGHT_POINT_ONE&&!order.equals("Name")){if(order.equals("Category"))values.sort(Comparator.comparing((StartSearch.Result i)->i.group).thenComparing(i->i.title.toLowerCase(Locale.ROOT)));else if(order.equals("Recent")){List<String> recent=store.recents();values.sort(Comparator.comparingInt(i->{int at=recent.indexOf(i.id);return at<0?Integer.MAX_VALUE:at;}));}}
        else values.sort(Comparator.comparing(i->i.title.toLowerCase(Locale.ROOT)));int rows=Math.max(1,(lastHeight-dp(32))/dp(60));LinearLayout column=null;
        for(int i=0;i<values.size();i++){if(i%rows==0){column=Aero.column(getContext());LayoutParams lp=new LayoutParams(dp(230),-1);lp.rightMargin=dp(20);parent.addView(column,lp);}column.addView(appRow(values.get(i)),new LayoutParams(-1,dp(60)));}
    }
    private View appRow(StartSearch.Result item){LinearLayout row=new LinearLayout(getContext());row.setGravity(Gravity.CENTER_VERTICAL);row.setPadding(dp(6),0,dp(8),0);row.addView(icon(item,Color.WHITE),new LayoutParams(dp(32),dp(32)));TextView text=label(item.title,14);text.setMaxLines(2);text.setPadding(dp(10),0,0,0);row.addView(text,new LayoutParams(0,-2,1));row.setContentDescription("Open "+item.title);row.setFocusable(true);row.setBackground(TenStyle.hover(getContext(),Color.TRANSPARENT));row.setOnClickListener(v->actions.open(item.id));if(item.shortcut)row.setOnLongClickListener(v->{selected=item.id;renderCommands();return true;});return row;}
    void showSearch(String scope){this.scope=scope;surface=Surface.SEARCH;selected=null;if(getVisibility()!=VISIBLE)show(true);else render();search.setHint("Search "+scope.toLowerCase(Locale.ROOT));search.requestFocus();}
    private void renderSearch(){
        LinearLayout content=Aero.column(getContext());content.setPadding(dp(24),dp(8),dp(20),0);viewport.addView(content,new FrameLayout.LayoutParams(-1,-1));
        LinearLayout filters=new LinearLayout(getContext());HorizontalScrollView scrollFilters=new HorizontalScrollView(getContext());scrollFilters.addView(filters);content.addView(scrollFilters,new LayoutParams(-1,dp(48)));
        String[] scopes=profile==EightProfile.EIGHT?new String[]{"Apps","Settings","Files"}:new String[]{"Everywhere","Apps","Settings","Files"};
        for(String filter:scopes){TextView button=label(filter,14);button.setGravity(Gravity.CENTER);button.setPadding(dp(12),0,dp(12),0);button.setContentDescription("Search scope "+filter);button.setBackgroundColor(filter.equals(scope)?0xff604398:Color.TRANSPARENT);button.setFocusable(true);button.setOnClickListener(v->{scope=filter;renderSearchContent();});filters.addView(button,new LayoutParams(-2,-1));}
        ScrollView listScroll=new ScrollView(getContext());LinearLayout list=Aero.column(getContext());listScroll.addView(list);content.addView(listScroll,new LayoutParams(-1,0,1));
        String query=search.getText().toString().trim(),q=query.toLowerCase(Locale.ROOT);List<StartSearch.Result> results=new ArrayList<>();
        if(scope.equals("Apps")||scope.equals("Everywhere")){for(StartSearch.Result item:builtins())if(q.isEmpty()||item.title.toLowerCase(Locale.ROOT).contains(q))results.add(item);for(AppCatalog.Entry app:all)if(q.isEmpty()||app.label.toLowerCase(Locale.ROOT).contains(q))results.add(new StartSearch.Result(app.id,app.label,"Installed apps","programs",app.icon,true));}
        if(scope.equals("Settings")||scope.equals("Everywhere"))for(String[] entry:EightPCSettings.categories(store))if(q.isEmpty()||entry[1].toLowerCase(Locale.ROOT).contains(q))results.add(new StartSearch.Result("eight-setting:"+entry[0],entry[1],"PC settings","settings",null,false));
        if(scope.equals("Files")||scope.equals("Everywhere")){for(StartSearch.Result item:StartSearch.find(store,all,query))if(item.id.startsWith("search-file:")||item.id.startsWith("recent-file:")||item.group.equals("Folders"))results.add(item);if(query.isEmpty()){JSONArray files=LinkedFiles.list(store);for(int i=0;i<files.length();i++){JSONObject file=files.optJSONObject(i);if(file!=null)results.add(new StartSearch.Result("search-file:"+file.optString("uri"),file.optString("name"),"Linked files","documentfile",null,false));}}}
        Set<String> seen=new HashSet<>();for(StartSearch.Result item:results)if(seen.add(item.id))list.addView(appRow(item),new LayoutParams(-1,dp(56)));
        if(seen.isEmpty()){TextView empty=label(scope.equals("Files")?"No matching linked files. Link files in File Explorer to search them here.":"No matching results.",15);empty.setPadding(0,dp(20),0,0);list.addView(empty);}
    }
    private void renderSearchContent(){renderContent();}
    private List<StartSearch.Result> builtins(){List<StartSearch.Result> result=new ArrayList<>();for(String[] entry:new String[][]{{"@desktop","Desktop","desktop"},{"@nota","Nth Nota","notes"},{"@files","File Explorer","files"},{"@browser",BrowserPanel.NAME,"browser"},{"@calendar","Calendar","calendar"},{"@weather","Weather","weather"},{"@photos","Photos","pictures-app"},{MediaLibrary.PLAYER,MediaLibrary.PLAYER_TITLE,"mediaplayer"},{"@notes","Notepad","notes"},{"@gadget:calculator","Calculator","calculator"},{"@settings","PC settings","control"},{"@legacycontrol","Control Panel","control"},{"@taskmanager","Task Manager","taskmanager"},{"@recycle","Recycle Bin","recycle"}})result.add(new StartSearch.Result(entry[0],entry[1],"Nth tools",entry[2],null,true));return result;}
    private StartSearch.Result resolve(String id){for(StartSearch.Result item:builtins())if(item.id.equals(id))return item;for(AppCatalog.Entry app:all)if(app.id.equals(id))return new StartSearch.Result(id,app.label,"Installed apps","programs",app.icon,true);return new StartSearch.Result(id,"Unavailable app","Missing app","programs",null,false);}
    private View icon(StartSearch.Result item,int ink){if(item.icon!=null){ImageView image=new ImageView(getContext());image.setImageDrawable(item.icon);return image;}if(item.id.equals("@nota"))return new TenStyle.Glyph(getContext(),"nota",ink);if(EightAppSpec.get(item.id)!=null)return new EightTileGlyph(getContext(),item.id);return new AeroIcon(getContext(),item.kind);}
    private int color(String id){switch(id){case "@desktop":return 0xff007ca8;case "@nota":return 0xff6b3f98;case "@browser":return 0xff007db5;case "@weather":return 0xff006c90;case "@calendar":return 0xff64448e;case "@photos":return 0xffa12a75;case "@settings":return 0xff3a5c99;case "@notes":return 0xff9a4c08;default:int[] colors={0xff00885c,0xff824095,0xffad3f20,0xff2f5698};return colors[Math.floorMod(id.hashCode(),colors.length)];}}
    private String liveText(StartSearch.Result item){if(!store.prefs.getBoolean("eight_live_tiles",true))return item.title;if(item.id.equals("@calendar"))return new java.text.SimpleDateFormat("EEEE d",Locale.getDefault()).format(new Date());if(item.id.equals("@weather"))return "Weather · "+store.prefs.getString("weather_city","Choose a place");return item.title;}
    void show(boolean keyboard){surface=keyboard?Surface.SEARCH:Surface.START;selected=null;if(keyboard)scope=profile==EightProfile.EIGHT?"Apps":"Everywhere";setVisibility(VISIBLE);bringToFront();appearanceChanged();search.setText("");requestFocus();if(keyboard)search.requestFocus();handler.removeCallbacks(live);handler.postDelayed(live,60000-System.currentTimeMillis()%60000);surfaceChanged.run();}
    void hide(){handler.removeCallbacks(live);((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindowToken(),0);search.clearFocus();setVisibility(GONE);surfaceChanged.run();}
    boolean back(){if(selected!=null){selected=null;render();return true;}if(surface!=Surface.START){surface=Surface.START;search.setText("");render();return true;}return false;}
    JSONObject state(){JSONObject result=new JSONObject();GadgetLayout.put(result,"visible",getVisibility()==VISIBLE);GadgetLayout.put(result,"surface",surface.name());GadgetLayout.put(result,"scope",scope);GadgetLayout.put(result,"query",search.getText().toString());GadgetLayout.put(result,"scroll",horizontal==null?0:horizontal.getScrollX());return result;}
    void restore(JSONObject state){if(state==null||!state.optBoolean("visible")){hide();return;}show(false);try{surface=Surface.valueOf(state.optString("surface","START"));}catch(IllegalArgumentException ignored){surface=Surface.START;}scope=state.optString("scope","Apps");search.setText(state.optString("query"));render();post(()->{if(horizontal!=null)horizontal.scrollTo(state.optInt("scroll"),0);});}
    @Override public boolean dispatchTouchEvent(MotionEvent event){zoom.onTouchEvent(event);return super.dispatchTouchEvent(event);}
    @Override protected void onDetachedFromWindow(){handler.removeCallbacksAndMessages(null);super.onDetachedFromWindow();}
}
