package com.gyj.Test.LinkedList;

/**
 * 链表反转
 * Created by Gao on 2018/3/20.
 */
public class ReverseList {

    private static class Node {
        int val;
        Node next;

        private Node(int val) {
            this.val = val;
        }
    }

    /**
     * 迭代实现反转链表，迭代法思路很清晰，就是将当前节点和下一节点保存起来，然后将当前节点反转
     *
     * @param head
     * @return
     */
    private static Node reverseList(Node head) {

        //head看作是前一结点，head.next是当前结点，reHead是反转后新链表的头结点
        if (head == null || head.next == null) {
            return head;    //若为空链或者当前结点在尾结点，则直接还回
        }

        Node reHead = null;             //定义新链表头结点
        while (head != null) {          //当前结点为null，说明位于尾结点
            Node current = head.next;   //记录下一个节点
            head.next = reHead;         //将rehead节点连接到head节点上
            reHead = head;              //让rehead指向head
            head = current;             //将head指向下一个节点
        }

        return reHead;
    }

    /**
     * 递归实现反转链表
     *
     * @param head
     * @return
     */
    private static Node reverseList1(Node head) {

        //head看作是前一结点，head.next是当前结点，reHead是反转后新链表的头结点
        if (head == null || head.next == null) {
            return head;  //若为空链或者当前结点在尾结点，则直接还回
        }

        Node reHead = reverseList1(head.next);
        //将头节点置于末端
        head.next.next = head;
        //防止链表循环
        head.next = null;

        return reHead;
    }

    /**
     * 先找到最后一个节点，然后从最后一个节点之前的那个节点的方法体中开始将下一个指向当前一个,
     * 然后当前节点反转时其后面的节点已经进行反转了，不需要管。最后返回原来的最后一个节点。
     *
     * @param head
     * @return
     */
    private static Node reverseList2(Node head) {

        if (head == null || head.next == null) {
            return head;
        }

        Node next = head.next;
        head.next = null;
        Node reHead = reverseList2(next);
        next.next = head;

        return reHead;

    }

    private static void printList(Node head) {
        if (head == null) {
            return;
        }
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }

    public static void main(String[] args) {

        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        Node rehead = reverseList(n1);
        //Node rehead = reverseList1(n1);
        //Node rehead = reverseList2(n1);
        printList(rehead);


        Node head = new Node(0);
        Node tmp = null;
        Node cur = null;
        // 构造一个长度为10的链表，保存头节点对象head
        for (int i = 1; i < 10; i++) {
            tmp = new Node(i);
            if (1 == i) {
                head.next = tmp;
            } else {
                cur.next = tmp;
            }
            cur = tmp;
        }
        Node rehead2 = reverseList2(head);
        printList(rehead2);
    }
}
