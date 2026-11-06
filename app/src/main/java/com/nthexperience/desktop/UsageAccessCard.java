package com.nthexperience.desktop;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

/** Live product identity in an instruction card; never a screenshot of another Nth app. */
final class UsageAccessCard extends LinearLayout {
    UsageAccessCard(Context c){super(c);setOrientation(VERTICAL);setBackgroundColor(0xff202020);Aero.pad(this,18);TenAppearance.preserve(this);setContentDescription("Usage Access recovery for "+c.getString(R.string.app_name));
        TextView heading=Aero.text(c,"App info                 ⋮",18,Color.WHITE);heading.setMinHeight(Aero.dp(c,42));addView(heading);
        LinearLayout identity=new LinearLayout(c);identity.setGravity(Gravity.CENTER_VERTICAL);ImageView icon=new ImageView(c);icon.setImageResource(R.drawable.ic_launcher);icon.setContentDescription(c.getString(R.string.app_name)+" app icon");identity.addView(icon,new LayoutParams(Aero.dp(c,64),Aero.dp(c,64)));
        TextView name=Aero.text(c,c.getString(R.string.app_name),18,Color.WHITE);name.setPadding(Aero.dp(c,14),0,0,0);identity.addView(name,new LayoutParams(0,-2,1));addView(identity,new LayoutParams(-1,-2));
        TextView step=Aero.text(c,"⋮  →  Allow restricted settings",15,Color.WHITE);step.setPadding(0,Aero.dp(c,18),0,0);addView(step);TextView hint=Aero.text(c,"If Android offers this option, enable it, then return to Usage Access.",12,0xffcccccc);hint.setPadding(0,Aero.dp(c,10),0,0);addView(hint);
    }
}
