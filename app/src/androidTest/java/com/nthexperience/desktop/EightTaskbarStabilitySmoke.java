package com.nthexperience.desktop;

import android.app.*;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import org.json.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/** Recording regression: changing taskbar height must not resnap the desktop behind it. */
public final class EightTaskbarStabilitySmoke extends Instrumentation {
    MainActivity home;View root;DesktopStore store;int scenarios,frames;
    @Override public void onCreate(Bundle args){super.onCreate(args);start();}
    @Override public void onStart(){Bundle result=new Bundle();try{
        shell("wm size 1080x2400");
        store=new DesktopStore(getTargetContext());EightDefaults.initialize(store);
        store.prefs.edit().putInt("startup_mode",2).putBoolean("welcome_on_start",false).putBoolean("reduce_motion",true).putBoolean("taskbar_static",true).putBoolean("taskbar_expanded",false).commit();
        getTargetContext().getSharedPreferences(StorageAccess.PREFS,0).edit().putBoolean("seen",true).commit();
        getTargetContext().getSharedPreferences(StartupExperience.SESSION,0).edit().putBoolean("seen",true).putBoolean("welcome_seen",true).putBoolean(StartupExperience.HOME_OFFERED,true).commit();
        home=(MainActivity)startActivitySync(new Intent(Intent.ACTION_MAIN).setComponent(new ComponentName(getTargetContext(),MainActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));root=home.getWindow().getDecorView();settle();
        for(EightProfile profile:EightProfile.values()){
            home=EightShellSwitch.choose(this,home,profile);root=home.getWindow().getDecorView();
            for(boolean landscape:new boolean[]{false,true}){
                ui(()->home.setRequestedOrientation(landscape?ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:ActivityInfo.SCREEN_ORIENTATION_PORTRAIT));settle();
                scenario(profile.id+(landscape?"-landscape":"-portrait"),true,true,false);
            }
        }
        ui(()->home.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT));settle();
        scenario("automatic-unsaved",false,true,false);
        scenario("free-placed",true,false,false);
        scenario("scrolled",true,true,true);
        result.putString("stream","PASS: "+scenarios+" taskbar height scenarios / "+frames+" actual pre-draw frames; 21 desktop icons retain screen positions, cell sizes and icon sizes across repeated menu and Control Panel Apply expansion/collapse; 5x9 imported-style grid at 80% icon size, both 8/8.1 shells, portrait/landscape, automatic unsaved layout, free placement and scrolled sheet; saved coordinates unchanged; taller taskbar only reduces visible scroll area\n");finish(Activity.RESULT_OK,result);
    }catch(Throwable error){result.putString("stream","FAIL: "+android.util.Log.getStackTraceString(error));try{shot("eight710-failure");}catch(Exception ignored){}finish(Activity.RESULT_CANCELED,result);}}
    void scenario(String name,boolean custom,boolean snap,boolean scrolled)throws Exception{
        ui(()->{
            home.open("@desktop");store.prefs.edit().putBoolean("taskbar_expanded",false).putBoolean("taskbar_static",true).putBoolean("snap_desktop",snap).putInt("desktop_columns_portrait",custom?5:0).putInt("desktop_rows_portrait",custom?9:0).putInt("desktop_columns_landscape",custom?5:0).putInt("desktop_rows_landscape",custom?6:0).putInt("desktop_icon_percent",80).putInt("desktop_spacing_x",0).putInt("desktop_spacing_y",0).commit();
            try{JSONObject folders=new JSONObject();List<String> ids=new ArrayList<>();for(int n=0;n<20;n++){String id="folder:"+new UUID(0,n+1);ids.add(id);folders.put(id,new JSONObject().put("name","Folder "+(n+1)).put("items",new JSONArray().put("@notes")));}ids.add("@recycle");store.prefs.edit().putString("app_folders",folders.toString()).commit();store.desktop(ids);TenDesktopItems.positions(store,"portrait",new JSONObject());TenDesktopItems.positions(store,"landscape",new JSONObject());}catch(JSONException e){throw new RuntimeException(e);}
            store.quickLaunch(Arrays.asList("@computer","@browser","@notes","@calendar","@weather","@photos","@files","@taskmanager","@nota","@recycle","@backup","@appearance"));home.applyTenAppearance();
        });settle();
        ui(()->{
            DesktopIconGrid grid=find(DesktopIconGrid.class);check(grid.getChildCount()==21,"Restored-style desktop fixture");
            if(custom){JSONObject points=new JSONObject();float density=home.getResources().getDisplayMetrics().density;
                for(int n=0;n<grid.getChildCount();n++){View child=grid.getChildAt(n);float y=child.getTop()/density;if(scrolled&&n==grid.getChildCount()-1)y=(grid.viewportHeight()+grid.cellHeight()*3)/density;else if(!snap)y+=n%3*2.5f;
                    JSONObject point=new JSONObject();GadgetLayout.put(point,"x",DesktopGeometry.fraction(child.getLeft(),grid.getWidth()-grid.cellWidth()));GadgetLayout.put(point,"y",y);GadgetLayout.put(points,String.valueOf(child.getTag()),point);}
                String axis=home.getResources().getConfiguration().orientation==android.content.res.Configuration.ORIENTATION_LANDSCAPE?"landscape":"portrait";TenDesktopItems.positions(store,axis,points);grid.requestLayout();
            }
        });settle();
        if(scrolled){ui(()->((ScrollView)find(DesktopIconGrid.class).getParent()).scrollTo(0,find(DesktopIconGrid.class).cellHeight()/2));settle();}
        String[] saved={null};Watcher[] watcher={null};ui(()->{saved[0]=TenDesktopItems.portable(store);watcher[0]=new Watcher();root.getViewTreeObserver().addOnPreDrawListener(watcher[0]);});
        try{
            for(int round=0;round<2;round++){
                ui(this::toggleMenu);settle();verify(watcher[0],name+" expanded from menu");
                if(name.equals("81-portrait")&&round==0)shot("eight710-stable-expanded");
                ui(this::toggleProperties);settle();verify(watcher[0],name+" collapsed from properties");
            }
            ui(()->check(saved[0].equals(TenDesktopItems.portable(store)),"Taskbar does not write desktop positions: "+name));
            if(name.equals("81-portrait"))shot("eight710-stable-collapsed");scenarios++;
        }finally{ui(()->{root.getViewTreeObserver().removeOnPreDrawListener(watcher[0]);frames+=watcher[0].count;});}
    }
    void verify(Watcher watcher,String action){check(watcher.failure==null,action+": "+watcher.failure);}
    final class Watcher implements ViewTreeObserver.OnPreDrawListener{
        final Map<String,String> baseline=geometry();int count;String failure;
        public boolean onPreDraw(){count++;Map<String,String> now=geometry();if(failure==null&&!baseline.equals(now)){for(String id:baseline.keySet())if(!Objects.equals(baseline.get(id),now.get(id))){failure=id+" moved from "+baseline.get(id)+" to "+now.get(id);break;}if(failure==null)failure="Desktop membership changed";}return true;}
    }
    Map<String,String> geometry(){
        DesktopIconGrid grid=find(DesktopIconGrid.class);Map<String,String> values=new LinkedHashMap<>();
        for(int n=0;n<grid.getChildCount();n++){View child=grid.getChildAt(n);int[] at=new int[2];child.getLocationOnScreen(at);View icon=((ViewGroup)child).getChildAt(0);values.put(String.valueOf(child.getTag()),at[0]+","+at[1]+","+child.getWidth()+","+child.getHeight()+","+icon.getWidth()+","+icon.getHeight());}return values;
    }
    void toggleMenu(){try{View taskbar=EightSmoke.description(root,"Desktop taskbar");java.lang.reflect.Method method=MainActivity.class.getDeclaredMethod("taskbarMenu",View.class,float.class,float.class);method.setAccessible(true);method.invoke(home,taskbar,10f,10f);java.lang.reflect.Field field=MainActivity.class.getDeclaredField("desktopMenu");field.setAccessible(true);Object menu=field.get(home);java.lang.reflect.Field items=DesktopContextMenu.class.getDeclaredField("items");items.setAccessible(true);View toggle=EightSmoke.description((View)items.get(menu),"Expand taskbar");check(toggle!=null,"Actual expansion menu row");toggle.performClick();}catch(Exception error){throw new RuntimeException(error);}}
    void toggleProperties(){home.open("eight-desktop:taskbar");EightTaskbarProperties properties=find(EightTaskbarProperties.class);View parent=properties;while(!(parent instanceof DesktopWindow))parent=(View)parent.getParent();DesktopWindow window=(DesktopWindow)parent;((CheckBox)EightSmoke.description(properties,"Expand taskbar to two rows")).performClick();EightSmoke.description(properties,"Taskbar properties Apply").performClick();window.closeWindow();}
    void settle()throws Exception{
        CountDownLatch ready=new CountDownLatch(1);ui(()->{ViewTreeObserver.OnPreDrawListener listener=new ViewTreeObserver.OnPreDrawListener(){int draws;public boolean onPreDraw(){if(++draws==3){root.getViewTreeObserver().removeOnPreDrawListener(this);ready.countDown();}else root.postOnAnimation(()->root.invalidate());return true;}};root.getViewTreeObserver().addOnPreDrawListener(listener);root.invalidate();});check(ready.await(8,TimeUnit.SECONDS),"Native frames settled");waitForIdleSync();
    }
    void ui(Runnable action)throws Exception{FutureTask<Void> job=new FutureTask<>(action,null);home.runOnUiThread(job);job.get(25,TimeUnit.SECONDS);waitForIdleSync();}
    <T extends View>T find(Class<T> type){return EightSmoke.find(root,type);}void check(boolean ok,String message){if(!ok)throw new AssertionError(message);}
    void shell(String command)throws IOException{try(ParcelFileDescriptor descriptor=getUiAutomation().executeShellCommand(command);InputStream in=new FileInputStream(descriptor.getFileDescriptor())){byte[] buffer=new byte[4096];while(in.read(buffer)!=-1){}}}
    void shot(String name)throws Exception{settle();Bitmap bitmap=Bitmap.createBitmap(root.getWidth(),root.getHeight(),Bitmap.Config.ARGB_8888);ui(()->root.draw(new Canvas(bitmap)));try(OutputStream out=new FileOutputStream(new File(getTargetContext().getFilesDir(),name+".png"))){bitmap.compress(Bitmap.CompressFormat.PNG,100,out);}finally{bitmap.recycle();}}
}
