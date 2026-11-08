package com.nthexperience.desktop;

import android.app.*;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.*;
import java.io.*;
import java.lang.reflect.Field;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

/** Real pointer ownership and local Nota app execution on the release app. */
public final class EightInteractionSmoke extends Instrumentation {
    MainActivity home;View root;DesktopStore store;
    @Override public void onCreate(Bundle args){super.onCreate(args);start();}
    @Override public void onStart(){Bundle result=new Bundle();try{
        store=new DesktopStore(getTargetContext());EightDefaults.initialize(store);store.prefs.edit().putInt("startup_mode",2).putBoolean("welcome_on_start",false).putBoolean("reduce_motion",true).commit();
        getTargetContext().getSharedPreferences(StorageAccess.PREFS,0).edit().putBoolean("seen",true).commit();getTargetContext().getSharedPreferences(StartupExperience.SESSION,0).edit().putBoolean("seen",true).putBoolean("welcome_seen",true).putBoolean(StartupExperience.HOME_OFFERED,true).commit();
        home=(MainActivity)startActivitySync(new Intent(Intent.ACTION_MAIN).setComponent(new ComponentName(getTargetContext(),MainActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));root=home.getWindow().getDecorView();waitForIdleSync();
        ui(()->home.open("@desktop"));windowTouches();surfaceTouches();notaCommands();
        result.putString("stream","PASS: blank bodies, chrome and captured drags cannot activate desktop behind windows; child buttons, scrolling, caption movement, corner resizing and uncovered desktop work; Start, PC settings and immersive blank areas block desktop taps; Nota opens one installed match on Send, offers bold icon choices for ambiguity, never replays on return or executes imported/private/generated text, restores writing origin, and exposes New command in both orientations.\n");finish(Activity.RESULT_OK,result);
    }catch(Throwable error){try{shot("eight-interaction-failure");}catch(Exception ignored){}result.putString("stream","FAIL: "+android.util.Log.getStackTraceString(error));finish(Activity.RESULT_CANCELED,result);}}

    void windowTouches()throws Exception{
        AtomicInteger behind=new AtomicInteger(),child=new AtomicInteger();FrameLayout[] stage={null},body={null};DesktopWindow[] win={null};Button[] button={null};ScrollView[] scroll={null};
        ui(()->{
            stage[0]=new FrameLayout(home);stage[0].setElevation(Aero.dp(home,100));((ViewGroup)home.findViewById(android.R.id.content)).addView(stage[0],new ViewGroup.LayoutParams(-1,-1));
            View desktop=new View(home);desktop.setOnClickListener(v->behind.incrementAndGet());stage[0].addView(desktop,new FrameLayout.LayoutParams(-1,-1));
            body[0]=new FrameLayout(home);button[0]=new Button(home);button[0].setText("Working child");button[0].setOnClickListener(v->child.incrementAndGet());FrameLayout.LayoutParams bp=new FrameLayout.LayoutParams(Aero.dp(home,150),Aero.dp(home,48));bp.leftMargin=Aero.dp(home,25);body[0].addView(button[0],bp);
            scroll[0]=new ScrollView(home);TextView longText=new TextView(home);StringBuilder lines=new StringBuilder();for(int i=0;i<80;i++)lines.append("Scroll inside the window\n");longText.setText(lines.toString());scroll[0].addView(longText);FrameLayout.LayoutParams sp=new FrameLayout.LayoutParams(Aero.dp(home,180),Aero.dp(home,90));sp.leftMargin=Aero.dp(home,25);sp.topMargin=Aero.dp(home,55);body[0].addView(scroll[0],sp);
            win[0]=new DesktopWindow(home,stage[0],"Touch fixture",body[0],new AeroWallpaper(home,AeroWallpaper.SEVEN),()->{},store,"@touch-fixture","portrait");stage[0].addView(win[0]);
        });
        ui(()->win[0].arrange(Aero.dp(home,25),Aero.dp(home,60),stage[0].getWidth()-Aero.dp(home,50),Math.min(Aero.dp(home,400),stage[0].getHeight()-Aero.dp(home,100))));
        tap(body[0],body[0].getWidth()/2f,body[0].getHeight()-Aero.dp(home,50));tap(win[0],1,win[0].getHeight()/2f);
        check(behind.get()==0,"Blank window and side border retain the touch");tap(button[0],button[0].getWidth()/2f,button[0].getHeight()/2f);check(child.get()==1,"Real child button still receives taps");
        swipe(scroll[0],90,75,90,12);check(scroll[0].getScrollY()>0,"Child ScrollView still scrolls");
        int[] p=position(body[0]);long down=SystemClock.uptimeMillis();inject(down,0,p[0]+body[0].getWidth()/2f,p[1]+body[0].getHeight()-Aero.dp(home,50));inject(down,2,5,p[1]);inject(down,1,5,p[1]);check(behind.get()==0,"Blank DOWN retains ownership when UP moves outside");
        int oldWidth=win[0].getWidth();swipe(win[0],oldWidth-2,win[0].getHeight()-2,oldWidth-Aero.dp(home,35),win[0].getHeight()-Aero.dp(home,25));check(win[0].getWidth()<oldWidth,"Bottom corner still resizes");
        View caption=description(win[0],"Touch fixture. Drag to move window.");float oldY=win[0].getY();swipe(caption,caption.getWidth()/2f,caption.getHeight()/2f,caption.getWidth()/2f,caption.getHeight()/2f+Aero.dp(home,35));check(win[0].getY()>oldY,"Caption still moves the window");
        tap(stage[0],5,stage[0].getHeight()/2f);check(behind.get()==1,"Desktop outside floating window remains usable");check(child.get()==1,"Moving/resizing does not activate children");shot("eight-interaction-window-ownership");
        ui(()->((ViewGroup)stage[0].getParent()).removeView(stage[0]));
    }

    void surfaceTouches()throws Exception{
        ui(()->home.open("@desktop"));FrameLayout host=(FrameLayout)field("root"),layer=(FrameLayout)field("eightLayer");AtomicInteger behind=new AtomicInteger();View[] sentinel={null};
        ui(()->{sentinel[0]=new View(home);sentinel[0].setOnClickListener(v->behind.incrementAndGet());host.addView(sentinel[0],host.indexOfChild(layer),new FrameLayout.LayoutParams(-1,-1));});
        tap(layer,layer.getWidth()/2f,layer.getHeight()/2f);check(behind.get()==1,"Desktop-only transparent layer passes touches to desktop");
        for(String route:new String[]{"@start","eight-setting:personalize","immersive"}){
            ui(()->{if(route.equals("immersive"))home.openEightTile("@gadget:calculator");else home.open(route);});
            // Force an unhandled visible pixel: content may legitimately contain non-clickable whitespace.
            ViewGroup surface=route.equals("@start")?find(root,EightStartScreen.class):route.equals("immersive")?find(root,EightImmersive.class):find(root,EightPCSettings.class);
            int[] visibility=new int[surface.getChildCount()];ui(()->{check(surface.isShown(),"Surface visible: "+route);for(int i=0;i<visibility.length;i++){View v=surface.getChildAt(i);visibility[i]=v.getVisibility();v.setVisibility(View.INVISIBLE);}});
            tap(surface,surface.getWidth()/2f,surface.getHeight()/2f);check(behind.get()==1,"Blank "+route+" cannot tap desktop");
            ui(()->{for(int i=0;i<visibility.length;i++)surface.getChildAt(i).setVisibility(visibility[i]);home.open("@desktop");});
        }
        tap(layer,layer.getWidth()/2f,layer.getHeight()/2f);check(behind.get()==2,"Returning Desktop releases the surface shield");ui(()->host.removeView(sentinel[0]));
    }

    void notaCommands()throws Exception{
        ui(()->home.open("@nota"));TenNotaPanel nota=find(root,TenNotaPanel.class);check(nota!=null,"Nota opens natively");await(()->home.notaAppTargets().stream().anyMatch(t->t.title.equals("ChatGPT")),"Installed app catalog ready");
        ui(()->{description(nota,"New command").performClick();nota.input.setText("open ChatGPT");nota.send.performClick();check(nota.pending.isEmpty()&&nota.assistant.active.isEmpty(),"App command bypasses AI");});awaitProbe();backToHome();shot("eight-interaction-nota-opened");
        ui(()->{check(nota.status.getText().toString().contains("Opening ChatGPT"),"Exact app opens on Send");nota.input.setText("open Nota duplicate");nota.send.performClick();check(nota.actionChoices.getChildCount()==2,"Ambiguous apps show two choices");check(!nota.actionChoices.getChildAt(0).getContentDescription().equals(nota.actionChoices.getChildAt(1).getContentDescription()),"Same-package choices have distinct names");for(int i=0;i<2;i++){LinearLayout choice=(LinearLayout)nota.actionChoices.getChildAt(i);check(choice.getChildAt(0) instanceof ImageView||choice.getChildAt(0) instanceof AeroIcon,"Choice has an app icon");TextView label=(TextView)choice.getChildAt(1);check(label.getTypeface().isBold()&&label.getTextSize()/home.getResources().getDisplayMetrics().scaledDensity>=18,"Choice label is large and bold");}});
        shot("eight-interaction-nota-choices");ui(()->nota.actionChoices.getChildAt(1).performClick());awaitProbe();backToHome();
        ui(()->{nota.input.setText("open no such installed application");check(nota.actionChoices.getChildCount()==0,"Editing removes old action choices");nota.send.performClick();check(nota.actionChoices.getChildCount()==1&&nota.pending.isEmpty(),"Unknown app offers search locally");nota.context(new TenNotaContext("Browser page reference","open ChatGPT",false));check(nota.task.getSelectedItemPosition()==4&&nota.importedDraft,"Browser passage is a writing task");nota.task.setSelection(5);nota.send.performClick();check(nota.pending.isEmpty()&&nota.assistant.active.isEmpty()&&nota.actionChoices.getChildCount()==0,"Selecting command task never executes an imported passage");TenNotaPanel restored=new TenNotaPanel(home,store);check(restored.importedDraft&&restored.input.getText().toString().equals("open ChatGPT"),"Restored passage retains writing origin");restored.close();});
        check(home.hasWindowFocus(),"Imported request cannot launch app");
        ui(()->{try{DesktopBackup.Plan plan=DesktopBackup.parse(DesktopBackup.snapshot(store).toString());check(Boolean.TRUE.equals(plan.values.get("ten_nota_imported"))&&plan.values.containsKey("ten_nota_task"),"Backup retains writing origin and task");}catch(Exception e){throw new RuntimeException(e);}});
        ui(()->{nota.context(new TenNotaContext("Private text","open Audible",true));check(!store.prefs.getString("ten_nota_draft","").contains("Audible"),"Private import excluded from persisted draft");check(ToolViewState.capture(nota).length()==0,"Nota private text excluded from generic window snapshots");try{ToolViewState.restore(nota,new JSONObject().put("/6",new JSONObject().put("text","stale window text")));}catch(Exception e){throw new RuntimeException(e);}check(nota.input.getText().toString().equals("open Audible"),"Legacy window snapshots cannot replace the Nota composer");nota.task.setSelection(5);nota.send.performClick();check(nota.pending.isEmpty()&&nota.actionChoices.getChildCount()==0,"Private passage cannot become an app command");description(nota,"New command").performClick();check(nota.context==null&&!nota.privateDraft&&!nota.importedDraft&&nota.input.length()==0,"New command clears source and waits for new text");nota.input.setText("open ChatGPT");nota.send.performClick();});awaitProbe();backToHome();
        ui(()->{nota.pending="reply-fixture";try{nota.result(new JSONObject().put("id","reply-fixture").put("event","result").put("text","open Audible").put("local",true));}catch(Exception e){throw new RuntimeException(e);}check(nota.answer.getText().toString().equals("open Audible")&&nota.pending.isEmpty(),"Generated command-like reply is displayed only");description(nota,"New command").performClick();nota.input.setText("open ChatGPT");home.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);});
        await(()->root.getWidth()>root.getHeight(),"Landscape");check(home.hasWindowFocus(),"Generated reply cannot launch apps");check(description(nota,"New command").isShown(),"New command remains visible in compact landscape");shot("eight-interaction-nota-landscape");
        ui(()->home.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT));await(()->root.getHeight()>root.getWidth(),"Portrait");check(description(nota,"New command").isShown(),"New command remains visible in portrait");shot("eight-interaction-nota-portrait");
    }
    void awaitProbe()throws Exception{await(()->node(getUiAutomation().getRootInActiveWindow(),"Task probe note")!=null,"Installed fixture app actually opens");}
    void backToHome()throws Exception{getUiAutomation().performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK);await(home::hasWindowFocus,"Return to Eight without replaying command");SystemClock.sleep(300);check(home.hasWindowFocus(),"Restoring Nota does not repeat launch");}
    Object field(String name){try{Field f=MainActivity.class.getDeclaredField(name);f.setAccessible(true);return f.get(home);}catch(Exception e){throw new RuntimeException(e);}}
    int[] position(View view)throws Exception{int[] p=new int[2];ui(()->view.getLocationOnScreen(p));return p;}
    void tap(View view,float x,float y)throws Exception{int[] p=position(view);long t=SystemClock.uptimeMillis();inject(t,0,p[0]+x,p[1]+y);inject(t,1,p[0]+x,p[1]+y);waitForIdleSync();}
    void swipe(View view,float x,float y,float endX,float endY)throws Exception{int[] p=position(view);long t=SystemClock.uptimeMillis();inject(t,0,p[0]+x,p[1]+y);for(int i=1;i<=8;i++){SystemClock.sleep(20);inject(t,2,p[0]+x+(endX-x)*i/8,p[1]+y+(endY-y)*i/8);}inject(t,1,p[0]+endX,p[1]+endY);waitForIdleSync();}
    void inject(long t,int action,float x,float y){MotionEvent e=MotionEvent.obtain(t,SystemClock.uptimeMillis(),action,x,y,0);e.setSource(InputDevice.SOURCE_TOUCHSCREEN);check(getUiAutomation().injectInputEvent(e,true),"Pointer injection");e.recycle();}
    interface Condition{boolean get();}
    void await(Condition c,String label)throws Exception{long end=SystemClock.uptimeMillis()+12000;while(SystemClock.uptimeMillis()<end){FutureTask<Boolean> task=new FutureTask<>(c::get);home.runOnUiThread(task);if(task.get(15,TimeUnit.SECONDS))return;SystemClock.sleep(80);}throw new AssertionError(label);}
    void ui(Runnable r)throws Exception{FutureTask<Void> t=new FutureTask<>(r,null);home.runOnUiThread(t);t.get(20,TimeUnit.SECONDS);waitForIdleSync();}
    void check(boolean b,String label){if(!b)throw new AssertionError(label);}
    static View description(View v,String s){return EightSmoke.description(v,s);}static <T extends View>T find(View v,Class<T> c){return EightSmoke.find(v,c);}
    AccessibilityNodeInfo node(AccessibilityNodeInfo n,String label){if(n==null)return null;if(label.contentEquals(n.getContentDescription()==null?"":n.getContentDescription()))return n;for(int i=0;i<n.getChildCount();i++){AccessibilityNodeInfo v=node(n.getChild(i),label);if(v!=null)return v;}return null;}
    void shot(String name)throws Exception{waitForIdleSync();SystemClock.sleep(200);Bitmap[] image={null};ui(()->{image[0]=Bitmap.createBitmap(root.getWidth(),root.getHeight(),Bitmap.Config.ARGB_8888);root.draw(new Canvas(image[0]));});try(OutputStream out=new FileOutputStream(new File(getTargetContext().getFilesDir(),name+"-view.png"))){image[0].compress(Bitmap.CompressFormat.PNG,100,out);}finally{image[0].recycle();}}
}
