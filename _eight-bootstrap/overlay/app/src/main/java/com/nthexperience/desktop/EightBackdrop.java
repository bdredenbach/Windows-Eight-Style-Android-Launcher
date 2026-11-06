package com.nthexperience.desktop;

import android.graphics.*;
import android.graphics.drawable.Drawable;

/** Original restrained line pattern, drawn locally rather than shipping reference screenshots. */
final class EightBackdrop extends Drawable {
    final Paint paint=new Paint(3); final Path path=new Path(); final int color;final AeroWallpaper wallpaper;
    EightBackdrop(int color){this(color,null);}
    EightBackdrop(int color,AeroWallpaper wallpaper){this.color=color;this.wallpaper=wallpaper;}
    @Override public void draw(Canvas canvas){
        Rect r=getBounds();canvas.drawColor(color);
        if(wallpaper!=null&&wallpaper.getWidth()>0&&wallpaper.getHeight()>0){int saved=canvas.save();canvas.clipRect(r);canvas.scale(r.width()/(float)wallpaper.getWidth(),r.height()/(float)wallpaper.getHeight());wallpaper.draw(canvas);canvas.restoreToCount(saved);canvas.drawColor(0x8821164f);return;}
        paint.setColor(0x245d72c4);paint.setStrokeWidth(Math.max(1,r.height()/120f));paint.setStyle(Paint.Style.STROKE);
        for(int i=0;i<5;i++){float y=r.bottom-r.height()*(.05f+i*.035f);path.reset();path.moveTo(r.left,y);path.cubicTo(r.width()*.24f,y-r.height()*.48f,r.width()*.55f,y+r.height()*.28f,r.right,y-r.height()*.25f);canvas.drawPath(path,paint);}
        paint.setStyle(Paint.Style.FILL);
    }
    @Override public void setAlpha(int alpha){}
    @Override public void setColorFilter(ColorFilter filter){}
    @Override public int getOpacity(){return PixelFormat.OPAQUE;}
}
