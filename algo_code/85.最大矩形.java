/*
 * @lc app=leetcode.cn id=85 lang=java
 *
 * [85] 最大矩形
 *
 * https://leetcode.cn/problems/maximal-rectangle/description/
 *
 * algorithms
 * Hard (55.78%)
 * Likes:    1741
 * Dislikes: 0
 * Total Accepted:    217.6K
 * Total Submissions: 389.9K
 * Testcase Example:  '[["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]'
 *
 * 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
 * 
 * 
 * 
 * 示例 1：
 * 
 * 
 * 输入：matrix =
 * [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
 * 输出：6
 * 解释：最大矩形如上图所示。
 * 
 * 
 * 示例 2：
 * 
 * 
 * 输入：matrix = [["0"]]
 * 输出：0
 * 
 * 
 * 示例 3：
 * 
 * 
 * 输入：matrix = [["1"]]
 * 输出：1
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * rows == matrix.length
 * cols == matrix[0].length
 * 1 <= row, cols <= 200
 * matrix[i][j] 为 '0' 或 '1'
 * 
 * 
 */

// @lc code=start
class Solution {
    public int maximalRectangle(char[][] matrix) {
        int n = matrix.length; // row
        int m = matrix[0].length; // col
        
        // 前缀和：每列向下累加
        int[][] pre = new int[n][m];
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                if (i == 0) {
                    pre[i][j] = matrix[i][j] == '1' ? 1 : 0;
                } else if (matrix[i][j] == '1') {
                    pre[i][j] = pre[i - 1][j] + 1;
                } else {
                    pre[i][j] = 0;
                }
            }
        }

        // 遍历每行，类似 LC84 求最大矩形
        int res = 0;
        for (int i = 0; i < n; i++) {
            Deque<Integer> stack = new ArrayDeque<>();
            for (int j = 0; j <= m; j++) {
                int cur = (j == m) ? 0 : pre[i][j]; // 尾部补 0 清栈
                while (!stack.isEmpty() && pre[i][stack.peek()] > cur) {
                    // stack.peek(), p(pop), j(push) 
                    int p = stack.pop();
                    int right = j;
                    int left = stack.isEmpty() ? 0 : stack.peek() + 1;
                    res = Math.max(res, (right - left) * pre[i][p]);
                }
                stack.push(j);
            }
        }

        return res;
    }
}
/**
 * 横向前缀和
 */
class Solution2 {
    public int maximalRectangle(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] memo = new int[n][m];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j == 0) {
                    memo[i][j] = matrix[i][j] == '1' ? 1 : 0;
                } else if (matrix[i][j] == '1') {
                    memo[i][j] = memo[i][j - 1] + 1;
                } else {
                    memo[i][j] = 0;
                }
            }
        }
        // 遍历每一列：寻找最大的矩形
        int res = 0;
        for (int j = 0; j < m; j++) {
            Deque<Integer> stack = new ArrayDeque<>();
            for (int i = 0; i <= n; i++) {
                while (!stack.isEmpty() && (i == n || memo[stack.peek()][j] > memo[i][j])) {
                    int p = stack.pop();
                    int right = i;
                    int left = stack.isEmpty() ? 0 : stack.peek() + 1;
                    res = Math.max(res, (right - left) * memo[p][j]);
                }
                stack.push(i);
            }
        }
        return res;
    }
}
/**
 * matrix[i][j]若为1，可以作为右下角，遍历得到最大矩阵
 */
class Solution1 {
    public int maximalRectangle(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] memo = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j == 0) {
                    memo[i][j] = matrix[i][j] == '1' ? 1 : 0;
                } else if (matrix[i][j] == '1') {
                    memo[i][j] = memo[i][j - 1] + 1;
                } else {
                    memo[i][j] = 0;
                }
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (memo[i][j] > 0) {
                    int w = memo[i][j];
                    int h = 0;
                    for (int k = i; k >= 0; k--) {
                        w = Math.min(memo[k][j], w);
                        h++;
                        res = Math.max(res, w * h);
                    }
                }
            }
        }
        return res;
    }
}
// @lc code=end

