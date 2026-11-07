package com.nthexperience.desktop;

import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.os.*;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.*;
import java.util.*;

/** Android Clock owns scheduled ringing; the stopwatch is local and restart-aware. */
final class EightAlarms extends LinearLayout {
    final Activity activity;final DesktopStore store;final LinearLayout content;final Handler main=new Handler(Looper.getMainLooper());String tab="Alarm";TextView watch;boolean closed;
    final Runnable tick=new Runnable(){public void run(){if(!closed&&isShown()){updateWatch();main.postDelayed(this,100);}}};
    EightAlarms(Activity a,DesktopStore store){super(a);activity=a;this.store=store;setOrientation(VERTICAL);setBackgroundColor(0xff242424);setContentDescription("Eight Alarms app");TenAppearance.preserve(this);
        TextView title=text("Alarms",34);title.setPadding(dp(24),dp(18),dp(24),dp(14));addView(title);LinearLayout tabs=new LinearLayout(a);for(String name:new String[]{"Alarm","Timer","Stopwatch"})tabs.addView(button(name,()->{tab=name;render();}),new LayoutParams(0,dp(48),1));addView(tabs);ScrollView scroll=new ScrollView(a);content=Aero.column(a);content.setPadding(dp(24),dp(18),dp(24),dp(24));scroll.addView(content);addView(scroll,new LayoutParams(-1,0,1));checkBoot();render();}
    int dp(int n){return Aero.dp(getContext(),n);}TextView text(String value,int size){return Aero.text(activity,value,size,Color.WHITE);}
    TextView button(String name,Runnable run){TextView b=text(name,16);b.setContentDescription("Alarms "+name);b.setGravity(Gravity.CENTER);b.setPadding(dp(8),dp(10),dp(8),dp(10));b.setMinHeight(dp(48));b.setFocusable(true);b.setOnClickListener(v->run.run());return b;}
    void render(){content.removeAllViews();watch=null;switch(tab){case "Alarm":
        AlarmManager alarms=activity.getSystemService(AlarmManager.class);AlarmManager.AlarmClockInfo next=alarms==null?null:alarms.getNextAlarmClock();content.addView(text(next==null?"No upcoming device alarm":java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT,java.text.DateFormat.SHORT).format(new Date(next.getTriggerTime())),28));content.addView(text("Alarms ring through your Android Clock app. Complete the confirmation there.",15));
        content.addView(button("Add alarm",()->{Calendar now=Calendar.getInstance();new TimePickerDialog(activity,(v,h,m)->launch(new Intent(AlarmClock.ACTION_SET_ALARM).putExtra(AlarmClock.EXTRA_HOUR,h).putExtra(AlarmClock.EXTRA_MINUTES,m).putExtra(AlarmClock.EXTRA_MESSAGE,"Nth Desktop Eight")),now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),android.text.format.DateFormat.is24HourFormat(activity)).show();}));content.addView(button("Open device alarms",()->launch(new Intent(AlarmClock.ACTION_SHOW_ALARMS))));break;
        case "Timer":EditText minutes=new EditText(activity);minutes.setTextColor(Color.WHITE);minutes.setHintTextColor(0xffbbbbbb);minutes.setSingleLine();minutes.setInputType(2);minutes.setHint("Minutes (1–1440)");minutes.setContentDescription("Timer minutes");content.addView(minutes,new LayoutParams(-1,dp(52)));content.addView(text("Android Clock keeps the timer running and rings when time is up.",15));content.addView(button("Start timer",()->{try{int n=Integer.parseInt(minutes.getText().toString());if(n<1||n>1440)throw new NumberFormatException();launch(new Intent(AlarmClock.ACTION_SET_TIMER).putExtra(AlarmClock.EXTRA_LENGTH,n*60).putExtra(AlarmClock.EXTRA_MESSAGE,"Nth Desktop Eight"));}catch(NumberFormatException ex){minutes.setError("Enter 1–1440 minutes");}}));break;
        case "Stopwatch":watch=text("",42);watch.setGravity(Gravity.CENTER);content.addView(watch,new LayoutParams(-1,dp(110)));content.addView(button(running()?"Pause":"Start",()->{long elapsed=elapsed();store.prefs.edit().putLong("eight_watch_elapsed",elapsed).putLong("eight_watch_start",running()?-1:SystemClock.elapsedRealtime()).putInt("eight_watch_boot",boot()).apply();render();}));content.addView(button("Lap",()->{String prior=store.prefs.getString("eight_watch_laps","");String line=format(elapsed());String saved=(line+"\n"+prior);if(saved.length()>2000)saved=saved.substring(0,2000);store.prefs.edit().putString("eight_watch_laps",saved).apply();render();}));content.addView(button("Reset",()->{store.prefs.edit().putLong("eight_watch_elapsed",0).putLong("eight_watch_start",-1).remove("eight_watch_laps").apply();render();}));content.addView(text(store.prefs.getString("eight_watch_laps",""),16));updateWatch();break;
    }}
    void launch(Intent intent){try{activity.startActivity(intent);}catch(ActivityNotFoundException|SecurityException ex){Aero.dialog(activity).setTitle("Clock app needed").setMessage("Install or enable an Android Clock app to schedule alarms and timers.").setPositiveButton("OK",null).show();}}
    int boot(){return Settings.Global.getInt(activity.getContentResolver(),Settings.Global.BOOT_COUNT,0);}
    void checkBoot(){if(store.prefs.getInt("eight_watch_boot",boot())!=boot())store.prefs.edit().putLong("eight_watch_start",-1).putInt("eight_watch_boot",boot()).apply();}
    boolean running(){return store.prefs.getLong("eight_watch_start",-1)>=0;}
    long elapsed(){long start=store.prefs.getLong("eight_watch_start",-1);return store.prefs.getLong("eight_watch_elapsed",0)+(start>=0?Math.max(0,SystemClock.elapsedRealtime()-start):0);}
    static String format(long time){return String.format(Locale.getDefault(),"%02d:%02d:%02d.%01d",time/3600000,time/60000%60,time/1000%60,time/100%10);}
    void updateWatch(){if(watch!=null)watch.setText(format(elapsed()));}
    @Override public void onVisibilityAggregated(boolean visible){super.onVisibilityAggregated(visible);main.removeCallbacks(tick);if(visible&&!closed&&content!=null){checkBoot();main.post(tick);}}
    void close(){closed=true;main.removeCallbacksAndMessages(null);}
}
