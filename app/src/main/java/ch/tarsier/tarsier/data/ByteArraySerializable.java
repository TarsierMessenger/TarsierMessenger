package ch.tarsier.tarsier.data;

/**
 * To be implemented by classes that can be serialized to a byte array.
 *
 * @author romac
 */
public interface ByteArraySerializable {

    /**
     * Serialize this instance into a byte array.
     *
     * @return a byte array
     */
    byte[] toByteArray();
}
