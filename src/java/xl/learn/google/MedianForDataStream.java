package xl.learn.google;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by xuelin on 9/26/17.
 * Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.

 Examples:
 [2,3,4] , the median is 3

 [2,3], the median is (2 + 3) / 2 = 2.5

 Design a data structure that supports the following two operations:

 void addNum(int num) - Add a integer number from the data stream to the data structure.
 double findMedian() - Return the median of all elements so far.
 For example:

 addNum(1)
 addNum(2)
 findMedian() -> 1.5
 addNum(3)
 findMedian() -> 2

 Solution:

 Two heaps:
max heap of smaller half and min heap of bigger half. The two tops are median (candidates)
 Need balance the two heaps:
 when h1 - h2 > 1: extract h1 top insert into h2.
 O(logN, 1)!

 Naive:
 keep current elements sorted:
 O(1, n^2)

 Naive 2:
 sort when get mediuan
 O(1, nLogn)

 */
public class MedianForDataStream {

    public MedianForDataStream() {
    }
    public void addNum(int num) {

    }
    public double findMedian() {
        throw new NotImplementedException();
    }
}
