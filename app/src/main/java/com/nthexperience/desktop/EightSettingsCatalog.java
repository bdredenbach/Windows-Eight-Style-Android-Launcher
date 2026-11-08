package com.nthexperience.desktop;
import java.util.*;

/** Eight destinations shared by shortcuts, Run, search and Nota. Legacy IDs are aliases only. */
final class EightSettingsCatalog {
    static final class Page {final String id,title;Page(String id,String title){this.id=id;this.title=title;}}
    static final List<Page> PAGES=Collections.unmodifiableList(Arrays.asList(
        new Page("themes","Personalization"),new Page("background","Desktop Background"),new Page("colors","Color and Appearance"),
        new Page("taskbar","Taskbar and Navigation"),new Page("desktop","Desktop Icon Settings"),new Page("display","Display"),
        new Page("sound","Sound"),new Page("fonts","Fonts"),new Page("multitasking","Window Arrangement"),
        new Page("storage","Storage"),new Page("installed","Programs and Features"),new Page("defaults","Default Programs"),
        new Page("profile","User Accounts"),new Page("motion","Display and Motion"),new Page("tools","Nth Tools"),
        new Page("games","Games"),new Page("assistant","Nth Nota"),new Page("clipboard","Clipboard"),
        new Page("about","System"),new Page("backup","Backup and Restore"),new Page("recovery","Recovery"),
        new Page("startup","Startup and Sounds"),new Page("clock","Clock appearance"),
        new Page("taskbarapps","Taskbar apps and access"),new Page("backgroundoptions","Picture and slideshow options")));
    static String canonical(String id){
        for(Page p:PAGES)if(p.id.equals(id))return id;
        switch(id){case "home":case "personalization":return "themes";case "system":return "about";case "apps":case "remote":case "components":case "optional":return "installed";
        case "accounts":return "profile";case "icons":return "desktop";case "sounds":return "sound";case "grouping":return "taskbar";case "sidebar":return "tools";case "accessibility":case "ease":return "motion";case "gaming":return "games";
        case "start":case "tablet":return "pc:personalize";case "notifications":case "notification-access":case "focus":return "pc:notifications";
        case "privacy":case "permissions":return "pc:privacy";case "search":case "search-options":return "pc:search";
        case "network":case "wifi":case "airplane":case "vpn":return "android:"+(id.equals("network")?"network":id);
        case "devices":case "sharing":case "shared":return "android:connected";case "projecting":return "android:cast";
        case "power":return "android:battery";case "updates":case "update":return "android:device";
        case "android-accounts":return "android:accounts";case "bluetooth":case "printing":case "input":case "date":case "language":case "speech":return "android:"+(id.equals("printing")?"print":id);
        case "time":return "android:date";case "lock":return "android:security";default:return "themes";}
    }
    static String title(String id){for(Page p:PAGES)if(p.id.equals(id))return p.title;return "Personalization";}
    private EightSettingsCatalog(){}
}
