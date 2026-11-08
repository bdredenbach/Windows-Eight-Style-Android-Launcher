package com.nthexperience.desktop;
import org.json.*;
/** Only local, explicitly saved records are portable. URI grants and provider rows stay outside backups. */
final class EightPimStore {
    static JSONArray rows(DesktopStore store,String key){try{return EightPimModels.clean(key,new JSONArray(store.prefs.getString(key,"[]")));}catch(JSONException|ClassCastException invalid){return new JSONArray();}}
    static JSONObject get(DesktopStore store,String key,String id){JSONArray rows=rows(store,key);for(int i=0;i<rows.length();i++){JSONObject row=rows.optJSONObject(i);if(row!=null&&id.equals(row.optString("id")))return row;}return null;}
    static JSONObject save(DesktopStore store,String key,JSONObject value)throws JSONException{JSONObject clean=EightPimModels.clean(key,value);JSONArray rows=rows(store,key),out=new JSONArray();boolean found=false;for(int i=0;i<rows.length();i++){JSONObject row=rows.getJSONObject(i);if(row.getString("id").equals(clean.getString("id"))){out.put(clean);found=true;}else out.put(row);}if(!found){if(out.length()>=EightPimModels.limit(key))throw new JSONException("This collection is full. Remove an item first.");out.put(clean);}if(!store.prefs.edit().putString(key,out.toString()).commit())throw new JSONException("The item could not be saved.");return clean;}
    static void delete(DesktopStore store,String key,String id)throws JSONException{JSONArray rows=rows(store,key),out=new JSONArray();for(int i=0;i<rows.length();i++){JSONObject row=rows.getJSONObject(i);if(!id.equals(row.getString("id")))out.put(row);}if(!store.prefs.edit().putString(key,out.toString()).commit())throw new JSONException("The change could not be saved.");}
    private EightPimStore(){}
}
