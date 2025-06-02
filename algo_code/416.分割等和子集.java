/*
 * @lc app=leetcode.cn id=416 lang=java
 *
 * [416] 分割等和子集
 *
 * https://leetcode.cn/problems/partition-equal-subset-sum/description/
 *
 * algorithms
 * Medium (53.49%)
 * Likes:    2351
 * Dislikes: 0
 * Total Accepted:    771.2K
 * Total Submissions: 1.4M
 * Testcase Example:  '[1,5,11,5]'
 *
 * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 * 
 * 
 * 
 * 示例 1：
 * 
 * 
 * 输入：nums = [1,5,11,5]
 * 输出：true
 * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
 * 
 * 示例 2：
 * 
 * 
 * 输入：nums = [1,2,3,5]
 * 输出：false
 * 解释：数组不能分割成两个元素和相等的子集。
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 
 * 1 
 * 
 * 
 */

// @lc code=start
class Solution {
    /**
     * 1、求和，除以2得到目标值
     * 2、01背包，选or不选：dp[i]代表能否凑成i
     */
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false; // 奇数，不可能均分
        }
        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true; // 边界
        // 01背包：外层物品，内层容量，防止物品被多次选择
        for (int i = 0; i < nums.length; i++) { // 物品
            for (int j = target; j >= nums[i]; j--) { // 容量
                dp[j] = dp[j] || dp[j - nums[i]];
            }
        }
        return dp[target];
    }
}
// @lc code=end

