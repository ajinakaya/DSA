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

    // Method to find the 'x' closest values to a target in a binary search tree
    public static List<Integer> closestValues(TreeNode root, double target, int x) {
        
         // Initialize a list to store the result
        List<Integer> result = new ArrayList<>();

          // Initialize two stacks for predecessor and successor nodes
        Stack<TreeNode> predecessorStack = new Stack<>();
        Stack<TreeNode> successorStack = new Stack<>();

         // Initialize the stacks with the nodes closest to the target
        initializeStacks(root, target, predecessorStack, successorStack);

        while (x > 0) {
             // Calculate the absolute differences between the target and the values in the stacks
            double predDiff = predecessorStack.isEmpty() ? Double.MAX_VALUE : Math.abs(target - predecessorStack.peek().val);
            double succDiff = successorStack.isEmpty() ? Double.MAX_VALUE : Math.abs(target - successorStack.peek().val);

             // Compare the differences and add the closest value to the result
            if (predDiff < succDiff) {
                result.add(0,predecessorStack.peek().val);// Add to the beginning of the list
                getPredecessor(predecessorStack);// Move to the next predecessor
            } else {
                result.add(0,successorStack.peek().val);// Add to the beginning of the list
                getSuccessor(successorStack);// Move to the next successor
            }

            x--;// Decrement the count of remaining closest values
        }

        return result;
    }

    private static void initializeStacks(TreeNode root, double target, Stack<TreeNode> predecessorStack, Stack<TreeNode> successorStack) {
        while (root != null) {
            // Compare the current node's value with the target
            if (root.val == target) {
                predecessorStack.push(root); // Current node is a potential successor
                successorStack.push(root);// Move to the left subtree
                break;
            } else if (root.val > target) {
                successorStack.push(root);// Current node is a potential predecessor
                root = root.left;// Move to the left subtree
            } else {
                predecessorStack.push(root);// Current node is a potential predecessor
                root = root.right;// Move to the right subtree
            }
        }
    }

    // Helper method to move to the next predecessor in the stack
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

        // Helper method to move to the next successor in the stack
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
