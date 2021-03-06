package sid.roborally.application_functionality;

import org.junit.Test;
import sid.roborally.application_functionality.reference.TileIDReference;

import static org.junit.Assert.assertEquals;

public class TileIDReferenceTest {

    @Test
    public void ConvertFlagIndexToFlagIdTest() {
        assertEquals(1, TileIDReference.flagIndexToId(55));
        assertEquals(2, TileIDReference.flagIndexToId(63));
        assertEquals(3, TileIDReference.flagIndexToId(71));
        assertEquals(4, TileIDReference.flagIndexToId(79));
    }

    @Test (expected = IllegalArgumentException.class)
    public void FlagIndexIllegalArgumentThrowsException() {
        assertEquals(1,TileIDReference.flagIndexToId(54));
    }

    @Test
    public void ConvertArchiveMarkerIndexToArchiveIDTest() {
        assertEquals(1, TileIDReference.archiveIndexToID(121));
        assertEquals(2, TileIDReference.archiveIndexToID(122));
        assertEquals(3, TileIDReference.archiveIndexToID(123));
        assertEquals(4, TileIDReference.archiveIndexToID(124));
        assertEquals(5, TileIDReference.archiveIndexToID(129));
        assertEquals(6, TileIDReference.archiveIndexToID(130));
        assertEquals(7, TileIDReference.archiveIndexToID(131));
        assertEquals(8, TileIDReference.archiveIndexToID(132));
    }

    @Test (expected = IllegalArgumentException.class)
    public void ArchiveMarkerIllegalArgumentThrowsException() { assertEquals(1,TileIDReference.archiveIndexToID(127)); }
}
