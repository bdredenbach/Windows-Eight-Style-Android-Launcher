package com.nthexperience.desktop;
import android.view.View;
/** Explicit lifetime disposal for owned utilities, independent of immersive presentation transfers. */
final class EightTools {
    static void close(View body){if(body instanceof EightWordPad)((EightWordPad)body).close();if(body instanceof EightPaint)((EightPaint)body).close();if(body instanceof EightReader)((EightReader)body).close();if(body instanceof EightSoundRecorder)((EightSoundRecorder)body).close();if(body instanceof EightAlarms)((EightAlarms)body).close();if(body instanceof EightMobilityCenter)((EightMobilityCenter)body).close();}
    private EightTools(){}
}
