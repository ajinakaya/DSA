package Question4;

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;

    public TreeNode(int val) {
        this.val = val;
        this.left = this.right = null;
    }
}

public class binarysearchtree {

    // Method to find the x closest values to the target in the binary search tree
    public static List<Integer> closestValues(TreeNode root, double target, int x) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Double> minHeap = new PriorityQueue<>(Comparator.comparingDouble(a -> Math.abs(a - target)));

          // Performing in-order traversal to visit nodes in ascending order
        inOrderTraversal(root, target, x, minHeap);

        while (x > 0 && !minHeap.isEmpty()) {
            result.add(minHeap.poll().intValue());
            x--;
        }

        return result;
    }

    private static void inOrderTraversal(TreeNode root, double target, int x, PriorityQueue<Double> minHeap) {
        if (root == null) {
            return;
        }

        // Traverse the left subtree
        inOrderTraversal(root.left, target, x, minHeap);

        // Add the current node's value to the minHeap
        minHeap.offer((double) root.val);

         // If the size of minHeap exceeds x, remove the smallest element
        if (minHeap.size() > x) {
            minHeap.poll();
        }

         // Traverse the right subtree
        inOrderTraversal(root.right, target, x, minHeap);
    }

    public static void main(String[] args) {
     
        // Creating a balanced binary search tree (BST)
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        double target = 3.8;
        int x = 2;

          // Find x closest values to the target and print the result
        List<Integer> closestValues = closestValues(root, target, x);
        System.out.println("Closest values to " + target + " are: " + closestValues);
    }
}
