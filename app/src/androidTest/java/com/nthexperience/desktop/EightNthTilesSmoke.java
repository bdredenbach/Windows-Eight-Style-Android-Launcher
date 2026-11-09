package com.nthexperience.desktop;

import android.app.*;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import org.json.*;

/** Fresh installs, upgrades, portable backups and real local tile content. */
public final class EightNthTilesSmoke extends Instrumentation {
    MainActivity home;View root;DesktopStore store;EightStartScreen screen;FrameLayout overlay;String opened;int launches;
    @Override public void onCreate(Bundle args){super.onCreate(args);start();}
    @Override public void onStart(){Bundle result=new Bundle();try{
        store=new DesktopStore(getTargetContext());lifecycle();fixtures();
        store.prefs.edit().putInt("startup_mode",2).putBoolean("welcome_on_start",false).putBoolean("reduce_motion",true).commit();
        getTargetContext().getSharedPreferences(StorageAccess.PREFS,0).edit().putBoolean("seen",true).commit();getTargetContext().getSharedPreferences(StartupExperience.SESSION,0).edit().putBoolean("seen",true).putBoolean("welcome_seen",true).putBoolean(StartupExperience.HOME_OFFERED,true).commit();
        home=(MainActivity)startActivitySync(new Intent(Intent.ACTION_MAIN).setComponent(new ComponentName(getTargetContext(),MainActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));root=home.getWindow().getDecorView();waitForIdleSync();
        ui(()->{home.open("@desktop");overlay=new FrameLayout(home);home.addContentView(overlay,new ViewGroup.LayoutParams(-1,-1));screen=new EightStartScreen(home,new StartMenu.Actions(){public void open(String id){opened=id;launches++;}public void options(View v,String id){}public void home(){}public void settings(){}public boolean isHome(){return false;}},store,new AeroWallpaper(home,11),()->{});overlay.addView(screen,new FrameLayout.LayoutParams(-1,-1));screen.show(false);});
        for(EightProfile profile:EightProfile.values())for(int orientation:new int[]{ActivityInfo.SCREEN_ORIENTATION_PORTRAIT,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE}){
            ui(()->home.setRequestedOrientation(orientation));SystemClock.sleep(450);waitForIdleSync();ui(()->{store.prefs.edit().putString("eight_profile",profile.id).commit();screen.show(false);});waitForIdleSync();
            for(EightTileCatalog.Entry entry:EightTileCatalog.ALL){final View[] tile={null};ui(()->{tile[0]=tile(entry);check(tile[0]!=null,entry.id+" tile visible in tree");tile[0].requestRectangleOnScreen(new Rect(0,0,tile[0].getWidth(),tile[0].getHeight()),true);});waitForIdleSync();ui(()->{Rect rect=new Rect();check(tile[0].getGlobalVisibleRect(rect)&&rect.width()>0&&rect.height()>0,entry.id+" reachable by scrolling");checkLiveText(tile[0]);opened=null;tile[0].performClick();check(entry.id.equals(opened),entry.id+" keeps launch destination");});}
            ui(()->{EightTileCatalog.Entry first=EightTileCatalog.get("@desktop");tile(first).requestRectangleOnScreen(new Rect(0,0,tile(first).getWidth(),tile(first).getHeight()),true);});shot("nth-tiles-"+profile.id+"-"+orientation);
            ui(()->{View note=tile(EightTileCatalog.get("@notes"));View parent=(View)note.getParent();int x=note.getLeft(),y=note.getTop();screen.refreshLiveTiles();check(note==tile(EightTileCatalog.get("@notes"))&&note.getParent()==parent&&note.getLeft()==x&&note.getTop()==y,"Live refresh retains tile and position");live();});
        }
        check(launches==216,"All 54 tiles dispatch in both shells and orientations");ui(()->{screen.hide();overlay.removeAllViews();home.open("@settings");});
        result.putString("stream","PASS: 54 Nth tiles in both shells and orientations; 216 launch dispatches; fresh/upgrade/restore/unpin policies; real local live providers, toggles, clear and retained geometry\n");finish(Activity.RESULT_OK,result);
    }catch(Throwable error){android.util.Log.e("EightNthTilesSmoke","Failure",error);result.putString("stream","FAIL: "+error+"\n");finish(Activity.RESULT_CANCELED,result);}}
    private void lifecycle()throws Exception{
        store.prefs.edit().clear().commit();EightDefaults.initialize(store);check(store.prefs.getBoolean("eight_live_tiles",false),"Live defaults enabled");
        for(EightProfile p:EightProfile.values()){EightStartData d=new EightStartData(store,p);check(count(d)==54&&d.groups.size()==7,"Fresh tile groups");check(EightStartData.parse(EightStartData.encode(d.groups),p).size()==7,"Portable layout round trip");}
        EightStartData custom=new EightStartData(store,EightProfile.EIGHT);custom.groups.clear();EightStartData.Group own=new EightStartData.Group("Essentials");own.items.add(new EightStartData.Item("@notes","Wide"));own.items.add(new EightStartData.Item("@desktop","Medium"));custom.groups.add(own);custom.save();String prefix=EightStartData.encode(custom.groups);
        store.prefs.edit().remove(EightTileCatalog.SEEDED).putBoolean("eight_live_tiles",false).putString("note","kept text").commit();EightDefaults.initialize(store);custom=new EightStartData(store,EightProfile.EIGHT);check(count(custom)==54&&custom.groups.get(0).name.equals("Essentials")&&custom.groups.get(0).items.get(0).id.equals("@notes")&&custom.groups.get(0).items.get(0).size.equals("Wide")&&custom.groups.get(0).items.get(1).id.equals("@desktop"),"Upgrade preserves original group, order and sizes");check(!store.prefs.getBoolean("eight_live_tiles",true)&&store.prefs.getString("note","").equals("kept text"),"Upgrade preserves content and live-off choice");
        String upgraded=EightStartData.encode(custom.groups);EightDefaults.initialize(store);check(upgraded.equals(store.prefs.getString(EightStartData.EIGHT,"")),"Upgrade is idempotent");custom.unpin("@mail");EightDefaults.initialize(store);check(new EightStartData(store,EightProfile.EIGHT).item("@mail")==null,"User unpins remain unpinned");check(new EightStartData(store,EightProfile.EIGHT).addMissingApps()==1,"Explicit add missing restores only missing tile");
        JSONObject legacy=DesktopBackup.snapshot(store);legacy.getJSONObject("settings").remove(EightTileCatalog.SEEDED);legacy.getJSONObject("settings").getJSONObject(EightStartData.EIGHT).put("value",prefix);DesktopBackup.apply(getTargetContext(),store,DesktopBackup.parse(legacy.toString()),false);EightDefaults.initialize(store);check(prefix.equals(store.prefs.getString(EightStartData.EIGHT,"")),"Restoring old layout does not seed new defaults");DesktopBackup.apply(getTargetContext(),store,DesktopBackup.staged(getTargetContext(),DesktopBackup.UNDO),true);check(count(new EightStartData(store,EightProfile.EIGHT))==54,"Undo restores full layout");
        store.prefs.edit().clear().commit();EightDefaults.initialize(store);
    }
    private void fixtures()throws Exception{
        store.prefs.edit().putString(EightPimModels.DRAFTS,new JSONArray().put(EightPimModels.clean(EightPimModels.DRAFTS,new JSONObject().put("id","draft").put("subject","Private subject").put("body","Private body"))).toString())
            .putString(EightPimModels.PEOPLE,new JSONArray().put(EightPimModels.clean(EightPimModels.PEOPLE,new JSONObject().put("id","person").put("name","Private person").put("favorite",true))).toString())
            .putString(EightPimModels.PLACES,new JSONArray().put(EightPimModels.clean(EightPimModels.PLACES,new JSONObject().put("id","place").put("name","Private place").put("lat",10).put("lon",20))).toString())
            .putString("note","one two three").putString("gadget_note","four five").putString("gadget_data",new JSONObject().put("worldclock",new JSONObject().put("zone1","UTC").put("zone2","America/New_York")).put("checklist",new JSONObject().put("tasks",new JSONArray().put(new JSONObject().put("text","Private task").put("done",true)).put(new JSONObject().put("text","Another task").put("done",false)))).put("timer",new JSONObject().put("duration",300000).put("remaining",65000).put("running",false)).put("stopwatch",new JSONObject().put("elapsed",125000).put("running",false)).put("daysuntil",new JSONObject().put("date",java.time.LocalDate.now().plusDays(3).toString()).put("title","My date")).toString()).commit();
    }
    private void live(){
        EightLiveTiles live=screen.liveTiles;long now=System.currentTimeMillis();
        check(live.snapshot("@mail",now).badge==1&&first(live,"@mail").title.equals("1")&&first(live,"@mail").detail.contains("Local draft"),"Mail shows local drafts");check(live.snapshot("@people",now).badge==1&&first(live,"@people").title.equals("1"),"People count and favorites");check(first(live,"@maps").title.equals("1"),"Saved places count");check(first(live,"@notes").title.equals("3")&&first(live,"@sticky").title.equals("2"),"Saved note word counts");
        check(live.snapshot("@gadget:worldclock",now).items.size()==2,"Two real world clocks");check(first(live,"@gadget:checklist").title.equals("1 / 2")&&live.snapshot("@gadget:checklist",now).badge==1,"Checklist progress");check(first(live,"@gadget:timer").title.equals("01:05")&&first(live,"@gadget:stopwatch").title.equals("02:05"),"Saved paused clocks");check(first(live,"@gadget:daysuntil").title.equals("3"),"Saved event countdown");
        for(String id:new String[]{"@mail","@people","@maps","@notes","@sticky","@gadget:checklist"})for(EightTileSnapshot.Item item:live.snapshot(id,now).items)check(!item.key().contains("Private"),"Counts do not expose local bodies");
        check(home.existingPlayback()==null,"Live tiles never start media");live.toggle("@mail");check(live.snapshot("@mail",now)==null,"Individual live off");live.toggle("@mail");store.prefs.edit().putBoolean("eight_live_tiles",false).commit();check(live.snapshot("@people",now)==null,"Global live off");store.prefs.edit().putBoolean("eight_live_tiles",true).commit();live.clear();check(live.snapshot("@mail",now)==null&&live.snapshot("@gadget:checklist",now)==null,"Clear personal tile info includes new providers");store.prefs.edit().putString("note","one two three four").commit();check(first(live,"@notes").title.equals("4"),"Updated source repopulates cleared tile");store.prefs.edit().remove("eight_tile_cleared_"+EightDefaults.profile(store).id).putString("note","one two three").commit();
    }
    private EightTileSnapshot.Item first(EightLiveTiles live,String id){EightTileSnapshot snapshot=live.snapshot(id,System.currentTimeMillis());check(snapshot!=null&&!snapshot.items.isEmpty(),id+" has content");return snapshot.items.get(0);}
    private void checkLiveText(View tile){
        if(!(tile instanceof ViewGroup))return;
        for(int i=0;i<((ViewGroup)tile).getChildCount();i++){
            View child=((ViewGroup)tile).getChildAt(i);if(!(child instanceof EightLiveTileView)||child.getVisibility()!=View.VISIBLE)continue;
            ViewGroup live=(ViewGroup)child,body=(ViewGroup)live.getChildAt(1);View detail=body.getChildAt(1),caption=live.getChildAt(2);check(detail.getVisibility()==View.VISIBLE&&detail.getHeight()>0,"Live subtitle remains visible: "+tile.getContentDescription());int[] textAt=new int[2],captionAt=new int[2];detail.getLocationOnScreen(textAt);caption.getLocationOnScreen(captionAt);check(textAt[1]+detail.getHeight()<=captionAt[1],"Live text fits above caption: "+tile.getContentDescription());
        }
    }
    private View tile(EightTileCatalog.Entry e){return description(screen,e.title(screen.profile)+", "+screen.data.item(e.id).size+" Start tile");}
    private View description(View view,String text){if(text.contentEquals(view.getContentDescription()==null?"":view.getContentDescription()))return view;if(view instanceof ViewGroup)for(int i=0;i<((ViewGroup)view).getChildCount();i++){View found=description(((ViewGroup)view).getChildAt(i),text);if(found!=null)return found;}return null;}
    private int count(EightStartData data){int count=0;for(EightStartData.Group group:data.groups)count+=group.items.size();return count;}
    private void ui(Runnable task)throws Exception{FutureTask<Void> future=new FutureTask<>(task,null);home.runOnUiThread(future);future.get(25,TimeUnit.SECONDS);waitForIdleSync();}
    private void shot(String name)throws Exception{waitForIdleSync();ui(()->{Bitmap image=Bitmap.createBitmap(root.getWidth(),root.getHeight(),Bitmap.Config.ARGB_8888);root.draw(new Canvas(image));try(FileOutputStream out=new FileOutputStream(new File(getTargetContext().getFilesDir(),name+".png"))){image.compress(Bitmap.CompressFormat.PNG,100,out);}catch(IOException e){throw new RuntimeException(e);}finally{image.recycle();}});}
    private static void check(boolean value,String message){if(!value)throw new AssertionError(message);}
}
