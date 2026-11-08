package com.nthexperience.desktop;

import org.json.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/** Bounded portable local data. Device contacts/events and account credentials never enter these records. */
final class EightPimModels {
    static final String EVENTS="eight_appointments",PEOPLE="eight_people",DRAFTS="eight_mail_drafts",PLACES="eight_map_places",ALBUMS="eight_photo_albums";
    static final List<String> KEYS=Collections.unmodifiableList(Arrays.asList(EVENTS,PEOPLE,DRAFTS,PLACES,ALBUMS));
    static int limit(String key){return key.equals(EVENTS)?512:key.equals(PEOPLE)?500:key.equals(DRAFTS)?50:key.equals(PLACES)?200:32;}
    static String text(JSONObject row,String key,int max)throws JSONException{Object value=row.opt(key);if(value==null)return "";if(!(value instanceof String)||((String)value).length()>max||((String)value).indexOf('\0')>=0)throw new JSONException("Invalid "+key);return (String)value;}
    static long number(JSONObject row,String key)throws JSONException{Object v=row.get(key);if(!(v instanceof Number))throw new JSONException("Invalid "+key);double d=((Number)v).doubleValue();long n=((Number)v).longValue();if(!Double.isFinite(d)||d!=n)throw new JSONException("Invalid "+key);return n;}
    static boolean bool(JSONObject row,String key)throws JSONException{Object v=row.opt(key);if(v==null)return false;if(!(v instanceof Boolean))throw new JSONException("Invalid "+key);return (Boolean)v;}
    static JSONObject clean(String key,JSONObject row)throws JSONException{
        JSONObject out=new JSONObject();String id=text(row,"id",80);if(!id.matches("[A-Za-z0-9_-]{1,80}"))throw new JSONException("Invalid local identifier");out.put("id",id);
        if(key.equals(EVENTS)){
            String title=text(row,"title",256).trim(),zone=text(row,"zone",80);long start=number(row,"start"),end=number(row,"end");
            if(title.isEmpty()||start<0||end<=start||end>7258118400000L||end-start>366L*86400000L)throw new JSONException("Enter a title and an end after the start (up to one year).");
            if(!Arrays.asList(TimeZone.getAvailableIDs()).contains(zone))throw new JSONException("Unknown appointment time zone");
            out.put("title",title).put("start",start).put("end",end).put("zone",zone).put("allDay",bool(row,"allDay")).put("location",text(row,"location",512)).put("notes",text(row,"notes",4096));
        }else if(key.equals(PEOPLE)){
            String name=text(row,"name",256).trim();if(name.isEmpty())throw new JSONException("Enter a contact name.");
            out.put("name",name).put("phone",single(text(row,"phone",160))).put("email",single(text(row,"email",320))).put("address",text(row,"address",1024)).put("notes",text(row,"notes",2048)).put("favorite",bool(row,"favorite"));
        }else if(key.equals(DRAFTS)){
            for(String field:new String[]{"to","cc","bcc"})out.put(field,single(text(row,field,2048)));
            out.put("subject",single(text(row,"subject",512))).put("body",text(row,"body",16384));
            JSONArray attachments=row.optJSONArray("attachments");if(attachments==null)attachments=new JSONArray();if(attachments.length()>10)throw new JSONException("Choose up to ten attachments.");JSONArray links=new JSONArray();Set<String> seen=new HashSet<>();
            for(int i=0;i<attachments.length();i++){Object v=attachments.get(i);if(!(v instanceof String)||((String)v).length()>4096||!((String)v).startsWith("content://"))throw new JSONException("Invalid attachment");if(seen.add((String)v))links.put(v);}out.put("attachments",links);
        }else if(key.equals(PLACES)){
            String name=text(row,"name",256).trim();double lat=coordinate(row,"lat",85),lon=coordinate(row,"lon",180);if(name.isEmpty())throw new JSONException("Enter a place name.");out.put("name",name).put("lat",lat).put("lon",lon).put("notes",text(row,"notes",2048));
        }else if(key.equals(ALBUMS)){
            String uri=text(row,"uri",4096),name=text(row,"name",256).trim();if(!uri.startsWith("content://")||name.isEmpty())throw new JSONException("Choose a photo folder.");out.put("uri",uri).put("name",name);
        }else throw new JSONException("Unknown local collection");
        return out;
    }
    static double coordinate(JSONObject row,String key,double max)throws JSONException{Object n=row.get(key);if(!(n instanceof Number))throw new JSONException("Invalid coordinate");double v=((Number)n).doubleValue();if(!Double.isFinite(v)||Math.abs(v)>max)throw new JSONException("Coordinate is outside the map.");return v;}
    static String single(String value)throws JSONException{if(value.indexOf('\n')>=0||value.indexOf('\r')>=0)throw new JSONException("Use a single line.");return value;}
    static JSONArray clean(String key,JSONArray source)throws JSONException{if(!KEYS.contains(key)||source.length()>limit(key))throw new JSONException("Too many local records");JSONArray out=new JSONArray();Set<String> seen=new HashSet<>();for(int i=0;i<source.length();i++){JSONObject row=clean(key,source.getJSONObject(i));if(!seen.add(row.getString("id")))throw new JSONException("Duplicate local identifier");out.put(row);}return out;}
    static JSONObject create(){JSONObject row=new JSONObject();try{row.put("id",UUID.randomUUID().toString());}catch(JSONException impossible){throw new IllegalStateException(impossible);}return row;}
    static boolean overlaps(JSONObject row,long from,long until){return row.optLong("start")<until&&row.optLong("end")>from;}
    static String recipients(String input,boolean required)throws JSONException{single(input);List<String> result=new ArrayList<>();for(String part:input.split("[,;]")){String email=part.trim();if(email.isEmpty())continue;if(!email.matches("[^\\s@,;<>]+@[^\\s@,;<>]+\\.[^\\s@,;<>]+"))throw new JSONException("Enter complete email addresses, separated by commas.");result.add(email);}if(required&&result.isEmpty())throw new JSONException("Enter at least one recipient.");return String.join(",",result);}
    static String enc(String s){try{return URLEncoder.encode(s,"UTF-8").replace("+","%20");}catch(java.io.UnsupportedEncodingException impossible){throw new IllegalStateException(impossible);}}
    static String mailto(JSONObject draft)throws JSONException{return "mailto:"+recipients(draft.optString("to"),true)+"?cc="+enc(recipients(draft.optString("cc"),false))+"&bcc="+enc(recipients(draft.optString("bcc"),false))+"&subject="+enc(single(draft.optString("subject")))+"&body="+enc(draft.optString("body"));}
    static double[] coordinates(String value){String[] pair=value.trim().split("\\s*,\\s*");if(pair.length!=2)return null;try{double a=Double.parseDouble(pair[0]),b=Double.parseDouble(pair[1]);return Double.isFinite(a)&&Double.isFinite(b)&&Math.abs(a)<=85&&Math.abs(b)<=180?new double[]{a,b}:null;}catch(NumberFormatException e){return null;}}
    static String mapUrl(double lat,double lon,int zoom){if(!Double.isFinite(lat)||!Double.isFinite(lon)||Math.abs(lat)>85||Math.abs(lon)>180)throw new IllegalArgumentException("Invalid map coordinates");double d=180/Math.pow(2,Math.max(1,Math.min(18,zoom)));double left=Math.max(-180,lon-d),right=Math.min(180,lon+d),bottom=Math.max(-85,lat-d*.65),top=Math.min(85,lat+d*.65);return String.format(Locale.ROOT,"https://www.openstreetmap.org/export/embed.html?bbox=%.7f%%2C%.7f%%2C%.7f%%2C%.7f&layer=mapnik&marker=%.7f%%2C%.7f",left,bottom,right,top,lat,lon);}
    static String icsText(String value){return value.replace("\\","\\\\").replace("\r\n","\n").replace("\r","\n").replace("\n","\\n").replace(";","\\;").replace(",","\\,");}
    static String fold(String line){StringBuilder out=new StringBuilder();int bytes=0;for(int at=0;at<line.length();){int cp=line.codePointAt(at);String s=new String(Character.toChars(cp));int n=s.getBytes(StandardCharsets.UTF_8).length;if(bytes+n>75){out.append("\r\n ");bytes=1;}out.append(s);bytes+=n;at+=Character.charCount(cp);}return out+"\r\n";}
    static String ics(JSONObject input)throws JSONException{JSONObject row=clean(EVENTS,input);SimpleDateFormat utc=new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'",Locale.ROOT),date=new SimpleDateFormat("yyyyMMdd",Locale.ROOT);utc.setTimeZone(TimeZone.getTimeZone("UTC"));date.setTimeZone(TimeZone.getTimeZone(row.getString("zone")));boolean all=row.getBoolean("allDay");List<String> lines=Arrays.asList("BEGIN:VCALENDAR","VERSION:2.0","PRODID:-//Nth Experience//Desktop Eight//EN","BEGIN:VEVENT","UID:"+row.getString("id")+"@nth-desktop-eight","DTSTAMP:"+utc.format(new Date()),(all?"DTSTART;VALUE=DATE:":"DTSTART:")+(all?date:utc).format(new Date(row.getLong("start"))),(all?"DTEND;VALUE=DATE:":"DTEND:")+(all?date:utc).format(new Date(row.getLong("end"))),"SUMMARY:"+icsText(row.getString("title")),"LOCATION:"+icsText(row.getString("location")),"DESCRIPTION:"+icsText(row.getString("notes")),"END:VEVENT","END:VCALENDAR");StringBuilder out=new StringBuilder();for(String line:lines)out.append(fold(line));return out.toString();}
    private EightPimModels(){}
}
