package com.nthexperience.desktop;

final class EightDefaults {
    static EightProfile profile(DesktopStore store){return EightProfile.from(store.prefs.getString("eight_profile","8"));}
    static boolean selectProfile(DesktopStore store,EightProfile selected){
        if(profile(store)==selected)return false;
        boolean updated=selected==EightProfile.EIGHT_POINT_ONE;
        store.prefs.edit().putString("eight_profile",selected.id)
            .putBoolean("eight_boot_desktop",updated)
            .putBoolean("eight_start_wallpaper",updated)
            .putBoolean("eight_81_update",updated).apply();
        return true;
    }
    static void initialize(DesktopStore store){
        if(!store.prefs.getBoolean("eight_v3_surfaces",false)){store.prefs.edit().putBoolean("eight_v3_surfaces",true).putBoolean("ten_apps_dark",false).putBoolean("ten_light",true).putBoolean("ten_accent_titles",true).putBoolean("ten_accent_shell",false).putBoolean("ten_transparency",false).apply();}
        if(store.prefs.getBoolean("eight_initialized",false))return;
        store.prefs.edit().putBoolean("eight_initialized",true).putString("eight_profile","8")
            .putBoolean("ten_initialized",true).putBoolean("ten_v2_defaults",true)
            .putBoolean("ten_v13_taskbar_defaults",true).putBoolean("taskbar_static",false)
            .putString("ten_search_mode","Hidden").putBoolean("ten_light",true)
            .putBoolean("ten_apps_dark",false).putBoolean("ten_accent_titles",true)
            .putString("ten_shell_style","Ten").putInt("ten_accent",0xff5899c0)
            .putInt("wallpaper",11).putBoolean("ten_ribbon_expanded",false)
            .putString("desktop_name","Nth Experience").apply();
        for(EightProfile profile:EightProfile.values())new EightStartData(store,profile).save();
    }
    private EightDefaults(){}
}
