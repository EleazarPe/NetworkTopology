package org.example;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class HyperCubeNode implements Runnable {

    private int id;
    private String name;
    private List<HyperCubeNode> vecinos;
    private Set<Integer> messages;
    private final HyperCubeNetwork network;

    public HyperCubeNode(int id, String name, HyperCubeNetwork network) {
        this.id = id;
        this.name = name;
        this.vecinos = new ArrayList<>();
        this.messages = new HashSet<>();
        this.network = network;
    }

    public int getId() {
        return id;
    }

    public HyperCubeNode setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public HyperCubeNode setName(String name) {
        this.name = name;
        return this;
    }

    public List<HyperCubeNode> getVecinos() {
        return vecinos;
    }

    public HyperCubeNode setVecinos(List<HyperCubeNode> vecinos) {
        this.vecinos = vecinos;
        return this;
    }

    public Set<Integer> getMessages() {
        return messages;
    }

    public HyperCubeNode setMessages(Set<Integer> messages) {
        this.messages = messages;
        return this;
    }

    public void sendMessage(int idMessage, int from, int to, String message, AtomicBoolean continueSend) throws InterruptedException {

        for (HyperCubeNode vecino : vecinos){
            if (vecino.getId() != from) {
                network.getExec().submit(() -> {
                    try {
                        vecino.receiveMessage(idMessage, from, to, message, continueSend);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

    }

    public void receiveMessage(int idMessage, int from, int to, String message, AtomicBoolean continueSend) throws InterruptedException {

        Thread.sleep(new Random().nextInt(10000));

        if (!continueSend.get()){
            //System.out.println("Mensanje: " + message + " Recibido en " + name + ". Mensaje No continuara.");
        }

        else if (id == to){
            continueSend.set(false);
            System.out.println("Mensanje: " + message + " Recibido en " + name + ". Finalizando Mensaje.");
        }

        //TODO: add message to set
        else if (messages.contains(idMessage)){
            System.out.println("Mensanje: " + message + " Recibido en " + name + ". Mensaje No continuara.");
        }
        else{
            messages.add(idMessage);
            System.out.println("Node: "+name + " received message: " + message);
            sendMessage(idMessage, from, to, message, continueSend);
        }

    }

    @Override
    public void run() {

    }
}
