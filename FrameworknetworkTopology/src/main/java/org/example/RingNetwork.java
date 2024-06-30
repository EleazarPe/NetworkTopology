package org.example;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RingNetwork implements NetworkTopology{
    private List<Node> nodes;
    private ExecutorService exec;

    public RingNetwork(List<Node> nodes) {
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
            Node nodo = destinationNode(from);
            for (int i=0; i< nodes.size(); i++){
                System.out.println("Actual: "+nodo.getName());
                Node vecino = nodo.getVecinos().getFirst();
                nodo = nodo.getVecinos().getFirst();
                if (vecino.getId() == to){
                    try {
                        vecino.receiveMessage(message);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
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
