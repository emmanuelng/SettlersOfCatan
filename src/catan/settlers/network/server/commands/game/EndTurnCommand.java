package catan.settlers.network.server.commands.game;

import catan.settlers.client.model.ClientModel;
import catan.settlers.network.server.Server;
import catan.settlers.network.server.Session;
import catan.settlers.network.server.commands.ClientToServerCommand;
import catan.settlers.server.model.Game;
import catan.settlers.server.model.TurnData;
import catan.settlers.server.model.Game.turnAction;

public class EndTurnCommand implements ClientToServerCommand {

	private static final long serialVersionUID = 1L;
	private TurnData turnData;
	private int gameId;
	
	public EndTurnCommand() {
		this.gameId = ClientModel.instance.getGameStateManager().getGameId();
		this.turnData = new TurnData(ClientModel.instance);
		turnData.setAction(turnAction.ENDTURN);
	}

	@Override
	public void execute(Session sender, Server server) {
		Game game = server.getGameManager().getGameById(gameId);
		game.receiveResponse(sender.getCredentials(), turnData);
	}

}
