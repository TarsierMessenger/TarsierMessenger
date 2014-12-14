package ch.tarsier.tarsier.util;

/**
 * ByteUtils is the class that provides bytes utilities.
 *
 * @author FredericJacobs
 */
public class ByteUtils {

    public static byte[] combine(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static byte[][] split(byte[] input, int firstLength, int secondLength) {
        byte[][] parts = new byte[2][];

        parts[0] = new byte[firstLength];
        System.arraycopy(input, 0, parts[0], 0, firstLength);

        parts[1] = new byte[secondLength];
        System.arraycopy(input, firstLength, parts[1], 0, secondLength);

        return parts;
    }

    public static byte[] prependInt(int a, byte[] b) {
        byte[] prepended = new byte[1];
        prepended[0] = (byte) a;
        return combine(prepended, b);
    }
}
