package com.nthexperience.desktop;
import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

/** Desktop is one surface. Only actual owned immersive sessions appear as app cards. */
final class EightAppSwitcher extends FrameLayout {
    final EightImmersive apps;final Runnable desktop,changed;final LinearLayout list;View previousFocus;
    EightAppSwitcher(Context context,EightImmersive apps,Runnable desktop,Runnable changed){
        super(context);this.apps=apps;this.desktop=desktop;this.changed=changed;setElevation(Aero.dp(context,42));setBackgroundColor(0x55000000);setContentDescription("Eight left app switcher");setVisibility(GONE);setOnClickListener(v->dismiss());TenAppearance.preserve(this);
        ScrollView scroll=new ScrollView(context);scroll.setBackgroundColor(0xff252525);scroll.setOnClickListener(v->{});list=Aero.column(context);list.setPadding(Aero.dp(context,10),Aero.dp(context,16),Aero.dp(context,10),Aero.dp(context,16));scroll.addView(list);addView(scroll,new FrameLayout.LayoutParams(Aero.dp(context,210),-1,Gravity.START));
    }
    void toggle(){if(!dismiss())show();}
    boolean dismiss(){if(getVisibility()!=VISIBLE)return false;setVisibility(GONE);if(previousFocus!=null&&previousFocus.isShown())previousFocus.requestFocus();previousFocus=null;changed.run();return true;}
    void show(){apps.captureVisible();previousFocus=getRootView().findFocus();list.removeAllViews();
        TextView heading=Aero.text(getContext(),"Apps",25,Color.WHITE);heading.setPadding(Aero.dp(getContext(),8),0,0,Aero.dp(getContext(),16));list.addView(heading);
        card("Desktop","computer",null,()->{dismiss();desktop.run();},null);
        for(EightImmersive.Session session:apps.ordered())card(session.spec.title,session.spec.icon,session,()->{dismiss();apps.show(session.spec.id);},()->{apps.close(session.spec.id);show();});
        if(apps.sessions.isEmpty()){TextView hint=Aero.text(getContext(),"Open a built-in app from Start to keep it here.",14,Color.WHITE);Aero.pad(hint,10);list.addView(hint);}
        setVisibility(VISIBLE);bringToFront();requestFocus();changed.run();
    }
    private void card(String title,String icon,EightImmersive.Session session,Runnable open,Runnable close){
        LinearLayout card=Aero.column(getContext());card.setPadding(Aero.dp(getContext(),6),Aero.dp(getContext(),8),Aero.dp(getContext(),6),Aero.dp(getContext(),8));card.setBackgroundColor(0xff3b3b3b);card.setContentDescription("Switch to "+title);card.setFocusable(true);card.setOnClickListener(v->open.run());
        if(session!=null&&session.preview!=null){ImageView preview=new ImageView(getContext());preview.setImageBitmap(session.preview);preview.setScaleType(ImageView.ScaleType.CENTER_CROP);card.addView(preview,new LinearLayout.LayoutParams(-1,Aero.dp(getContext(),95)));}
        else{View image=new AeroIcon(getContext(),icon);LinearLayout.LayoutParams picture=new LinearLayout.LayoutParams(Aero.dp(getContext(),48),Aero.dp(getContext(),48));picture.gravity=Gravity.CENTER_HORIZONTAL;card.addView(image,picture);}
        LinearLayout caption=new LinearLayout(getContext());caption.setGravity(Gravity.CENTER_VERTICAL);TextView text=Aero.text(getContext(),title,15,Color.WHITE);text.setMaxLines(2);caption.addView(text,new LinearLayout.LayoutParams(0,Aero.dp(getContext(),46),1));if(close!=null){TextView x=Aero.text(getContext(),"×",22,Color.WHITE);x.setGravity(Gravity.CENTER);x.setContentDescription("Close "+title+" from switcher");x.setOnClickListener(v->close.run());caption.addView(x,new LinearLayout.LayoutParams(Aero.dp(getContext(),44),Aero.dp(getContext(),46)));}card.addView(caption);LinearLayout.LayoutParams bounds=new LinearLayout.LayoutParams(-1,-2);bounds.bottomMargin=Aero.dp(getContext(),10);list.addView(card,bounds);
    }
}
