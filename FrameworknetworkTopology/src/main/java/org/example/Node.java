package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node implements Runnable {
    private int id;
    private String name;
    private List<Node> vecinos;

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
        this.vecinos = new ArrayList<>();
    }

    public void run() {
        try {
            int random = new Random().nextInt(10000);
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void receiveMessage(String message) throws InterruptedException {
        Thread.sleep(new Random().nextInt(10000));
        System.out.println("Node: "+name + " received message: " + message);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getVecinos() {
        return vecinos;
    }

    public void setVecinos(List<Node> vecinos) {
        this.vecinos = vecinos;
    }
    public void addVecino(Node node){
        this.vecinos.add(node);
    }
    public Node getVecino(int id){
        for (Node vecino : vecinos) {
            if (vecino.getId() == id){
                return vecino;
            }
        }
        return null;
    }
}
