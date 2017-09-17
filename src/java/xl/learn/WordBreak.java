package xl.learn;

import com.google.common.base.Strings;

import java.util.*;

/**
 *
 *
 * Created by xuelin on 5/3/17.
 */
public class WordBreak {

    private static void addMatch(int from, String word, Map<Integer, Set<String>> prefixEndWords) {
        int index = from + word.length();
        prefixEndWords.putIfAbsent(index, new HashSet<>());
        if (prefixEndWords.get(index).add(word));
    }

    private static void breakItFrom(String ss, int from, Collection<String> words, Map<Integer, Set<String>> prefixEndWords) {
        for (String word: words) {
            String substr = ss.substring(from);
            if (substr.startsWith(word)) {
                addMatch(from, word, prefixEndWords);
            }
        }
    }

    public static List<List<String>> breakIt(String ss, Collection<String> dictWords) {
        Map<Integer, Set<String>> prefixEndWords = new HashMap<>();
        int from = 0;
        while (from < ss.length()) {
            breakItFrom(ss, from, dictWords, prefixEndWords);
            if (from == 0 && prefixEndWords.isEmpty())
                return Collections.emptyList();
            final int finalFrom = from;
            from = (prefixEndWords.keySet()).stream().filter(x -> x.intValue() > finalFrom).min(Comparator.<Integer>naturalOrder()).get();
        }

        List<List<String>> lists = new ArrayList<List<String>>();
        List<String> list = new ArrayList<>();
        list.add("");
        lists.add(list);
        return collect(ss.length(), prefixEndWords, lists);

    }

    private static List<List<String>> collect(int end, Map<Integer, Set<String>> prefixEndWords, List<List<String>> currLists) {
        List<List<String>> newLists = new ArrayList<>();
        for (String word: prefixEndWords.get(end)) {
            List<List<String>> thisLists = new ArrayList<>();
            for (List<String> list: currLists) {
                ArrayList<String> newList = new ArrayList<>();
                newList.addAll(list);
                newList.add(0, word);
                thisLists.add(newList);
            }

            if (end - word.length() > 0)
                thisLists = collect(end - word.length(), prefixEndWords, thisLists);

            newLists.addAll(thisLists);

        }
        return newLists;
    }

    public static List<List<String>> breakIt2(String ss, Collection<String> dictWords) {
        int n = ss.length();
        List<List<String>>[] dp = new List[n + 1];
        List<List<String>> initial = new ArrayList<>();
        List<String> newList = new ArrayList<>();
        newList.add("");
        initial.add(newList);
        dp[0] = initial;

        for (int i = 1; i < n+1; i++) {
            List<List<String>> newLists = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                if (dictWords.contains(ss.substring(j, i))) {
                    for (List<String> list: dp[j]) {
                        newList = new ArrayList<>();
                        newList.addAll(list);
                        newList.add(ss.substring(j, i));
                        newLists.add(newList);
                    }
                }
            }
            dp[i] = newLists;
        }

        return dp[n];
    }


    public static void main(String[] args0) {
        String[] wordsArr = new String[]{
                "cat", "cats", "and", "sand", "dog"
        };
        String ss = "catsanddog";
        List<String> words = Arrays.asList(wordsArr);
        List<List<String>> answers = breakIt2(ss, words);
        for (List<String> answer: answers) {
            System.out.println(String.join(" ", answer));
        }
    }
}
