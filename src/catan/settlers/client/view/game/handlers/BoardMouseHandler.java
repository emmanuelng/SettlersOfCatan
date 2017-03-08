package catan.settlers.client.view.game.handlers;

import java.util.ArrayList;

import org.minueto.handlers.MinuetoMouseHandler;

import catan.settlers.client.view.game.Clickable;

public class BoardMouseHandler implements MinuetoMouseHandler {

	private ArrayList<Clickable> clickableElmts;

	public BoardMouseHandler() {
		clickableElmts = new ArrayList<>();
	}

	public void handleMousePress(int x, int y, int button) {

		for (Clickable c : clickableElmts) {
			if (c.isClicked(x, y)) {
				c.onclick();
			}
		}
	}

	public void register(Clickable c) {
		System.out.println("registering: " + c);
		clickableElmts.add(c);
	}

	/**
	 * Print out the mouse button values and mouse location when the user
	 * releases a mouse button. Constants to identify mouse button values are
	 * available in the MinuetoMouse class.
	 **/
	public void handleMouseRelease(int x, int y, int button) {
		System.out.println("Mouse release on button " + button + " detected at " + x + "," + y);
	}

	/**
	 * This method is called to handle mouse move events. We are not printing
	 * any message since a LOT of these events are generated when the mouse is
	 * moved.
	 **/
	public void handleMouseMove(int x, int y) {

		// Not going to print on this event.
	}
}