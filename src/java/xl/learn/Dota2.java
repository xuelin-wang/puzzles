package xl.learn;

/**
 * In the world of Dota2, there are two parties: the Radiant and the Dire.

 The Dota2 senate consists of senators coming from two parties.
 Now the senate wants to make a decision about a change in the Dota2 game.
 The voting for this change is a round-based procedure. In each round,
 each senator can exercise one of the two rights:

 Ban one senator's right:
 A senator can make another senator lose all his rights in this and all the following rounds.
 Announce the victory:
 If this senator found the senators who still have rights to vote are all from the same party,
 he can announce the victory and make the decision about the change in the game.
 Given a string representing each senator's party belonging.
 The character 'R' and 'D' represent the Radiant party and the Dire party respectively.
 Then if there are n senators, the size of the given string will be n.

 The round-based procedure starts from the first senator to the last senator in the given order.
 This procedure will last until the end of voting. All the senators who have lost their rights will
 be skipped during the procedure.

 Suppose every senator is smart enough and will play the best strategy for his own party,
 you need to predict which party will finally announce the victory and make the change in the Dota2 game.
 The output should be Radiant or Dire.


 */
public class Dota2 {

    public static void main(String[] args) {
        for (String input: new String[]{
                "RD",
                "RDD",
                "DR",
                "DRR",
                "DRDRR",
                "RDRDD",
                "RDRDR",
                "DRDRD"
        }) {
            System.out.println("input: " + input);
            System.out.println("output: " + winner(input.toCharArray()));
        }
    }

    public static String winner(char[] input)
    {
        int d = 0;
        int r = 0;
        int n = input.length;
        int i = 0;
        boolean changed = false;

        while (true) {
            char ch = input[i];
            if (ch == 'D') {
                if (d < r) {
                    input[i] = ' ';
                    changed = true;
                }
                d++;
            }
            else if (ch == 'R') {
                if (r < d) {
                    input[i] = ' ';
                    changed = true;
                }
                r++;
            }

            i++;
            if (i == n) {
                if (r != d) {
                    for (int j = 0; j < n; j++) {
                        if (r > d && input[j] == 'D') {
                            input[j] = ' ';
                            r--;
                            changed = true;
                        }
                        if (r < d && input[j] == 'R') {
                            input[j] = ' ';
                            d--;
                            changed = true;
                        }
                        if (r==d)
                            break;
                    }
                    if (r > d)
                        return "Radient";
                    if (r < d)
                        return "Dire";
                }

                if (!changed) {
                    if (r > d)
                        return "Radient";
                    else if (d > r)
                        return "Dire";
                    else
                        throw new RuntimeException("impossible r==d: " + r);
                }
                i = 0;
                r = d = 0;
                changed = false;
            }
        }
    }
}
