// Functions in here are called from Java and are the "public" entry points for notifying the engine
// about actions the player performed or messages that have been received over the network.

engine_onServerMessage = function(message) {
    var transition = JSON.parse(message);

    // Execute transition on server
    var func = "transition_server_" + transition.name;
    eval(func)(transition.values);

    // Now send transition with processed values to clients
    bridge.sendToClients(helper_createTransition(
        transition.name, transition.values
    ));
};

engine_onClientMessage = function(message) {
    var transition = JSON.parse(message);

    // Execute transition locally
    var func = "transition_client_" + transition.name;
    eval(func)(transition.values);

    bridge.notifyGameStateChanged(JSON.stringify(gamestate));
};

engine_joinGame = function(player_id) {
    bridge.sendToServer(helper_createTransition(
        "join", {
            player_id: player_id
        }
    ));
};

// Helper methods

helper_createTransition = function(name, values) {
    return JSON.stringify({
        name: name,
        values: values
    });
};
