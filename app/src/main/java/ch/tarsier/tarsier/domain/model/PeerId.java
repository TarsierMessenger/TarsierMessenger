package ch.tarsier.tarsier.domain.model;

import java.util.Arrays;

/**
 * @author romac
 */
public class PeerId {

    private byte[] mBytes;

    public PeerId(byte[] bytes) {
        mBytes = bytes;
    }

    byte[] getBytes() {
        return mBytes;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        // noinspection SimplifiableIfStatement
        if (that == null || this.getClass() != that.getClass()) {
            return false;
        }

        return Arrays.equals(mBytes, ((PeerId) that).getBytes());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mBytes);
    }

}
