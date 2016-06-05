package com.androidzeitgeit.sample_js_engine;

import android.util.Log;

import org.mozilla.javascript.Context;

/**
 * This object will be exposed to JavaScript and JavaScript land will be able to call methods on
 * this object.
 *
 * Maybe it makes sense to have different implementations for server/clients. But for the sake of
 * simplicity I'll try to just use one implementation for both.
 */
public class Bridge {
    private FakeNetwork network;

    public Bridge(FakeNetwork network) {
        this.network = network;
    }

    public void sendToServer(Object object) {
        String message = (String) Context.jsToJava(object, String.class);

        network.sendToServer(message);
    }

    public void sendToClients(Object object) {
        String message = (String) Context.jsToJava(object, String.class);

        network.sendToClients(message);
    }

    public void notifyGameStateChanged(Object object) {
        String state = (String) Context.jsToJava(object, String.class);
        Log.d("Bridge", "Game state changed: " + state);
    }

    /**
     * For debugging \o/
     */
    public void log(Object object) {
        Log.d("Bridge", (String) Context.jsToJava(object, String.class));
    }
}
