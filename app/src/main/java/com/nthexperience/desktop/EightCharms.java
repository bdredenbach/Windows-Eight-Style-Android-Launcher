package com.nthexperience.desktop;

import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

/** One transient right-hand layer with explicit dismissal and contextual destinations. */
final class EightCharms extends FrameLayout {
    interface Host {void open(String id);void start();void share();String context();default void changed(){}}
    final Host host;final LinearLayout panel,clockPanel;final ScrollView scroller;final TextView clock,date;View previousFocus;
    private String current="";private boolean preview;private final android.os.Handler handler=new android.os.Handler(android.os.Looper.getMainLooper());
    private final Runnable hidePreview=()->dismiss();private final Runnable tick=new Runnable(){public void run(){updateClock();if(isShown()&&!preview&&current.equals("charms"))handler.postDelayed(this,30000);}};
    EightCharms(Context context,Host host){
        super(context);this.host=host;setElevation(Aero.dp(context,40));setContentDescription("Eight Charms overlay");setBackgroundColor(Color.TRANSPARENT);setVisibility(GONE);setFocusableInTouchMode(true);TenAppearance.preserve(this);
        setOnClickListener(v->dismiss());clockPanel=new LinearLayout(context);clockPanel.setGravity(Gravity.CENTER_VERTICAL);clockPanel.setPadding(dp(16),dp(12),dp(16),dp(12));clockPanel.setBackgroundColor(0xf5171717);clock=Aero.text(context,"",getResources().getConfiguration().screenWidthDp<420?30:40,Color.WHITE);date=Aero.text(context,"",getResources().getConfiguration().screenWidthDp<420?13:16,Color.WHITE);date.setPadding(dp(14),0,0,0);clockPanel.addView(clock);clockPanel.addView(date);FrameLayout.LayoutParams timeBounds=new FrameLayout.LayoutParams(-2,-2,Gravity.BOTTOM|Gravity.START);timeBounds.leftMargin=dp(20);timeBounds.bottomMargin=dp(24);addView(clockPanel,timeBounds);panel=Aero.column(context);panel.setGravity(Gravity.CENTER_VERTICAL);panel.setBackgroundColor(0xff171717);panel.setOnClickListener(v->{});scroller=new ScrollView(context);scroller.setFillViewport(true);scroller.setBackgroundColor(0xff171717);scroller.addView(panel,new ScrollView.LayoutParams(-1,-2));addView(scroller,new FrameLayout.LayoutParams(Aero.dp(context,104),-1,Gravity.END));
    }
    private int dp(int value){return Aero.dp(getContext(),value);}
    boolean dismiss(){if(getVisibility()!=VISIBLE)return false;handler.removeCallbacks(tick);handler.removeCallbacks(hidePreview);scroller.animate().cancel();scroller.setTranslationX(0);scroller.setAlpha(1);preview=false;current="";setVisibility(GONE);if(previousFocus!=null&&previousFocus.isShown())previousFocus.requestFocus();previousFocus=null;return true;}
    void toggle(){if(getVisibility()==VISIBLE&&!preview)dismiss();else show("charms");}
    void show(String kind){
        boolean wasVisible=getVisibility()==VISIBLE;String previous=current;if(!wasVisible)previousFocus=getRootView().findFocus();handler.removeCallbacks(tick);handler.removeCallbacks(hidePreview);scroller.animate().cancel();scroller.setTranslationX(0);scroller.setAlpha(1);current=kind;preview=kind.equals("preview");boolean rail=preview||kind.equals("charms");setVisibility(VISIBLE);bringToFront();panel.removeAllViews();
        FrameLayout.LayoutParams bounds=(FrameLayout.LayoutParams)scroller.getLayoutParams();int width=getWidth()>0?getWidth():getResources().getDisplayMetrics().widthPixels;bounds.width=rail?dp(width<dp(500)?72:88):Math.min(dp(346),Math.max(dp(216),width-dp(40)));scroller.setLayoutParams(bounds);
        int color=preview?Color.TRANSPARENT:rail?0xff171717:getContext().getSharedPreferences("desktop",0).getInt("eight_start_color",0xff21164f);panel.setBackgroundColor(color);scroller.setBackgroundColor(color);clockPanel.setVisibility(kind.equals("charms")?VISIBLE:GONE);updateClock();panel.setGravity(rail?Gravity.CENTER_VERTICAL:Gravity.TOP);panel.setPadding(0,dp(16),0,dp(16));
        if(rail){
            charm("Search","search",()->{dismiss();host.open("@context-search");});charm("Share","share",()->{dismiss();host.share();});charm("Start","start",()->{dismiss();host.start();});charm("Devices","devices",()->show("devices"));charm("Settings","settings",()->show("settings"));
        }else if(kind.equals("tiles")){tiles();
        }else if(kind.equals("volume")||kind.equals("brightness")){
            link("←  Settings",()->show("settings"));LinearLayout controls=Aero.column(getContext());Aero.pad(controls,16);controls.setBackgroundColor(0xfff3f3f3);panel.addView(controls,new LinearLayout.LayoutParams(-1,-2));
            if(kind.equals("brightness"))new BrightnessControl((android.app.Activity)getContext()).content(controls);
            else{android.media.AudioManager audio=(android.media.AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);TextView label=Aero.text(getContext(),"Music volume",18,Aero.INK);controls.addView(label);SeekBar slider=new SeekBar(getContext());slider.setContentDescription("Music volume");slider.setMax(Math.max(1,audio.getStreamMaxVolume(android.media.AudioManager.STREAM_MUSIC)));slider.setProgress(audio.getStreamVolume(android.media.AudioManager.STREAM_MUSIC));slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){public void onStartTrackingTouch(SeekBar s){}public void onStopTrackingTouch(SeekBar s){}public void onProgressChanged(SeekBar s,int value,boolean user){if(user)audio.setStreamVolume(android.media.AudioManager.STREAM_MUSIC,value,0);}});controls.addView(slider,new LinearLayout.LayoutParams(-1,dp(52)));}
        }else{
            TextView title=Aero.text(getContext(),kind.equals("devices")?"Devices":"Settings",28,Color.WHITE);title.setPadding(dp(26),dp(4),dp(18),dp(18));title.setContentDescription(kind.equals("devices")?"Devices":"Settings");title.setFocusable(true);title.setOnClickListener(v->show("charms"));panel.addView(title);
            TextView context=Aero.text(getContext(),host.context(),18,Color.WHITE);context.setPadding(dp(22),dp(20),dp(18),dp(20));panel.addView(context);
            if(kind.equals("devices")){link("Connected devices",()->launch("setting:android:connected"));link("Print",()->launch("setting:android:print"));link("Project / Cast",()->launch("setting:android:cast"));}
            else{String contextName=host.context();if(contextName.equals("Start")){link("Start",()->launch("eight-setting:personalize"));link("Tiles",()->show("tiles"));link("Help",()->launch("eight-help:start"));}
                else if(contextName.equals("Desktop")){link("Control Panel",()->launch("@legacycontrol"));link("Personalization",()->launch("@appearance"));link("PC info",()->launch("setting:page:system"));}
                else if(!contextName.equals("PC settings"))link("App settings and commands",()->launch("@immersive-settings"));
                panel.addView(new View(getContext()),new LinearLayout.LayoutParams(1,0,1));settingsControls();link("Change PC settings",()->launch("@settings"));}

        }
        if(kind.equals("charms"))handler.post(tick);
        if(EightSurfaceMotion.enabled(new DesktopStore(getContext()))&&!preview&&(!wasVisible||!previous.equals(kind))){scroller.setTranslationX(dp(rail?20:70));scroller.setAlpha(.25f);scroller.animate().translationX(0).alpha(1).setDuration(180).start();}
        if(!preview)requestFocus();
    }
    private void updateClock(){java.util.Date now=new java.util.Date();clock.setText(android.text.format.DateFormat.getTimeFormat(getContext()).format(now));date.setText(new java.text.SimpleDateFormat("EEEE\nMMMM d",java.util.Locale.getDefault()).format(now));clockPanel.setContentDescription(clock.getText()+" "+date.getText());}
    void pointer(float x,float y,int width,int height){
        if(width<=0||height<=0)return;boolean corner=x>=width-dp(7)&&(y<dp(16)||y>=height-dp(16));
        if(getVisibility()!=VISIBLE){if(corner)show("preview");return;}if(!current.equals("charms")&&!preview)return;
        if(preview){if(x<width-scroller.getLayoutParams().width-dp(24)){dismiss();return;}if(!corner&&x>=width-scroller.getLayoutParams().width&&y>dp(18)&&y<height-dp(18))show("charms");}
        else if(x<width-scroller.getLayoutParams().width-dp(24)){handler.removeCallbacks(hidePreview);handler.postDelayed(hidePreview,400);}else handler.removeCallbacks(hidePreview);
    }
    @Override public boolean dispatchTouchEvent(MotionEvent event){if(preview&&event.getActionMasked()==MotionEvent.ACTION_DOWN&&event.getX()<getWidth()-scroller.getLayoutParams().width){dismiss();return false;}return super.dispatchTouchEvent(event);}
    @Override protected void onDetachedFromWindow(){handler.removeCallbacksAndMessages(null);super.onDetachedFromWindow();}
    private void tiles(){
        TextView heading=Aero.text(getContext(),"←  Tiles",28,Color.WHITE);heading.setPadding(dp(24),dp(4),dp(20),dp(18));heading.setOnClickListener(v->show("settings"));heading.setContentDescription("Tiles settings Back");heading.setFocusable(true);panel.addView(heading);
        LinearLayout body=Aero.column(getContext());body.setPadding(dp(24),dp(22),dp(24),dp(22));body.setBackgroundColor(Color.WHITE);panel.addView(body,new LinearLayout.LayoutParams(-1,0,1));DesktopStore store=new DesktopStore(getContext());
        Switch tools=new Switch(getContext());tools.setText("Show administrative tools");tools.setTextSize(14);tools.setTextColor(0xff555555);tools.setContentDescription("Show administrative tools");tools.setMinHeight(dp(64));tools.setChecked(store.prefs.getBoolean("eight_admin_tools",false));tools.setOnCheckedChangeListener((v,value)->{new EightStartData(store,EightDefaults.profile(store)).administrative(value);host.changed();});body.addView(tools,new LinearLayout.LayoutParams(-1,-2));
        TextView description=Aero.text(getContext(),"Clear personal info from my tiles",14,0xff555555);description.setPadding(0,dp(18),0,dp(10));body.addView(description);TextView clear=Aero.text(getContext(),"Clear",14,0xff333333);clear.setGravity(Gravity.CENTER);clear.setBackgroundColor(0xffdddddd);clear.setContentDescription("Clear personal info from tiles");clear.setFocusable(true);clear.setOnClickListener(v->{new EightLiveTiles(store,getContext()).clear();host.changed();Toast.makeText(getContext(),"Tile content cleared. Your app data and tile positions are kept.",Toast.LENGTH_LONG).show();});body.addView(clear,new LinearLayout.LayoutParams(dp(100),dp(44)));
    }
    private void settingsControls(){GridLayout grid=new GridLayout(getContext());grid.setColumnCount(3);String[][] controls={{"Wi-Fi","network","setting:android:wifi"},{"Volume","volume","volume"},{"Brightness","sun","brightness"},{"Notifications","actions","setting:android:notifications"},{"Power","power","@power"},{"Keyboard","input","setting:android:input"}};for(int i=0;i<controls.length;i++){String[] item=controls[i];LinearLayout cell=Aero.column(getContext());cell.setGravity(Gravity.CENTER);cell.setFocusable(true);cell.setContentDescription(item[0]);cell.addView(new TenStyle.Glyph(getContext(),item[1],Color.WHITE),new LinearLayout.LayoutParams(dp(27),dp(27)));TextView label=Aero.text(getContext(),item[0],11,Color.WHITE);label.setGravity(Gravity.CENTER);cell.addView(label);cell.setOnClickListener(v->{if(item[2].equals("volume")||item[2].equals("brightness"))show(item[2]);else launch(item[2]);});GridLayout.LayoutParams bounds=new GridLayout.LayoutParams(GridLayout.spec(i/3),GridLayout.spec(i%3,1f));bounds.width=0;bounds.height=dp(72);grid.addView(cell,bounds);}panel.addView(grid,new LinearLayout.LayoutParams(-1,dp(144)));}
    private void launch(String id){dismiss();host.open(id);}
    private void charm(String label,String glyph,Runnable action){LinearLayout button=Aero.column(getContext());button.setGravity(Gravity.CENTER);button.setFocusable(true);button.setContentDescription(label+" charm");button.setBackground(TenStyle.hover(getContext(),Color.TRANSPARENT));button.setOnClickListener(v->action.run());button.addView(new EightGlyph(getContext(),glyph,glyph.equals("start")&&!preview?0xff6749e6:Color.WHITE,false),new LinearLayout.LayoutParams(dp(32),dp(32)));if(!preview){TextView text=Aero.text(getContext(),label,13,Color.WHITE);text.setGravity(Gravity.CENTER);LinearLayout.LayoutParams caption=new LinearLayout.LayoutParams(-1,-2);caption.topMargin=dp(6);button.addView(text,caption);}panel.addView(button,new LinearLayout.LayoutParams(-1,Math.max(dp(48),Math.min(dp(84),((getHeight()>0?getHeight():getResources().getDisplayMetrics().heightPixels)-dp(32))/5))));}
    private void link(String label,Runnable action){TextView button=Aero.text(getContext(),label,17,Color.WHITE);button.setGravity(Gravity.CENTER_VERTICAL);button.setMinHeight(dp(52));button.setPadding(dp(22),dp(10),dp(18),dp(10));button.setFocusable(true);button.setContentDescription(label);button.setBackground(TenStyle.hover(getContext(),Color.TRANSPARENT));button.setOnClickListener(v->action.run());panel.addView(button,new LinearLayout.LayoutParams(-1,-2));}
}
