package com.gyj.Test.Tree;

/**
 * 二叉树的遍历方式
 * 前序递归遍历算法：访问根结点-->递归遍历根结点的左子树-->递归遍历根结点的右子树
 * 中序递归遍历算法：递归遍历根结点的左子树-->访问根结点-->递归遍历根结点的右子树
 * 后序递归遍历算法：递归遍历根结点的左子树-->递归遍历根结点的右子树-->访问根结点
 * Created by Gao on 2018/3/30.
 */
//二叉树节点
public class BinaryTreeNode {

    private int data;
    private BinaryTreeNode left;
    private BinaryTreeNode right;

    public BinaryTreeNode() {

    }

    public BinaryTreeNode(int data, BinaryTreeNode left, BinaryTreeNode right) {
        super();
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public BinaryTreeNode getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode left) {
        this.left = left;
    }

    public BinaryTreeNode getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode right) {
        this.right = right;
    }
}