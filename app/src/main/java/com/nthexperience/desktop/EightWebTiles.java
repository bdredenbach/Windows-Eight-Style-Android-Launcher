package com.nthexperience.desktop;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import org.json.*;
/** Only explicit, validated HTTP(S) references become persistent Start shortcuts. */
final class EightWebTiles {
    static final String KEY="eight_web_tiles";
    static boolean validId(String id){return id!=null&&id.matches("@web:[a-f0-9]{64}");}
    static String id(String url){try{byte[] digest=MessageDigest.getInstance("SHA-256").digest(url.getBytes(StandardCharsets.UTF_8));StringBuilder out=new StringBuilder("@web:");for(byte b:digest)out.append(String.format(Locale.ROOT,"%02x",b&255));return out.toString();}catch(java.security.NoSuchAlgorithmException e){throw new AssertionError(e);}}
    static JSONObject parse(String value)throws JSONException{
        JSONObject all=new JSONObject(value);if(all.length()>EightStartData.MAX_TILES)throw new JSONException("Too many website tiles");
        Iterator<String> keys=all.keys();while(keys.hasNext()){String key=keys.next();JSONObject item=all.getJSONObject(key);Object url=item.get("url"),title=item.get("title");if(!validId(key)||!(url instanceof String)||((String)url).length()>4096||!BrowserUrls.web((String)url)||!id((String)url).equals(key)||!(title instanceof String)||((String)title).trim().isEmpty()||((String)title).length()>120)throw new JSONException("Invalid website tile");}return all;
    }
    static JSONObject list(DesktopStore store){try{return parse(store.prefs.getString(KEY,"{}"));}catch(JSONException e){return new JSONObject();}}
    static JSONObject get(DesktopStore store,String id){return list(store).optJSONObject(id);}
    static boolean pin(DesktopStore store,String url,String title){
        if(!BrowserUrls.web(url)||url.length()>4096||title.trim().isEmpty()||title.length()>120)return false;
        String id=id(url);JSONObject all=list(store);EightStartData layout=new EightStartData(store,EightDefaults.profile(store));
        if((!all.has(id)&&all.length()>=EightStartData.MAX_TILES)||(layout.item(id)==null&&layout.count()>=EightStartData.MAX_TILES))return false;
        try{all.put(id,new JSONObject().put("url",url).put("title",title.trim()));store.prefs.edit().putString(KEY,all.toString()).apply();if(layout.item(id)==null)return layout.pin(id);return true;}catch(JSONException e){return false;}
    }
    private EightWebTiles(){}
}
