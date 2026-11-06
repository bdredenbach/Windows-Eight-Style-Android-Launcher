package com.nthexperience.desktop;

final class EightDefaults {
    static EightProfile profile(DesktopStore store){return EightProfile.from(store.prefs.getString("eight_profile","8"));}
    static void initialize(DesktopStore store){
        if(store.prefs.getBoolean("eight_initialized",false))return;
        store.prefs.edit().putBoolean("eight_initialized",true).putString("eight_profile","8")
            .putBoolean("ten_initialized",true).putBoolean("ten_v2_defaults",true)
            .putBoolean("ten_v13_taskbar_defaults",true).putBoolean("taskbar_static",false)
            .putString("ten_search_mode","Hidden").putBoolean("ten_light",true)
            .putBoolean("ten_apps_dark",false).putBoolean("ten_accent_titles",true)
            .putString("ten_shell_style","Ten").putInt("ten_accent",0xff5899c0)
            .putInt("wallpaper",5).putBoolean("ten_ribbon_expanded",false)
            .putString("desktop_name","Nth Experience").apply();
        for(EightProfile profile:EightProfile.values())new EightStartData(store,profile).save();
    }
    private EightDefaults(){}
}
