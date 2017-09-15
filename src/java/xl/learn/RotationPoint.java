package xl.learn;

/**
 * Created by xuelin on 9/14/17.
 *
 * words dictionally ordered from middle, find the rotation index
 *
 */
public class RotationPoint {
    public static int findPoint(String[] words) {
        //n...za......m
        //Note that for mid point p, if its greater than first element, then rotation point is to the right,
        // otherwise, left.

        return find(words, 0, words.length);
    }

    private static int find(String[] words, int start, int end) {
        if (start == end)
            return start;

        if (start == end - 1) {
            if (words[start].compareToIgnoreCase(words[end]) < 0)
                return start;
            else
                return end;
        }

        int mid = (start + end) / 2;
        if (words[mid].compareToIgnoreCase(words[start]) > 0)
            return find(words, mid + 1, end);
        else
            return find(words, start, mid);

    }

    public static void main(String[] args) {

        String[] words = {
        "ptolemaic",
                "retrograde",
                "supplant",
                "undulate",
                "xenoepist",
                "asymptote",
                "babka",
                "banoffee",
                "engender",
                "karpatka",
                "othellolagkage"};

        int index = findPoint(words);
        System.out.println("index: " + index);

    }
}
