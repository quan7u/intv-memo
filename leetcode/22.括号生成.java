/*
 * @lc app=leetcode.cn id=22 lang=java
 *
 * [22] 括号生成
 *
 * https://leetcode.cn/problems/generate-parentheses/description/
 *
 * algorithms
 * Medium (78.86%)
 * Likes:    3850
 * Dislikes: 0
 * Total Accepted:    1M
 * Total Submissions: 1.3M
 * Testcase Example:  '3'
 *
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 * 
 * 
 * 
 * 示例 1：
 * 
 * 
 * 输入：n = 3
 * 输出：["((()))","(()())","(())()","()(())","()()()"]
 * 
 * 
 * 示例 2：
 * 
 * 
 * 输入：n = 1
 * 输出：["()"]
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 <= n <= 8
 * 
 * 
 */

// @lc code=start
class Solution {
    public List<String> generateParenthesis(int n) {
        // n对 回溯 结束条件：括号数
        List<String> res = new ArrayList<>();
        dfs(res, n, new StringBuilder(), 0, 0);
        return res;
    }

    public void dfs(List<String> res, int n, StringBuilder sb, int leftCnt, int rightCnt) {
        if (sb.length() == 2 * n) {
            res.add(sb.toString());
            return;
        }
        if (leftCnt < n) {
            sb.append("(");
            dfs(res, n, sb, leftCnt + 1, rightCnt);
            sb.deleteCharAt(sb.length() - 1);
        }
        if (rightCnt < leftCnt) {
            sb.append(")");
            dfs(res, n, sb, leftCnt, rightCnt + 1);
            sb.deleteCharAt(sb.length() - 1);
        }

    }
}
// @lc code=end

