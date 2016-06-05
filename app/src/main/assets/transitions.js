////////////////////////////////////////////////////////////////////////////////////////////////////
// JOIN
////////////////////////////////////////////////////////////////////////////////////////////////////
transition_client_join = function(values) {
    // Just add the new player to the list
    gamestate.players.push({
        "player_id": values.player_id,
    })
}

transition_server_join = function(values) {
    // TODO: Send current game state to new client

    // Do what the clients do
    transition_client_join(values)
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// SHOOT
////////////////////////////////////////////////////////////////////////////////////////////////////

transition_client_shoot = function(values) {
}

transition_server_shoot = function(values) {
}
