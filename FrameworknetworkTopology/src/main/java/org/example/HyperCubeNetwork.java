package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class HyperCubeNetwork implements NetworkTopology{

    private ExecutorService exec;
    private List<HyperCubeNode> nodes = new ArrayList<>();
    private int messagesIdGen = 1;

    public HyperCubeNetwork(int dimensionSize) {
        createHyperCubeNetwork(dimensionSize);
        configureNet();
    }

    // Crea y combina los nodos creando un HyperCube, dado el tamaño de la dimension
    private void createHyperCubeNetwork(int dimensionSize) {

        for (int i = 0; i < Math.pow(2, dimensionSize); i++) {
            HyperCubeNode node = new HyperCubeNode(i,"Nodo"+String.valueOf(i), this);
            nodes.add(node);
        }

        for (HyperCubeNode node : nodes) {
            for (HyperCubeNode posibleVecino : nodes) {
                int andResult = node.getId() & posibleVecino.getId();
                if ((andResult & (andResult-1)) == 0){
                    node.getVecinos().add(posibleVecino);
                }
            }
        }

    }

    @Override
    public void configureNet() {
        exec = Executors.newFixedThreadPool(nodes.size());
        System.out.println("nodos: " + nodes.size());
    }

    @Override
    public void sendMessage(int from, int to, String message) throws InterruptedException {
        HyperCubeNode fromNode = findNode(from);
        if (fromNode == null){
            throw new InterruptedException();
        }

        fromNode.receiveMessage(messagesIdGen, from, to, message, new AtomicBoolean(true));
        messagesIdGen++;
    }

    @Override
    public void runNet() {
        for (HyperCubeNode node : nodes) {
            exec.submit(node);
        }
    }

    @Override
    public void stopNet() {
        exec.shutdown();
    }

    private HyperCubeNode findNode(int id){
        for (HyperCubeNode node : nodes) {
            if(node.getId() == id){
                return node;
            }
        }
        return null;
    }

    public ExecutorService getExec() {
        return exec;
    }

    public HyperCubeNetwork setExec(ExecutorService exec) {
        this.exec = exec;
        return this;
    }

    public List<HyperCubeNode> getNodes() {
        return nodes;
    }

    public HyperCubeNetwork setNodes(List<HyperCubeNode> nodes) {
        this.nodes = nodes;
        return this;
    }

    public int getMessagesIdGen() {
        return messagesIdGen;
    }

    public HyperCubeNetwork setMessagesIdGen(int messagesIdGen) {
        this.messagesIdGen = messagesIdGen;
        return this;
    }
}
