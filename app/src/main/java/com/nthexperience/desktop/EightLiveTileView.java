package com.nthexperience.desktop;

import android.content.Context;
import android.graphics.*;
import android.net.Uri;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.concurrent.*;

/** A retained content surface. Only its face slides; its parent remains the drag target. */
final class EightLiveTileView extends FrameLayout {
    private static final ExecutorService IMAGES=Executors.newFixedThreadPool(2);
    private final TextView heading,detail,badge;private final ImageView image;
    private String current="",requested="";private Bitmap bitmap;private int generation;
    private final boolean large;
    EightLiveTileView(Context c,String title,String size){
        super(c);large=size.equals("Large");setVisibility(GONE);setClipChildren(true);setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS);
        image=new ImageView(c);image.setScaleType(ImageView.ScaleType.CENTER_CROP);addView(image,new FrameLayout.LayoutParams(-1,-1));
        LinearLayout text=Aero.column(c);text.setGravity(Gravity.CENTER_VERTICAL);text.setPadding(dp(12),dp(8),dp(12),dp(32));
        heading=Aero.text(c,"",size.equals("Wide")?20:24,Color.WHITE);heading.setMaxLines(large?5:3);heading.setEllipsize(TextUtils.TruncateAt.END);text.addView(heading);
        detail=Aero.text(c,"",12,Color.WHITE);detail.setMaxLines(large?4:2);detail.setEllipsize(TextUtils.TruncateAt.END);detail.setPadding(0,dp(5),0,0);text.addView(detail);addView(text,new FrameLayout.LayoutParams(-1,-1));
        TextView name=Aero.text(c,title,12,Color.WHITE);name.setSingleLine();name.setEllipsize(TextUtils.TruncateAt.END);name.setPadding(dp(9),dp(5),dp(34),dp(6));name.setBackgroundColor(0xaa000000);addView(name,new FrameLayout.LayoutParams(-1,dp(28),Gravity.BOTTOM));
        badge=Aero.text(c,"",13,Color.WHITE);badge.setGravity(Gravity.CENTER);badge.setBackgroundColor(0x99000000);FrameLayout.LayoutParams bp=new FrameLayout.LayoutParams(dp(32),dp(26),Gravity.BOTTOM|Gravity.END);addView(badge,bp);badge.setVisibility(GONE);
    }
    private int dp(int n){return Aero.dp(getContext(),n);}
    void pause(){animate().cancel();setTranslationY(0);setAlpha(1);}
    void show(EightTileSnapshot snapshot,int index,boolean motion){
        if(snapshot==null||snapshot.items.isEmpty())return;EightTileSnapshot.Item item=snapshot.items.get(Math.floorMod(index,snapshot.items.size()));badge.setText(snapshot.badgeText());badge.setVisibility(snapshot.badge==0?GONE:VISIBLE);
        android.graphics.Rect visible=new android.graphics.Rect();if(!item.image.equals(requested)&&(item.image.isEmpty()||getGlobalVisibleRect(visible))){requested=item.image;loadImage(item.image);}
        if(item.key().equals(current))return;boolean slide=motion&&!current.isEmpty()&&isShown();pause();current=item.key();heading.setText(item.title);heading.setTextSize(item.tag.equals("date")||item.tag.equals("current")?large?54:38:large?24:20);detail.setText(item.detail);
        if(slide){setTranslationY(dp(18));setAlpha(.5f);animate().translationY(0).alpha(1).setDuration(300).start();}
    }
    private void clearBitmap(){image.setImageDrawable(null);if(bitmap!=null){bitmap.recycle();bitmap=null;}}
    private void loadImage(String uri){int token=++generation;clearBitmap();if(uri.isEmpty())return;
        Context context=getContext().getApplicationContext();IMAGES.execute(()->{Bitmap result=null;
            try{BitmapFactory.Options opts=new BitmapFactory.Options();opts.inJustDecodeBounds=true;try(InputStream in=context.getContentResolver().openInputStream(Uri.parse(uri))){BitmapFactory.decodeStream(in,null,opts);}if(opts.outWidth<=0||opts.outHeight<=0)throw new IOException();opts.inSampleSize=1;while(Math.max(opts.outWidth,opts.outHeight)/opts.inSampleSize>512)opts.inSampleSize*=2;opts.inJustDecodeBounds=false;try(InputStream in=context.getContentResolver().openInputStream(Uri.parse(uri))){result=BitmapFactory.decodeStream(in,null,opts);}}catch(Exception|OutOfMemoryError ignored){}
            Bitmap decoded=result;post(()->{if(token!=generation||!isAttachedToWindow()){if(decoded!=null)decoded.recycle();return;}bitmap=decoded;image.setImageBitmap(decoded);if(decoded==null){heading.setText("Choose photos");detail.setText("Reconnect pictures from the tile menu");}});
        });
    }
    @Override protected void onDetachedFromWindow(){generation++;requested="";current="";pause();clearBitmap();super.onDetachedFromWindow();}
}
