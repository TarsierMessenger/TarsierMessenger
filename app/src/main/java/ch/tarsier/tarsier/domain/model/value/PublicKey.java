package ch.tarsier.tarsier.domain.model.value;

import java.util.Arrays;

/**
 * @author romac
 */
public class PublicKey {

    private byte[] mBytes;

    public PublicKey(byte[] bytes) {
        mBytes = bytes;
    }

    public byte[] getBytes() {
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

        return Arrays.equals(mBytes, ((PublicKey) that).getBytes());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mBytes);
    }

}
