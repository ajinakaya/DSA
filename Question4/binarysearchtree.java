package Question4;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class TreeNode {
    int val;
    TreeNode left, right;

    public TreeNode(int val) {
        this.val = val;
        this.left = this.right = null;
    }
}

public class binarysearchtree {

    public static List<Integer> closestValues(TreeNode root, double target, int x) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> predecessorStack = new Stack<>();
        Stack<TreeNode> successorStack = new Stack<>();

        initializeStacks(root, target, predecessorStack, successorStack);

        while (x > 0) {
            double predDiff = predecessorStack.isEmpty() ? Double.MAX_VALUE : Math.abs(target - predecessorStack.peek().val);
            double succDiff = successorStack.isEmpty() ? Double.MAX_VALUE : Math.abs(target - successorStack.peek().val);

            if (predDiff < succDiff) {
                result.add(0,predecessorStack.peek().val);
                getPredecessor(predecessorStack);
            } else {
                result.add(0,successorStack.peek().val);
                getSuccessor(successorStack);
            }

            x--;
        }

        return result;
    }

    private static void initializeStacks(TreeNode root, double target, Stack<TreeNode> predecessorStack, Stack<TreeNode> successorStack) {
        while (root != null) {
            if (root.val == target) {
                predecessorStack.push(root);
                successorStack.push(root);
                break;
            } else if (root.val > target) {
                successorStack.push(root);
                root = root.left;
            } else {
                predecessorStack.push(root);
                root = root.right;
            }
        }
    }

    private static void getPredecessor(Stack<TreeNode> predecessorStack) {
        TreeNode node = predecessorStack.pop();
        if (node.left != null) {
            node = node.left;
            while (node != null) {
                predecessorStack.push(node);
                node = node.right;
            }
        }
    }

    private static void getSuccessor(Stack<TreeNode> successorStack) {
        TreeNode node = successorStack.pop();
        if (node.right != null) {
            node = node.right;
            while (node != null) {
                successorStack.push(node);
                node = node.left;
            }
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        double target = 3.8;
        int x = 2;

        List<Integer> result = closestValues(root, target, x);
        System.out.println("Closest values to " + target + ": " + result);
    }
}
