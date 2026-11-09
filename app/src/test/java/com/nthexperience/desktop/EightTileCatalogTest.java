package com.nthexperience.desktop;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class EightTileCatalogTest {
    @Test public void everyOwnedAppAndUtilityHasOneDefaultTile(){
        Set<String> ids=new HashSet<>();for(EightTileCatalog.Entry e:EightTileCatalog.ALL){assertTrue(e.id,ids.add(e.id));assertFalse(e.title.isEmpty());}
        for(EightAppSpec app:EightAppSpec.ALL)assertTrue(app.id,ids.contains(app.id));
        for(String gadget:GadgetCatalog.IDS)if(GadgetCatalog.extra(gadget))assertTrue(gadget,ids.contains("@gadget:"+gadget));
        assertTrue(ids.size()<=EightStartData.MAX_TILES);assertEquals(54,ids.size());
        for(String id:EightLiveTiles.IDS)assertTrue(id,ids.contains(id));
    }
    @Test public void eachProfileUsesValidVariedSizesAndNonoverlappingPacking(){
        for(EightProfile profile:EightProfile.values()){
            List<EightStartData.Group> groups=EightStartData.defaults(profile);assertEquals(7,groups.size());Set<String> sizes=new HashSet<>();
            for(EightStartData.Group group:groups){List<String> requested=new ArrayList<>();for(EightStartData.Item item:group.items){assertTrue(profile.supports(item.size));requested.add(item.size);sizes.add(item.size);}
                for(int rows:new int[]{4,6,12}){List<EightTileLayout.Cell> cells=EightTileLayout.place(requested,rows,profile);for(int i=0;i<cells.size();i++){EightTileLayout.Cell cell=cells.get(i);assertTrue(cell.row+cell.h<=rows);for(int j=0;j<i;j++)assertFalse(EightTileLayout.overlaps(cell,cells.get(j)));}}
            }
            assertEquals(profile==EightProfile.EIGHT?new HashSet<>(Arrays.asList("Medium","Wide")):new HashSet<>(Arrays.asList("Small","Medium","Wide","Large")),sizes);
        }
    }
    @Test public void tileLabelsStayReadableAcrossBothVisualStyles(){
        int paper=0,color=0;Set<Integer> fills=new HashSet<>();for(EightTileCatalog.Entry e:EightTileCatalog.ALL){assertTrue(e.id+" contrast",TenAccent.contrast(e.color,e.ink())>=4.5);fills.add(e.color);if(e.paper)paper++;else color++;}assertTrue(paper>10&&color>10&&fills.size()>25);
    }
    @Test public void defaultsNeverShareMutableGroupsOrTiles(){
        List<EightStartData.Group> a=EightStartData.defaults(EightProfile.EIGHT),b=EightStartData.defaults(EightProfile.EIGHT),c=EightStartData.defaults(EightProfile.EIGHT_POINT_ONE);a.get(0).name="Changed";a.get(0).items.get(0).size="Medium";a.get(0).items.remove(1);assertEquals("Essentials",b.get(0).name);assertEquals("Wide",b.get(0).items.get(0).size);assertEquals(6,b.get(0).items.size());assertEquals(6,c.get(0).items.size());assertEquals("Computer",EightTileCatalog.get("@computer").title(EightProfile.EIGHT));assertEquals("This PC",EightTileCatalog.get("@computer").title(EightProfile.EIGHT_POINT_ONE));
    }
}
