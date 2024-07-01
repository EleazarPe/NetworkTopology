package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TreeNode implements Runnable {
    private int id;
    private String name;
    private TreeNode parent;
    private List<TreeNode> children;


    public TreeNode(int id, String name) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public void addChild(TreeNode child) {
        child.setParent(this);
        children.add(child);
    }
    public List<TreeNode> getChildren() {
        return children;
    }

    public void receiveMessage(String message) throws InterruptedException {
        Thread.sleep(new Random().nextInt(10000));
        System.out.println("Node: "+name + " received message: " + message);
    }

    public void run() {
        try {
            int random = new Random().nextInt(10000);
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) throws InterruptedException {
        for (TreeNode child : children) {
            child.receiveMessage("Message from Node " + id + ": " + message);
        }
        /*if (parent != null) {
            parent.receiveMessage("Message from Node " + id + ": " + message);
        }*/
    }
}

