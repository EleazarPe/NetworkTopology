package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BusNetwork implements NetworkTopology {
    private List<Node> nodes;
    private ExecutorService exec;

    public BusNetwork(List<Node> nodes) {
        this.nodes = nodes;
        configureNet();
    }

    public Node destinationNode(int id) {
        for (Node node : nodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }

    @Override
    public void configureNet() {
        exec = Executors.newFixedThreadPool(nodes.size());
        System.out.println("nodos: " + nodes.size());

    }

    @Override
    public void sendMessage(int from, int to, String message) {
        exec.submit(() -> {
            System.out.println("Sending message: " + message);
            if (destinationNode(to) == null) {
                System.out.println("Node not found");
            } else {
                try {
                    destinationNode(to).receiveMessage(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void runNet() {
        for (Node node : nodes) {
            exec.submit(node);
        }
    }

    @Override
    public void stopNet() {
        exec.shutdown();
//        try {
//            if (!exec.awaitTermination(5, TimeUnit.SECONDS)) {
//                exec.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            exec.shutdownNow();
//        }
    }
}
