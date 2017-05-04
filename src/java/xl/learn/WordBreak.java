package xl.learn;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuelin on 5/3/17.
 */
public class WordBreak {
    public static List<List<String>> breakIt(String ss, List<String> words) {
        Map<String, List<List<String>>> strToAnswers = new HashMap<>();
        List<String> answer = new ArrayList<>();
        answer.add("");
        List<List<String>> answers = new ArrayList<>();
        answers.add(answer);
        strToAnswers.put("", answers);
        return breakIt(ss, words, strToAnswers);
    }

    public static List<List<String>> breakIt(String ss, List<String> words, Map<String, List<List<String>>> strToAnswers) {
        if (strToAnswers.containsKey(ss)) {
            return strToAnswers.get(ss);
        }

        for (String word: words) {
            if (ss.startsWith(word)) {
                List<List<String>> answers = breakIt(ss.substring(word.length()), words, strToAnswers);
                List<List<String>> newAnswers =  strToAnswers.get(ss);
                if (newAnswers == null) {
                    newAnswers = new ArrayList<>();
                    strToAnswers.put(ss, newAnswers);
                }
                for (List<String> answer: answers) {
                    List<String> newAnswer = new ArrayList<>();
                    newAnswer.add(word);
                    newAnswer.addAll(answer);
                    newAnswers.add(newAnswer);
                }
            }
        }
        return strToAnswers.get(ss);
    }

    public static void main(String[] args0) {
        String[] args = new String[]{
                "catsanddog", "cat", "cats", "and", "sand", "dog"
        };
        String ss = args[0];
        List<String> words = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            words.add(args[i]);
        }
        List<List<String>> answers = breakIt(ss, words);
        for (List<String> answer: answers) {
            System.out.println(String.join(" ", answer));
        }
    }
}
