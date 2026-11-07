package com.nthexperience.desktop;
import android.animation.ValueAnimator;
import android.graphics.*;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.*;
/** One cancellable owned-surface transition; navigation commits before motion starts. */
final class EightSurfaceMotion {
    enum Kind { START, APP, SETTINGS, DESKTOP }
    private final FrameLayout parent;private final DesktopStore store;
    private View moving;private ImageView ghost;private Bitmap picture;private int generation;
    EightSurfaceMotion(FrameLayout parent,DesktopStore store){this.parent=parent;this.store=store;}
    static boolean enabled(DesktopStore store){return !store.prefs.getBoolean("reduce_motion",false)&&ValueAnimator.areAnimatorsEnabled();}
    private static void reset(View view){if(view==null)return;view.animate().setListener(null).withEndAction(null).cancel();view.setAlpha(1);view.setScaleX(1);view.setScaleY(1);view.setRotationY(0);view.setTranslationX(0);view.setTranslationY(0);}
    void cancel(){generation++;reset(moving);moving=null;if(ghost!=null){ghost.animate().cancel();parent.removeView(ghost);ghost.setImageDrawable(null);ghost=null;}picture=null;}
    void run(View target,Kind kind,boolean privateContent,Runnable navigate){
        cancel();boolean animate=enabled(store)&&parent.isLaidOut()&&parent.getWidth()>0&&parent.getHeight()>0;
        if(animate&&!privateContent)try{float scale=Math.min(1f,1000f/Math.max(parent.getWidth(),parent.getHeight()));picture=Bitmap.createBitmap(Math.max(1,(int)(parent.getWidth()*scale)),Math.max(1,(int)(parent.getHeight()*scale)),Bitmap.Config.ARGB_8888);Canvas canvas=new Canvas(picture);canvas.scale(scale,scale);parent.draw(canvas);}catch(OutOfMemoryError|RuntimeException ignored){picture=null;}
        navigate.run();if(!animate)return;int token=generation;
        if(picture!=null){ghost=new ImageView(parent.getContext());ghost.setImageBitmap(picture);ghost.setScaleType(ImageView.ScaleType.FIT_XY);ghost.setContentDescription("Eight transition preview");ghost.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS);ghost.setClickable(false);ghost.setElevation(target==null?Aero.dp(parent.getContext(),29):Math.max(0,target.getElevation()-1));FrameLayout.LayoutParams bounds=new FrameLayout.LayoutParams(parent.getWidth(),parent.getHeight());bounds.leftMargin=-parent.getPaddingLeft();bounds.topMargin=-parent.getPaddingTop();parent.addView(ghost,bounds);ghost.animate().alpha(0).setDuration(220).start();}
        moving=target;
        if(target!=null&&target.getVisibility()==View.VISIBLE){target.setCameraDistance(Aero.dp(parent.getContext(),12000));target.setPivotX((parent.getWidth()-parent.getPaddingLeft()-parent.getPaddingRight())/2f);target.setPivotY((parent.getHeight()-parent.getPaddingTop()-parent.getPaddingBottom())/2f);target.setAlpha(.2f);float scale=kind==Kind.START?.9f:.66f;target.setScaleX(scale);target.setScaleY(scale);target.setRotationY(kind==Kind.APP||kind==Kind.SETTINGS?-16:0);target.animate().alpha(1).scaleX(1).scaleY(1).rotationY(0).setInterpolator(new PathInterpolator(.15f,.8f,.2f,1)).setDuration(260).withEndAction(()->{if(generation==token)cancel();}).start();}
        else parent.postDelayed(()->{if(generation==token)cancel();},230);
    }
}
