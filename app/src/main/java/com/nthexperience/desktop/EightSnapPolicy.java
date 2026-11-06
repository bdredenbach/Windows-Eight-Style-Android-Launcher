package com.nthexperience.desktop;

/** Density-independent usable bounds, rather than Windows hardware pixel requirements. */
final class EightSnapPolicy {
    static boolean fits(EightProfile profile,int width,EightAppSpec primary,EightAppSpec secondary){
        if(primary==null||secondary==null||primary.id.equals(secondary.id))return false;
        if(profile==EightProfile.EIGHT)return secondary.compact&&width>=Math.max(480,primary.minimum)+320+8;
        return width>=primary.minimum+secondary.minimum+8;
    }
    static int split(EightProfile profile,int width,float ratio,EightAppSpec primary,EightAppSpec secondary){
        if(!fits(profile,width,primary,secondary))return width;
        if(profile==EightProfile.EIGHT)return width-328;
        if(!Float.isFinite(ratio))ratio=.5f;
        return Math.max(primary.minimum,Math.min(width-8-secondary.minimum,Math.round((width-8)*ratio)));
    }
    private EightSnapPolicy(){}
}
