package com.nthexperience.desktop;

import android.Manifest;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.os.*;
import android.view.*;
import java.util.*;
import java.util.concurrent.*;

/** Actual Android Settings returns for Eight's ordered and manual Usage Access guides. */
public final class EightInitializationSmoke extends TenThemeSmoke {
 @Override public void onCreate(Bundle args){super.onCreate(args);}
 @Override public void onStart(){Bundle result=new Bundle();try{
  store=new DesktopStore(getTargetContext());store.prefs.edit().clear().putInt("startup_mode",2).putBoolean("welcome_on_start",false).putBoolean("reduce_motion",true).putBoolean("taskbar_apps",false).putString("weather_city","Saved city").commit();
  getTargetContext().getSharedPreferences(InitializationState.PREFS,0).edit().clear().commit();
  getTargetContext().getSharedPreferences(StorageAccess.PREFS,0).edit().clear().commit();
  getTargetContext().getSharedPreferences(StartupExperience.SESSION,0).edit().clear().putBoolean("seen",true).putBoolean("welcome_seen",false).commit();
  home=(MainActivity)startActivitySync(new Intent(Intent.ACTION_MAIN).setComponent(new ComponentName(getTargetContext(),MainActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));root=home.getWindow().getDecorView();
  for(String title:new String[]{"Default Launcher","Set up Nth Explorer","Set up Usage Access","Allow Notifications Access","Modify System Settings (Brightness)"}){
   awaitSystem(title);
   if(title.equals("Default Launcher"))check(EightSmoke.find(root,EightStartScreen.class).isShown(),"Eight Start surface remains behind ordered setup");
   if(title.equals("Set up Nth Explorer"))shot("eight714-file-guide-portrait");
   if(title.equals("Set up Usage Access")){
    systemClick("OK");awaitSystemPackage("com.android.settings");
    check(!ExternalTasks.permitted(home),"Usage Access starts off for the return regression");
    check(getUiAutomation().performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK),"Return from Android Usage Access without granting it");awaitSystem(title);
    check(new InitializationState(home).step()==InitializationState.Step.USAGE,"Denied or ungranted Usage Access returns to the same guide");
    shot("eight714-usage-return-guide");recreateHome();awaitSystem(title);
    check(new InitializationState(home).step()==InitializationState.Step.USAGE,"Ungranted Usage Access guide survives recreation");
    shot("eight714-usage-guide-portrait");
    if(Build.VERSION.SDK_INT>=33){
     check(node(getUiAutomation().getRootInActiveWindow(),"Open App info")!=null,"Illustrated guide exposes App info recovery");
     systemClick("Open App info");awaitSystemPackage("com.android.settings");
     check(getUiAutomation().performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK),"Android App info returns to guide");awaitSystem(title);
     check(new InitializationState(home).step()==InitializationState.Step.USAGE,"Reviewing App info preserves the Usage Access step");
    }
    ui(()->home.setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE));await(()->root.getWidth()>root.getHeight(),"Usage guide landscape");awaitSystem(title);shot("eight714-usage-guide-landscape");
    ui(()->home.setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT));await(()->root.getHeight()>root.getWidth(),"Usage guide portrait");awaitSystem(title);
   }
   if(title.equals("Set up Nth Explorer")){recreateHome();awaitSystem(title);}
   systemClick("Later");
  }
  awaitSystem("Allow Location");shot("eight714-location-guide");
  systemClick("Later");
  await(()->new InitializationState(home).step()==InitializationState.Step.DONE,"Optional setup completes");
  check(!StorageAccess.has(home)&&!ExternalTasks.permitted(home),"Declines leave optional access off");
  check(store.prefs.getString("weather_city","").equals("Saved city"),"Setup keeps the saved weather city");
  recreateHome();await(()->home.hasWindowFocus(),"Focus after recreation");
  check(new InitializationState(home).step()==InitializationState.Step.DONE,"Completed setup does not replay after recreation");
  manualRecovery();
  grantedReturn();
  result.putString("stream","PASS: Eight Start backdrop; actual Android Usage Access return without a grant retains the illustrated guide; App info return and rotation/recreation retain recovery; Later continues; completed setup stays completed; Welcome Center manual retry preserves the guide; actual Usage Access grant refreshes state and continues; saved city retained\n");finish(Activity.RESULT_OK,result);
 }catch(Throwable error){try{shot("eight714-setup-failure");}catch(Exception ignored){}result.putString("stream","FAIL: "+android.util.Log.getStackTraceString(error));finish(Activity.RESULT_CANCELED,result);}}
 private void awaitSystemPackage(String pkg)throws Exception{
  long end=SystemClock.uptimeMillis()+15000;
  while(SystemClock.uptimeMillis()<end){android.view.accessibility.AccessibilityNodeInfo window=getUiAutomation().getRootInActiveWindow();if(window!=null&&pkg.contentEquals(window.getPackageName()))return;Thread.sleep(80);}throw new AssertionError("Android screen unavailable: "+pkg);
 }
 private void recreateHome()throws Exception{
  MainActivity previous=home;BlockingQueue<MainActivity> next=new LinkedBlockingQueue<>();Application app=(Application)getTargetContext().getApplicationContext();
  Application.ActivityLifecycleCallbacks callback=new Application.ActivityLifecycleCallbacks(){public void onActivityResumed(Activity a){if(a instanceof MainActivity&&a!=previous)next.offer((MainActivity)a);}public void onActivityCreated(Activity a,Bundle b){}public void onActivityStarted(Activity a){}public void onActivityStopped(Activity a){}public void onActivityPaused(Activity a){}public void onActivitySaveInstanceState(Activity a,Bundle b){}public void onActivityDestroyed(Activity a){}};
  app.registerActivityLifecycleCallbacks(callback);try{ui(home::recreate);home=next.poll(20,TimeUnit.SECONDS);check(home!=null,"Home recreated");root=home.getWindow().getDecorView();}finally{app.unregisterActivityLifecycleCallbacks(callback);}
 }
 @Override android.view.accessibility.AccessibilityNodeInfo node(android.view.accessibility.AccessibilityNodeInfo current,String text){
  if(current==null)return null;
  if((text.equals("OK")&&"Uninstall".equalsIgnoreCase(String.valueOf(current.getText())))||text.equalsIgnoreCase(String.valueOf(current.getText()))||text.equalsIgnoreCase(String.valueOf(current.getContentDescription())))return current;
  for(int i=0;i<current.getChildCount();i++){android.view.accessibility.AccessibilityNodeInfo found=node(current.getChild(i),text);if(found!=null)return found;}return null;
 }
 @Override void systemClick(String label)throws Exception{
  android.view.accessibility.AccessibilityNodeInfo item=awaitSystem(label);
  android.graphics.Rect bounds=new android.graphics.Rect();item.getBoundsInScreen(bounds);
  for(int n=0;item!=null&&n<6;n++,item=item.getParent())if(item.isClickable()&&item.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)){waitForIdleSync();Thread.sleep(180);return;}
  long now=SystemClock.uptimeMillis();MotionEvent down=MotionEvent.obtain(now,now,0,bounds.centerX(),bounds.centerY(),0),up=MotionEvent.obtain(now,now+60,1,bounds.centerX(),bounds.centerY(),0);
  down.setSource(InputDevice.SOURCE_TOUCHSCREEN);up.setSource(InputDevice.SOURCE_TOUCHSCREEN);
  try{check(getUiAutomation().injectInputEvent(down,true)&&getUiAutomation().injectInputEvent(up,true),"Click system control: "+label);}finally{down.recycle();up.recycle();}waitForIdleSync();Thread.sleep(180);
 }
 private void manualRecovery()throws Exception{
  ui(()->home.open("@welcome"));await(()->EightSmoke.find(root,WelcomeCenter.class)!=null,"Eight Welcome Center opens");systemClick("Set up Usage Access");awaitSystem("OK");
  systemClick("OK");awaitSystemPackage("com.android.settings");getUiAutomation().performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK);awaitSystem("OK");
  if(Build.VERSION.SDK_INT>=33){systemClick("Open App info");awaitSystemPackage("com.android.settings");getUiAutomation().performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK);awaitSystem("OK");check(node(getUiAutomation().getRootInActiveWindow(),"Open App info")!=null,"Manual App info returns to recovery, not another Settings launch");}
  shot("eight714-manual-usage-return");systemClick("Cancel");check(new InitializationState(home).step()==InitializationState.Step.DONE,"Manual retry does not reset completed initialization");
 }
 private void grantedReturn()throws Exception{
  getTargetContext().getSharedPreferences(InitializationState.PREFS,0).edit().putInt("step",InitializationState.Step.USAGE.ordinal()).commit();recreateHome();awaitSystem("OK");systemClick("OK");awaitSystemPackage("com.android.settings");
  try(ParcelFileDescriptor descriptor=getUiAutomation().executeShellCommand("appops set --uid "+getTargetContext().getPackageName()+" GET_USAGE_STATS allow");java.io.InputStream input=new java.io.FileInputStream(descriptor.getFileDescriptor())){byte[] buffer=new byte[1024];while(input.read(buffer)!=-1){}}
  check(ExternalTasks.permitted(home),"Android actually grants Usage Access");getUiAutomation().performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK);awaitSystem("Allow Notifications Access");check(new InitializationState(home).step()==InitializationState.Step.NOTIFICATIONS,"Granted return advances normally");systemClick("Later");awaitSystem("Modify System Settings (Brightness)");systemClick("Later");awaitSystem("Allow Location");systemClick("Later");await(()->new InitializationState(home).step()==InitializationState.Step.DONE,"Granted retry completes remaining optional setup");
 }
}
