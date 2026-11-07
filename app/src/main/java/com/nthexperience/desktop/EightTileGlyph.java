package com.nthexperience.desktop;
import android.content.Context;
import android.graphics.*;
import android.view.View;

/** Original monochrome tile marks for owned immersive apps, separate from desktop folder artwork. */
final class EightTileGlyph extends View {
    final String id;final Paint paint=new Paint(3);final Path path=new Path();
    EightTileGlyph(Context context,String id){super(context);this.id=id;setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);}
    @Override protected void onDraw(Canvas canvas){super.onDraw(canvas);canvas.save();float scale=Math.min(getWidth(),getHeight())/64f;canvas.translate((getWidth()-64*scale)/2,(getHeight()-64*scale)/2);canvas.scale(scale,scale);paint.setColor(Color.WHITE);paint.setStrokeWidth(3);paint.setStyle(Paint.Style.STROKE);
        switch(id){
            case "@help":canvas.drawCircle(32,32,25,paint);paint.setStyle(Paint.Style.FILL);paint.setTextSize(40);paint.setTypeface(Typeface.create("sans-serif",Typeface.NORMAL));paint.setTextAlign(Paint.Align.CENTER);canvas.drawText("?",32,47,paint);break;
            case "@readinglist":canvas.drawRect(8,10,30,54,paint);canvas.drawRect(34,10,56,54,paint);for(int y=20;y<49;y+=10){canvas.drawLine(13,y,25,y,paint);canvas.drawLine(39,y,51,y,paint);}break;
            case "@alarms":canvas.drawCircle(32,34,22,paint);canvas.drawLine(32,18,32,34,paint);canvas.drawLine(32,34,44,40,paint);canvas.drawLine(12,10,23,5,paint);canvas.drawLine(41,5,52,10,paint);break;
            case "@soundrecorder":canvas.drawRoundRect(25,6,39,39,7,7,paint);canvas.drawArc(17,18,47,47,0,180,false,paint);canvas.drawLine(32,47,32,58,paint);canvas.drawLine(22,58,42,58,paint);break;
            case "@reader":canvas.drawRect(12,5,52,59,paint);paint.setStyle(Paint.Style.FILL);paint.setTextSize(18);paint.setTypeface(Typeface.create("sans-serif",Typeface.BOLD));paint.setTextAlign(Paint.Align.CENTER);canvas.drawText("PDF",32,37,paint);break;
            case "@news":canvas.drawRect(6,10,58,54,paint);canvas.drawRect(12,17,29,35,paint);for(int y=18;y<49;y+=8){canvas.drawLine(35,y,51,y,paint);if(y>35)canvas.drawLine(12,y,29,y,paint);}break;
            case "@browser":canvas.drawCircle(32,32,23,paint);canvas.drawOval(22,9,42,55,paint);canvas.drawOval(9,23,55,41,paint);break;
            case "@photos":canvas.drawRect(7,12,57,53,paint);canvas.drawCircle(44,23,5,paint);path.reset();path.moveTo(8,49);path.lineTo(24,30);path.lineTo(37,43);path.lineTo(46,35);path.lineTo(56,49);canvas.drawPath(path,paint);break;
            case "@calendar":canvas.drawRect(10,13,54,55,paint);canvas.drawLine(10,24,54,24,paint);canvas.drawLine(22,7,22,19,paint);canvas.drawLine(42,7,42,19,paint);paint.setStyle(Paint.Style.FILL);paint.setTypeface(Typeface.create("sans-serif-light",Typeface.NORMAL));paint.setTextSize(24);paint.setTextAlign(Paint.Align.CENTER);canvas.drawText(Integer.toString(java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)),32,47,paint);break;
            case "@weather":canvas.drawCircle(23,23,12,paint);for(int i=0;i<8;i++){double angle=i*Math.PI/4;canvas.drawLine(23+16*(float)Math.cos(angle),23+16*(float)Math.sin(angle),23+21*(float)Math.cos(angle),23+21*(float)Math.sin(angle),paint);}paint.setStyle(Paint.Style.FILL);canvas.drawRoundRect(17,36,59,54,8,8,paint);canvas.drawCircle(34,36,12,paint);canvas.drawCircle(49,39,10,paint);break;
            case "@mediaplayer":paint.setStyle(Paint.Style.FILL);path.reset();path.moveTo(22,11);path.lineTo(53,32);path.lineTo(22,53);path.close();canvas.drawPath(path,paint);break;
            case "@gadget:calculator":canvas.drawRect(15,6,49,58,paint);canvas.drawRect(21,13,43,24,paint);paint.setStyle(Paint.Style.FILL);for(int y=31;y<55;y+=9)for(int x=21;x<45;x+=9)canvas.drawRect(x,y,x+5,y+5,paint);break;
            default:canvas.drawRect(14,7,50,57,paint);for(int y=21;y<50;y+=9)canvas.drawLine(22,y,43,y,paint);
        }canvas.restore();
    }
}
