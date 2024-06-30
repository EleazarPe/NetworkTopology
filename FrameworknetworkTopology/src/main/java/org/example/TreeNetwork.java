package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TreeNetwork implements NetworkTopology{
    private TreeNode rootNode;
    private ExecutorService exec;

    public TreeNetwork(TreeNode nodes) {
        this.rootNode = nodes;
        configureNet();
    }

    public void configureNet() {
        exec = Executors.newFixedThreadPool(1);
    }

    @Override
    public void sendMessage(int from, int to, String message) throws InterruptedException {
        TreeNode sender = findNode(rootNode, from);
        if (sender != null) {
            sender.sendMessage(message);
        } else {
            System.out.println("Node not found");
        }
    }

    @Override
    public void runNet() {

    }

    private void startNode(TreeNode node) {
        exec.submit(node);
        for (TreeNode child : node.getChildren()) {
            startNode(child);
        }
    }


    private TreeNode findNode(TreeNode currentNode, int nodeId) {
        if (currentNode.getId() == nodeId) {
            return currentNode;
        }
        for (TreeNode child : currentNode.getChildren()) {
            TreeNode result = findNode(child, nodeId);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public void stopNet() {
        exec.shutdown();
    }

}
