package logic;

/**
 * Created by Mateusz on 20.01.2017.
 * Project VoxelMemory
 */
public class KnownElement {

    public KnownElement(int x, int y, int block) {
        this.x = x;
        this.y = y;
        this.block = block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KnownElement that = (KnownElement) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return block == that.block;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + block;
        return result;
    }

    int x;
    int y;
    int block;
}
