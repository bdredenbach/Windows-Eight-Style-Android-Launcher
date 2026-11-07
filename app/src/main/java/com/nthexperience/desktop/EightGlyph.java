package com.nthexperience.desktop;
import android.content.Context;
import android.graphics.*;
import android.view.View;
/** Original vector artwork for Eight's filled Charms and outlined app commands. */
final class EightGlyph extends View {
    private String kind;private final int ink;private final boolean circle;private final Paint p=new Paint(3);
    EightGlyph(Context c,String kind,int ink,boolean circle){super(c);this.kind=kind;this.ink=ink;this.circle=circle;}
    void kind(String value){kind=value;invalidate();}
    @Override protected void onDraw(Canvas c){
        super.onDraw(c);c.save();c.translate(getWidth()/2f,getHeight()/2f);float unit=Math.min(getWidth(),getHeight())/40f;c.scale(unit,unit);
        p.setColor(ink);p.setAlpha(isEnabled()?255:90);p.setStrokeWidth(circle?1.25f:2.5f);p.setStrokeCap(Paint.Cap.SQUARE);p.setStrokeJoin(Paint.Join.MITER);p.setStyle(Paint.Style.STROKE);
        if(circle)c.drawCircle(0,0,17,p);
        if(kind.equals("search")){c.drawCircle(3,-3,8,p);p.setStrokeWidth(4);c.drawLine(-3,3,-12,12,p);}
        else if(kind.equals("back")||kind.equals("forward")){c.save();if(kind.equals("forward"))c.rotate(180);c.drawLine(-8,0,9,0,p);c.drawLine(-8,0,-1,-7,p);c.drawLine(-8,0,-1,7,p);c.restore();}
        else if(kind.equals("reload")){c.drawArc(-8,-8,8,8,-55,285,false,p);c.drawLine(5,-9,7,-2,p);c.drawLine(7,-2,0,-3,p);}
        else if(kind.equals("stop")){c.drawLine(-6,-6,6,6,p);c.drawLine(-6,6,6,-6,p);}
        else if(kind.equals("pin")){Path path=new Path();path.moveTo(-5,-9);path.lineTo(5,-9);path.lineTo(3,-5);path.lineTo(3,0);path.lineTo(6,3);path.lineTo(-6,3);path.lineTo(-3,0);path.lineTo(-3,-5);path.close();c.drawPath(path,p);c.drawLine(0,3,0,11,p);}
        else if(kind.equals("tools")){c.save();c.rotate(40);c.drawLine(0,-1,0,11,p);p.setStyle(Paint.Style.FILL);c.drawCircle(0,10,2,p);p.setStyle(Paint.Style.STROKE);c.drawArc(-6,-12,6,0,-25,230,false,p);c.drawLine(-5,-8,-1,-4,p);c.drawLine(5,-8,1,-4,p);c.restore();}
        else if(kind.equals("start")){p.setStyle(Paint.Style.FILL);for(int x=0;x<2;x++)for(int y=0;y<2;y++){Path pane=new Path();float l=-15+x*16,rr=l+14,t=-12+y*14,b=t+12;pane.moveTo(l,t-(x==1?2:0));pane.lineTo(rr,t-(x==1?4:1));pane.lineTo(rr,b+(x==1?4:1));pane.lineTo(l,b+(x==1?2:0));pane.close();c.drawPath(pane,p);}}
        else if(kind.equals("share")){c.drawCircle(0,0,13,p);p.setStyle(Paint.Style.FILL);for(int i=0;i<3;i++){double a=(i*120-60)*Math.PI/180;c.drawCircle((float)Math.cos(a)*13,(float)Math.sin(a)*13,4.6f,p);}}
        else if(kind.equals("devices")){p.setStrokeWidth(4.5f);Path screen=new Path();screen.moveTo(-9,-9);screen.quadTo(-9,-14,-4,-14);screen.lineTo(10,-14);screen.quadTo(17,-14,17,-7);screen.lineTo(17,7);screen.quadTo(17,14,10,14);screen.lineTo(-4,14);screen.quadTo(-9,14,-9,10);c.drawPath(screen,p);p.setStyle(Paint.Style.FILL);c.drawRect(-16,-5,-1,6,p);}
        else if(kind.equals("settings")){p.setStyle(Paint.Style.FILL);for(int i=0;i<8;i++){c.save();c.rotate(i*45);c.drawRect(-3,-17,3,-9,p);c.restore();}Path gear=new Path();gear.setFillType(Path.FillType.EVEN_ODD);gear.addCircle(0,0,13,Path.Direction.CW);gear.addCircle(0,0,6,Path.Direction.CW);c.drawPath(gear,p);}
        c.restore();
    }
}
