package com.nthexperience.desktop;
import android.view.View;
import android.content.Context;
import android.widget.FrameLayout;
/** Explicit lifetime disposal for owned utilities, independent of immersive presentation transfers. */
final class EightTools {
    static boolean utility(String id){return java.util.Arrays.asList("@help","@reader","@readinglist","@alarms","@soundrecorder","@paint","@wordpad","@characters","@mobility").contains(id);}
    static View icon(Context c,String id){FrameLayout icon=new FrameLayout(c);int color=id.equals("@soundrecorder")||id.equals("@reader")?0xff9c241f:id.equals("@readinglist")?0xff9a352b:id.equals("@help")?0xff006b61:id.equals("@characters")?0xff70409c:0xff316c99;icon.setBackgroundColor(color);int pad=Aero.dp(c,3);icon.setPadding(pad,pad,pad,pad);icon.addView(new EightTileGlyph(c,id),new FrameLayout.LayoutParams(-1,-1));icon.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);return icon;}
    static void close(View body){if(body instanceof EightWordPad)((EightWordPad)body).close();if(body instanceof EightPaint)((EightPaint)body).close();if(body instanceof EightReader)((EightReader)body).close();if(body instanceof EightSoundRecorder)((EightSoundRecorder)body).close();if(body instanceof EightAlarms)((EightAlarms)body).close();if(body instanceof EightMobilityCenter)((EightMobilityCenter)body).close();}
    private EightTools(){}
}
