package MusicRecordSystemAVL;

import java.util.NoSuchElementException;

/**
 *
 * @author tokta
 */
public class AVLTreeComparable<Key extends Comparable<Key>> {

    private AVLNodeComparable root;

    public AVLTreeComparable() {
        root = null;
    }

    public int height() {
        return height(root);
    }

    private int height(AVLNodeComparable x) {
        if (x == null) {
            return -1;
        }
        return x.height;
    }

    private int size(AVLNodeComparable x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Integer search(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to search() is null");
        }

        AVLNodeComparable x = get(root, key);

        if (x == null) {
            return null;
        }

        return x.index;
    }

    private AVLNodeComparable get(AVLNodeComparable x, Key key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo((Key) x.key);

        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x;
        }
    }

    public boolean contains(Key key) {
        return (search(key) != -1);
    }

    public void insert(Key key, int index) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }

        if (index == -1) {
            delete(key);
            return;
        }

        root = put(root, key, index);
    }

    private AVLNodeComparable put(AVLNodeComparable x, Key key, int index) {
        if (x == null) {
            return new AVLNodeComparable(key, index, 0, 1);
        }

        int cmp = key.compareTo((Key) x.key);

        if (cmp < 0) {
            x.left = put(x.left, key, index);
        } else if (cmp > 0) {
            x.right = put(x.right, key, index);
        } else {
            x.index = index;
            return x;
        }

        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return balance(x);
    }

    private AVLNodeComparable balance(AVLNodeComparable x) {
        if (balanceFactor(x) < -1) {
            if (balanceFactor(x.right) > 0) {
                x.right = rotateRight(x.right);
            }
            x = rotateLeft(x);

        } else if (balanceFactor(x) > 1) {
            if (balanceFactor(x.left) < 0) {
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        }
        return x;
    }

    private AVLNodeComparable rotateRight(AVLNodeComparable x) {
        AVLNodeComparable y = x.left;
        x.left = y.right;
        y.right = x;

        y.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

    private AVLNodeComparable rotateLeft(AVLNodeComparable x) {
        AVLNodeComparable y = x.right;
        x.right = y.left;
        y.left = x;

        y.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

    private int balanceFactor(AVLNodeComparable x) {
        return (height(x.left) - height(x.right));
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }

        if (!contains(key)) {
            return;
        }

        root = delete(root, key);
    }

    private AVLNodeComparable delete(AVLNodeComparable x, Key key) {
        int cmp = key.compareTo((Key) x.key);

        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.left == null) {
                return x.right;
            } else if (x.right == null) {
                return x.left;
            } else {
                AVLNodeComparable y = x;
                x = min(y.right);
                x.right = deleteMin(y.right);
                x.left = y.left;
            }
        }

        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return balance(x);
    }

    private void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("called deleteMin() with empty symbol table");
        }

        root = deleteMin(root);
    }

    private AVLNodeComparable deleteMin(AVLNodeComparable x) {
        if (x.left == null) {
            return x.right;
        }

        x.left = deleteMin(x.left);

        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return balance(x);
    }

    private void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("called deleteMax() with empty symbol table");
        }

        root = deleteMax(root);
    }

    private AVLNodeComparable deleteMax(AVLNodeComparable x) {
        if (x.right == null) {
            return x.left;
        }

        x.right = deleteMax(x.right);

        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return balance(x);
    }

    private Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("called min() with empty symbol table");
        }

        return (Key) min(root).key;
    }

    private AVLNodeComparable min(AVLNodeComparable x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    private Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("called max() with empty symbol table");
        }
        return (Key) max(root).key;
    }

    private AVLNodeComparable max(AVLNodeComparable x) {
        if (x.right == null) {
            return x;
        }
        return max(x.right);
    }

    public void traverseInOrder() {
        traverseInOrder(root);
    }

    private void traverseInOrder(AVLNodeComparable focus) {
        if (focus.left != null) {
            traverseInOrder(focus.left);
        }

        System.out.println(focus.key);

        if (focus.right != null) {
            traverseInOrder(focus.right);
        }
    }

    public void searchGivenBoundariesOfID(int min, int max, Song[] musicList) {
        searchGivenBoundariesOfID(root, min, max, musicList);
    }

    private void searchGivenBoundariesOfID(AVLNodeComparable focus, int min, int max, Song[] musicList) {
        if (focus.left != null) {
            searchGivenBoundariesOfID(focus.left, min, max, musicList);
        }

        if ((int) focus.key >= min && (int) focus.key <= max) {
            System.out.println(musicList[focus.index]);
        }

        if (focus.right != null) {
            searchGivenBoundariesOfID(focus.right, min, max, musicList);
        }
    }

}

