// We initialize an empty object so that it exists globally
gamestate = null;


gamestate_findPlayerById = function(id) {
    for (index in gamestate.players) {
        var player = gamestate.players[index];

        if (player.player_id == id) {
            return player;
        }
    }
};
