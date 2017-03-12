package catan.settlers.network.client.commands;

import catan.settlers.client.view.ClientWindow;
import catan.settlers.client.view.game.DialogBox;

public class TurnResponseCommand implements ServerToClientCommand {

	private static final long serialVersionUID = 1L;
	private boolean success;
	private String message;

	public TurnResponseCommand(String message, boolean success) {
		this.message = message;
		this.success = success;
	}

	@Override
	public void execute() {
		DialogBox dbox = new DialogBox("Success!", message);

		if (!success) {
			dbox = new DialogBox("Failure", message);
		}

		ClientWindow.getInstance().getGameWindow().setDialogBox(dbox);
	}

}
