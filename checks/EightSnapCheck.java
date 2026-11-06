package com.nthexperience.desktop;
import java.util.*;
public final class EightSnapCheck {
    public static void main(String[] args){int count=0;for(EightProfile profile:EightProfile.values())for(EightAppSpec first:EightAppSpec.ALL)for(EightAppSpec second:EightAppSpec.ALL)for(int width=240;width<=1800;width+=13)for(float ratio:new float[]{-.5f,0,.2f,.5f,.9f,2,Float.NaN}){
        boolean fits=EightSnapPolicy.fits(profile,width,first,second);int split=EightSnapPolicy.split(profile,width,ratio,first,second);
        if(!fits){if(split!=width)throw new AssertionError("Narrow fallback corrupted");continue;}
        if(split<first.minimum||width-split-8<second.minimum||first==second)throw new AssertionError("Unusable snapped app bounds");
        if(profile==EightProfile.EIGHT&&(!second.compact||width-split-8!=320))throw new AssertionError("Original Eight companion policy");count++;
    }if(EightAppSpec.get("com.other.app/.Main")!=null)throw new AssertionError("External app treated as owned");System.out.println("PASS: "+count+" supported snap combinations, profile limits, minimum widths and narrow-screen fallback");}
}
