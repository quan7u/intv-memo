/*
 * @lc app=leetcode.cn id=215 lang=java
 *
 * [215] 数组中的第K个最大元素
 *
 * https://leetcode.cn/problems/kth-largest-element-in-an-array/description/
 *
 * algorithms
 * Medium (60.82%)
 * Likes:    2746
 * Dislikes: 0
 * Total Accepted:    1.4M
 * Total Submissions: 2.2M
 * Testcase Example:  '[3,2,1,5,6,4]\n2'
 *
 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
 * 
 * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * 
 * 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
 * 
 * 
 * 
 * 示例 1:
 * 
 * 
 * 输入: [3,2,1,5,6,4], k = 2
 * 输出: 5
 * 
 * 
 * 示例 2:
 * 
 * 
 * 输入: [3,2,3,1,2,4,5,5,6], k = 4
 * 输出: 4
 * 
 * 
 * 
 * 提示： 
 * 
 * 
 * 1 <= k <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * 
 * 
 */

// @lc code=start
/**
 * 三路快排在大量重复元素场景下性能更优
 */
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        return quickSelect(nums, 0, n - 1, n - k);
    }

    private int quickSelect(int[] nums, int l, int r, int index) {
        int[] range = partition(nums, l, r);
        int start = range[0];
        int end = range[1];

        if (start <= index && index <= end) {
            return nums[start];
        } else if (start > index) {
            return quickSelect(nums, l, start - 1, index);
        } else {
            return quickSelect(nums, end + 1, r, index);
        }
    }

    private int[] partition(int[] nums, int l, int r) {
        int pivot = nums[l];
        int i = l + 1;
        int lt = l;
        int gt = r;
        while (i <= gt) {
            if (nums[i] < pivot) {
                swap(nums, i, lt);
                i++;
                lt++;
            } else if (nums[i] > pivot) {
                swap(nums, i, gt);
                gt--;
            } else {
                i++;
            }
        }
        return new int[]{lt, gt};
    }

    private void swap(int[] nums, int a, int b) {
        int t = nums[a];
        nums[a] = nums[b];
        nums[b] = t;
    }
}

class Solution1 {
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }

        for (int i = n - 1; i > n - k; i--) {
            swap(nums, 0, i);
            heapify(nums, i, 0);
        }

        return nums[0];
    }

    private void heapify(int[] nums, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && nums[left] > nums[largest]) {
            largest = left;
        }

        if (right < n && nums[right] > nums[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(nums, largest, i);
            heapify(nums, n, largest);
        }
    }

    private void swap(int[] nums, int a, int b) {
        int t = nums[a];
        nums[a] = nums[b];
        nums[b] = t;
    } 
}
// @lc code=end

