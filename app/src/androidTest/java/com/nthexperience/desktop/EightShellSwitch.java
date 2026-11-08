package com.nthexperience.desktop;

import android.app.*;
import android.os.SystemClock;
import android.view.View;
import java.util.concurrent.*;

/** Select the real radio control and follow the resulting, bounded Activity recreation. */
final class EightShellSwitch {
    static MainActivity choose(Instrumentation test,MainActivity current,EightProfile profile)throws Exception{
        boolean changing=EightDefaults.profile(new DesktopStore(current))!=profile;
        Instrumentation.ActivityMonitor monitor=test.addMonitor(MainActivity.class.getName(),null,false);
        try{
            ui(current,()->{
                current.open("@settings");
                View choice=EightSmoke.description(current.getWindow().getDecorView(),"Use "+profile.label+" shell");
                if(choice==null||!choice.isShown())throw new AssertionError("Visible shell selector: "+profile.label);
                choice.performClick();
            });
            MainActivity result=current;
            if(changing){
                Activity next=monitor.waitForActivityWithTimeout(15000);
                if(!(next instanceof MainActivity)||next==current)throw new AssertionError("Shell change refreshes the launcher Activity");
                result=(MainActivity)next;
                MainActivity target=result;boolean[] ready={false};long until=SystemClock.uptimeMillis()+15000;
                while(!ready[0]&&SystemClock.uptimeMillis()<until){
                    ui(target,()->{
                        View root=target.getWindow().getDecorView();
                        EightStartScreen start=EightSmoke.find(root,EightStartScreen.class);
                        EightPCSettings settings=EightSmoke.find(root,EightPCSettings.class);
                        ready[0]=root.getWidth()>0&&start!=null&&settings!=null&&!settings.isShown()
                            &&start.isShown()==(profile==EightProfile.EIGHT);
                    });
                    if(!ready[0])SystemClock.sleep(50);
                }
                if(!ready[0])throw new AssertionError("Refreshed shell reaches its default Desktop/Start surface");
                if(!current.isDestroyed())throw new AssertionError("Previous shell Activity is released");
            }
            if(monitor.waitForActivityWithTimeout(400)!=null)throw new AssertionError("Shell selection must not refresh twice or refresh an unchanged profile");
            test.waitForIdleSync();
            return result;
        }finally{test.removeMonitor(monitor);}
    }
    private static void ui(MainActivity home,Runnable action)throws Exception{
        FutureTask<Void> task=new FutureTask<>(action,null);home.runOnUiThread(task);task.get(20,TimeUnit.SECONDS);
    }
    private EightShellSwitch(){}
}
