package org.example;

public interface NetworkTopology {
    void configureNet();
    void sendMessage(int from, int to, String message) throws InterruptedException;
    void runNet();
    void stopNet();
}
