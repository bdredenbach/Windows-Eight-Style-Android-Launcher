package com.nthexperience.desktop;

import java.util.*;

/** One catalog for discoverability, default Start groups and each owned app's tile identity. */
final class EightTileCatalog {
    static final String SEEDED="eight_nth_tiles_v1";
    static final class Entry {
        final String id,title,icon,group,size,eightOneSize;final int color;final boolean paper;
        Entry(String id,String title,String icon,String group,String size,String eightOneSize,int color,boolean paper){this.id=id;this.title=title;this.icon=icon;this.group=group;this.size=size;this.eightOneSize=eightOneSize;this.color=color;this.paper=paper;}
        String size(EightProfile profile){return profile==EightProfile.EIGHT_POINT_ONE?eightOneSize:size;}
        String title(EightProfile profile){return id.equals("@computer")&&profile==EightProfile.EIGHT_POINT_ONE?"This PC":title;}
        int ink(){return paper?0xff183247:0xffffffff;}
    }
    private static Entry tile(String id,String title,String icon,String group,String size,int color){return new Entry(id,title,icon,group,size,size,color,false);}
    private static Entry tile(String id,String title,String icon,String group,String size,String newer,int color){return new Entry(id,title,icon,group,size,newer,color,false);}
    private static Entry paper(String id,String title,String icon,String group,String size,int color){return new Entry(id,title,icon,group,size,size,color,true);}
    static final List<Entry> ALL=Collections.unmodifiableList(Arrays.asList(
        tile("@desktop","Desktop","desktop","Essentials","Wide",0xff007ca8),
        tile("@nota","Nth Nota","notes","Essentials","Wide",0xff6b3f98),
        tile("@browser",BrowserPanel.NAME,"browser","Essentials","Medium",0xff007db5),
        paper("@files","File Explorer","file-explorer","Essentials","Medium",0xffeee9d9),
        tile("@calendar","Calendar","calendar","Essentials","Medium","Large",0xff64448e),
        tile("@weather","Weather","weather","Essentials","Wide",0xff006c90),
        tile("@mail","Mail","mail","Connect","Wide",0xff0078a4),
        tile("@people","People","contacts","Connect","Medium",0xffad3d16),
        tile("@maps","Maps","network","Connect","Wide",0xff70439a),
        tile("@news","News","news","Connect","Wide",0xffa52824),
        tile("@readinglist","Reading List","document","Connect","Wide",0xff923b31),
        tile("@photos","Photos","pictures-app","Media","Wide","Large",0xffa12a75),
        tile(MediaLibrary.PLAYER,MediaLibrary.PLAYER_TITLE,"mediaplayer","Media","Wide",0xff126d47),
        tile(MediaLibrary.CENTER,MediaLibrary.CENTER_TITLE,"mediacenter","Media","Wide",0xff243e79),
        tile("@reader","Reader","documentfile","Media","Medium",0xff9c241f),
        tile("@soundrecorder","Sound Recorder","music","Media","Medium",0xff8c282d),
        tile("@notes","Notepad","notes","Create","Medium",0xff88500d),
        tile("@sticky","Sticky Notes","sticky","Create","Wide",0xff755a14),
        paper("@wordpad","WordPad","wordpad","Create","Wide",0xffe5ecf4),
        paper("@paint","Paint","paint","Create","Medium",0xffe3efe7),
        paper("@characters","Character Map","fonts","Create","Medium",0xffeee7f3),
        tile("@gadget:calculator","Calculator","calculator","Create","Medium",0xff17604d),
        tile("@gadget:colors","Color Studio","appearance","Create","Medium",0xff633b87),
        paper("@gadget:textcounter","Text Counter","notes","Create","Wide",0xffe7edf0),
        tile("@alarms","Alarms","clock","Plan and play","Medium",0xff9c3523),
        tile("@clock","Clock","clock","Plan and play","Medium",0xff295c75),
        tile("@gadget:worldclock","World Clock","worldclock","Plan and play","Wide",0xff176b76),
        tile("@gadget:checklist","Checklist","checklist","Plan and play","Wide",0xff356a35),
        tile("@gadget:timer","Countdown","timer","Plan and play","Medium",0xff8c4721),
        tile("@gadget:stopwatch","Stopwatch","stopwatch","Plan and play","Medium",0xff255b6a),
        tile("@gadget:daysuntil","Days Until","calendar","Plan and play","Wide",0xff754a86),
        tile("@gadget:dice","Dice Roller","puzzle","Plan and play","Medium","Small",0xff653785),
        tile("@gadget:puzzle","Tile Puzzle","puzzle","Plan and play","Medium",0xff445a25),
        paper("@gadget:converter","Unit Converter","converter","Utilities","Medium",0xffe1edef),
        paper("@gadget:compass","Compass","compass","Utilities","Medium",0xffeee5dc),
        paper("@gadget:storage","Storage Meter","drive","Utilities","Medium",0xffe4e9ee),
        paper("@gadget:network","Network Meter","network","Utilities","Medium",0xffe1edf4),
        tile("@gadget:feeds","Feed Headlines","feeds","Utilities","Wide",0xff9c491d),
        paper("@mobility","Windows Mobility Center","computer","Utilities","Medium",0xffe8edf0),
        paper("@computer","Computer","computer","Your PC","Medium",0xffe1e9f0),
        tile("@settings","PC settings","control","Your PC","Medium",0xff3a5c99),
        paper("@legacycontrol","Control Panel","control","Your PC","Medium",0xffe5eaf0),
        paper("@taskmanager","Task Manager","taskmanager","Your PC","Wide",0xffdeebec),
        paper("@recycle","Recycle Bin","recycle","Your PC","Medium",0xffe6edf0),
        paper("@backup","Backup and Restore","drive","Your PC","Wide",0xffe2eae4),
        paper("@organize","Desktop Organization","folderapps","Your PC","Medium",0xffeee7db),
        paper("@quicklaunch","Quick Launch","programs","Your PC","Medium",0xffeee9e0),
        paper("@workspaces","Saved Workspaces","computer","Your PC","Medium",0xffe3e9f2),
        paper("@recent","Recent Items","document","Your PC","Medium",0xffe9e7e2),
        paper("@filemanagers","File Managers","folderapps","Your PC","Medium",0xffe4ece7),
        tile("@gadgets","Nth Tools","feeds","Your PC","Medium",0xff30616a),
        tile("@help","Help+Tips","information","Your PC","Medium",0xff006b61),
        paper("@run","Run","run","Your PC","Medium",0xffe8e9f0),
        tile("@powershell","Nth Command Prompt","run","Your PC","Medium",0xff242a30)
    ));
    static Entry get(String id){for(Entry entry:ALL)if(entry.id.equals(id))return entry;return null;}
    static List<EightStartData.Group> defaults(EightProfile profile){
        LinkedHashMap<String,EightStartData.Group> groups=new LinkedHashMap<>();
        for(Entry entry:ALL){EightStartData.Group group=groups.get(entry.group);if(group==null){group=new EightStartData.Group(entry.group);groups.put(entry.group,group);}group.items.add(new EightStartData.Item(entry.id,entry.size(profile)));}
        return new ArrayList<>(groups.values());
    }
    private EightTileCatalog(){}
}
