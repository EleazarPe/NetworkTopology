package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        /* Descomentar la topologia que se desea utilizar */
        busNet();
        //ringNet();
        //meshNet();
        //starNet();
        //hyperCubeNet();
        //treeNet();
        //fullNet();
        //switchedNet();
    }
    public static void busNet(){
        List<Node> nodes = List.of(new Node(0, "A"), new Node(1, "B"), new Node(2, "C"));
        BusNetwork busNetwork = new BusNetwork(nodes);
        System.out.println("Bus Network");
        busNetwork.runNet();
        busNetwork.sendMessage(0, 1, "MENSAJE1");
        busNetwork.sendMessage(0, 2, "MENSAJE2");
        busNetwork.sendMessage(0, 0, "MENSAJE3");
        busNetwork.stopNet();
    }
    public static void ringNet(){
        List<Node> nodes = List.of(new Node(0, "A"), new Node(1, "B"), new Node(2, "C"));
        nodes.get(0).setVecinos(List.of(nodes.get(1)));
        nodes.get(1).setVecinos(List.of(nodes.get(2)));
        nodes.get(2).setVecinos(List.of(nodes.get(0)));
        RingNetwork ringNetwork = new RingNetwork(nodes);
        ringNetwork.runNet();
        ringNetwork.sendMessage(2, 1, "MENSAJE1");
        ringNetwork.sendMessage(0, 2, "MEANSAJE2");
        ringNetwork.stopNet();
    }
    public static void meshNet(){
        List<Node> nodes = List.of(new Node(0, "A"), new Node(1, "B"), new Node(2, "C"));
        for (Node node : nodes) {
            for (Node vecino : nodes) {
                if (node.getId() != vecino.getId()) {
                    node.addVecino(vecino);
                }
            }
        }
        MeshNetwork meshNetwork = new MeshNetwork(nodes);
        meshNetwork.runNet();
        meshNetwork.sendMessage(2, 1, "MENSAJE1");
        meshNetwork.sendMessage(1, 2, "MENSAJE2");
        meshNetwork.sendMessage(1, 0, "MENSAJE3");
        meshNetwork.stopNet();
    }
    public static void fullNet(){
        List<Node> nodes = List.of(new Node(0, "A"), new Node(1, "B"), new Node(2, "C"));
        for (Node node : nodes) {
            for (Node vecino : nodes) {
                if (node.getId() != vecino.getId()) {
                    node.addVecino(vecino);
                }
            }
        }
        FullyConectedNetwork fullyConectedNetwork = new FullyConectedNetwork(nodes);
        fullyConectedNetwork.runNet();
        fullyConectedNetwork.sendMessage(2, 1, "MENSAJE1");
        fullyConectedNetwork.sendMessage(1, 2, "MENSAJE2");
        fullyConectedNetwork.sendMessage(1, 0, "MENSAJE3");
        fullyConectedNetwork.stopNet();
    }
    public static void starNet(){
        List<Node> nodes = List.of(new Node(-1, "Central"),new Node(0, "A"), new Node(1, "B"), new Node(2, "C"));
        for (Node node : nodes) {
            if (node.getId() != -1) {
                nodes.get(0).addVecino(node);
            }
        }
        StarNetwork starNetwork = new StarNetwork(nodes);
        starNetwork.runNet();
        starNetwork.sendMessage(-1, 0, "MENSAJE1");
        starNetwork.sendMessage(-1, 1, "MENSAJE2");
        starNetwork.sendMessage(-1, 2, "MENSAJE3");
        starNetwork.stopNet();
    }
    public static void treeNet(){
        TreeNode root = new TreeNode(0, "Root");
        TreeNode nodeA = new TreeNode(1, "A");
        TreeNode nodeB = new TreeNode(2, "B");
        TreeNode nodeC = new TreeNode(3, "C");
        TreeNode nodeD = new TreeNode(4, "D");
        TreeNode nodeE = new TreeNode(5, "E");

        root.addChild(nodeA);
        root.addChild(nodeB);
        nodeA.addChild(nodeC);
        nodeA.addChild(nodeD);
        nodeB.addChild(nodeE);

        TreeNetwork treeNetwork = new TreeNetwork(root);
        treeNetwork.runNet();
        try {
            treeNetwork.sendMessage(0, 1, "MENSAJE1");
            treeNetwork.sendMessage(0, 2, "MENSAJE2");
            treeNetwork.sendMessage(0, 3, "MENSAJE3");
            treeNetwork.sendMessage(0, 4, "MENSAJE4");
            treeNetwork.sendMessage(0, 5, "MENSAJE5");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        treeNetwork.stopNet();
    }

    public static void hyperCubeNet(){
        HyperCubeNetwork hyperCubeNetwork = new HyperCubeNetwork(3);
        hyperCubeNetwork.runNet();

        try{
            hyperCubeNetwork.sendMessage(0,7, "Hola...");
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            Thread.sleep(20000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Stop HyperCube Network");
        hyperCubeNetwork.stopNet();


    }

    public static void switchedNet(){

        SwitchedNetwork switchedNetwork = new SwitchedNetwork(10);
        switchedNetwork.runNet();

        try{
            switchedNetwork.getNodes().getFirst().sendMessage(7, "Hola esde 0.");
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            switchedNetwork.getNodes().getFirst().sendMessage(8, "Hola desde 0 a 8.");
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            switchedNetwork.getNodes().getFirst().sendMessage(4, "Hola desde 0 otra vez.");
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Stop Switched Network");
        switchedNetwork.stopNet();


    }
}