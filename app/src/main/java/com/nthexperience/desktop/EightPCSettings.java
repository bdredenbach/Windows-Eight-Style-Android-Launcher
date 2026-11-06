package com.nthexperience.desktop;

import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.Settings;
import android.view.*;
import android.widget.*;
import java.util.function.Consumer;

/** Full-screen PC settings, separate from the traditional desktop Control Panel. */
final class EightPCSettings extends LinearLayout {
    static final String[][] CATEGORIES={{"personalize","Personalize"},{"users","Users"},{"notifications","Notifications"},{"search","Search"},{"share","Share"},{"general","General"},{"privacy","Privacy"},{"devices","Devices"},{"wireless","Wireless"},{"access","Ease of Access"},{"sync","Sync your settings"},{"update","Windows Update"}};
    static final String[][] CATEGORIES_81={{"pcdevices","PC and devices"},{"users","Accounts"},{"searchapps","Search and apps"},{"privacy","Privacy"},{"wireless","Network"},{"time","Time and language"},{"access","Ease of Access"},{"recovery","Update and recovery"}};
    static String[][] categories(DesktopStore store){return EightDefaults.profile(store)==EightProfile.EIGHT_POINT_ONE?CATEGORIES_81:CATEGORIES;}
    final DesktopStore store; final Consumer<String> open; final Runnable changed,back;
    final LinearLayout categories,body;final TextView heading;
    String page="personalize";
    EightPCSettings(Context context,DesktopStore store,Consumer<String> open,Runnable changed,Runnable back){
        super(context);this.store=store;this.open=open;this.changed=changed;this.back=back;
        setOrientation(VERTICAL);setBackgroundColor(0xfff0f0f0);setElevation(Aero.dp(context,28));setContentDescription("Eight PC settings");TenAppearance.preserve(this);
        LinearLayout top=new LinearLayout(context);top.setGravity(Gravity.CENTER_VERTICAL);top.setPadding(dp(16),0,dp(18),0);top.setBackgroundColor(0xffeeeeee);
        TextView close=text("←",26,0xff333333);close.setGravity(Gravity.CENTER);close.setContentDescription("Back from PC settings");close.setFocusable(true);close.setOnClickListener(v->back.run());top.addView(close,new LayoutParams(dp(48),dp(72)));
        heading=text("PC settings",29,0xff333333);top.addView(heading,new LayoutParams(0,-2,1));addView(top,new LayoutParams(-1,dp(78)));
        LinearLayout split=new LinearLayout(context);addView(split,new LayoutParams(-1,0,1));
        ScrollView navigation=new ScrollView(context);navigation.setBackgroundColor(0xffeeeeee);categories=Aero.column(context);navigation.addView(categories);split.addView(navigation,new LayoutParams(dp(132),-1));
        ScrollView scroll=new ScrollView(context);scroll.setFillViewport(true);body=Aero.column(context);body.setPadding(dp(18),dp(18),dp(18),dp(24));scroll.addView(body);split.addView(scroll,new LayoutParams(0,-1,1));
        addOnLayoutChangeListener((v,l,t,r,b,ol,ot,or,ob)->{int width=r-l>=dp(600)?dp(210):Math.min(dp(140),Math.max(dp(105),(r-l)/3));if(navigation.getLayoutParams().width!=width){navigation.getLayoutParams().width=width;navigation.requestLayout();}});
        render();
    }
    private int dp(int value){return Aero.dp(getContext(),value);}
    private TextView text(String value,int size,int color){return Aero.text(getContext(),value,size,color);}
    void navigate(String page){this.page=page;normalizePage();render();}
    private void normalizePage(){if(EightDefaults.profile(store)==EightProfile.EIGHT_POINT_ONE){if(page.equals("personalize")||page.equals("devices"))page="pcdevices";if(page.equals("search")||page.equals("share")||page.equals("notifications"))page="searchapps";if(page.equals("general")||page.equals("sync")||page.equals("update"))page="recovery";}else{if(page.equals("pcdevices"))page="personalize";if(page.equals("searchapps"))page="search";if(page.equals("recovery")||page.equals("time"))page="general";}boolean known=false;for(String[] row:categories(store))if(row[0].equals(page))known=true;if(!known)page=EightDefaults.profile(store)==EightProfile.EIGHT?"personalize":"pcdevices";}
    void render(){
        normalizePage();
        categories.removeAllViews();for(String[] row:categories(store)){TextView button=text(row[1],15,0xff222222);button.setGravity(Gravity.CENTER_VERTICAL);button.setMinHeight(dp(52));button.setPadding(dp(15),dp(10),dp(10),dp(10));button.setBackgroundColor(page.equals(row[0])?0xffd4d4d4:Color.TRANSPARENT);button.setContentDescription("PC settings category "+row[1]);button.setFocusable(true);button.setOnClickListener(v->navigate(row[0]));categories.addView(button,new LayoutParams(-1,-2));}
        body.removeAllViews();String title=page;for(String[] row:categories(store))if(row[0].equals(page))title=row[1];body.addView(text(title,25,0xff222222));
        switch(page){
            case "personalize":personalize();break;
            case "pcdevices":personalize();button("Lock screen",()->open.accept("setting:android:security"));button("Mouse and touchpad",()->open.accept("setting:android:input"));button("Typing",()->open.accept("setting:android:input"));button("Corners and edges",()->open.accept("eight-desktop:multitasking"));button("Power and sleep",()->open.accept("setting:android:battery"));button("PC info",()->open.accept("ten-setting:about"));button("Display",()->open.accept("setting:android:display"));button("Storage",()->open.accept("setting:android:storage"));button("Connected devices",()->open.accept("setting:android:connected"));break;
            case "searchapps":note("Search local apps, settings and linked files together, or choose a scope.");button("Search everywhere",()->open.accept("eight-search:Everywhere"));button("Apps",()->open.accept("eight-search:Apps"));button("Files",()->open.accept("eight-search:Files"));button("App notifications",()->open.accept("setting:android:notifications"));button("Default apps",()->open.accept("setting:android:defaults"));break;
            case "time":button("Date and time",()->open.accept("setting:android:date"));button("Language",()->open.accept("setting:android:language"));button("Keyboard",()->open.accept("setting:android:input"));break;
            case "recovery":note("Manage Eight backups and device updates.");button("Backup and restore",()->open.accept("@backup"));button("Android device information and updates",()->open.accept("setting:android:device"));button("Default Home app",()->open.accept("setting:home"));button("Startup and sounds",()->open.accept("setting:page:startup"));break;
            case "users":note("Your local Nth profile");EditText name=new EditText(getContext());name.setSingleLine();name.setText(store.prefs.getString("desktop_name","Nth Experience"));name.setContentDescription("Eight profile name");body.addView(name,new LayoutParams(-1,dp(48)));button("Save name",()->{String value=name.getText().toString().trim();if(value.isEmpty()||value.length()>60){name.setError("Use 1–60 characters.");return;}store.prefs.edit().putString("desktop_name",value).apply();changed.run();});button("Choose profile picture",()->open.accept("setting:page:accounts"));button("Android accounts",()->open.accept("setting:android:accounts"));break;
            case "notifications":note("Android controls notification permissions and access.");button("Android app notifications",()->open.accept("setting:android:notifications"));break;
            case "search":note("Search actual installed apps, PC settings and linked files.");button("Apps",()->open.accept("eight-search:Apps"));button("Settings",()->open.accept("eight-search:Settings"));button("Files",()->open.accept("eight-search:Files"));break;
            case "share":note("Use Share in File Explorer, Photos or the browser to send your selected content.");button("File Explorer",()->open.accept("@files"));button("Photos",()->open.accept("@photos"));break;
            case "general":toggle("Reduce motion","reduce_motion",false);button("Default Home app",()->open.accept("setting:home"));button("Startup and sounds",()->open.accept("setting:page:startup"));button("Language and keyboard",()->open.accept("setting:android:language"));button("Backup and restore",()->open.accept("@backup"));break;
            case "privacy":note("Choose which Android permissions Eight can use. Nota cloud assistance remains optional and free.");button("App permissions",()->open.accept("setting:android:permissions"));button("Nth Nota",()->open.accept("@nota"));break;
            case "devices":button("Connected devices",()->open.accept("setting:android:connected"));button("Printing",()->open.accept("setting:android:print"));button("Cast",()->open.accept("setting:android:cast"));break;
            case "wireless":button("Wi-Fi",()->open.accept("setting:android:wifi"));button("Bluetooth",()->open.accept("setting:android:bluetooth"));button("Airplane mode",()->open.accept("setting:android:airplane"));break;
            case "access":toggle("Reduce motion","reduce_motion",false);toggle("Visible Start button in Windows 8","eight_visible_start",false);button("Android accessibility",()->open.accept("setting:android:accessibility"));button("Fonts",()->open.accept("ten-setting:fonts"));break;
            case "sync":note("Export your Nth settings and both Start layouts, then restore them on another installation of Eight.");button("Backup and restore",()->open.accept("@backup"));break;
            case "update":note("Nth Desktop Eight "+BuildConfig.VERSION_NAME+"\n"+EightDefaults.profile(store).label+" shell\nAndroid "+android.os.Build.VERSION.RELEASE);button("Android device information and updates",()->open.accept("setting:android:device"));break;
        }
    }
    private void personalize(){
        note("Shell experience");RadioGroup profiles=new RadioGroup(getContext());
        for(EightProfile profile:EightProfile.values()){RadioButton radio=new RadioButton(getContext());radio.setId(profile==EightProfile.EIGHT?800:801);radio.setText(profile.label);radio.setTextColor(0xff222222);radio.setContentDescription("Use "+profile.label+" shell");radio.setMinHeight(dp(48));profiles.addView(radio);}
        profiles.check(EightDefaults.profile(store)==EightProfile.EIGHT?800:801);profiles.setOnCheckedChangeListener((group,id)->{store.prefs.edit().putString("eight_profile",id==801?"81":"8").apply();changed.run();render();});body.addView(profiles);
        note("Each shell remembers its own tile sizes, groups and ordering.");
        if(EightDefaults.profile(store)==EightProfile.EIGHT_POINT_ONE){toggle("Open Desktop when Eight launches","eight_boot_desktop",false);toggle("Use desktop wallpaper on Start","eight_start_wallpaper",false);toggle("8.1 Update mouse controls and taskbar apps","eight_81_update",false);toggle("Keep browser address bar visible","eight_always_address",false);}
        button("Start color",()->{String[] names={"Indigo","Plum","Teal","Crimson","Graphite","Deep blue"};int[] colors={0xff21164f,0xff4a1958,0xff004b50,0xff771b35,0xff252525,0xff17385e};Aero.dialog(getContext()).setTitle("Start color").setItems(names,(d,n)->{store.prefs.edit().putInt("eight_start_color",colors[n]).apply();changed.run();}).show();});
        toggle("Live calendar and weather labels","eight_live_tiles",true);
        CheckBox aero=new CheckBox(getContext());aero.setText("Aero desktop finish");aero.setTextColor(0xff222222);aero.setChecked(TenThemes.aero(store.prefs));aero.setMinHeight(dp(48));aero.setOnCheckedChangeListener((v,value)->{store.prefs.edit().putString("ten_shell_style",value?"Aero":"Ten").apply();changed.run();});body.addView(aero);
        button("Desktop background",()->open.accept("setting:page:background"));button("Desktop colors and Aero controls",()->open.accept("ten-setting:colors"));button("Control Panel",()->open.accept("@legacycontrol"));
    }
    private void note(String value){TextView note=text(value,14,0xff555555);note.setPadding(0,dp(16),0,dp(12));body.addView(note);}
    private void button(String label,Runnable action){TextView button=text(label,15,0xff5a268c);button.setMinHeight(dp(50));button.setGravity(Gravity.CENTER_VERTICAL);button.setPadding(0,dp(9),0,dp(9));button.setContentDescription(label);button.setFocusable(true);button.setOnClickListener(v->action.run());body.addView(button,new LayoutParams(-1,-2));}
    private void toggle(String label,String key,boolean fallback){CheckBox box=new CheckBox(getContext());box.setText(label);box.setContentDescription(label);box.setTextColor(0xff222222);box.setChecked(store.prefs.getBoolean(key,fallback));box.setMinHeight(dp(50));box.setOnCheckedChangeListener((v,value)->{store.prefs.edit().putBoolean(key,value).apply();changed.run();});body.addView(box,new LayoutParams(-1,-2));}
}
