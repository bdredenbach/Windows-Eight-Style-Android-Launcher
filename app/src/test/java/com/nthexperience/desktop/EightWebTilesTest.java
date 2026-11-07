package com.nthexperience.desktop;
import org.json.*;
import org.junit.Test;
import static org.junit.Assert.*;
public class EightWebTilesTest {
    private String entry(String url,String title)throws Exception{return new JSONObject().put(EightWebTiles.id(url),new JSONObject().put("url",url).put("title",title)).toString();}
    @Test public void stableIdKeepsDistinctDestinations(){assertEquals(EightWebTiles.id("https://example.com/a"),EightWebTiles.id("https://example.com/a"));assertNotEquals(EightWebTiles.id("https://example.com/a"),EightWebTiles.id("https://example.com/b"));assertTrue(EightWebTiles.validId(EightWebTiles.id("https://example.com/a")));assertFalse(EightWebTiles.validId("@web:javascript:alert(1)"));}
    @Test public void acceptsPortableHttpReference()throws Exception{assertEquals(1,EightWebTiles.parse(entry("https://example.com/a?b=1","Example")).length());}
    @Test public void rejectsActiveContentAndCredentials()throws Exception{for(String url:new String[]{"javascript:alert(1)","file:///sdcard/private","intent://launch","https://user:secret@example.com/"})try{EightWebTiles.parse(entry(url,"Bad"));fail(url);}catch(JSONException expected){}}
    @Test public void rejectsMismatchedUrlAndEmptyLabel()throws Exception{JSONObject all=new JSONObject(entry("https://example.com/a","Example"));all.getJSONObject(EightWebTiles.id("https://example.com/a")).put("url","https://example.com/b");try{EightWebTiles.parse(all.toString());fail("ID must belong to URL");}catch(JSONException expected){}try{EightWebTiles.parse(entry("https://example.com/"," "));fail("Empty title");}catch(JSONException expected){}}
}
