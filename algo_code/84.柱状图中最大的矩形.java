/*
 * @lc app=leetcode.cn id=84 lang=java
 *
 * [84] 柱状图中最大的矩形
 *
 * https://leetcode.cn/problems/largest-rectangle-in-histogram/description/
 *
 * algorithms
 * Hard (47.44%)
 * Likes:    2938
 * Dislikes: 0
 * Total Accepted:    532K
 * Total Submissions: 1.1M
 * Testcase Example:  '[2,1,5,6,2,3]'
 *
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 * 
 * 
 * 
 * 示例 1:
 * 
 * 
 * 
 * 
 * 输入：heights = [2,1,5,6,2,3]
 * 输出：10
 * 解释：最大的矩形为图中红色区域，面积为 10
 * 
 * 
 * 示例 2：
 * 
 * 
 * 
 * 
 * 输入： heights = [2,4]
 * 输出： 4
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 
 * 0 
 * 
 * 
 */

// @lc code=start
/**
 * 维护递增栈，若遇到小于栈顶p元素的x，出栈计算面积
 * 面积分为左右部分 （pre,j,i）
 * 右：j 到 i，不含i
 * 左：pre 到 j，不含pre，不含j（j在右部分已经计算在内）
 * 
 * 注意：遍历完毕之后，栈内还可能存在元素没有处理
 * 此时将数组长度n代入x处理，
 * 
 */
class Solution {
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int n = heights.length;
        int res = 0;
        for (int i = 0; i <= n; i++) {
            int h = -1;
            if (i != n) {
                h = heights[i];
            }
            while (!stack.isEmpty() && heights[stack.peek()] > h) {
                int j = stack.pop();
                int right = i;
                int left = stack.isEmpty() ? 0 : stack.peek() + 1;
                res = Math.max(res, (right - left) * heights[j]);
            }
            stack.push(i);
        }
        return res;
    }
}

class Solution1 {
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int n = heights.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                int j = stack.pop();
                int right = i - j;
                int left = (stack.isEmpty() ? j : (j - stack.peek() - 1));
                res = Math.max(res, (left + right) * heights[j]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int right = n - j;
            int left = (stack.isEmpty() ? j : (j - stack.peek() - 1));
            res = Math.max(res, (left + right) * heights[j]);
        }
        return res;
    }
}
// @lc code=end

