package com.adolesce.server.javabasic;

import lombok.*;
import org.junit.Test;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/11/18 20:00
 */
public class ListNodeTest {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class ListNode{
        int value;
        private ListNode pre;
        private ListNode next;
    }

    private ListNode head;
    private ListNode end;

    @Test
    public void test1(){
        ListNode node1 = new ListNode();
        node1.pre = null;
        node1.value = 1;

        ListNode node2 = new ListNode();
        node2.value = 2;
        node2.pre = node1;
        node1.next = node2;

        ListNode node3 = new ListNode();
        node3.setValue(3);
        node3.setPre(node2);
        node2.setNext(node3);

        ListNode node4 = new ListNode();
        node4.setValue(4);
        node4.setPre(node3);
        node3.setNext(node4);

        ListNode node5 = new ListNode();
        node5.setValue(5);
        node5.setPre(node4);
        node4.setNext(node5);

        node5.setNext(null);

        head = node1;
        end = node5;

        ListNode result = removeNthFromEnd(head,5);
        soutList(result);
    }

    private void soutList(ListNode head) {
        ListNode node = head;
        while (node != null){
            System.out.println(node.value);
            node = node.next;
        }
    }

    public ListNode removeNthFromEnd(ListNode head,int n){
        ListNode node = head;
        int size = 0;
        while (node != null){
            node = node.next;
            size++;
        }
        if (n == size) {
            return head.next;
        }
        //  1  2  3  4  5
        int desk = size - n;

        node = head;
        for (int i = 1; i < desk ; i++) {
            node = node.next;
        }
        node.next = node.next.next;
        return head;
    }

}
