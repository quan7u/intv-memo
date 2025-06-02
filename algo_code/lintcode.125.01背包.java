/*
有 n 个物品和一个大小为 m 的背包. 给定数组 A 表示每个物品的大小和数组 V 表示每个物品的价值.

问最多能装入背包的总价值是多大?
 */

/*
暴力递归>>>记忆化搜索>>>二维dp>>>一维dp
*/

/**
 * 四、一维dp
 * 由二维dp可知，状态转移依赖于上一层的结果，所以可用【滚动数组】优化空间
 * 遍历顺序：外层物品，内层容量
 * 注意：相比与二维dp，内层的遍历顺序改成了从大到小
 * 因为依赖于上层的状态，如果从小到大会导致上层状态被覆盖，从而计算错误
 */
public class Solution {
    public int backPackII(int m, int[] a, int[] v) {
        int n = a.length;
        int[] dp = new int[m + 1];

        for (int i = 0; i < n; i++) {
            for (int j = m; j >= a[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - a[i]] + v[i]);
            }
        }
        return dp[m];
    }
}


/**
 * 三、二维dp
 * dp[i][j] 前i个物品，背包容量为j时的最大价值
 * 状态转移（实际 i、j 需要考虑边界）
 *   选：dp[i][j] = dp[i - 1][j - a[i]] + v[i]
 * 不选：dp[i][j] = dp[i - 1][j]
 * 
 * 遍历顺序：外层物品，内层容量（从小到大）（每个物品只使用一次）
 * 注意：有剩余空间才可以选 或 不选；无空间需要继承上一层的
 */
public class Solution2 {
    public int backPackII(int m, int[] a, int[] v) {
        int n = a.length;
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (j >= a[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - a[i - 1]] + v[i - 1]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][m];
    }
}

/**
 * 一、暴力递归：解决问题的子问题
 * 时间复杂度：2的n次方
 * 
 * 二、记忆化搜索：缓存重叠子问题的解
 * 时间复杂度：O(n * m)
 * 空间复杂度：O(n * m)
 */
public class Solution1 {

    int[][] mem;
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param a: Given n items with size A[i]
     * @param v: Given n items with value V[i]
     * @return: The maximum value
     */
    public int backPackII(int m, int[] a, int[] v) {
        int n = a.length;
        mem = new int[n][m + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(mem[i], -1);
        }
        return dfs(n - 1, m, a, v);
    }

    private int dfs(int i, int m, int[] a, int[] v) {
        if (i < 0 || m <= 0) {
            return 0;
        }
        
        if (mem[i][m] != -1) {
            return mem[i][m];
        }

        // 选 or 不选
        int choose = 0;
        if (m >= a[i]) {
            choose = v[i] + dfs(i - 1, m - a[i], a, v);
        }
        int unchoose = dfs(i - 1, m, a, v);
        mem[i][m] = Math.max(choose, unchoose);
        return mem[i][m];
    }
}