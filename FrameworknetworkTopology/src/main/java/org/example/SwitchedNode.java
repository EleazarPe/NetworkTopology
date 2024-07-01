package org.example;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class SwitchedNode implements Runnable {

    private int id;
    private String name;
    private final SwitchedNetwork network;

    public SwitchedNode(int id, String name, SwitchedNetwork network) {
        this.id = id;
        this.name = name;
        this.network = network;
    }

    public int getId() {
        return id;
    }

    public SwitchedNode setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SwitchedNode setName(String name) {
        this.name = name;
        return this;
    }

    public void sendMessage(int to, String message) throws InterruptedException {

        network.getExec().submit(() -> {
            try {
                network.sendMessage(id, to, message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    public void receiveMessage(int from, int to, String message) throws InterruptedException {

        Thread.sleep(new Random().nextInt(10000));

        if (id == to){
            System.out.println("Mensanje: " + message + " Recibido en " + name + ". Finalizando Mensaje.");
        }
        //TODO: add message to set
        else{
            System.out.println("Node: "+name + " received message: " + message);
        }

    }

    @Override
    public void run() {

    }
}
