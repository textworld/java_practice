package cn.textworld.java.leetcode;

import java.util.Arrays;
import java.util.List;

public class LeetCode139 {
    public boolean wordBreak(String s, List<String> wordDict) {
        if(s == null || s.length() == 0) return false;

        boolean[] result = new boolean[s.length()+1];
        result[0] = true;

        for (int i = 0; i < s.length(); i++) {
            for(String word : wordDict) {
                boolean match = false;

                int start = i - word.length() + 1;
                if (start < 0) continue;
                if (!result[start]) continue;

                boolean findSuffix = true;
                for(int j = start; j < word.length()+start; j++) {
                    if (s.charAt(j) != word.charAt(j-start)) {
                        findSuffix = false;
                        break;
                    }
                }

                if (!findSuffix) continue;

                result[i+1] = true;
                System.out.println("i: " + i + ", word:" + word);
                break;
            }
            System.out.println("i:" + i + ", " + result[i+1]);
        }
        return result[s.length()];
    }

    public static void main(String[] args) {
        LeetCode139 leetCode139 = new LeetCode139();
        leetCode139.wordBreak("catsandog", Arrays.asList(new String[]{"cats", "dog", "sand", "cat"}));
    }
}
