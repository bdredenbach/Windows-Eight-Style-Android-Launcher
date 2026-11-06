package com.nthexperience.desktop;
import java.util.*;

/** Only launcher-owned tools participate in immersive hosting and snap. */
final class EightAppSpec {
    final String id,title,icon;final int minimum;final boolean compact;
    EightAppSpec(String id,String title,String icon,int minimum,boolean compact){this.id=id;this.title=title;this.icon=icon;this.minimum=minimum;this.compact=compact;}
    static final List<EightAppSpec> ALL=Collections.unmodifiableList(Arrays.asList(
        new EightAppSpec("@news","News","news",320,true),
        new EightAppSpec("@notes","Notepad","notes",240,true),
        new EightAppSpec("@nota","Nth Nota","notes",280,true),
        new EightAppSpec("@gadget:calculator","Calculator","calculator",260,true),
        new EightAppSpec("@calendar","Calendar","calendar",280,true),
        new EightAppSpec("@browser","Nth Browser","browser",360,false),
        new EightAppSpec("@photos","Photos","pictures",240,true),
        new EightAppSpec("@weather","Weather","weather",320,true),
        new EightAppSpec("@mediaplayer","Nth Media Player","mediaplayer",360,false)
    ));
    static EightAppSpec get(String id){for(EightAppSpec spec:ALL)if(spec.id.equals(id))return spec;return null;}
}
