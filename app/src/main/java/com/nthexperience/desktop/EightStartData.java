package com.nthexperience.desktop;

import java.util.*;
import org.json.*;

/** Each shell keeps its own logical tile layout; rotation never writes pixel positions. */
final class EightStartData {
    static final String EIGHT="eight_start_8", EIGHT_ONE="eight_start_81";
    static final int MAX_TILES=128, MAX_GROUPS=16;
    static final class Item {
        final String id; String size;
        Item(String id,String size){this.id=id;this.size=size;}
    }
    static final class Group {
        String name; final List<Item> items=new ArrayList<>();
        Group(String name){this.name=name;}
    }
    final DesktopStore store; final EightProfile profile; final List<Group> groups;
    EightStartData(DesktopStore store,EightProfile profile){
        this.store=store;this.profile=profile;
        List<Group> loaded;
        try{loaded=parse(store.prefs.getString(key(profile),""),profile);}catch(JSONException e){loaded=defaults();}
        groups=loaded;
    }
    static String key(EightProfile profile){return profile==EightProfile.EIGHT?EIGHT:EIGHT_ONE;}
    private static List<Group> defaults(){
        List<Group> result=new ArrayList<>();
        Group essentials=new Group("Essentials");
        String[] ids={"@desktop","@nota","@browser","@files","@calendar","@weather"};
        for(String id:ids)essentials.items.add(new Item(id,id.equals("@desktop")||id.equals("@nota")||id.equals("@weather")?"Wide":"Medium"));
        result.add(essentials);Group tools=new Group("Nth tools");
        for(String id:new String[]{"@photos",MediaLibrary.PLAYER,"@notes","@gadget:calculator","@settings","@legacycontrol"})tools.items.add(new Item(id,"Medium"));
        result.add(tools);return result;
    }
    static List<Group> parse(String value,EightProfile profile)throws JSONException{
        JSONObject root=new JSONObject(value);JSONArray raw=root.getJSONArray("groups");
        if(root.getInt("version")!=1||raw.length()<1||raw.length()>MAX_GROUPS)throw new JSONException("Invalid Eight Start groups");
        List<Group> groups=new ArrayList<>();Set<String> names=new HashSet<>(),ids=new HashSet<>();
        for(int n=0;n<raw.length();n++){
            JSONObject object=raw.getJSONObject(n);Object name=object.get("name");
            if(!(name instanceof String)||((String)name).trim().isEmpty()||((String)name).length()>40||!names.add(((String)name).toLowerCase(Locale.ROOT)))throw new JSONException("Invalid tile group name");
            Group group=new Group((String)name);JSONArray items=object.getJSONArray("items");
            for(int i=0;i<items.length();i++){
                JSONObject item=items.getJSONObject(i);Object id=item.get("id"),size=item.get("size");
                if(!(id instanceof String)||!(size instanceof String)||!profile.supports((String)size)||!ids.add((String)id)||ids.size()>MAX_TILES||!validId((String)id))throw new JSONException("Invalid Eight Start tile");
                group.items.add(new Item((String)id,(String)size));
            }
            groups.add(group);
        }
        return groups;
    }
    static boolean validId(String id){return id.equals("@desktop")||EightWebTiles.validId(id)||DesktopBackup.shortcut(id,new JSONObject(),false);}
    void save(){
        try{JSONArray array=new JSONArray();for(Group group:groups){JSONArray items=new JSONArray();for(Item item:group.items)items.put(new JSONObject().put("id",item.id).put("size",item.size));array.put(new JSONObject().put("name",group.name).put("items",items));}
            store.prefs.edit().putString(key(profile),new JSONObject().put("version",1).put("groups",array).toString()).apply();
        }catch(JSONException e){throw new IllegalStateException(e);}
    }
    Item item(String id){for(Group group:groups)for(Item item:group.items)if(item.id.equals(id))return item;return null;}
    boolean pin(String id){if(item(id)!=null||!validId(id)||count()>=MAX_TILES)return false;groups.get(0).items.add(new Item(id,"Medium"));save();return true;}
    int count(){int count=0;for(Group group:groups)count+=group.items.size();return count;}
    void unpin(String id){for(Group group:groups)group.items.removeIf(i->i.id.equals(id));save();if(EightWebTiles.validId(id))EightWebTiles.removeIfUnpinned(store,id);}
    void administrative(boolean show){
        String key="eight_admin_added_"+profile.id;List<String> inserted=new ArrayList<>();try{JSONArray rows=new JSONArray(store.prefs.getString(key,"[]"));for(int i=0;i<rows.length();i++)inserted.add(rows.getString(i));}catch(JSONException ignored){}
        if(show){Group group=null;for(Group candidate:groups)if(candidate.name.equals("Administrative tools"))group=candidate;if(group==null&&groups.size()<MAX_GROUPS){group=new Group("Administrative tools");groups.add(group);}if(group!=null)for(String id:new String[]{"@taskmanager","@management","@events","@devices"})if(item(id)==null&&count()<MAX_TILES){group.items.add(new Item(id,"Medium"));inserted.add(id);}}
        else{for(Group group:groups)group.items.removeIf(item->inserted.contains(item.id));groups.removeIf(group->groups.size()>1&&group.name.equals("Administrative tools")&&group.items.isEmpty());inserted.clear();}
        save();store.prefs.edit().putBoolean("eight_admin_tools",show).putString(key,new JSONArray(inserted).toString()).apply();
    }
    void move(String id,Group target,int position){Item item=item(id);if(item==null)return;for(Group group:groups)group.items.remove(item);target.items.add(Math.max(0,Math.min(position,target.items.size())),item);save();}
    boolean name(Group group,String value){String name=value.trim();if(name.isEmpty()||name.length()>40||groups.stream().anyMatch(g->g!=group&&g.name.equalsIgnoreCase(name)))return false;if(group==null){if(groups.size()>=MAX_GROUPS)return false;groups.add(new Group(name));}else group.name=name;save();return true;}
}
