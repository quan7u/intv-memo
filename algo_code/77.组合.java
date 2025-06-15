/*
 * @lc app=leetcode.cn id=77 lang=java
 *
 * [77] 组合
 *
 * https://leetcode.cn/problems/combinations/description/
 *
 * algorithms
 * Medium (77.68%)
 * Likes:    1773
 * Dislikes: 0
 * Total Accepted:    896K
 * Total Submissions: 1.2M
 * Testcase Example:  '4\n2'
 *
 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
 * 
 * 你可以按 任何顺序 返回答案。
 * 
 * 
 * 
 * 示例 1：
 * 
 * 
 * 输入：n = 4, k = 2
 * 输出：
 * [
 * ⁠ [2,4],
 * ⁠ [3,4],
 * ⁠ [2,3],
 * ⁠ [1,2],
 * ⁠ [1,3],
 * ⁠ [1,4],
 * ]
 * 
 * 示例 2：
 * 
 * 
 * 输入：n = 1, k = 1
 * 输出：[[1]]
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
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(res, 1, n, k, new ArrayList<>());
        return res;
    }

    private void dfs(List<List<Integer>> res, int index, int n, int k, List<Integer> list) {
        if (list.size() == k) {
            res.add(new ArrayList(list));
            return;
        }
        if (index > n) {
            return;
        }
        for (int i = index; i <= n; i++) {
            list.add(i);
            dfs(res, i + 1, n, k, list);
            list.remove(list.size() - 1);
        }
    }
}
// @lc code=end

