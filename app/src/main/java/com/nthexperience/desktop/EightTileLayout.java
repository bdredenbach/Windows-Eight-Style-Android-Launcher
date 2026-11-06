package com.nthexperience.desktop;

import java.util.*;

/** Height-bounded packing. Start continues horizontally instead of growing down. */
final class EightTileLayout {
    static final class Cell {
        final int col,row,w,h;
        Cell(int col,int row,int w,int h){this.col=col;this.row=row;this.w=w;this.h=h;}
    }
    static List<Cell> place(List<String> sizes,int rows,EightProfile profile) {
        rows=Math.max(4,Math.min(16,rows));
        List<Cell> result=new ArrayList<>();
        for(String requested:sizes) {
            String size=profile.supports(requested)?requested:"Medium";
            int w=size.equals("Small")?1:size.equals("Wide")||size.equals("Large")?4:2;
            int h=size.equals("Small")?1:size.equals("Large")?4:2;
            search:for(int col=0;;col++)for(int row=0;row+h<=rows;row++) {
                Cell candidate=new Cell(col,row,w,h);
                if(!free(candidate,result))continue;
                result.add(candidate);break search;
            }
        }
        return result;
    }
    static boolean overlaps(Cell a,Cell b){return a.col<b.col+b.w&&b.col<a.col+a.w&&a.row<b.row+b.h&&b.row<a.row+a.h;}
    private static boolean free(Cell candidate,List<Cell> prior){for(Cell cell:prior)if(overlaps(candidate,cell))return false;return true;}
    private EightTileLayout(){}
}
