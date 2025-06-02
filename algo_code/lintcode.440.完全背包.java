/*
给定 n 种物品, 每种物品都有无限个. 第 i 个物品的体积为 A[i], 价值为 V[i].

再给定一个容量为 m 的背包. 问可以装入背包的最大价值是多少?
 */

/**
 * 二维
 * 当没有足够空间时，沿用上层结果 dp[i - 1][j]
 * 与01背包不同的是，可重复选，所以有：dp[i][j - a[i - 1]] + v[i - 1]
 */
public class Solution {
    public int backPackIII(int[] a, int[] v, int m) {
        int n = a.length;
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) { // 物品
            for (int j = 1; j <= m; j++) { // 背包容量
                if (j >= a[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - a[i - 1]] + v[i - 1]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][m];
    }
}


/**
 * 一维：for循环顺序不影响状态
 */
public class Solution {
    public int backPackIII(int[] a, int[] v, int m) {
        int n = a.length;
        int[] dp = new int[m + 1];

        for (int i = 1; i <= n; i++) { // 物品
            for (int j = 1; j <= m; j++) { // 背包容量
                if (j >= a[i - 1]) {
                    dp[j] = Math.max(dp[j], dp[j - a[i - 1]] + v[i - 1]);
                }
            }
        }
        return dp[m];
    }
}