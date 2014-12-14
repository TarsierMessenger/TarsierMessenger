package ch.tarsier.tarsier.test.util;

import android.test.AndroidTestCase;

import ch.tarsier.tarsier.util.ByteUtils;

/**
 * ByteUtilsTest test the ByteUtils class.
 *
 * @see ch.tarsier.tarsier.util.ByteUtils
 * @author gluthier
 */
public class ByteUtilsTest extends AndroidTestCase {

    private static final int LENGTH = 3;
    private static final byte[] ARRAY_1 = new byte[] {0, 1};
    private final byte[] ARRAY_2 = new byte[] {2};

    public void testCombineTwoByteArrays() {
        byte[] result = ByteUtils.combine(ARRAY_1, ARRAY_2);

        assertEquals(LENGTH, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        assertEquals(2, result[2]);
    }

    public void testSplitTwoByteArrays() {
        byte[] combinedArrays = ByteUtils.combine(ARRAY_1, ARRAY_2);
        byte[][] result = ByteUtils.split(combinedArrays, 2, 1);

        assertEquals(2, result[0].length);
        assertEquals(1, result[1].length);
        assertEquals(ARRAY_1[0], result[0][0]);
        assertEquals(ARRAY_1[1], result[0][1]);
        assertEquals(ARRAY_2[0], result[1][0]);
    }

    public void testPrependIntToArray() {
        byte[] result = ByteUtils.prependInt(0, ARRAY_2);

        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(2, result[1]);
    }
}
