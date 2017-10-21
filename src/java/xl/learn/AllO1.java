package xl.learn;

import java.util.*;

/**
 *
 * Implement a data structure supporting the following operations:

 Inc(Key) - Inserts a new key with value 1. Or increments an existing key by 1.
 Key is guaranteed to be a non-empty string.
 Dec(Key) - If Key's value is 1, remove it from the data structure.
 Otherwise decrements an existing key by 1. If the key does not exist, this function does nothing.
 Key is guaranteed to be a non-empty string.
 GetMaxKey() - Returns one of the keys with maximal value.
 If no element exists, return an empty string "".
 GetMinKey() - Returns one of the keys with minimal value.
 If no element exists, return an empty string "".
 Challenge: Perform all these in O(1) time complexity.

 solution:
 ValueNode { int val; ValueNode next; ValueNode prev; ValueNode up; ValueNode down; }
   use prev/next to link nodes with same value. use up/down to link next bigger/smaller node in the doubly linked list as explained below.
 Map<Key, ValueNode> kvMap

 Double linked list of "head" ValueNodes to keep track of different values. A head ValueNode for a value is just any ValueNode with the value.
 head and tail of this list remember min and max.


 Map<Key, Cell> keyData: Cell is linked structure with left, right, up, down links.
 Map<Integer, Cell> headers: map value to header for the value
 mainHeader
     |
  header for i <--> key i1 <-->  key i2 <--> header
     |
  header for j <-->  key j1
     |
  header for k <-->  key k1 <--> key k2 <--> key k3 <-->
     |
 mainHeader

 Inc(Key): from keyData, get its Cell. if exists, remove original cell from row. if list for the row is empty now, remove the header.
     for new Cell with increased value, add it to the keyData map. if no row for new cell, insert new header row. add cell to the row.
 Dec(Key): opposite operation from above.
 GetMaxKey() - mainHeader.up.right.key
 GetMinKey() - mainHeader.down.right.key

 */

public class AllO1<T> {
    public static class Cell<T> {
        int val;
        T key;
        Cell<T> left;
        Cell<T> right;
        Cell<T> up;
        Cell<T> down;

        public Cell(T key, int val, Cell left, Cell right, Cell up, Cell down) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
            this.up = up;
            this.down = down;
        }
    }

    final Map<T, Cell<T>> kvs;
    final Map<Integer, Cell<T>> headers;
    final Cell<T> mainHeader;

    public AllO1() {
        kvs = new HashMap<>();
        headers = new HashMap<>();
        mainHeader = new Cell(null, 0, null, null, null, null);
        mainHeader.up = mainHeader;
        mainHeader.down = mainHeader;
        mainHeader.left = null;
        mainHeader.right = null;
    }

    private void removeCell(Cell<T> cell) {
        kvs.remove(cell.key);

        int val = cell.val;
        Cell<T> header = headers.get(val);

        cell.left.right = cell.right;
        cell.right.left = cell.left;

        if (header.left == header) {
            headers.remove(val);
            header.up.down = header.down;
            header.down.up = header.up;
        }
    }

    private void addCell(Cell<T> cell, Cell<T> headerBelow) {
        kvs.put(cell.key, cell);

        int val = cell.val;
        Cell<T> header = headers.get(val);

        if (header == null) {
            header = new Cell(null, val, null, null, null, null);
            header.left = header;
            header.right = header;
            headers.put(val, header);

            header.up = headerBelow.up;
            header.down = headerBelow;

            headerBelow.up.down = header;
            headerBelow.up = header;
        }

        cell.left = header;
        cell.right = header.right;
        header.right.left = cell;
        header.right = cell;
    }

    public void inc(T key) {
        Cell<T> fromCell = kvs.get(key);
        Cell<T> headerBelow;
        final int fromVal, toVal;
        if (fromCell != null) {
            fromVal = fromCell.val;
            headerBelow = headers.get(fromVal).down;
            removeCell(fromCell);
        }
        else {
            fromVal = 0;
            headerBelow = mainHeader.down;
        }
        toVal = fromVal + 1;
        headerBelow = headerBelow.val == toVal ? headerBelow.down : headerBelow;
        Cell<T> toCell = new Cell(key, toVal, null, null, null, null);
        addCell(toCell, headerBelow);
    }


    public void dec(T key) {
        Cell<T> fromCell = kvs.get(key);
        if (fromCell == null)
            return;

        Cell<T> headerBelow;
        final int fromVal, toVal;
        fromVal = fromCell.val;
        headerBelow = headers.get(fromVal).up;
        removeCell(fromCell);

        toVal = fromVal - 1;
        headerBelow = headerBelow.val == toVal ? headerBelow.down : headerBelow;
        Cell<T> toCell = new Cell(key, toVal, null, null, null, null);
        addCell(toCell, headerBelow);
    }

    public T getMaxKey() {
        Cell<T> cell = mainHeader.up.right;
        if (cell == null)
            return null;
        else
            return cell.key;
    }

    public T getMinKey() {
        Cell<T> cell = mainHeader.down.right;
        if (cell == null)
            return null;
        else
            return cell.key;
    }

    public static void main(String[] args) {

    }
}
