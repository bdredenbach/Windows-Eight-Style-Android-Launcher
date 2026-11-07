package com.nthexperience.desktop;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class EightTileSnapshotTest {
    @Test public void queueReplacesTagsAndKeepsOnlyFiveItems(){List<EightTileSnapshot.Item> rows=new ArrayList<>();for(int i=0;i<8;i++)rows.add(new EightTileSnapshot.Item("id"+i,"old "+i,""));rows.add(new EightTileSnapshot.Item("id7","revised",""));EightTileSnapshot s=new EightTileSnapshot(rows,0,0);assertEquals(5,s.items.size());assertEquals("id3",s.items.get(0).tag);assertEquals("revised",s.items.get(4).title);try{s.items.clear();fail("Snapshot must be immutable");}catch(UnsupportedOperationException expected){}}
    @Test public void expiryAndBadgeBoundariesAreExplicit(){EightTileSnapshot s=new EightTileSnapshot(Collections.emptyList(),1000,100);assertFalse(s.expired(999));assertTrue(s.expired(1000));assertEquals("99+",s.badgeText());assertEquals("",new EightTileSnapshot(Collections.emptyList(),0,-1).badgeText());assertEquals("99",new EightTileSnapshot(Collections.emptyList(),0,99).badgeText());assertFalse(new EightTileSnapshot(Collections.emptyList(),0,1).expired(Long.MAX_VALUE));}
    @Test public void nestedSettingsHaveUniqueStableRoutes(){assertEquals(9,EightSettingsPages.ALL.size());for(Map.Entry<String,String[][]> category:EightSettingsPages.ALL.entrySet()){Set<String> routes=new HashSet<>();for(String[] row:category.getValue()){assertTrue(routes.add(row[0]));assertSame(row,EightSettingsPages.find(category.getKey(),row[0]));assertFalse(row[1].isEmpty());}}assertNull(EightSettingsPages.find("unknown","none"));}
}
