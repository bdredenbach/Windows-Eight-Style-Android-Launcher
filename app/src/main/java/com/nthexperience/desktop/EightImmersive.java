package com.nthexperience.desktop;

import android.app.AlertDialog;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import org.json.*;

/** Hosts the same owned app bodies as Desktop. A presentation transfer is never an app close. */
@androidx.media3.common.util.UnstableApi
final class EightImmersive extends FrameLayout {
    interface Host {DesktopWindow prepare(String id);void front();void desktop();void switcher();void changed();void share();}
    static final class Session {
        final EightAppSpec spec;final DesktopWindow window;final ViewGroup desktopParent;final ViewGroup.LayoutParams desktopParams;
        final FrameLayout pane;Bitmap preview;boolean transferring;
        Session(EightAppSpec spec,DesktopWindow window,FrameLayout pane){this.spec=spec;this.window=window;this.pane=pane;desktopParent=(ViewGroup)window.body.getParent();desktopParams=window.body.getLayoutParams();}
    }
    final DesktopStore store;final Host host;
    final LinkedHashMap<String,Session> sessions=new LinkedHashMap<>();
    final List<String> recent=new ArrayList<>();
    final FrameLayout content;final LinearLayout commands,caption;final TextView gestureHint,commandHandle;
    final HorizontalScrollView commandScroll;final View divider;
    String primary,secondary,focused;float ratio=.5f;boolean appBar,foreground=true;
    private int priorWidth,priorHeight;private float gestureX,gestureY;private boolean edgeTracking,edgeCaptured;

