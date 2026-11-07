package com.nthexperience.desktop;

import java.util.*;

/** EightPoint One's second level, kept separate from the original Eight category model. */
final class EightSettingsPages {
    static final Map<String,String[][]> ALL=new LinkedHashMap<>();
    static {
        ALL.put("pcdevices",new String[][]{{"lock","Lock screen","setting:android:security"},{"display","Display","eight-desktop:display"},{"bluetooth","Bluetooth","setting:android:bluetooth"},{"devices","Devices","setting:android:connected"},{"mouse","Mouse and touchpad","setting:android:input"},{"typing","Typing","setting:android:input"},{"corners","Corners and edges","eight-desktop:multitasking"},{"power","Power and sleep","setting:android:battery"},{"autoplay","AutoPlay","setting:android:defaults"},{"info","PC info","eight-desktop:about"}});
        ALL.put("users",new String[][]{{"account","Your account",""},{"signin","Sign-in options","setting:android:security"},{"other","Other accounts","setting:android:accounts"}});
        ALL.put("storage",new String[][]{{"files","File storage","@computer"},{"camera","Camera roll","@photos"},{"sync","Sync settings","@backup"},{"metered","Metered connections","setting:android:network"}});
        ALL.put("searchapps",new String[][]{{"search","Search","eight-search:Everywhere"},{"share","Share",""},{"notifications","Notifications","setting:android:notifications"},{"sizes","App sizes","setting:android:apps"},{"defaults","Defaults","setting:android:defaults"}});
        ALL.put("privacy",new String[][]{{"general","General","setting:android:permissions"},{"location","Location","setting:android:location"},{"webcam","Webcam","setting:android:permissions"},{"microphone","Microphone","setting:android:permissions"},{"other","Other devices","setting:android:connected"}});
        ALL.put("wireless",new String[][]{{"connections","Connections","setting:android:wifi"},{"airplane","Airplane mode","setting:android:airplane"},{"proxy","Proxy","setting:android:network"},{"homegroup","HomeGroup",""},{"workplace","Workplace","setting:android:accounts"}});
        ALL.put("time",new String[][]{{"date","Date and time","setting:android:date"},{"language","Region and language","setting:android:language"}});
        ALL.put("access",new String[][]{{"narrator","Narrator","setting:android:accessibility"},{"magnifier","Magnifier","setting:android:accessibility"},{"contrast","High contrast","setting:android:accessibility"},{"keyboard","Keyboard","setting:android:input"},{"mouse","Mouse","setting:android:input"},{"other","Other options",""}});
        ALL.put("recovery",new String[][]{{"update","Windows Update","setting:android:device"},{"history","File History","@backup"},{"recovery","Recovery",""}});
    }
    static String[] find(String category,String id){for(String[] row:ALL.getOrDefault(category,new String[0][]))if(row[0].equals(id))return row;return null;}
    private EightSettingsPages(){}
}
