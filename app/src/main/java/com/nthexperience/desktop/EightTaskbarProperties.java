package com.nthexperience.desktop;

import android.content.*;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;
import java.util.*;
import java.util.function.Consumer;

/** A pending edit buffer gives Apply, OK and Cancel distinct, consistent behavior. */
final class EightTaskbarProperties extends LinearLayout {
    final DesktopStore store;final Consumer<String> open;final Runnable changed;final LinearLayout body;final Map<String,Object> draft=new LinkedHashMap<>();
    String tab="Taskbar";final TextView apply;
    EightTaskbarProperties(android.content.Context c,DesktopStore store,Consumer<String> open,Runnable changed){
        super(c);this.store=store;this.open=open;this.changed=changed;setOrientation(VERTICAL);setBackgroundColor(Color.WHITE);setContentDescription("Taskbar and Navigation properties");
        HorizontalScrollView strip=new HorizontalScrollView(c);LinearLayout tabs=new LinearLayout(c);for(String name:new String[]{"Taskbar","Navigation","Jump Lists","Toolbars"}){TextView button=command(name,()->{tab=name;render();});tabs.addView(button,new LayoutParams(-2,dp(44)));}strip.addView(tabs);addView(strip);
        body=Aero.column(c);body.setPadding(dp(12),dp(14),dp(12),dp(18));addView(body,new LayoutParams(-1,-2));
        LinearLayout footer=new LinearLayout(c);footer.setGravity(Gravity.END);footer.addView(command("OK",()->finish(true)));footer.addView(command("Cancel",()->finish(false)));apply=command("Apply",this::commit);footer.addView(apply);addView(footer);render();
    }
    int dp(int n){return Aero.dp(getContext(),n);}
    TextView command(String name,Runnable action){TextView t=Aero.text(getContext(),name,14,0xff155b91);t.setContentDescription(name.equals("Apply")||name.equals("OK")||name.equals("Cancel")?"Taskbar properties "+name:"Taskbar properties tab "+name);t.setPadding(dp(12),dp(10),dp(12),dp(10));t.setMinHeight(dp(44));t.setFocusable(true);t.setBackground(Aero.selection(getContext()));t.setOnClickListener(v->action.run());return t;}
    void finish(boolean save){android.view.View parent=this;while(parent.getParent() instanceof android.view.View&&!(parent instanceof DesktopWindow))parent=(android.view.View)parent.getParent();if(save)commit();else draft.clear();if(parent instanceof DesktopWindow)((DesktopWindow)parent).closeWindow();else open.accept("@desktop");}
    void commit(){SharedPreferences.Editor edit=store.prefs.edit();for(Map.Entry<String,Object> entry:draft.entrySet())if(entry.getValue() instanceof Boolean)edit.putBoolean(entry.getKey(),(Boolean)entry.getValue());else edit.putString(entry.getKey(),entry.getValue().toString());edit.apply();draft.clear();apply.setEnabled(false);changed.run();}
    void check(String title,String key,boolean fallback){CheckBox box=new CheckBox(getContext());box.setText(title);box.setTextColor(Color.BLACK);box.setContentDescription(title);box.setChecked(draft.containsKey(key)?(Boolean)draft.get(key):store.prefs.getBoolean(key,fallback));box.setMinHeight(dp(48));box.setOnCheckedChangeListener((v,on)->{draft.put(key,on);apply.setEnabled(true);});body.addView(box);}
    void note(String title){body.addView(Aero.text(getContext(),title,14,0xff333333));}
    void render(){body.removeAllViews();note(tab);switch(tab){
        case "Taskbar":check("Fit all taskbar buttons without scrolling","taskbar_static",false);check("Show taskbar labels","taskbar_show_labels",false);check("Show thumbnail previews","taskbar_previews",true);String grouping=(String)draft.getOrDefault("taskbar_grouping",store.prefs.getString("taskbar_grouping","Always"));body.addView(command("Taskbar buttons: "+grouping,()->Aero.dialog(getContext()).setTitle("Taskbar buttons").setItems(new String[]{"Always","When full","Never"},(d,n)->{draft.put("taskbar_grouping",new String[]{"Always","When full","Never"}[n]);render();}).show()));break;
        case "Navigation":if(EightDefaults.profile(store)==EightProfile.EIGHT_POINT_ONE){check("Go to Desktop when Eight launches","eight_boot_desktop",false);check("Show desktop background on Start","eight_start_wallpaper",false);check("8.1 Update mouse controls and taskbar apps","eight_81_update",false);}else check("Visible Start button for touch access","eight_visible_start",false);break;
        case "Jump Lists":check("Store and display recently opened items","recent_items_enabled",true);body.addView(command("Recent Items",()->open.accept("@recent")));break;
        case "Toolbars":body.addView(command("Choose pinned taskbar shortcuts",()->open.accept("@quicklaunch")));body.addView(command("Android app usage access",()->open.accept("setting:taskbar-usage-welcome")));note("These actions open their own settings and take effect there.");break;
    }apply.setEnabled(!draft.isEmpty());}
}
