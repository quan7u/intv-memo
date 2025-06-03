/*
 * @lc app=leetcode.cn id=96 lang=java
 *
 * [96] 不同的二叉搜索树
 *
 * https://leetcode.cn/problems/unique-binary-search-trees/description/
 *
 * algorithms
 * Medium (71.48%)
 * Likes:    2646
 * Dislikes: 0
 * Total Accepted:    513.6K
 * Total Submissions: 718.5K
 * Testcase Example:  '3'
 *
 * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。
 * 
 * 
 * 
 * 示例 1：
 * 
 * 
 * 输入：n = 3
 * 输出：5
 * 
 * 
 * 示例 2：
 * 
 * 
 * 输入：n = 1
 * 输出：1
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 
 * 
 * 
 */

// @lc code=start
/**
 * dp[i] 代表i个节点 BST的种数
 */
class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n + 1]; // i个节点的解决方案
        dp[0] = 1;
        dp[1] = 1;
        int res = 0;
        for (int i = 2; i <= n; i++) { // 节点数
            for (int j = 1; j <= i; j++) { // 根节点的选择
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }
}

class Solution1 {

    int[] memo;

    public int numTrees(int n) {
        memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return dfs(n);
    }

    private int dfs(int n) {
        if (n == 0 || n == 1) { // 无节点 or 单个节点
            return 1; // 只有一种方案
        }
        if (memo[n] != -1) {
            return memo[n];
        }
        int cnt = 0;
        for (int i = 1; i <= n; i++) { // n个节点，使用第i个节点作为根节点求解方案
            // 左边是 1～i；右边是i~n
            cnt += dfs(i - 1) * dfs(n - i);
        }
        memo[n] = cnt;
        return memo[n];
    }
}
// @lc code=end

