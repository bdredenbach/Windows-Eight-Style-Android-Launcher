package com.nthexperience.desktop;

import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

/** One transient right-hand layer with explicit dismissal and contextual destinations. */
final class EightCharms extends FrameLayout {
    interface Host {void open(String id);void start();void share();String context();}
    final Host host;final LinearLayout panel;final ScrollView scroller;View previousFocus;
    EightCharms(Context context,Host host){
        super(context);this.host=host;setElevation(Aero.dp(context,40));setContentDescription("Eight Charms overlay");setBackgroundColor(0x33000000);setVisibility(GONE);setFocusableInTouchMode(true);TenAppearance.preserve(this);
        setOnClickListener(v->dismiss());panel=Aero.column(context);panel.setGravity(Gravity.CENTER_VERTICAL);panel.setBackgroundColor(0xff171717);panel.setOnClickListener(v->{});scroller=new ScrollView(context);scroller.setFillViewport(true);scroller.setBackgroundColor(0xff171717);scroller.addView(panel,new ScrollView.LayoutParams(-1,-2));addView(scroller,new FrameLayout.LayoutParams(Aero.dp(context,104),-1,Gravity.END));
    }
    private int dp(int value){return Aero.dp(getContext(),value);}
    boolean dismiss(){if(getVisibility()!=VISIBLE)return false;setVisibility(GONE);if(previousFocus!=null&&previousFocus.isShown())previousFocus.requestFocus();previousFocus=null;return true;}
    void toggle(){if(getVisibility()==VISIBLE)dismiss();else show("charms");}
    void show(String kind){
        if(getVisibility()!=VISIBLE)previousFocus=getRootView().findFocus();setVisibility(VISIBLE);bringToFront();panel.removeAllViews();
        FrameLayout.LayoutParams bounds=(FrameLayout.LayoutParams)scroller.getLayoutParams();bounds.width=kind.equals("charms")?dp(104):Math.min(dp(320),Math.max(dp(220),getResources().getDisplayMetrics().widthPixels-dp(30)));scroller.setLayoutParams(bounds);
        panel.setGravity(kind.equals("charms")?Gravity.CENTER_VERTICAL:Gravity.TOP);panel.setPadding(0,dp(16),0,dp(16));
        if(kind.equals("charms")){
            charm("Search","search",()->{dismiss();host.open("@context-search");});charm("Share","share",()->{dismiss();host.share();});charm("Start","start",()->{dismiss();host.start();});charm("Devices","devices",()->show("devices"));charm("Settings","settings",()->show("settings"));
        }else if(kind.equals("volume")||kind.equals("brightness")){
            link("←  Settings",()->show("settings"));LinearLayout controls=Aero.column(getContext());Aero.pad(controls,16);controls.setBackgroundColor(0xfff3f3f3);panel.addView(controls,new LinearLayout.LayoutParams(-1,-2));
            if(kind.equals("brightness"))new BrightnessControl((android.app.Activity)getContext()).content(controls);
            else{android.media.AudioManager audio=(android.media.AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);TextView label=Aero.text(getContext(),"Music volume",18,Aero.INK);controls.addView(label);SeekBar slider=new SeekBar(getContext());slider.setContentDescription("Music volume");slider.setMax(Math.max(1,audio.getStreamMaxVolume(android.media.AudioManager.STREAM_MUSIC)));slider.setProgress(audio.getStreamVolume(android.media.AudioManager.STREAM_MUSIC));slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){public void onStartTrackingTouch(SeekBar s){}public void onStopTrackingTouch(SeekBar s){}public void onProgressChanged(SeekBar s,int value,boolean user){if(user)audio.setStreamVolume(android.media.AudioManager.STREAM_MUSIC,value,0);}});controls.addView(slider,new LinearLayout.LayoutParams(-1,dp(52)));}
        }else{
            link("←  "+(kind.equals("devices")?"Devices":"Settings"),()->show("charms"));
            TextView context=Aero.text(getContext(),host.context(),18,Color.WHITE);context.setPadding(dp(22),dp(20),dp(18),dp(20));panel.addView(context);
            if(kind.equals("devices")){link("Connected devices",()->launch("setting:android:connected"));link("Print",()->launch("setting:android:print"));link("Project / Cast",()->launch("setting:android:cast"));}
            else{String contextName=host.context();boolean app=!contextName.equals("Start")&&!contextName.equals("Desktop")&&!contextName.equals("PC settings");link(contextName.equals("Start")?"Personalize Start":app?"App settings and commands":"Control Panel",()->launch(contextName.equals("Start")?"eight-setting:personalize":app?"@immersive-settings":"@legacycontrol"));link("Wi-Fi",()->launch("setting:android:wifi"));link("Volume",()->show("volume"));link("Brightness",()->show("brightness"));link("Power",()->launch("@power"));View space=new View(getContext());panel.addView(space,new LinearLayout.LayoutParams(1,0,1));link("Change PC settings",()->launch("@settings"));}
        }
        requestFocus();
    }
    private void launch(String id){dismiss();host.open(id);}
    private void charm(String label,String glyph,Runnable action){LinearLayout button=Aero.column(getContext());button.setGravity(Gravity.CENTER);button.setFocusable(true);button.setContentDescription(label+" charm");button.setBackground(TenStyle.hover(getContext(),0xff171717));button.setOnClickListener(v->action.run());button.addView(new TenStyle.Glyph(getContext(),glyph,Color.WHITE),new LinearLayout.LayoutParams(dp(32),dp(32)));TextView text=Aero.text(getContext(),label,13,Color.WHITE);text.setGravity(Gravity.CENTER);LinearLayout.LayoutParams caption=new LinearLayout.LayoutParams(-1,-2);caption.topMargin=dp(6);button.addView(text,caption);panel.addView(button,new LinearLayout.LayoutParams(-1,Math.max(dp(48),Math.min(dp(84),((getHeight()>0?getHeight():getResources().getDisplayMetrics().heightPixels)-dp(32))/5))));}
    private void link(String label,Runnable action){TextView button=Aero.text(getContext(),label,17,Color.WHITE);button.setGravity(Gravity.CENTER_VERTICAL);button.setMinHeight(dp(52));button.setPadding(dp(22),dp(10),dp(18),dp(10));button.setFocusable(true);button.setContentDescription(label);button.setBackground(TenStyle.hover(getContext(),0xff171717));button.setOnClickListener(v->action.run());panel.addView(button,new LinearLayout.LayoutParams(-1,-2));}
}
