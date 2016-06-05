package com.androidzeitgeit.sample_js_engine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // EVERYTHING'S HAPPENING ON THE MAIN THREAD \o/

        // We are the server
        Server server = new Server(new GameEngine(this));

        // And we are also two clients (To avoid writing network code now)
        Client client1 = new Client(new GameEngine(this), "Juhani");
        Client client2 = new Client(new GameEngine(this), "Rick");

        // Server and clients all have their own GameEngine with game state etc.

        FakeNetwork fakeNetwork = new FakeNetwork();

        server.start(fakeNetwork);

        client1.connect(fakeNetwork);
        client2.connect(fakeNetwork);
    }
}
