/*
有 n 个物品和一个大小为 m 的背包. 给定数组 A 表示每个物品的大小和数组 V 表示每个物品的价值.

问最多能装入背包的总价值是多大?
 */
public class Solution {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param a: Given n items with size A[i]
     * @param v: Given n items with value V[i]
     * @return: The maximum value
     */
     // -1 表示未计算；memo[i][w] 表示从 i 开始，剩余容量为 w 的最大价值
    private int[][] memo;

    public int backPackII(int m, int[] a, int[] v) {
        int n = a.length;
        memo = new int[n][m + 1];

        // 初始化为 -1（未访问）
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= m; j++) {
                memo[i][j] = -1;
            }
        }
        return dfs(0, a, v, m);
    }

    public int dfs(int i, int[] weights, int[] values, int W) {
        if (i == weights.length) {
            return 0;
        }
        if (memo[i][W] != -1) {
            return memo[i][W];
        }
        int choose = 0;
        if (weights[i] <= W) {
            choose = values[i] + dfs(i + 1, weights, values, W - weights[i]);
        }
        int unchoose = dfs(i + 1, weights, values, W);
        memo[i][W] = Math.max(choose, unchoose);
        return memo[i][W];
    }
}