package com.androidzeitgeit.sample_js_engine;

import android.util.Log;

import com.androidzeitgeit.sample_js_engine.event.GameStateChangedEvent;
import com.androidzeitgeit.sample_js_engine.state.GameEvent;
import com.androidzeitgeit.sample_js_engine.state.GameState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Bus;

import org.mozilla.javascript.Context;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This object will be exposed to JavaScript and JavaScript land will be able to call methods on
 * this object.
 *
 * Maybe it makes sense to have different implementations for server/clients. But for the sake of
 * simplicity I'll try to just use one implementation for both.
 */
public class Bridge {
    private Gson gson;
    private FakeNetwork network;
    private Bus bus;

    public Bridge(FakeNetwork network) {
        this.network = network;
        this.gson = new Gson();
    }

    public Bridge(FakeNetwork network, Bus bus) {
        this(network);

        this.bus = bus;
    }

    public void sendToServer(Object object) {
        String message = (String) Context.jsToJava(object, String.class);

        network.sendToServer(message);
    }

    public void sendToClients(Object object) {
        String message = (String) Context.jsToJava(object, String.class);

        network.sendToClients(message);
    }

    public void notifyGameStateChanged(Object objectState, Object objectEvents) {
        String state = (String) Context.jsToJava(objectState, String.class);
        String events = (String) Context.jsToJava(objectEvents, String.class);

        GameState gameState = gson.fromJson(state, GameState.class);

        Type listType = new TypeToken<ArrayList<GameEvent>>() {}.getType();
        List<GameEvent> gameEvents = gson.fromJson(events, listType);

        bus.post(new GameStateChangedEvent(gameState, gameEvents));
    }

    /**
     * For debugging \o/
     */
    public void log(Object object) {
        Log.d("Bridge", (String) Context.jsToJava(object, String.class));
    }
}
