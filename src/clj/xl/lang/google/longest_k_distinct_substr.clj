"
Given a string, find the length of the longest substring T that contains at most k distinct characters.

For example, Given s = 'eceba' and k = 2,

T is 'ece' which its length is 3.

Analysis:
Scan from beginning, remember last new char index and set of new chars.
Time is O(n).
"

(ns xl.lang.google.longest-k-distinct-substr)
