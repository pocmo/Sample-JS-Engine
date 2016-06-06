// There are always two functions for each transition:
//   transition_client_*name*
//   transition_server_*name*
//
// A client transition can return a list of game events.


////////////////////////////////////////////////////////////////////////////////////////////////////
// JOIN
////////////////////////////////////////////////////////////////////////////////////////////////////
transition_client_join = function(values) {
    // Just add the new player to the list
    gamestate.players.push({
        "player_id": values.player_id,
        "energy": 100
    })
}

transition_server_join = function(values) {
    // We just send the current game state to all clients (Avoiding a special case here)
    // Clients that already have a game state will ignore it.
    bridge.sendToClients(helper_createTransition(
        "updateGameState", {
            "gamestate": gamestate
        }
    ));

    // Do what the clients do
    transition_client_join(values)
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// UPDATE GAME STATE
////////////////////////////////////////////////////////////////////////////////////////////////////

transition_client_updateGameState = function(values) {
    // Copy game state from the server if we do not have one already
    if (gamestate == null) {
        gamestate = values.gamestate;
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// SHOOT
////////////////////////////////////////////////////////////////////////////////////////////////////

transition_server_shoot = function(values) {
    values.isHit = Math.random() < 0.5;

    transition_client_shoot(values);
}

transition_client_shoot = function(values) {
    var target = gamestate_findPlayerById(values.target)

    if (values.isHit) {
        target.energy -= 10;

        return [{
            action: 1, // shoot
            sourcePlayerId: values.source,
            targetPlayerId: values.target
        }]
    }

    return [{
        action: 2, // miss
        sourcePlayerId: values.source,
        targetPlayerId: values.target
    }]
}


