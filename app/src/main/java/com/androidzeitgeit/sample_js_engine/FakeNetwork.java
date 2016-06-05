package com.androidzeitgeit.sample_js_engine;

import java.util.ArrayList;
import java.util.List;

/**
 * I'm too lazy to actually write network code now. So this will just pretend to send things between
 * the server and the clients.
 */
public class FakeNetwork {
    private List<Client> clients;
    private Server server;

    public FakeNetwork() {
        clients = new ArrayList<>();
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void sendToServer(String message) {
        server.onMessageReceived(message);
    }

    public void sendToClients(String message) {
        for (Client client : clients) {
            client.onMessageReceived(message);
        }
    }
}
