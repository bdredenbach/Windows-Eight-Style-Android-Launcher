package com.nthexperience.desktop;

import java.util.*;

/** Content never owns a tile's position, selection or launch destination. */
final class EightTileSnapshot {
    static final class Item {
        final String tag,title,detail,image;
        Item(String tag,String title,String detail){this(tag,title,detail,"");}
        Item(String tag,String title,String detail,String image){this.tag=tag;this.title=title;this.detail=detail;this.image=image;}
        String key(){return tag+"\n"+title+"\n"+detail+"\n"+image;}
    }
    final List<Item> items; final long expiresAt; final int badge;
    EightTileSnapshot(List<Item> values,long expiresAt,int badge){
        LinkedHashMap<String,Item> tagged=new LinkedHashMap<>();
        for(Item item:values)if(item!=null&&!item.tag.isEmpty())tagged.put(item.tag,item);
        ArrayList<Item> bounded=new ArrayList<>(tagged.values());
        if(bounded.size()>5)bounded=new ArrayList<>(bounded.subList(bounded.size()-5,bounded.size()));
        items=Collections.unmodifiableList(bounded);this.expiresAt=expiresAt;this.badge=Math.max(0,badge);
    }
    boolean expired(long now){return expiresAt>0&&now>=expiresAt;}
    String badgeText(){return badge==0?"":badge>99?"99+":Integer.toString(badge);}
}
