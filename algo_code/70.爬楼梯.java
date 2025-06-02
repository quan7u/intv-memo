/*
 * @lc app=leetcode.cn id=70 lang=java
 *
 * [70] 爬楼梯
 *
 * https://leetcode.cn/problems/climbing-stairs/description/
 *
 * algorithms
 * Easy (55.29%)
 * Likes:    3830
 * Dislikes: 0
 * Total Accepted:    1.8M
 * Total Submissions: 3.3M
 * Testcase Example:  '2'
 *
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * 
 * 
 * 
 * 示例 1：
 * 
 * 
 * 输入：n = 2
 * 输出：2
 * 解释：有两种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶
 * 2. 2 阶
 * 
 * 示例 2：
 * 
 * 
 * 输入：n = 3
 * 输出：3
 * 解释：有三种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶 + 1 阶
 * 2. 1 阶 + 2 阶
 * 3. 2 阶 + 1 阶
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 <= n <= 45
 * 
 * 
 */

// @lc code=start
/**
 * 二维dp
 * n>=1保证了初始化时不会越界
 */
class Solution {
    public int climbStairs(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}

/**
 * 记忆化搜索
 */
class Solution1 {
    int[] mem;
    public int climbStairs(int n) {
        mem = new int[n + 1];
        mem[0] = 1;
        mem[1] = 1;
        return dfs(n);
    }

    private int dfs(int i) {
        if (mem[i] != 0) {
            return mem[i];
        }
        mem[i] = dfs(i - 1) + dfs(i - 2);
        return mem[i];
    }
}
// @lc code=end

