package xl.learn.google;

/**
 * Created by xuelin on 10/1/17.
 * Given an array of n distinct non-empty strings, you need to generate minimal possible abbreviations for every word following rules below.

 Begin with the first character and then the number of characters abbreviated, which followed by the last character.
 If there are any conflict, that is more than one words share the same abbreviation, a longer prefix is used instead of only the first character until making the map from word to abbreviation become unique. In other words, a final abbreviation cannot map to more than one original words.
 If the abbreviation doesn't make the word shorter, then keep it as original.
 Example:
 Input: ["like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion"]
 Output: ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]
 Note:
 Both n and the length of each word will not exceed 400.
 The length of each word is greater than 1.
 The words consist of lowercase English letters only.
 The return answers should be in the same order as the original array.

 Analysis:
   Sort it by length then lexical order.
 Then for each word, <=3, leave it to original word. otherwise: first letter + length + last letter.
 Check for dups. for each dup a<n>b, for dup words ws, find max prefix between adjacent words in ws m, if use m+1 prefix:
 if length longer or equals orig length, use orig words for ws. otherwise, use m+1 prefix.
 Cost: O(nlogn), space O(n).

 Optimization:
 No need to sort. use map to track abbrev. word to orig word to get list of dupe words per abrev.
 For each dupe word, to find max prefix, need O(klogk)?

 */
public class MinWordAbbreviations {
}
