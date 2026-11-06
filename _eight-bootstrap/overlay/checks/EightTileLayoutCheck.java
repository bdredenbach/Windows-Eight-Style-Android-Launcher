package com.nthexperience.desktop;
import java.util.*;

/** Property checks across changing viewport heights, sizes, and both profiles. */
public final class EightTileLayoutCheck {
    public static void main(String[] args){
        Random random=new Random(8102012);int checked=0;
        for(EightProfile profile:EightProfile.values())for(int rows=4;rows<=16;rows++)for(int sample=0;sample<90;sample++){
            List<String> sizes=new ArrayList<>();String[] allowed=profile.sizes();int count=random.nextInt(65);
            for(int n=0;n<count;n++)sizes.add(allowed[random.nextInt(allowed.length)]);
            List<EightTileLayout.Cell> cells=EightTileLayout.place(sizes,rows,profile),again=EightTileLayout.place(sizes,rows,profile);
            if(cells.size()!=count)throw new AssertionError("Lost tile");
            for(int i=0;i<count;i++){
                EightTileLayout.Cell cell=cells.get(i),same=again.get(i);
                if(cell.col<0||cell.row<0||cell.row+cell.h>rows||cell.col+cell.w>Math.max(4,count*4))throw new AssertionError("Tile outside group bounds");
                if(cell.col!=same.col||cell.row!=same.row||cell.w!=same.w||cell.h!=same.h)throw new AssertionError("Unstable layout");
                for(int j=0;j<i;j++)if(EightTileLayout.overlaps(cell,cells.get(j)))throw new AssertionError("Overlapping tiles");
                checked++;
            }
        }
        EightTileLayout.Cell small=EightTileLayout.place(Arrays.asList("Small"),4,EightProfile.EIGHT_POINT_ONE).get(0);
        EightTileLayout.Cell normalized=EightTileLayout.place(Arrays.asList("Small"),4,EightProfile.EIGHT).get(0);
        if(small.w!=1||small.h!=1||normalized.w!=2||normalized.h!=2)throw new AssertionError("Profile tile formats mixed");
        System.out.println("PASS: "+checked+" tile placements; height bounds, overlap, determinism and profile formats");
    }
}
