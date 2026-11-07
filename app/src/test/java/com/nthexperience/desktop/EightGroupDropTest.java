package com.nthexperience.desktop;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class EightGroupDropTest {
    private static EightStartData.Group group(String name,String...ids){EightStartData.Group group=new EightStartData.Group(name);for(String id:ids)group.items.add(new EightStartData.Item(id,"Wide"));return group;}
    @Test public void draggingBeyondTheLastGroupKeepsTheTileAndItsSize(){
        EightStartData.Group first=group("Essentials","@desktop","@browser"),last=group("Tools","@notes");List<EightStartData.Group> groups=new ArrayList<>(Arrays.asList(first,last));EightStartData.Item tile=first.items.get(1);
        assertTrue(EightStartData.insertNewGroup(groups,"@browser",2));assertEquals(3,groups.size());assertSame(tile,groups.get(2).items.get(0));assertEquals("Wide",tile.size);assertEquals(1,first.items.size());assertSame(last,groups.get(1));
    }
    @Test public void insertingBetweenGroupsKeepsTheirOrderAndUsesAUniqueName(){
        EightStartData.Group first=group("Group 1","@desktop","@browser"),last=group("Tools","@notes");List<EightStartData.Group> groups=new ArrayList<>(Arrays.asList(first,last));
        assertTrue(EightStartData.insertNewGroup(groups,"@browser",1));assertSame(first,groups.get(0));assertSame(last,groups.get(2));assertEquals("Group 2",groups.get(1).name);assertEquals("@browser",groups.get(1).items.get(0).id);
    }
    @Test public void missingTilesInvalidPositionsAndGroupLimitKeepTheLayoutIntact(){
        List<EightStartData.Group> groups=new ArrayList<>();for(int n=0;n<EightStartData.MAX_GROUPS;n++)groups.add(group("Existing "+n,"tile-"+n));groups.get(0).items.add(new EightStartData.Item("extra","Medium"));
        assertFalse(EightStartData.insertNewGroup(groups,"missing",0));assertFalse(EightStartData.insertNewGroup(groups,"extra",-1));assertFalse(EightStartData.insertNewGroup(groups,"extra",groups.size()+1));assertFalse(EightStartData.insertNewGroup(groups,"extra",1));assertEquals(16,groups.size());assertEquals("extra",groups.get(0).items.get(1).id);
    }
    @Test public void movingTheOnlyTileRemovesItsEmptySourceEvenAtTheGroupLimit(){
        List<EightStartData.Group> groups=new ArrayList<>();for(int n=0;n<EightStartData.MAX_GROUPS;n++)groups.add(group("Existing "+n,"tile-"+n));EightStartData.Item tile=groups.get(0).items.get(0);
        assertTrue(EightStartData.insertNewGroup(groups,tile.id,groups.size()));assertEquals(16,groups.size());assertSame(tile,groups.get(15).items.get(0));assertEquals("Existing 1",groups.get(0).name);
    }
}
