package com.nthexperience.desktop;

import android.net.Uri;
import java.text.*;
import java.util.*;
import org.json.*;

/** Reads real local provider snapshots; it never starts a fetch from a tile animation. */
final class EightLiveTiles {
    final DesktopStore store;final android.content.Context host;private String weatherJson="";private WeatherClient.Snapshot weather;
    EightLiveTiles(DesktopStore store,android.content.Context host){this.store=store;this.host=host;}
    static final List<String> IDS=Collections.unmodifiableList(Arrays.asList("@weather","@calendar","@news","@photos",MediaLibrary.PLAYER,MediaLibrary.CENTER,"@readinglist","@alarms","@mail","@people","@maps","@notes","@sticky","@gadget:worldclock","@gadget:checklist","@gadget:timer","@gadget:stopwatch","@gadget:daysuntil","@gadget:feeds"));
    private final Map<String,String> seenCollections=new HashMap<>();private final Map<String,JSONArray> collections=new HashMap<>();
    private String gadgetSource="";private JSONObject gadgets=new JSONObject();
    static boolean supports(String id){return IDS.contains(id);}
    private JSONArray local(String key){String raw=store.prefs.getString(key,"[]");if(!raw.equals(seenCollections.get(key))){JSONArray rows;try{rows=EightPimModels.clean(key,new JSONArray(raw));}catch(Exception e){rows=new JSONArray();}seenCollections.put(key,raw);collections.put(key,rows);}return collections.get(key);}
    private JSONObject gadget(String kind){String raw=store.prefs.getString("gadget_data","{}");if(!raw.equals(gadgetSource)){gadgetSource=raw;try{gadgets=new JSONObject(raw);GadgetData.validate(gadgets);}catch(Exception e){gadgets=new JSONObject();}}JSONObject data=gadgets.optJSONObject(kind);return data==null?new JSONObject():data;}
    String key(String id){return "eight_tile_live_"+EightDefaults.profile(store).id+"_"+id;}
    boolean individual(String id){return store.prefs.getBoolean(key(id),true);}
    boolean enabled(String id){return supports(id)&&store.prefs.getBoolean("eight_live_tiles",true)&&individual(id);}
    void toggle(String id){store.prefs.edit().putBoolean(key(id),!store.prefs.getBoolean(key(id),true)).apply();}
    private WeatherClient.Snapshot weather(){String json=store.prefs.getString("weather_json","");if(!json.equals(weatherJson)){weatherJson=json;weather=null;try{weather=WeatherClient.Snapshot.parse(json);}catch(Exception ignored){}}return weather;}
    EightTileSnapshot snapshot(String id,long now){
        EightTileSnapshot snapshot=rawSnapshot(id,now);if(snapshot==null)return null;try{String cleared=new JSONObject(store.prefs.getString("eight_tile_cleared_"+EightDefaults.profile(store).id,"{}")).optString(id);return fingerprint(snapshot).equals(cleared)?null:snapshot;}catch(JSONException e){return snapshot;}
    }
    private static String fingerprint(EightTileSnapshot snapshot){StringBuilder key=new StringBuilder();for(EightTileSnapshot.Item item:snapshot.items)key.append(item.key()).append("\n");key.append(snapshot.badge);return EightWebTiles.id(key.toString());}
    void clear(){JSONObject cleared=new JSONObject();long now=System.currentTimeMillis();for(String id:IDS){EightTileSnapshot snapshot=rawSnapshot(id,now);if(snapshot!=null)try{cleared.put(id,fingerprint(snapshot));}catch(JSONException ignored){}}store.prefs.edit().putString("eight_tile_cleared_"+EightDefaults.profile(store).id,cleared.toString()).apply();}
    EightTileSnapshot rawSnapshot(String id,long now){
        if(!enabled(id))return null;
        List<EightTileSnapshot.Item> items=new ArrayList<>();long expiry=0;int badge=0;
        if(id.equals("@calendar")){
            items.add(new EightTileSnapshot.Item("date",new SimpleDateFormat("d",Locale.getDefault()).format(new Date(now)),new SimpleDateFormat("EEEE\nMMMM yyyy",Locale.getDefault()).format(new Date(now))));
            java.util.List<JSONObject> events=new java.util.ArrayList<>();JSONArray local=local(EightPimModels.EVENTS);for(int i=0;i<local.length();i++){JSONObject e=local.optJSONObject(i);if(e!=null&&e.optLong("end")>now)events.add(e);}events.sort(java.util.Comparator.comparingLong(e->e.optLong("start")));for(int i=0;i<Math.min(2,events.size());i++){JSONObject e=events.get(i);long at=EightPimModels.displayTime(e,"start",TimeZone.getDefault());items.add(new EightTileSnapshot.Item(e.optString("id"),e.optString("title"),new SimpleDateFormat(e.optBoolean("allDay")?"MMM d · 'All day'":"MMM d HH:mm",Locale.getDefault()).format(new Date(at))));}
        }else if(id.equals("@weather")){
            try{WeatherClient.Snapshot w=weather();if(w==null)return null;
                boolean cached=now>=store.prefs.getLong("weather_expires",0);
                items.add(new EightTileSnapshot.Item("current",w.temperature(store.prefs.getBoolean("weather_f",Locale.getDefault().getCountry().equals("US")))+"°",store.prefs.getString("weather_city","Weather")+"\n"+(cached?"Cached · ":"")+w.condition()));
            }catch(Exception ignored){}
        }else if(id.equals("@news")||id.equals("@gadget:feeds")){
            expiry=store.prefs.getLong("feed_expires",0);
            // Old fixtures/backups without a freshness stamp are never presented as fresh news.
            if(expiry<=now)return null;
            JSONArray rows=FeedClient.items(store);
            for(int i=0;i<rows.length()&&items.size()<5;i++){JSONObject row=rows.optJSONObject(i);if(row!=null&&BrowserUrls.web(row.optString("url"))&&!row.optString("title").isEmpty())items.add(new EightTileSnapshot.Item(row.optString("url"),row.optString("title"),FeedClient.source(store.prefs.getString("feed_url",""))));}
        }else if(id.equals("@photos")){
            try{JSONArray photos=new JSONArray(store.prefs.getString("eight_tile_photos","[]"));for(int i=0;i<Math.min(5,photos.length());i++){String uri=photos.optString(i);if("content".equals(Uri.parse(uri).getScheme()))items.add(new EightTileSnapshot.Item(uri,"","",uri));}}catch(Exception ignored){}
        }else if((id.equals(MediaLibrary.PLAYER)||id.equals(MediaLibrary.CENTER))&&host instanceof MainActivity){
            MediaPlayback media=((MainActivity)host).existingPlayback();
            if(media!=null&&!media.released&&media.current()!=null&&media.player.getPlaybackState()!=androidx.media3.common.Player.STATE_ENDED&&media.player.getPlaybackState()!=androidx.media3.common.Player.STATE_IDLE&&media.player.getPlayerError()==null)
                items.add(new EightTileSnapshot.Item("track",media.current().name,media.player.isPlaying()?"Now playing":"Paused"));
        }else if(id.equals("@readinglist")){
            JSONArray rows=EightReadingList.items(store);for(int i=0;i<rows.length();i++){JSONObject row=rows.optJSONObject(i);if(row==null)continue;if(!row.optBoolean("read"))badge++;if(items.size()<5)items.add(new EightTileSnapshot.Item(row.optString("url"),row.optString("title"),row.optBoolean("read")?"Saved article":"Unread"));}
        }else if(id.equals("@mail")){
            int count=local(EightPimModels.DRAFTS).length();badge=count;items.add(new EightTileSnapshot.Item("count",Integer.toString(count),count==1?"Local draft\nOpen Mail to continue":"Local drafts\nSaved in Eight"));
        }else if(id.equals("@people")){
            JSONArray rows=local(EightPimModels.PEOPLE);int favorites=0;for(int i=0;i<rows.length();i++)if(rows.optJSONObject(i).optBoolean("favorite"))favorites++;
            items.add(new EightTileSnapshot.Item("count",Integer.toString(rows.length()),"Saved contacts\n"+favorites+" favorite"+(favorites==1?"":"s")));badge=favorites;
        }else if(id.equals("@maps")){
            int count=local(EightPimModels.PLACES).length();items.add(new EightTileSnapshot.Item("count",Integer.toString(count),count==1?"Saved place":"Saved places"));
        }else if(id.equals("@notes")||id.equals("@sticky")){
            String note=store.prefs.getString(id.equals("@notes")?"note":"gadget_note","");int words=GadgetExtras.words(note);items.add(new EightTileSnapshot.Item("count",Integer.toString(words),words==1?"Word saved locally":"Words saved locally"));
        }else if(id.equals("@gadget:worldclock")){
            JSONObject data=gadget("worldclock");String[] zones={data.optString("zone1",TimeZone.getDefault().getID()),data.optString("zone2","UTC")};
            for(int i=0;i<zones.length;i++){TimeZone zone=TimeZone.getTimeZone(zones[i]);DateFormat time=DateFormat.getTimeInstance(DateFormat.SHORT);time.setTimeZone(zone);items.add(new EightTileSnapshot.Item("time"+i,time.format(new Date(now)),zone.getID().replace('_',' ')+"\nWorld Clock"));}
        }else if(id.equals("@gadget:checklist")){
            JSONArray tasks=gadget("checklist").optJSONArray("tasks");int total=tasks==null?0:tasks.length(),done=0;for(int i=0;i<total;i++)if(tasks.optJSONObject(i).optBoolean("done"))done++;
            badge=total-done;items.add(new EightTileSnapshot.Item("count",done+" / "+total,"Tasks complete\n"+badge+" remaining"));
        }else if(id.equals("@gadget:timer")||id.equals("@gadget:stopwatch")){
            String kind=id.substring(8);JSONObject data=gadget(kind);if(data.length()==0)return null;long value=kind.equals("timer")?GadgetData.remaining(data,now,android.os.SystemClock.elapsedRealtime(),StartupExperience.boot(store.context)):GadgetData.stopwatch(data,now,android.os.SystemClock.elapsedRealtime(),StartupExperience.boot(store.context));
            items.add(new EightTileSnapshot.Item("time",GadgetData.duration(value,false),kind.equals("timer")&&value==0?"Countdown complete":data.optBoolean("running")?"Running":"Paused"));
        }else if(id.equals("@gadget:daysuntil")){
            JSONObject data=gadget("daysuntil");if(!data.has("date"))return null;java.time.LocalDate today=java.time.Instant.ofEpochMilli(now).atZone(java.time.ZoneId.systemDefault()).toLocalDate();long days=java.time.temporal.ChronoUnit.DAYS.between(today,GadgetExtras.target(data));
            items.add(new EightTileSnapshot.Item("count",days==0?"Today":Long.toString(Math.abs(days)),(days<0?"Days since ":days>0?"Days until ":"")+data.optString("title","Saved date")));
        }else if(id.equals("@alarms")){
            android.app.AlarmManager manager=store.context.getSystemService(android.app.AlarmManager.class);android.app.AlarmManager.AlarmClockInfo alarm=manager==null?null:manager.getNextAlarmClock();long next=alarm==null?0:alarm.getTriggerTime();if(next>now){expiry=next;items.add(new EightTileSnapshot.Item("next",DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date(next)),"Device alarm · "+DateFormat.getDateInstance(DateFormat.SHORT).format(new Date(next))));}
        }
        return items.isEmpty()?null:new EightTileSnapshot(items,expiry,badge);
    }
}
