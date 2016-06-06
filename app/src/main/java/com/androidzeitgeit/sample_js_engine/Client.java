package com.androidzeitgeit.sample_js_engine;

import android.util.Log;

import com.squareup.otto.Bus;

public class Client {
    private GameEngine engine;
    private FakeNetwork network;
    private String id;
    private Bus bus;

    public Client(GameEngine engine, String id) {
        this.engine = engine;
        this.id = id;

        // Normally there would be just one event bus. But as I'm pretending to be two clients at the
        // same time I need two buses.
        this.bus = new Bus();
    }

    public Bus getBus() {
        return bus;
    }

    public String getPlayerId() {
        return id;
    }

    public void connect(FakeNetwork network) {
        network.addClient(this);

        this.network = network;

        // Yay, we are connected to our fake network. Great.

        engine.setBridge(new Bridge(network, bus));

        // Let's join the game!
        engine.joinGame(id);
    }

    public void onMessageReceived(String message) {
        Log.i("CLIENT", "Received message: " + message);

        // Forward message to JS game engine
        engine.onClientMessage(message);
    }
}
