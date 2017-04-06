package catan.settlers.network.client.commands.game;

import catan.settlers.client.model.ClientModel;
import catan.settlers.network.client.commands.ServerToClientCommand;

public class UpdateBarbarianCounterCommand implements ServerToClientCommand {

	private static final long serialVersionUID = 5595446857378209044L;
	private int barbarianCounter;
	private boolean attacked;

	public UpdateBarbarianCounterCommand(int i) {
		barbarianCounter = i;
		if (i >= 7) {
			attacked = true;
		}
	}

	@Override
	public void execute() {
		ClientModel.instance.getGameStateManager().setBarbarianCounter(barbarianCounter);
		ClientModel.instance.getGameStateManager().setAttacked(true);
	}

}
