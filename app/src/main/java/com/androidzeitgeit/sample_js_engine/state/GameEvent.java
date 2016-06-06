package com.androidzeitgeit.sample_js_engine.state;

/**
 * I don't know how to best model this class so that it is generic enough for all use cases.
 *
 * This class represents an event that happened during executing a transition. Those events can
 * be used to animate the UI state.
 */
public class GameEvent {
    public static final int ACTION_SHOOT = 1;
    public static final int ACTION_MISS = 2;

    public int action;
    public String sourcePlayerId;
    public String targetPlayerId;
}
