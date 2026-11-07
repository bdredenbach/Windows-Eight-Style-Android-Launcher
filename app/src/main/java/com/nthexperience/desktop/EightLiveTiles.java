package com.nthexperience.desktop;

import android.net.Uri;
import java.text.*;
import java.util.*;
import org.json.*;

/** Reads real local provider snapshots; it never starts a fetch from a tile animation. */
final class EightLiveTiles {
    final DesktopStore store;final android.content.Context host;private String weatherJson="";private WeatherClient.Snapshot weather;
    EightLiveTiles(DesktopStore store,android.content.Context host){this.store=store;this.host=host;}
    static boolean supports(String id){return Arrays.asList("@weather","@calendar","@news","@photos",MediaLibrary.PLAYER,"@readinglist","@alarms").contains(id);}
    String key(String id){return "eight_tile_live_"+EightDefaults.profile(store).id+"_"+id;}
    boolean individual(String id){return store.prefs.getBoolean(key(id),true);}
    boolean enabled(String id){return supports(id)&&store.prefs.getBoolean("eight_live_tiles",true)&&individual(id);}
    void toggle(String id){store.prefs.edit().putBoolean(key(id),!store.prefs.getBoolean(key(id),true)).apply();}
    private WeatherClient.Snapshot weather(){String json=store.prefs.getString("weather_json","");if(!json.equals(weatherJson)){weatherJson=json;weather=null;try{weather=WeatherClient.Snapshot.parse(json);}catch(Exception ignored){}}return weather;}
    EightTileSnapshot snapshot(String id,long now){
        EightTileSnapshot snapshot=rawSnapshot(id,now);if(snapshot==null)return null;try{String cleared=new JSONObject(store.prefs.getString("eight_tile_cleared_"+EightDefaults.profile(store).id,"{}")).optString(id);return fingerprint(snapshot).equals(cleared)?null:snapshot;}catch(JSONException e){return snapshot;}
    }
    private static String fingerprint(EightTileSnapshot snapshot){StringBuilder key=new StringBuilder();for(EightTileSnapshot.Item item:snapshot.items)key.append(item.key()).append("\n");key.append(snapshot.badge);return EightWebTiles.id(key.toString());}
    void clear(){JSONObject cleared=new JSONObject();long now=System.currentTimeMillis();for(String id:Arrays.asList("@weather","@calendar","@news","@photos",MediaLibrary.PLAYER,"@readinglist","@alarms")){EightTileSnapshot snapshot=rawSnapshot(id,now);if(snapshot!=null)try{cleared.put(id,fingerprint(snapshot));}catch(JSONException ignored){}}store.prefs.edit().putString("eight_tile_cleared_"+EightDefaults.profile(store).id,cleared.toString()).apply();}
    EightTileSnapshot rawSnapshot(String id,long now){
        if(!enabled(id))return null;
        List<EightTileSnapshot.Item> items=new ArrayList<>();long expiry=0;int badge=0;
        if(id.equals("@calendar")){
            items.add(new EightTileSnapshot.Item("date",new SimpleDateFormat("d",Locale.getDefault()).format(new Date(now)),new SimpleDateFormat("EEEE\nMMMM yyyy",Locale.getDefault()).format(new Date(now))));
        }else if(id.equals("@weather")){
            try{WeatherClient.Snapshot w=weather();if(w==null)return null;
                boolean cached=now>=store.prefs.getLong("weather_expires",0);
                items.add(new EightTileSnapshot.Item("current",w.temperature(store.prefs.getBoolean("weather_f",Locale.getDefault().getCountry().equals("US")))+"°",store.prefs.getString("weather_city","Weather")+"\n"+(cached?"Cached · ":"")+w.condition()));
            }catch(Exception ignored){}
        }else if(id.equals("@news")){
            expiry=store.prefs.getLong("feed_expires",0);
            // Old fixtures/backups without a freshness stamp are never presented as fresh news.
            if(expiry<=now)return null;
            JSONArray rows=FeedClient.items(store);
            for(int i=0;i<rows.length()&&items.size()<5;i++){JSONObject row=rows.optJSONObject(i);if(row!=null&&BrowserUrls.web(row.optString("url"))&&!row.optString("title").isEmpty())items.add(new EightTileSnapshot.Item(row.optString("url"),row.optString("title"),FeedClient.source(store.prefs.getString("feed_url",""))));}
        }else if(id.equals("@photos")){
            try{JSONArray photos=new JSONArray(store.prefs.getString("eight_tile_photos","[]"));for(int i=0;i<Math.min(5,photos.length());i++){String uri=photos.optString(i);if("content".equals(Uri.parse(uri).getScheme()))items.add(new EightTileSnapshot.Item(uri,"","",uri));}}catch(Exception ignored){}
        }else if(id.equals(MediaLibrary.PLAYER)&&host instanceof MainActivity){
            MediaPlayback media=((MainActivity)host).existingPlayback();
            if(media!=null&&!media.released&&media.current()!=null&&media.player.getPlaybackState()!=androidx.media3.common.Player.STATE_ENDED&&media.player.getPlaybackState()!=androidx.media3.common.Player.STATE_IDLE&&media.player.getPlayerError()==null)
                items.add(new EightTileSnapshot.Item("track",media.current().name,media.player.isPlaying()?"Now playing":"Paused"));
        }else if(id.equals("@readinglist")){
            JSONArray rows=EightReadingList.items(store);for(int i=0;i<rows.length();i++){JSONObject row=rows.optJSONObject(i);if(row==null)continue;if(!row.optBoolean("read"))badge++;if(items.size()<5)items.add(new EightTileSnapshot.Item(row.optString("url"),row.optString("title"),row.optBoolean("read")?"Saved article":"Unread"));}
        }else if(id.equals("@alarms")){
            android.app.AlarmManager manager=store.context.getSystemService(android.app.AlarmManager.class);android.app.AlarmManager.AlarmClockInfo alarm=manager==null?null:manager.getNextAlarmClock();long next=alarm==null?0:alarm.getTriggerTime();if(next>now){expiry=next;items.add(new EightTileSnapshot.Item("next",DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date(next)),"Device alarm · "+DateFormat.getDateInstance(DateFormat.SHORT).format(new Date(next))));}
        }
        return items.isEmpty()?null:new EightTileSnapshot(items,expiry,badge);
    }
}
