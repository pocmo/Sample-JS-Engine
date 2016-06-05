package com.androidzeitgeit.sample_js_engine;

import android.util.Log;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GameEngine {
    private android.content.Context androidContext;
    private Context scriptContext;
    private ScriptableObject scriptScope;

    public GameEngine(android.content.Context androidContext) {
        this.androidContext = androidContext;

        scriptContext = Context.enter();
        scriptContext.setOptimizationLevel(-1);

        scriptScope = scriptContext.initStandardObjects();

        loadAsset("engine.js");
        loadAsset("gamestate.js");
        loadAsset("transitions.js");
    }

    private void loadAsset(String asset) {
        InputStream stream = null;

        try {
            stream = androidContext.getAssets().open(asset);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            scriptContext.evaluateReader(scriptScope, reader, "<GameEngine>", 1, null);
        } catch (IOException e) {
            // TODO: Handle this exception..
            Log.e("GameEngine", "IOException", e);
        } finally {
            if (stream != null) {
                try { stream.close(); } catch (IOException e) {}
            }
        }
    }

    public void setBridge(Bridge bridge) {
        Object wrappedBridge = Context.javaToJS(bridge, scriptScope);
        ScriptableObject.putProperty(scriptScope, "bridge", wrappedBridge);
    }

    public void onServerMessage(String message) {
        Function function = (Function) scriptScope.get("engine_onServerMessage");
        function.call(scriptContext, scriptScope, scriptScope, new Object[] { message });
    }

    public void onClientMessage(String message) {
        Function function = (Function) scriptScope.get("engine_onClientMessage");
        function.call(scriptContext, scriptScope, scriptScope, new Object[] { message });
    }

    public void joinGame(String playerId) {
        Function function = (Function) scriptScope.get("engine_joinGame");
        function.call(scriptContext, scriptScope, scriptScope, new Object[] { playerId });
    }
}
