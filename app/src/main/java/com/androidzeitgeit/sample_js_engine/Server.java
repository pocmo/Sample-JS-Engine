package com.androidzeitgeit.sample_js_engine;

import android.util.Log;

public class Server {
    private GameEngine engine;
    private FakeNetwork network;

    public Server(GameEngine engine) {
        this.engine = engine;
    }

    public void start(FakeNetwork network) {
        network.setServer(this);

        this.network = network;

        // We "running" and are waiting for connections now

        engine.setBridge(new Bridge(network));
        engine.initServer();
    }

    public void onMessageReceived(String message) {
        Log.i("SERVER", "Received message: " + message);

        // Forward message to JS game engine
        engine.onServerMessage(message);
    }
}
