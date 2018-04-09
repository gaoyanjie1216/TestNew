package com.gyj.Test.Tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树的遍历
 * 二叉树(BinaryTree)是n(n≥0)个结点的有限集，它或者是空集(n=0)，或者由一个根结点及两棵互不相交的、分别称作这个根的左子树和右子树的二叉树组成。
 * 1、先序遍历法：先访问根节点，再遍历左子树，最后遍历右子树；
 * 2、中序遍历法：先遍历左子树，再访问根节点，最后遍历右子树；
 * 3、后序遍历法：先遍历左子树，再遍历右子树，最后访问根节点。
 * Created by Gao on 2018/3/30.
 */
public class BinaryTree1 {

    //前序遍历递归的方式
    public void preOrder(BinaryTreeNode root) {
        if (null != root) {
            System.out.print(root.getData() + "\t");
            preOrder(root.getLeft());
            preOrder(root.getRight());
        }
    }

    //前序遍历非递归的方式
    public void preOrderNonRecursive(BinaryTreeNode root) {
        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
        while (true) {
            while (root != null) {
                System.out.print(root.getData() + "\t");
                stack.push(root);
                root = root.getLeft();
            }
            if (stack.isEmpty()) break;
            root = stack.pop();
            root = root.getRight();
        }
    }

    //中序遍历采用递归的方式
    public void inOrder(BinaryTreeNode root) {
        if (null != root) {
            inOrder(root.getLeft());
            System.out.print(root.getData() + "\t");
            inOrder(root.getRight());
        }
    }

    //中序遍历采用非递归的方式
    public void inOrderNonRecursive(BinaryTreeNode root) {
        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
        while (true) {
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }
            if (stack.isEmpty()) break;
            root = stack.pop();
            System.out.print(root.getData() + "\t");
            root = root.getRight();
        }
    }

    //后序遍历采用递归的方式
    public void postOrder(BinaryTreeNode root) {
        if (root != null) {
            postOrder(root.getLeft());
            postOrder(root.getRight());
            System.out.print(root.getData() + "\t");
        }
    }

    //后序遍历采用非递归的方式
    public void postOrderNonRecursive(BinaryTreeNode root) {
        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
        while (true) {
            if (root != null) {
                stack.push(root);
                root = root.getLeft();
            } else {
                if (stack.isEmpty()) return;

                if (null == stack.lastElement().getRight()) {
                    root = stack.pop();
                    System.out.print(root.getData() + "\t");
                    while (root == stack.lastElement().getRight()) {
                        System.out.print(stack.lastElement().getData() + "\t");
                        root = stack.pop();
                        if (stack.isEmpty()) {
                            break;
                        }
                    }
                }

                if (!stack.isEmpty())
                    root = stack.lastElement().getRight();
                else
                    root = null;
            }
        }
    }

    /**
     * 队列是一种特殊的线性表，它只允许在表的前端（front）进行删除操作，而在表的后端（rear）进行插入操作。进行插入操作的端称为队尾，
     * 进行删除操作的端称为队头。队列中没有元素时，称为空队列。在队列这种数据结构中，最先插入的元素将是最先被删除的元素；反之最后插入的元素将是最后被删除的元素，
     * 因此队列又称为“先进先出”（FIFO—first in first out）的线性表。
     * 在java5中新增加了java.util.Queue接口，用以支持队列的常见操作。该接口扩展了java.util.Collection接口。
     * Queue使用时要尽量避免Collection的add()和remove()方法，而是要使用offer()来加入元素，使用poll()来获取并移出元素。它们的优点是通过返回值可以判断成功与否，
     * add()和remove()方法在失败的时候会抛出异常。 如果要使用前端而不移出该元素，使用element()或者peek()方法。
     * 值得注意的是LinkedList类实现了Queue接口，因此我们可以把LinkedList当成Queue来用。
     *
     * @param root
     */
    //层序遍历
    public void levelOrder(BinaryTreeNode root) {
        BinaryTreeNode temp;
        Queue<BinaryTreeNode> queue = new LinkedList<BinaryTreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            temp = queue.poll();
            System.out.print(temp.getData() + "\t");
            if (null != temp.getLeft())
                queue.offer(temp.getLeft());
            if (null != temp.getRight()) {
                queue.offer(temp.getRight());
            }
        }
    }

    //计算二叉树的深度
    private static int getTreeDepth(BinaryTreeNode root) {

        if (root.getLeft() == null && root.getRight() == null) {
            return 1;
        }
        int left = 0, right = 0;
        if (root.getLeft() != null) {
            left = getTreeDepth(root.getLeft());
        }
        if (root.getRight() != null) {
            right = getTreeDepth(root.getRight());
        }

        return left > right ? left + 1 : right + 1;
    }


    public static void main(String[] args) {

        BinaryTreeNode node10 = new BinaryTreeNode(10, null, null);
        BinaryTreeNode node8 = new BinaryTreeNode(8, null, null);
        BinaryTreeNode node9 = new BinaryTreeNode(9, null, node10);
        BinaryTreeNode node4 = new BinaryTreeNode(4, null, null);
        BinaryTreeNode node5 = new BinaryTreeNode(5, node8, node9);
        BinaryTreeNode node6 = new BinaryTreeNode(6, null, null);
        BinaryTreeNode node7 = new BinaryTreeNode(7, null, null);
        BinaryTreeNode node2 = new BinaryTreeNode(2, node4, node5);
        BinaryTreeNode node3 = new BinaryTreeNode(3, node6, node7);
        BinaryTreeNode node1 = new BinaryTreeNode(1, node2, node3);

        BinaryTree1 tree = new BinaryTree1();
        //采用递归的方式进行遍历
        System.out.println("-----前序遍历------");
        tree.preOrder(node1);
        System.out.println();
        //采用非递归的方式遍历
        tree.preOrderNonRecursive(node1);
        System.out.println();

        //采用递归的方式进行遍历
        System.out.println("-----中序遍历------");
        tree.inOrder(node1);
        System.out.println();
        //采用非递归的方式遍历
        tree.inOrderNonRecursive(node1);
        System.out.println();

        //采用递归的方式进行遍历
        System.out.println("-----后序遍历------");
        tree.postOrder(node1);
        System.out.println();
        //采用非递归的方式遍历
        tree.postOrderNonRecursive(node1);
        System.out.println();

        //采用递归的方式进行遍历
        System.out.println("-----层序遍历------");
        tree.levelOrder(node1);
        System.out.println();
        System.out.print("树的深度为：" + getTreeDepth(node1));
    }
}
