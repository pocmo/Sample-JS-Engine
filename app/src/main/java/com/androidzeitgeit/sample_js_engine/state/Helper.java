package com.androidzeitgeit.sample_js_engine.state;

public class Helper {
    public static PlayerState findPlayerById(GameState gameState, String playerId) {
        for (PlayerState playerState : gameState.players) {
            if (playerId.equals(playerState.player_id)) {
                return playerState;
            }
        }

        throw new AssertionError("KABOOM!");
    }
}