    EightImmersive(Context context,DesktopStore store,Host host){
        super(context);this.store=store;this.host=host;setVisibility(GONE);setElevation(Aero.dp(context,26));setBackgroundColor(0xff171717);setContentDescription("Eight immersive apps");TenAppearance.preserve(this);
        content=new FrameLayout(context);addView(content,new FrameLayout.LayoutParams(-1,-1));
        divider=new View(context);divider.setBackgroundColor(0xff444444);divider.setContentDescription("Resize snapped apps");divider.setOnTouchListener((v,event)->{if(profile()==EightProfile.EIGHT||secondary==null)return false;if(event.getActionMasked()==MotionEvent.ACTION_MOVE){int[] origin=new int[2];getLocationOnScreen(origin);ratio=(event.getRawX()-origin[0])/Math.max(1,getWidth());layoutPanes();return true;}return true;});addView(divider,new FrameLayout.LayoutParams(dp(8),-1));
        commandScroll=new HorizontalScrollView(context);commandScroll.setHorizontalScrollBarEnabled(false);commandScroll.setFillViewport(true);commandScroll.setBackgroundColor(0xff252525);commandScroll.setElevation(dp(8));commands=new LinearLayout(context);commands.setGravity(Gravity.CENTER_VERTICAL);commandScroll.addView(commands,new HorizontalScrollView.LayoutParams(-2,-1));addView(commandScroll,new FrameLayout.LayoutParams(-1,dp(56),Gravity.BOTTOM));
        TextView handle=button("⋯","App commands",()->commands(!appBar));commandHandle=handle;handle.setBackgroundColor(0xaa222222);handle.setElevation(dp(9));FrameLayout.LayoutParams handleBounds=new FrameLayout.LayoutParams(dp(48),dp(44),Gravity.BOTTOM|Gravity.END);handleBounds.rightMargin=dp(32);addView(handle,handleBounds);
        caption=new LinearLayout(context);caption.setGravity(Gravity.CENTER_VERTICAL);caption.setBackgroundColor(0xff222222);caption.setElevation(dp(10));addView(caption,new FrameLayout.LayoutParams(-1,dp(42),Gravity.TOP));caption.setVisibility(GONE);
        gestureHint=button("Release near the bottom to close","Close drag progress",()->{});gestureHint.setBackgroundColor(0xee252525);gestureHint.setVisibility(GONE);gestureHint.setElevation(dp(12));addView(gestureHint,new FrameLayout.LayoutParams(-2,dp(54),Gravity.CENTER));
        addOnLayoutChangeListener((v,l,t,r,b,ol,ot,or,ob)->{if(priorWidth!=r-l||priorHeight!=b-t){priorWidth=r-l;priorHeight=b-t;layoutPanes();}});
    }
    private int dp(int value){return Aero.dp(getContext(),value);}
    private EightProfile profile(){return EightDefaults.profile(store);}
    boolean owns(String id){return sessions.containsKey(id);}
    boolean visible(){return getVisibility()==VISIBLE;}
    String title(){Session session=sessions.get(focused==null?primary:focused);return session==null?"Apps":session.spec.title;}
    View currentBody(){Session session=sessions.get(focused==null?primary:focused);return session==null?null:session.window.body;}
    boolean active(String id){return visible()&&(id.equals(primary)||id.equals(secondary)&&splitFits());}
    List<Session> ordered(){List<Session> result=new ArrayList<>();for(String id:recent){Session session=sessions.get(id);if(session!=null)result.add(session);}return result;}
    private void touch(String id){recent.remove(id);recent.add(0,id);focused=id;}
    private Session obtain(String id){
        Session existing=sessions.get(id);if(existing!=null)return existing;EightAppSpec spec=EightAppSpec.get(id);if(spec==null)return null;
        DesktopWindow window=host.prepare(id);if(window==null||window.getParent()==null||window.body.getParent()==null)return null;
        FrameLayout pane=new FrameLayout(getContext());pane.setContentDescription(spec.title+" immersive pane");pane.setBackgroundColor(0xfff7f7f7);content.addView(pane,new FrameLayout.LayoutParams(-1,-1));
        Session session=new Session(spec,window,pane);sessions.put(id,session);begin(window.body);session.transferring=true;
        session.desktopParent.removeView(window.body);window.setVisibility(GONE);pane.addView(window.body,new FrameLayout.LayoutParams(-1,-1));presentation(window.body,true);return session;
    }
    void show(String id){
        captureVisible();host.front();setVisibility(VISIBLE);Session session=obtain(id);if(session==null){hideSurface();return;}
        if(id.equals(secondary)){String old=primary;primary=id;secondary=old;}else if(!id.equals(primary)){primary=id;secondary=null;}
        touch(id);commands(false);render();
    }
    void resumeLast(){if(primary!=null&&sessions.containsKey(primary)){host.front();setVisibility(VISIBLE);render();}else host.desktop();}
    void hideSurface(){captureVisible();setVisibility(GONE);caption.setVisibility(GONE);updateActivity();host.changed();}
    void commands(boolean visible){appBar=visible;commandHandle.setVisibility(visible?GONE:VISIBLE);commandScroll.setVisibility(visible?VISIBLE:GONE);for(Session session:sessions.values())if(session.window.body instanceof BrowserPanel)((BrowserPanel)session.window.body).immersiveCommands(visible,profile());for(Session session:sessions.values()){if(session.window.body instanceof WeatherPanel)((WeatherPanel)session.window.body).immersive(true,visible);if(session.window.body instanceof PhotoGallery)((PhotoGallery)session.window.body).immersive(true,visible);}layoutPanes();}
    private void render(){
        commands.removeAllViews();command("Hide commands",()->commands(false));command("Switch apps",host::switcher);command("Open on Desktop",()->{dock(focused==null?primary:focused,true);host.desktop();});
        command("Snap another app",this::chooseSnap);if(secondary!=null){command("Swap sides",()->{String old=primary;primary=secondary;secondary=old;focused=primary;render();});command("One app",()->{secondary=null;render();});}
        command("Share",host::share);command("Close app",()->close(focused==null?primary:focused));
        caption.removeAllViews();TextView title=button(title(),"Immersive app title",()->{});caption.addView(title,new LinearLayout.LayoutParams(0,-1,1));caption.addView(button("—","Minimize immersive app",this::hideToDesktop),new LinearLayout.LayoutParams(dp(48),-1));caption.addView(button("×","Close immersive app",()->close(focused==null?primary:focused)),new LinearLayout.LayoutParams(dp(48),-1));
        layoutPanes();host.changed();
    }
    private TextView button(String text,String description,Runnable action){TextView view=Aero.text(getContext(),text,14,Color.WHITE);view.setGravity(Gravity.CENTER);view.setPadding(dp(14),0,dp(14),0);view.setFocusable(true);view.setContentDescription(description);view.setOnClickListener(v->action.run());view.setBackground(TenStyle.hover(getContext(),0xff252525));return view;}
    private void command(String label,Runnable action){commands.addView(button(label,label,action),new LinearLayout.LayoutParams(-2,-1));}
    void hideToDesktop(){hideSurface();host.desktop();}
    boolean splitFits(){Session a=sessions.get(primary),b=sessions.get(secondary);return EightSnapPolicy.fits(profile(),Math.round(getWidth()/getResources().getDisplayMetrics().density),a==null?null:a.spec,b==null?null:b.spec);}
    private void layoutPanes(){
        if(getWidth()<1)return;int width=getWidth(),height=Math.max(1,getHeight()-(appBar?dp(56):dp(44)));boolean split=secondary!=null&&splitFits();
        Session a=sessions.get(primary),b=sessions.get(secondary);int left=width;
        if(split)left=Math.round(EightSnapPolicy.split(profile(),Math.round(width/getResources().getDisplayMetrics().density),ratio,a.spec,b.spec)*getResources().getDisplayMetrics().density);
        for(Session session:sessions.values()){
            boolean first=session.spec.id.equals(primary),second=split&&session.spec.id.equals(secondary);session.pane.setVisibility(first||second?VISIBLE:GONE);
            FrameLayout.LayoutParams bounds=new FrameLayout.LayoutParams(first?left:Math.max(1,width-left-dp(8)),height);bounds.leftMargin=first?0:left+dp(8);session.pane.setLayoutParams(bounds);
            session.pane.setOnTouchListener((v,event)->{if(event.getActionMasked()==MotionEvent.ACTION_DOWN)focused=session.spec.id;return false;});
            if(session.transferring){session.transferring=false;end(session.window.body);}
        }
        FrameLayout.LayoutParams dividerBounds=new FrameLayout.LayoutParams(dp(8),height);dividerBounds.leftMargin=left;divider.setLayoutParams(dividerBounds);divider.setVisibility(split?VISIBLE:GONE);updateActivity();
    }
    private void chooseSnap(){
        List<EightAppSpec> options=new ArrayList<>();Session first=sessions.get(primary);int width=Math.round(getWidth()/getResources().getDisplayMetrics().density);
        for(EightAppSpec spec:EightAppSpec.ALL)if(first!=null&&EightSnapPolicy.fits(profile(),width,first.spec,spec))options.add(spec);
        if(options.isEmpty()){Toast.makeText(getContext(),"Rotate your phone or use a wider screen to show two apps. Switch apps is available at any size.",Toast.LENGTH_LONG).show();return;}
        Aero.dialog(getContext()).setTitle("Snap another app").setItems(options.stream().map(s->s.title).toArray(String[]::new),(d,n)->snap(options.get(n).id)).show();
    }
    boolean snap(String id){Session first=sessions.get(primary);EightAppSpec other=EightAppSpec.get(id);int width=Math.round(getWidth()/getResources().getDisplayMetrics().density);if(first==null||!EightSnapPolicy.fits(profile(),width,first.spec,other))return false;Session session=obtain(id);if(session==null)return false;secondary=id;focused=primary;ratio=.5f;render();return true;}
    void dock(String id,boolean restore){
        Session session=sessions.remove(id);if(session==null)return;recent.remove(id);View body=session.window.body;begin(body);
        try{session.pane.removeView(body);presentation(body,false);session.desktopParent.addView(body,session.desktopParams);content.removeView(session.pane);if(restore)session.window.restore();}finally{end(body);}
        if(session.preview!=null)session.preview.recycle();
        if(id.equals(primary)){primary=secondary;secondary=null;}else if(id.equals(secondary))secondary=null;
        if(primary==null&&!recent.isEmpty())primary=recent.get(0);focused=primary;if(primary==null)setVisibility(GONE);else render();host.changed();
    }
    void close(String id){Session session=sessions.get(id);if(session==null)return;boolean shown=visible();dock(id,false);session.window.closeWindow();if(primary==null){if(shown)host.desktop();}else{setVisibility(shown?VISIBLE:GONE);render();}}
    private void updateActivity(){for(Session session:sessions.values()){boolean active=foreground&&visible()&&session.pane.getVisibility()==VISIBLE;View body=session.window.body;if(body instanceof BrowserPanel)((BrowserPanel)body).active(active);if(body instanceof TenNotaPanel)((TenNotaPanel)body).foreground(active);if(body instanceof PhotoGallery)((PhotoGallery)body).active(active);}}
    void foreground(boolean active){foreground=active;updateActivity();}
    void appearanceChanged(){for(Session session:sessions.values())presentation(session.window.body,true);render();}
    private static void begin(View body){if(body instanceof TenNotaPanel)((TenNotaPanel)body).beginPresentationTransfer();if(body instanceof MediaPanel)((MediaPanel)body).beginPresentationTransfer();}
    private static void end(View body){if(body instanceof TenNotaPanel)((TenNotaPanel)body).endPresentationTransfer();if(body instanceof MediaPanel)((MediaPanel)body).endPresentationTransfer();}
    private void presentation(View body,boolean immersive){if(body instanceof WeatherPanel)((WeatherPanel)body).immersive(immersive,appBar);if(body instanceof PhotoGallery)((PhotoGallery)body).immersive(immersive,appBar);if(body instanceof MediaPanel)((MediaPanel)body).immersive(immersive);if(body instanceof EightCalculator)((EightCalculator)body).immersive(immersive);if(body instanceof BrowserPanel)((BrowserPanel)body).immersive(immersive,appBar,profile());}
    void captureVisible(){if(!visible())return;for(Session session:sessions.values())if(session.pane.isShown()){
        if(session.preview!=null){session.preview.recycle();session.preview=null;}View body=session.window.body;
        if(body instanceof BrowserPanel&&((BrowserPanel)body).isPrivate())continue;
        if(body.getWidth()<1||body.getHeight()<1)continue;
        try{Bitmap bitmap=Bitmap.createBitmap(240,160,Bitmap.Config.ARGB_8888);Canvas canvas=new Canvas(bitmap);canvas.scale(240f/body.getWidth(),160f/body.getHeight());body.draw(canvas);session.preview=bitmap;}catch(RuntimeException ignored){}
    }}
    JSONObject state(){JSONObject result=new JSONObject();JSONArray apps=new JSONArray();for(Session session:sessions.values()){JSONObject app=new JSONObject();GadgetLayout.put(app,"id",session.spec.id);GadgetLayout.put(app,"ui",ToolViewState.capture(session.window.body));apps.put(app);}GadgetLayout.put(result,"apps",apps);GadgetLayout.put(result,"recent",new JSONArray(recent));GadgetLayout.put(result,"primary",primary);GadgetLayout.put(result,"secondary",secondary);GadgetLayout.put(result,"focused",focused);GadgetLayout.put(result,"visible",visible());GadgetLayout.put(result,"ratio",ratio);return result;}
    void restore(JSONObject state){if(state==null)return;JSONArray apps=state.optJSONArray("apps");if(apps==null||apps.length()>EightAppSpec.ALL.size())return;setVisibility(VISIBLE);for(int i=0;i<apps.length();i++){JSONObject item=apps.optJSONObject(i);if(item==null)continue;Session session=obtain(item.optString("id"));if(session!=null)ToolViewState.restore(session.window.body,item.optJSONObject("ui"));}primary=state.optString("primary","");if(!sessions.containsKey(primary))primary=recent.isEmpty()?sessions.keySet().stream().findFirst().orElse(null):recent.get(0);secondary=state.optString("secondary","");if(!sessions.containsKey(secondary)||secondary.equals(primary))secondary=null;focused=state.optString("focused",primary);if(!sessions.containsKey(focused))focused=primary;ratio=(float)state.optDouble("ratio",.5);JSONArray order=state.optJSONArray("recent");if(order!=null)for(int i=0;i<Math.min(order.length(),EightAppSpec.ALL.size());i++){String id=order.optString(i);if(sessions.containsKey(id)&&!recent.contains(id))recent.add(id);}for(String id:sessions.keySet())if(!recent.contains(id))recent.add(id);setVisibility(state.optBoolean("visible")?VISIBLE:GONE);commands(false);render();}
    boolean back(){if(appBar){commands(false);return true;}if(visible()){View body=currentBody();if(body instanceof BrowserPanel&&((BrowserPanel)body).goBack())return true;if(body instanceof PhotoGallery&&((PhotoGallery)body).back()){hideToDesktop();return true;}hideToDesktop();return true;}return false;}
    @Override public boolean dispatchTouchEvent(MotionEvent event){if(event.getActionMasked()==MotionEvent.ACTION_DOWN&&splitFits()&&secondary!=null&&event.getY()<getHeight()-(appBar?dp(56):dp(44))&&!(caption.getVisibility()==VISIBLE&&event.getY()<dp(42))&&!(event.getX()>=divider.getLeft()&&event.getX()<divider.getRight())){Session right=sessions.get(secondary);if(right!=null)focused=event.getX()>=right.pane.getLeft()?secondary:primary;}return super.dispatchTouchEvent(event);}
    @Override public boolean dispatchHoverEvent(android.view.MotionEvent event){if(profile()==EightProfile.EIGHT_POINT_ONE&&store.prefs.getBoolean("eight_81_update",false)){if(event.getActionMasked()==MotionEvent.ACTION_HOVER_MOVE&&event.getY()<dp(30))caption.setVisibility(VISIBLE);else if(event.getActionMasked()==MotionEvent.ACTION_HOVER_EXIT||event.getY()>dp(70))caption.setVisibility(GONE);}return super.dispatchHoverEvent(event);}
    @Override public boolean onInterceptTouchEvent(MotionEvent event){
        if(event.getActionMasked()==MotionEvent.ACTION_DOWN){gestureX=event.getX();gestureY=event.getY();edgeTracking=gestureY<dp(28)||gestureX>dp(18)&&gestureX<dp(44);edgeCaptured=false;}
        if(event.getActionMasked()==MotionEvent.ACTION_MOVE&&edgeTracking){float dx=event.getX()-gestureX,dy=event.getY()-gestureY;if(gestureY<dp(28)&&dy>dp(35)||gestureX<dp(44)&&dx>dp(60)&&Math.abs(dx)>Math.abs(dy)){edgeCaptured=true;return true;}}
        return super.onInterceptTouchEvent(event);
    }
    @Override public boolean onTouchEvent(MotionEvent event){if(!edgeCaptured)return super.onTouchEvent(event);float dy=event.getY()-gestureY;
        if(event.getActionMasked()==MotionEvent.ACTION_MOVE&&gestureY<dp(28)){gestureHint.setVisibility(VISIBLE);gestureHint.setText(dy>getHeight()*.7f?"Release to close":"Drag down to close · release now for commands");}
        if(event.getActionMasked()==MotionEvent.ACTION_UP){gestureHint.setVisibility(GONE);edgeCaptured=false;if(gestureY<dp(28)){if(dy>getHeight()*.7f)close(focused==null?primary:focused);else commands(true);}else host.switcher();}
        if(event.getActionMasked()==MotionEvent.ACTION_CANCEL){gestureHint.setVisibility(GONE);edgeCaptured=false;}return true;}
}
