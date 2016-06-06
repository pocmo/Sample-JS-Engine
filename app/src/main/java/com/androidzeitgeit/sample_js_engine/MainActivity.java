package com.androidzeitgeit.sample_js_engine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidzeitgeit.sample_js_engine.ui.Presenter;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // EVERYTHING'S HAPPENING ON THE MAIN THREAD \o/

        // Server and clients all have their own GameEngine with game state etc.

        // We are the server
        Server server = new Server(new GameEngine(this));

        // And we are also two clients (To avoid writing network code now)
        GameEngine enginePlayer1 = new GameEngine(this);
        Client client1 = new Client(enginePlayer1, "Juhani");

        GameEngine enginePlayer2 = new GameEngine(this);
        Client client2 = new Client(enginePlayer2, "Rick");

        // Now let's create some UI

        LayoutInflater inflater = LayoutInflater.from(this);

        // The presenter objects will receive the events and update their part of the UI.

        View viewClient1 = inflater.inflate(R.layout.item_client, null);
        Presenter presenterClient1 = new Presenter(enginePlayer1, client1, viewClient1);
        client1.getBus().register(presenterClient1);
        Log.w("MainActivity", "presenter1 registered to bus: " + client1.getBus().hashCode());

        View viewClient2 = inflater.inflate(R.layout.item_client, null);
        Presenter presenterClient2 = new Presenter(enginePlayer2, client2, viewClient2);
        client2.getBus().register(presenterClient2);
        Log.w("MainActivity", "presenter2 registered to bus: " + client2.getBus().hashCode());

        ((ViewGroup) findViewById(R.id.container)).addView(viewClient1);
        ((ViewGroup) findViewById(R.id.container)).addView(viewClient2);

        // Let's connect everything through a faked network connection

        FakeNetwork fakeNetwork = new FakeNetwork();

        server.start(fakeNetwork);

        client1.connect(fakeNetwork);
        client2.connect(fakeNetwork);
    }
}
