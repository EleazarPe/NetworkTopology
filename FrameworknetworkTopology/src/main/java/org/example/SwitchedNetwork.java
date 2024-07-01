package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class SwitchedNetwork implements NetworkTopology{

    private ExecutorService exec;
    private List<SwitchedNode> nodes;

    public SwitchedNetwork(int nodeSize) {
        this.nodes = new ArrayList<>();
        for (int i = 0; i < nodeSize; i++) {
            this.nodes.add(new SwitchedNode(i, "Node"+i,this));
        }
        configureNet();
    }

    @Override
    public void configureNet() {
        exec = Executors.newFixedThreadPool(nodes.size());
        System.out.println("nodos: " + nodes.size());
    }

    @Override
    public void sendMessage(int from, int to, String message) throws InterruptedException {
        SwitchedNode toNode = findNode(to);
        if (toNode == null){
            throw new InterruptedException();
        }

        toNode.receiveMessage(from, to, message);
    }

    @Override
    public void runNet() {
        for (SwitchedNode node : nodes) {
            exec.submit(node);
        }
    }

    @Override
    public void stopNet() {
        exec.shutdown();
    }

    private SwitchedNode findNode(int id){
        for (SwitchedNode node : nodes) {
            if(node.getId() == id){
                return node;
            }
        }
        return null;
    }

    public ExecutorService getExec() {
        return exec;
    }

    public List<SwitchedNode> getNodes() {
        return nodes;
    }

    public SwitchedNetwork setNodes(List<SwitchedNode> nodes) {
        this.nodes = nodes;
        return this;
    }
}
