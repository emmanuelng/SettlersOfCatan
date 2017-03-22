package catan.settlers.client.view.game;

import java.util.ArrayList;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoRectangle;
import org.minueto.image.MinuetoText;

import catan.settlers.client.model.ClientModel;
import catan.settlers.client.view.ClientWindow;
import catan.settlers.client.view.game.actions.Action;
import catan.settlers.client.view.game.handlers.BoardMouseHandler;
import catan.settlers.client.view.game.handlers.Clickable;

public class ActionBoxImage extends MinuetoImage {

	private static final int WIDTH = 300;
	private static final int PADDING_TOP = 30;
	private static final int PADDING_LEFT = 10;

	private static final MinuetoColor BACKGROUND_COLOR = new MinuetoColor(233, 221, 175);
	private static final MinuetoColor TITLE_COLOR = new MinuetoColor(172, 167, 147);
	private static final MinuetoColor BUTTON_COLOR = new MinuetoColor(244, 238, 215);
	private static final MinuetoColor SHADOW_COLOR = new MinuetoColor(222, 205, 135);

	private MinuetoRectangle background;
	private MinuetoRectangle background_shadow;
	private MinuetoText title;
	private int box_x, box_y;
	private ArrayList<Clickable> allButtons;

	public ActionBoxImage() {
		super(ClientWindow.WINDOW_WIDTH, ClientWindow.WINDOW_HEIGHT);

		this.box_x = ClientWindow.WINDOW_WIDTH - WIDTH;
		this.box_y = 0;
		this.allButtons = new ArrayList<>();

		this.background = new MinuetoRectangle(WIDTH, ClientWindow.WINDOW_HEIGHT, BACKGROUND_COLOR, true);
		this.background_shadow = new MinuetoRectangle(WIDTH, ClientWindow.WINDOW_HEIGHT, SHADOW_COLOR, true);
		this.title = new MinuetoText("ACTIONS", new MinuetoFont("arial", 15, true, false), TITLE_COLOR);

		compose();
	}

	public void compose() {
		clear();
		unregisterAllButtons();
		ArrayList<Action> list = ClientModel.instance.getActionManager().getPossibleActions();

		if (list.size() > 0) {
			draw(background_shadow, box_x - 3, box_y);
			draw(background, box_x, box_y);
			draw(title, box_x + PADDING_LEFT, box_y + PADDING_TOP);

			int cur_x = box_x + PADDING_LEFT, cur_y = box_y + PADDING_TOP + title.getHeight() + 15;
			for (int i = 0; i < list.size(); i++) {
				Action currentAction = list.get(i);
				cur_y += addActionButton(currentAction, cur_x, cur_y) + 5;
			}
		}
	}

	private int addActionButton(Action action, int x, int y) {
		// Setup the button's text
		MinuetoFont font = new MinuetoFont("arial", 15, false, false);
		MinuetoText description = new MinuetoText(action.getDescription(), font, MinuetoColor.BLACK);

		// Compute the button's size
		int btn_width = WIDTH - 2 * PADDING_LEFT;
		int btn_height = 2 * description.getHeight();
		MinuetoRectangle btnBackground = new MinuetoRectangle(btn_width, btn_height, BUTTON_COLOR, true);
		MinuetoRectangle btnShadow = new MinuetoRectangle(btn_width, btn_height, SHADOW_COLOR, true);

		// Compute the text position
		int desc_x = x + 15;
		int desc_y = y + btn_height / 2 - description.getHeight() / 2;

		// Draw the button
		draw(btnShadow, x + 3, y + 3);
		draw(btnBackground, x, y);
		draw(description, desc_x, desc_y);

		// Register in mouseManager
		BoardMouseHandler mouseHandler = ClientWindow.getInstance().getGameWindow().getMouseHandler();
		int btn_x = x, btn_y = y;
		Clickable clickable = new Clickable() {

			@Override
			public void onclick() {
				action.sendCommand();
			}

			@Override
			public boolean isClicked(int x, int y) {
				return x > btn_x && x < btn_x + btn_width && y > btn_y + 100 && y < btn_y + btn_height + 100;
			}

			@Override
			public String getName() {
				return "ActionButton" + action;
			}
		};
		mouseHandler.register(clickable);
		allButtons.add(clickable);
		return btn_height;
	}

	private void unregisterAllButtons() {
		for (Clickable c : allButtons)
			ClientWindow.getInstance().getGameWindow().getMouseHandler().unregister(c);
	}
}