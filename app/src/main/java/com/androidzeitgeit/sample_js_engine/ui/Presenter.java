package com.androidzeitgeit.sample_js_engine.ui;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidzeitgeit.sample_js_engine.Client;
import com.androidzeitgeit.sample_js_engine.GameEngine;
import com.androidzeitgeit.sample_js_engine.R;
import com.androidzeitgeit.sample_js_engine.event.GameStateChangedEvent;
import com.androidzeitgeit.sample_js_engine.state.GameEvent;
import com.androidzeitgeit.sample_js_engine.state.GameState;
import com.androidzeitgeit.sample_js_engine.state.Helper;
import com.androidzeitgeit.sample_js_engine.state.PlayerState;
import com.squareup.otto.Subscribe;

import java.util.List;

public class Presenter {
    private GameEngine engine;
    private Client client;
    private View layout;

    public Presenter(GameEngine engine, Client client, View layout) {
        this.engine = engine;
        this.client = client;
        this.layout = layout;

        initialize();
    }

    private void initialize() {
        ((TextView) layout.findViewById(R.id.player)).setText(client.getPlayerId());
    }

    @Subscribe
    public void onGameStateChanged(GameStateChangedEvent event) {
        animateEvents(event.getGameState(), event.getGameEvents());

        final GameState gameState = event.getGameState();
        final PlayerState myself = findMyself(gameState);

        if (myself == null) {
            // I'm not part of the game yet.
            return;
        }

        ((TextView) layout.findViewById(R.id.energy)).setText(
            "Energy: " + findMyself(event.getGameState()).energy
        );

        final PlayerState enemy = findEnemy(gameState);
        if (enemy == null) {
            // No enemy connected yet
            return;
        }

        final Button button = (Button) layout.findViewById(R.id.shoot);
        button.setText("Shoot at " + enemy.player_id);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shootAtEnemy(myself.player_id, enemy.player_id);
            }
        });
    }

    private void animateEvents(GameState gameState, List<GameEvent> events) {
        for (GameEvent event : events) {
            if (event.action == GameEvent.ACTION_SHOOT) {
                animateShooting(gameState, event);
            }
            if (event.action == GameEvent.ACTION_MISS) {
                animateMissing(gameState, event);
            }
        }
    }

    private void animateShooting(GameState gameState, GameEvent event) {
        PlayerState sourcePlayer = Helper.findPlayerById(gameState, event.sourcePlayerId);
        PlayerState targetPlayer = Helper.findPlayerById(gameState, event.targetPlayerId);

        String message;

        if (sourcePlayer.player_id.equals(client.getPlayerId())) {
            message = "You shot " + targetPlayer.player_id + " in the face!";
        } else {
            message = "You have been shot by " + sourcePlayer.player_id + " in your face!";
        }

        TextView textView = (TextView) layout.findViewById(R.id.event);
        textView.setText(message);

        textView.setVisibility(View.VISIBLE);
        textView.setAlpha(1);
        textView.animate().setDuration(3000).alpha(0).start();

    }

    private void animateMissing(GameState gameState, GameEvent event) {
        PlayerState sourcePlayer = Helper.findPlayerById(gameState, event.sourcePlayerId);
        PlayerState targetPlayer = Helper.findPlayerById(gameState, event.targetPlayerId);

        String message;

        if (sourcePlayer.player_id.equals(client.getPlayerId())) {
            message = "You missed " + targetPlayer.player_id + " :(";
        } else {
            message = "You hear bullets fly by..";
        }

        TextView textView = (TextView) layout.findViewById(R.id.event);
        textView.setText(message);

        textView.setVisibility(View.VISIBLE);
        textView.setAlpha(1);
        textView.animate().setDuration(3000).alpha(0).start();
    }

    private void shootAtEnemy(String source, String target) {
        engine.shoot(source, target);
    }

    private PlayerState findMyself(GameState gameState) {
        for (PlayerState playerState : gameState.players) {
            if (playerState.player_id.equals(client.getPlayerId())) {
                return playerState;
            }
        }

        return null;
    }

    private PlayerState findEnemy(GameState gameState) {
        for (PlayerState playerState : gameState.players) {
            if (!playerState.player_id.equals(client.getPlayerId())) {
                return playerState;
            }
        }

        return null;
    }
}
