package com.androidzeitgeit.sample_js_engine.event;

import com.androidzeitgeit.sample_js_engine.state.GameEvent;
import com.androidzeitgeit.sample_js_engine.state.GameState;

import java.util.List;

public class GameStateChangedEvent {
    private GameState gameState;
    private List<GameEvent> gameEvents;

    public GameStateChangedEvent(GameState gameState, List<GameEvent> gameEvents) {
        this.gameState = gameState;
        this.gameEvents = gameEvents;
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<GameEvent> getGameEvents() {
        return gameEvents;
    }
}
