/*
 * @lc app=leetcode.cn id=474 lang=java
 *
 * [474] 一和零
 *
 * https://leetcode.cn/problems/ones-and-zeroes/description/
 *
 * algorithms
 * Medium (67.27%)
 * Likes:    1242
 * Dislikes: 0
 * Total Accepted:    264K
 * Total Submissions: 392.5K
 * Testcase Example:  '["10","0001","111001","1","0"]\n5\n3'
 *
 * 给你一个二进制字符串数组 strs 和两个整数 m 和 n 。
 * 
 * 
 * 请你找出并返回 strs 的最大子集的长度，该子集中 最多 有 m 个 0 和 n 个 1 。
 * 
 * 如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的 子集 。
 * 
 * 
 * 
 * 
 * 示例 1：
 * 
 * 
 * 输入：strs = ["10", "0001", "111001", "1", "0"], m = 5, n = 3
 * 输出：4
 * 解释：最多有 5 个 0 和 3 个 1 的最大子集是 {"10","0001","1","0"} ，因此答案是 4 。
 * 其他满足题意但较小的子集包括 {"0001","1"} 和 {"10","1","0"} 。{"111001"} 不满足题意，因为它含 4 个 1
 * ，大于 n 的值 3 。
 * 
 * 
 * 示例 2：
 * 
 * 
 * 输入：strs = ["10", "0", "1"], m = 1, n = 1
 * 输出：2
 * 解释：最大的子集是 {"0", "1"} ，所以答案是 2 。
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 <= strs.length <= 600
 * 1 <= strs[i].length <= 100
 * strs[i] 仅由 '0' 和 '1' 组成
 * 1 <= m, n <= 100
 * 
 * 
 */

// @lc code=start
class Weight {
    public int zeroCnt;
    public int oneCnt;

    public Weight(int zeroCnt, int oneCnt) {
        this.zeroCnt = zeroCnt;
        this.oneCnt = oneCnt;
    }
}
/**
 * 二维，滚动数组 优化空间复杂度
 */
class Solution {
    
    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < len; i++) {
            int zeroCnt = 0;
            int oneCnt = 0;
            for (int j = 0; j < strs[i].length(); j++) {
                if (strs[i].charAt(j) == '0') {
                    zeroCnt++;
                } else {
                    oneCnt++;
                }
            }
            Weight weight = new Weight(zeroCnt, oneCnt);

            for (int j = m; j >= weight.zeroCnt; j--) { // 0的数量
                for (int k = n; k >= weight.oneCnt; k--) { // 1的数量
                    dp[j][k] = Math.max(dp[j - weight.zeroCnt][k - weight.oneCnt] + 1, dp[j][k]);
                }
            }
        }
        return dp[m][n];
    }
}
/**
 * 三维
 */
class Solution1 {
    
    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        int[][][] dp = new int[len + 1][m + 1][n + 1];
        for (int i = 1; i <= len; i++) {
            int zeroCnt = 0;
            int oneCnt = 0;
            for (int j = 0; j < strs[i - 1].length(); j++) {
                if (strs[i - 1].charAt(j) == '0') {
                    zeroCnt++;
                } else {
                    oneCnt++;
                }
            }
            Weight weight = new Weight(zeroCnt, oneCnt);

            for (int j = 0; j <= m; j++) { // 0的数量
                for (int k = 0; k <= n; k++) { // 1的数量
                    if (j >= zeroCnt && k >= oneCnt) {
                        dp[i][j][k] = Math.max(dp[i - 1][j - weight.zeroCnt][k - weight.oneCnt] + 1, dp[i - 1][j][k]);
                    } else {
                        dp[i][j][k] = dp[i - 1][j][k];
                    }
                }
            }
        }
        return dp[len][m][n];
    }
}
// @lc code=end

