package com.gyj.Test.Tree;

import java.util.Stack;

/**
 * Created by Gao on 2018/3/12.
 */
public class TreeMirror {

    class TreeNode {
        int val;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 递归
     * @param root
     */
    public void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }

        TreeNode temp = root.left;  //交换根节点的左右孩子
        root.left = root.right;
        root.right = temp;
        Mirror(root.left);  //镜像根节点的左孩子的左右子树
        Mirror(root.right);   //镜像根节点的右孩子的左右子树
    }

    /**
     * 非递归实现, 先序遍历，如果根节点不为 null 将根节点入栈，当栈不为 null 出栈，交换左右节点，如果左右节点不为 null 入栈。
     * @param root
     */
    public void Mirror1(TreeNode root) {
        if (root == null) {
            return;
        }

        //LinkedList<TreeNode> stack = new LinkedList<TreeNode>();//借助于辅助栈
        Stack<TreeNode> stack = new Stack<TreeNode>();  //借助于辅助栈
        TreeNode current = null; //存放出栈的栈顶元素
        stack.push(root);   //将根元素入栈
        while (!stack.isEmpty()) {
            current = stack.pop();  //将根元素出栈 交换根元素的左右子树
            //若左右孩子不为空则交换左右孩子
            if (current.left != null || current.right != null) {
                TreeNode temp = current.left;
                current.left = current.right;
                current.right = temp;
            }
            //将根元素的左右孩子压入栈中
            if (current.left != null) {
                stack.push(current.left);
            }

            if (current.right != null) {
                stack.push(current.right);
            }
        }
    }

    public void Mirror2(TreeNode root) {
        if (root == null)
            return;

        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode current = null;
        stack.push(root);
        while (!stack.isEmpty()) {
            current = stack.pop();

        }
    }

}
