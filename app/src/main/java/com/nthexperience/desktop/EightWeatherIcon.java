package com.nthexperience.desktop;

import android.content.Context;
import android.graphics.*;
import android.view.View;
import java.util.Locale;

/** Flat condition art shared by the live tile and the Weather panorama. */
final class EightWeatherIcon extends View {
    private final Paint p=new Paint(3);private String symbol;private final int ink;
    EightWeatherIcon(Context c,String symbol,int ink){super(c);this.symbol=symbol;this.ink=ink;setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);}
    void symbol(String value){symbol=value;invalidate();}
    protected void onDraw(Canvas c){super.onDraw(c);int save=c.save();float unit=Math.min(getWidth(),getHeight())/72f;c.translate((getWidth()-72*unit)/2,(getHeight()-72*unit)/2);c.scale(unit,unit);p.setColor(ink);p.setStrokeWidth(3);p.setStrokeCap(Paint.Cap.ROUND);p.setStyle(Paint.Style.STROKE);String s=symbol==null?"unknown":symbol.toLowerCase(Locale.ROOT);
        if(s.equals("unknown")){p.setTextSize(46);p.setTextAlign(Paint.Align.CENTER);c.drawText("—",36,49,p);c.restoreToCount(save);return;}
        boolean cloudy=s.contains("cloud")||s.contains("rain")||s.contains("snow")||s.contains("sleet")||s.contains("fog")||s.contains("thunder");boolean sun=!cloudy||s.contains("partly")||s.contains("fair");
        if(sun){if(s.contains("night")){p.setStyle(Paint.Style.FILL);Path moon=new Path();moon.moveTo(34,8);moon.cubicTo(13,12,14,39,35,42);moon.cubicTo(25,33,24,17,34,8);c.drawPath(moon,p);}else{c.drawCircle(29,27,12,p);for(int i=0;i<8;i++){double a=i*Math.PI/4;c.drawLine(29+17*(float)Math.cos(a),27+17*(float)Math.sin(a),29+22*(float)Math.cos(a),27+22*(float)Math.sin(a),p);}}}
        if(cloudy){p.setStyle(Paint.Style.FILL);c.drawRoundRect(10,35,64,52,8,8,p);c.drawCircle(26,33,12,p);c.drawCircle(45,32,15,p);}
        p.setStyle(Paint.Style.STROKE);
        if(s.contains("thunder")){Path bolt=new Path();bolt.moveTo(39,48);bolt.lineTo(30,60);bolt.lineTo(39,60);bolt.lineTo(34,69);c.drawPath(bolt,p);}
        else if(s.contains("snow")||s.contains("sleet")){for(int x=20;x<60;x+=16){c.drawLine(x,58,x,67,p);c.drawLine(x-4,60,x+4,65,p);c.drawLine(x+4,60,x-4,65,p);}}
        else if(s.contains("rain")){for(int x=22;x<60;x+=15)c.drawLine(x,58,x-4,65,p);}
        if(s.contains("fog")){c.drawLine(10,58,64,58,p);c.drawLine(16,65,58,65,p);}c.restoreToCount(save);
    }
}
