package com.adolesce.server.javabasic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Test;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/12/25 20:58
 */
public class ListNodeTest2 {

    @NoArgsConstructor
    @Getter
    @Setter
    class ListNode {
        private ListNode next;
        private int value;
    }

    @Test
    public void test1(){
        ListNode node1 = new ListNode();
        node1.value = 1;

        ListNode node2 = new ListNode();
        node2.value = 2;
        node1.next = node2;

        ListNode node3 = new ListNode();
        node3.value = 3;
        node2.next = node3;

        ListNode node4 = new ListNode();
        node4.value = 4;
        node3.next = node4;

        ListNode node5 = new ListNode();
        node5.value = 5;
        node4.next = node5;

        ListNode resultNode = removeFromLastIndex(node1, 0);
        while (resultNode != null){
            System.out.println(resultNode.value);
            resultNode = resultNode.next;
        }
    }

    public ListNode removeFromLastIndex(ListNode head,int lastIndex){
        ListNode node = head;
        int size = 0;
        while(node != null){
            node = node.next;
            size++;
        }
        if(lastIndex > size || lastIndex <= 0){
            throw new RuntimeException("输入的角标不合法");
        }
        if(lastIndex == size){
            return head.next;
        }
        node = head;
        int index = size - lastIndex;
        for (int i = 1; i < index; i++) {
            node = node.next;
        }
        node.next = node.next.next;
        return head;
    }



}
