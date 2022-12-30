package com.adolesce.server.meetquestion;

import org.junit.Test;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/12/29 19:35
 */
public class ListNodeTest {
    class ListNode{
        private ListNode next;
        private int value;
    }

    @Test
    public void test(){
        ListNode node1 = new ListNode();
        node1.value = 1;

        ListNode node2 = new ListNode();
        node2.value = 2;

        ListNode node3 = new ListNode();
        node3.value = 3;

        ListNode node4 = new ListNode();
        node4.value = 4;

        ListNode node5 = new ListNode();
        node5.value = 5;

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        ListNode resultNode = removeNthFromEnd(node1, 0);
        while(resultNode != null){
            System.out.println(resultNode.value);
            resultNode = resultNode.next;
        }
    }

    /**
     * 根据指定角标删除链表中的节点
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode node = head;
        int size = 0;
        //循环，得到链表节点数量
        while (node != null){
            node = node.next;
            size++;
        }
        //检查输入角标是否合法
        if(n > size || n <= 0){
            throw new RuntimeException("角标不合法");
        }
        //如果删除节点的倒数角标与节点个数正好一致，返回头结点的下一个节点，即删除了头结点
        if(n == size){
            return head.next;
        }
        //拿到待删除节点的上一处节点，将该节点的下一个节点指向为下下个节点，实现删除指定倒数角标的节点，
        int index = size - n;
        node = head;
        for (int i = 1; i < index; i++) {
            node = node.next;
        }
        node.next = node.next.next;
        return head;
    }
}
