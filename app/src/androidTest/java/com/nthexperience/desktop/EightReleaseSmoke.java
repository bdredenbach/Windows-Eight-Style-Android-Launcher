package com.nthexperience.desktop;

import android.app.*;
import android.content.*;
import android.content.pm.ApplicationInfo;
import android.content.pm.ActivityInfo;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.concurrent.*;

/** Pre-promotion routes on the actual non-debuggable release application. */
public final class EightReleaseSmoke extends Instrumentation {
    MainActivity home;View root;DesktopStore store;EightStartScreen start;EightImmersive apps;
    @Override public void onCreate(Bundle args){EightTestSetup.afterInitialization(getTargetContext());super.onCreate(args);start();}
    @Override public void onStart(){Bundle result=new Bundle();try{
        check((getTargetContext().getApplicationInfo().flags&ApplicationInfo.FLAG_DEBUGGABLE)==0,"Tests exercise the non-debuggable release APK");
        store=new DesktopStore(getTargetContext());EightDefaults.initialize(store);store.prefs.edit().putInt("startup_mode",2).putBoolean("welcome_on_start",false).putBoolean("reduce_motion",true).commit();
        getTargetContext().getSharedPreferences(StorageAccess.PREFS,0).edit().putBoolean("seen",true).commit();getTargetContext().getSharedPreferences(StartupExperience.SESSION,0).edit().putBoolean("seen",true).putBoolean("welcome_seen",true).putBoolean(StartupExperience.HOME_OFFERED,true).commit();
        home=(MainActivity)startActivitySync(new Intent(Intent.ACTION_MAIN).setComponent(new ComponentName(getTargetContext(),MainActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));root=home.getWindow().getDecorView();waitForIdleSync();start=EightSmoke.find(root,EightStartScreen.class);apps=EightSmoke.find(root,EightImmersive.class);
        for(EightProfile profile:EightProfile.values()){
            ui(()->{home.open("@desktop");store.prefs.edit().putString("eight_profile",profile.id).commit();home.open("@start");start.appearanceChanged();});
            sendStringSync("calc");waitForIdleSync();ui(()->{check(start.surface==EightStartScreen.Surface.SEARCH,"Typing on Start opens search");check(start.search.getText().toString().equals("calc"),"First character and subsequent native keystrokes are kept; query="+start.search.getText());check(start.scope.equals(profile==EightProfile.EIGHT?"Apps":"Everywhere"),"Typing uses profile search scope");});shot("eight8-type-search-"+profile.id);
            sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);waitForIdleSync();ui(()->{check(EightAuditSmoke.findVisible(root,EightCalculator.class)!=null,"Enter launches first search result");home.open("@desktop");home.open("@allapps");});
            sendStringSync("weather");waitForIdleSync();ui(()->{check(start.surface==EightStartScreen.Surface.SEARCH&&start.search.getText().toString().equals("weather"),"Typing also searches Apps view");home.open("@desktop");home.open("@start");key(KeyEvent.KEYCODE_TAB,KeyEvent.META_CTRL_ON);check(start.surface==EightStartScreen.Surface.ALL_APPS,"Ctrl+Tab opens Apps");key(KeyEvent.KEYCODE_TAB,KeyEvent.META_CTRL_ON);check(start.surface==EightStartScreen.Surface.START,"Ctrl+Tab returns Start");});
            Rect area=rect(start.viewport);swipe(area.centerX(),area.bottom-35,area.centerX(),area.top+35);
            ui(()->{check(start.surface==(profile==EightProfile.EIGHT_POINT_ONE?EightStartScreen.Surface.ALL_APPS:EightStartScreen.Surface.START),"Upward Apps gesture is specific to 8.1");home.open("@desktop");start.showSearch("Files");check(start.scope.equals("Files"),"Opening hidden scoped search preserves Files");start.search.setText("no-matching-file-7fb4c8");start.search.onEditorAction(android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH);check(start.isShown(),"Submitting empty results keeps search open");home.open("@desktop");home.open("@settings");home.open("@start");home.onBackPressed();check(EightSmoke.find(root,EightPCSettings.class).isShown(),"Back from Start returns to PC settings");home.open("@desktop");});
            // Every registered immersive app must open, expose commands, survive Start, and reopen its same body.
            for(EightAppSpec spec:EightAppSpec.ALL){
                final View[] original={null};ui(()->{home.openEightTile(spec.id);original[0]=apps.currentBody();check(original[0]!=null&&original[0].isShown(),profile.label+" opens "+spec.title);apps.commands(true);check(apps.commandScroll.isShown(),"Commands reachable: "+spec.title);});
                ui(()->{check(original[0].getWidth()>0&&original[0].getHeight()>0,"Usable app content: "+spec.title);home.open("@start");check(start.isShown(),"Start over "+spec.title);home.open("@start");check(apps.visible()&&apps.currentBody()==original[0]&&original[0].isShown(),"Start returns retained "+spec.title);apps.close(spec.id);});
            }
            ui(()->{home.open("@notes");DesktopWindow notes=EightAuditSmoke.findVisible(root,DesktopWindow.class);check(notes!=null,"Desktop Notepad exists");home.openEightTile("@gadget:calculator");key(KeyEvent.KEYCODE_D,KeyEvent.META_META_ON);check(!apps.visible()&&!start.isShown()&&!EightSmoke.find(root,EightPCSettings.class).isShown(),"Win+D uncovers desktop from immersive app");check(!notes.isShown(),"Win+D hides desktop windows");key(KeyEvent.KEYCODE_D,KeyEvent.META_META_ON);check(notes.isShown(),"Second Win+D restores desktop windows");home.open("@settings");key(KeyEvent.KEYCODE_D,KeyEvent.META_META_ON);check(!EightSmoke.find(root,EightPCSettings.class).isShown(),"Win+D also leaves PC settings");home.open("@desktop");home.open("@charms");EightCharms charms=EightSmoke.find(root,EightCharms.class);for(String name:new String[]{"Search","Share","Start","Devices","Settings"})check(EightSmoke.description(charms,name+" charm").isShown(),"Visible "+name+" charm");});shot("eight8-charms-"+profile.id);
            ui(()->{EightCharms charms=EightSmoke.find(root,EightCharms.class);EightSmoke.description(charms,"Devices charm").performClick();check(EightSmoke.description(charms,"Connected devices").isShown(),"Devices routes reachable");home.onBackPressed();check(!charms.isShown(),"Back dismisses Charms");home.open("@desktop");});
        }
        ui(()->{home.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);home.open("@start");});SystemClock.sleep(700);waitForIdleSync();sendStringSync("personal");waitForIdleSync();ui(()->{check(start.search.hasFocus()&&start.search.getParent()==start.searchSide,"Landscape type-search keeps focus in right pane");check(start.search.getText().toString().equals("personal"),"Landscape query remains intact");});shot("eight8-landscape-search");
        result.putString("stream","PASS: non-debuggable release; native type-to-search and Enter in both profiles; Apps search; Ctrl+Tab both directions; 8.1-only upward Apps gesture; scoped hidden search and empty result; Back restores settings; all 17 immersive app routes retain bodies through Start in both profiles; Win+D desktop hide/restore from immersive/settings; five Charms and Devices dismissal; landscape query/focus\n");finish(Activity.RESULT_OK,result);
    }catch(Throwable failure){try{shot("eight8-failure");}catch(Exception ignored){}result.putString("stream","FAIL: "+android.util.Log.getStackTraceString(failure));finish(Activity.RESULT_CANCELED,result);}}
    void key(int code,int modifiers){long now=SystemClock.uptimeMillis();home.dispatchKeyEvent(new KeyEvent(now,now,KeyEvent.ACTION_DOWN,code,0,modifiers));home.dispatchKeyEvent(new KeyEvent(now,now,KeyEvent.ACTION_UP,code,0,modifiers));}
    void ui(Runnable action)throws Exception{FutureTask<Void> task=new FutureTask<>(action,null);home.runOnUiThread(task);task.get(25,TimeUnit.SECONDS);waitForIdleSync();}
    void check(boolean value,String message){if(!value)throw new AssertionError(message);}
    Rect rect(View view){Rect r=new Rect();check(view.getGlobalVisibleRect(r),"Visible gesture target");return r;}
    void swipe(float x,float y,float endX,float endY){long down=SystemClock.uptimeMillis();for(int i=0;i<=12;i++){int action=i==0?MotionEvent.ACTION_DOWN:i==12?MotionEvent.ACTION_UP:MotionEvent.ACTION_MOVE;MotionEvent e=MotionEvent.obtain(down,SystemClock.uptimeMillis(),action,x+(endX-x)*i/12,y+(endY-y)*i/12,0);e.setSource(InputDevice.SOURCE_TOUCHSCREEN);sendPointerSync(e);e.recycle();SystemClock.sleep(15);}waitForIdleSync();}
    void shot(String name)throws Exception{waitForIdleSync();Bitmap image=Bitmap.createBitmap(root.getWidth(),root.getHeight(),Bitmap.Config.ARGB_8888);ui(()->root.draw(new Canvas(image)));try(OutputStream out=new FileOutputStream(new File(getTargetContext().getFilesDir(),name+".png"))){image.compress(Bitmap.CompressFormat.PNG,100,out);}finally{image.recycle();}}
}
