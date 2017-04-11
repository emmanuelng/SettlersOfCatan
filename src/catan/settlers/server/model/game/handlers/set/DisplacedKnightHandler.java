package catan.settlers.server.model.game.handlers.set;

import catan.settlers.network.client.commands.game.CurrentPlayerChangedCommand;
import catan.settlers.server.model.Game;
import catan.settlers.server.model.Player;
import catan.settlers.server.model.TurnData;
import catan.settlers.server.model.map.Intersection;
import catan.settlers.server.model.units.Knight;

public class DisplacedKnightHandler extends SetOfOpponentMove {

	private static final long serialVersionUID = -264837676034161267L;
	private Knight knightToMove;
	
	public DisplacedKnightHandler(Knight knightToMove) {
		this.knightToMove = knightToMove;
	}
	
	@Override
	public void handle(Game game, Player sender, TurnData data) {
		if (contains(sender)) {
			Intersection newLocation = data.getIntersectionSelection();
			Intersection curKnightLoc = game.getGameBoardManager().getBoard().getIntersectionById(knightToMove.getLocatedAt().getId());

			if (newLocation.getUnit() != null || newLocation.isMaritime())
				return;
			if (knightToMove.canCanMoveIntersecIds().contains(newLocation.getId())) {
					knightToMove.setLocatedAt(newLocation);
					newLocation.setUnit(knightToMove);
					curKnightLoc.setUnit(null);
					
					game.setCurSetOfOpponentMove(null);
					game.sendToAllPlayers(new CurrentPlayerChangedCommand(game.getCurrentPlayer().getUsername()));
			}
		}
	}

}