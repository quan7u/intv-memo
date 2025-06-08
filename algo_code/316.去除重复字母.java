/*
 * @lc app=leetcode.cn id=316 lang=java
 *
 * [316] 去除重复字母
 *
 * https://leetcode.cn/problems/remove-duplicate-letters/description/
 *
 * algorithms
 * Medium (49.86%)
 * Likes:    1143
 * Dislikes: 0
 * Total Accepted:    159.6K
 * Total Submissions: 319.8K
 * Testcase Example:  '"bcabc"'
 *
 * 给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
 * 
 * 
 * 
 * 示例 1：
 * 
 * 
 * 输入：s = "bcabc"
 * 输出："abc"
 * 
 * 
 * 示例 2：
 * 
 * 
 * 输入：s = "cbacdcbc"
 * 输出："acdb"
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 <= s.length <= 10^4
 * s 由小写英文字母组成
 * 
 * 
 * 
 * 
 * 注意：该题与 1081
 * https://leetcode-cn.com/problems/smallest-subsequence-of-distinct-characters
 * 相同
 * 
 */

// @lc code=start
/**
 * 首先得知道哪些是重复的吧？
 * 比较巧妙的做法，可以记录元素最后一次出现的下标
 * 模拟时维护一个栈
 */
class Solution {
    public String removeDuplicateLetters(String s) {
        Map<Character, Integer> lastIndex = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            lastIndex.put(s.charAt(i), i);
        }

        Deque<Character> stack = new ArrayDeque<>();
        Set<Character> visited = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (visited.contains(cur)) {
                continue;
            }
            while (!stack.isEmpty() && stack.peek() > cur && lastIndex.get(stack.peek()) > i) {
                char p = stack.pop();
                visited.remove(p);
            }
            stack.push(cur);
            visited.add(cur);
        }
        int n = stack.size();
        char[] res = new char[n];
        for (int i = n - 1; i >= 0; i--) {
            res[i] = stack.pop();
        }
        return String.valueOf(res);
    }
}
// @lc code=end

