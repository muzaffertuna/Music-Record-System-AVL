package MusicRecordSystemAVL;

/**
 *
 * @author tokta
 */
public class AVLNodeComparable<Key extends Comparable<Key>> {

    public final Key key;
    public int index;
    public int height;
    public int size;
    public AVLNodeComparable left;
    public AVLNodeComparable right;

    public AVLNodeComparable(Key key, int index, int height, int size) {
        this.key = key;
        this.index = index;
        this.height = height;
        this.size = size;
    }

}
