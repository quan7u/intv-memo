/*
 * @lc app=leetcode.cn id=377 lang=java
 *
 * [377] 组合总和 Ⅳ
 *
 * https://leetcode.cn/problems/combination-sum-iv/description/
 *
 * algorithms
 * Medium (53.74%)
 * Likes:    1141
 * Dislikes: 0
 * Total Accepted:    270.6K
 * Total Submissions: 503.6K
 * Testcase Example:  '[1,2,3]\n4'
 *
 * 给你一个由 不同 整数组成的数组 nums ，和一个目标整数 target 。请你从 nums 中找出并返回总和为 target 的元素组合的个数。
 * 
 * 题目数据保证答案符合 32 位整数范围。
 * 
 * 
 * 
 * 示例 1：
 * 
 * 
 * 输入：nums = [1,2,3], target = 4
 * 输出：7
 * 解释：
 * 所有可能的组合为：
 * (1, 1, 1, 1)
 * (1, 1, 2)
 * (1, 2, 1)
 * (1, 3)
 * (2, 1, 1)
 * (2, 2)
 * (3, 1)
 * 请注意，顺序不同的序列被视作不同的组合。
 * 
 * 
 * 示例 2：
 * 
 * 
 * 输入：nums = [9], target = 3
 * 输出：0
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 
 * 1 
 * nums 中的所有元素 互不相同
 * 1 
 * 
 * 
 * 
 * 
 * 进阶：如果给定的数组中含有负数会发生什么？问题会产生何种变化？如果允许负数出现，需要向题目中添加哪些限制条件？
 * 
 */

// @lc code=start
class Solution {
    
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1]; // 满足和为i的组合数
        dp[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i - nums[j] >= 0) {
                    // 这里为什么是大于等于，而不是等于?
                    // 如果是等于，即i==nums[j]，只能得到dp[0]，只覆盖了nums[j]单个元素凑成target的情况
                    // 当判断大于等于0，加上nums[j]，都是新的组合
                    dp[i] += dp[i - nums[j]];
                }
            }
        }
        return dp[target];
    }
}

class Solution1 {

    int[] memo;

    public int combinationSum4(int[] nums, int target) {
        memo = new int[target + 1];
        Arrays.fill(memo, -1);
        dfs(nums, target);
        return memo[target];
    }

    public int dfs(int[] nums, int target) {
        if (target < 0) {
            return 0;
        } else if (target == 0) {
            return 1;
        }
        if (memo[target] != -1) {
            return memo[target];
        }
        int t = 0;
        for (int i = 0; i < nums.length; i++) {
            t += dfs(nums, target - nums[i]);
        }
        memo[target] = t;
        return t;
    }
}
// @lc code=end

