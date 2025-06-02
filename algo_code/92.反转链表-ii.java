/*
 * @lc app=leetcode.cn id=92 lang=java
 *
 * [92] 反转链表 II
 *
 * https://leetcode.cn/problems/reverse-linked-list-ii/description/
 *
 * algorithms
 * Medium (57.49%)
 * Likes:    1971
 * Dislikes: 0
 * Total Accepted:    637.7K
 * Total Submissions: 1.1M
 * Testcase Example:  '[1,2,3,4,5]\n2\n4'
 *
 * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left  。请你反转从位置 left 到位置 right 的链表节点，返回
 * 反转后的链表 。
 * 
 * 
 * 示例 1：
 * 
 * 
 * 输入：head = [1,2,3,4,5], left = 2, right = 4
 * 输出：[1,4,3,2,5]
 * 
 * 
 * 示例 2：
 * 
 * 
 * 输入：head = [5], left = 1, right = 1
 * 输出：[5]
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 链表中节点数目为 n
 * 1 
 * -500 
 * 1 
 * 
 * 
 * 
 * 
 * 进阶： 你可以使用一趟扫描完成反转吗？
 * 
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    /**
     * 找到pre、leftNode、rightNode、next
     * 示例：head = [1,2,3,4,5], left = 2, right = 4
     * [1,4,3,2,5]
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0, head);
        ListNode pre = dummy;
        for (int i = 1; i < left; i++) {
            pre = pre.next;
        } // pre = 1
        ListNode leftNode = pre.next; // left = 2
        pre.next = null;

        ListNode rightNode = leftNode;
        for (int i = left; i < right; i++) {
            rightNode = rightNode.next;
        } // right = 4
        ListNode next = rightNode.next;
        rightNode.next = null;

        reverse(leftNode);
        pre.next = rightNode;
        leftNode.next = next;

        return dummy.next;
    }

    public void reverse(ListNode head) {
        ListNode pre = null;
        ListNode p = head;
        while (p != null) {
            ListNode next = p.next;
            p.next = pre;
            pre = p;
            p = next;
        }
    }
}
// @lc code=end

