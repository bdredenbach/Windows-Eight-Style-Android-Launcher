package com.nthexperience.desktop;

import android.content.Context;
import android.graphics.Color;
import android.widget.*;

/** A compact first-run starting point; every tile opens an existing launcher function. */
final class WelcomeCenter extends VistaShell {
    WelcomeCenter(Context c,DesktopStore store,Actions actions){super(c,store,actions,"Welcome Center");search.setVisibility(GONE);start("welcome");}
    @Override void render(){side.removeAllViews();sideLink("Personalization",()->actions.open("@appearance"));sideLink("Computer",()->actions.open("@computer"));sideLink("Backup and Restore",()->actions.open("@backup"));address.setText("Welcome Center");LinearLayout column=column();
        LinearLayout brand=new LinearLayout(getContext());brand.setGravity(android.view.Gravity.CENTER_VERTICAL);brand.addView(new AeroIcon(getContext(),"orb"),new LayoutParams(dp(58),dp(58)));TextView title=Aero.text(getContext(),"Welcome to "+store.prefs.getString("desktop_name",getContext().getString(R.string.app_name)),20,0xff234f6d);title.setPadding(dp(12),0,0,0);brand.addView(title,new LayoutParams(0,-2,1));column.addView(brand,new LayoutParams(-1,dp(88)));
        heading(column,"Make yourself at home","Your Start screen, apps and desktop.");task(column,"files","Set up file browsing",StorageAccess.has(getContext())?"Device folders are ready in Nth Explorer":"Choose device access or connect individual folders",()->actions.action("storage-access"));
        task(column,"programs","Set up Usage Access","Optional. Show apps you open elsewhere on the taskbar.",()->actions.action("taskbar-usage-welcome"));
        task(column,"appearance","Personalize your desktop","Choose Eight colors, backgrounds or optional Aero glass",()->actions.open("@appearance"));task(column,"folderapps","Arrange apps and folders","Rename shortcuts and arrange your pinned taskbar apps",()->actions.open("@organize"));task(column,"clock","Explore Nth tools","Open the calculator, converter and text tools",()->actions.open("eight-desktop:tools"));task(column,"drive","Back up your desktop","Keep your notes, folders and both orientation layouts",()->actions.open("@backup"));
        if(!((MainActivity)getContext()).isHome())task(column,"computer","Make Nth Desktop your home","Choose it as Android’s default home app",()->actions.action("home"));
        heading(column,"A few familiar gestures","Start opens full-screen tiles. Use All apps to find installed apps, or begin typing to search. Hold a tile to select and resize it; use Groups to arrange the layout. Swipe from the right handle for Charms and from the left handle to switch apps. The ellipsis inside a full-screen app opens its commands. On a wide screen, choose Snap another app. Open on Desktop returns the same app to its window. Hold desktop space or the taskbar for their menus. Drag window corners to resize. Windows + C opens Charms, Windows + Tab switches apps, and Windows + R opens Run.");CheckBox show=Aero.check(getContext(),"Open Welcome Center when Nth Desktop starts",store.prefs.getBoolean("welcome_on_start",false));show.setOnCheckedChangeListener((v,checked)->store.prefs.edit().putBoolean("welcome_on_start",checked).apply());column.addView(show);status.setText("Welcome Center  •  Nth Experience");body(scrolling(column));}
    @Override void refreshApps(){render();}
    @Override void showFolders(){actions.open("@control");}
    @Override void goUp(){actions.open("@control");}
}
