package com.androidzeitgeit.sample_js_engine;

public class Client {
    private GameEngine engine;
    private FakeNetwork network;
    private String id;

    public Client(GameEngine engine, String id) {
        this.engine = engine;
        this.id = id;
    }

    public void connect(FakeNetwork network) {
        network.addClient(this);

        this.network = network;

        // Yay, we are connected to our fake network. Great.

        engine.setBridge(new Bridge(network));

        // Let's join the game!
        engine.joinGame(id);
    }

    public void onMessageReceived(String message) {
        // Forward message to JS game engine
        engine.onClientMessage(message);
    }
}
