/*
 * @lc app=leetcode.cn id=42 lang=java
 *
 * [42] 接雨水
 *
 * https://leetcode.cn/problems/trapping-rain-water/description/
 *
 * algorithms
 * Hard (65.37%)
 * Likes:    5727
 * Dislikes: 0
 * Total Accepted:    1.3M
 * Total Submissions: 2.1M
 * Testcase Example:  '[0,1,0,2,1,0,1,3,2,1,2,1]'
 *
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * 
 * 
 * 
 * 示例 1：
 * 
 * 
 * 
 * 
 * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出：6
 * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 
 * 
 * 
 * 示例 2：
 * 
 * 
 * 输入：height = [4,2,0,3,2,5]
 * 输出：9
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * n == height.length
 * 1 <= n <= 2 * 10^4
 * 0 <= height[i] <= 10^5
 * 
 * 
 */

// @lc code=start
/**
 * 维护 单调递减栈，当遇到大于栈顶的高度，说明可以接到雨水
 * 
 */
class Solution {
    public int trap(int[] height) {
        Deque<Integer> stack = new ArrayDeque<>();
        int n = height.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && height[stack.peek()] <= height[i]) {
                int bottom = stack.pop();
                if (stack.isEmpty()) {
                    break; // 不成凹槽
                }
                int left = stack.peek();
                int right = i;
                res += (right - left - 1) * (Math.min(height[left], height[right]) - height[bottom]);
            }
            stack.push(i);
        }
        return res;
    }
}
// @lc code=end

