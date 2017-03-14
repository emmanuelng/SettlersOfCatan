package catan.settlers.client.view.game;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

import catan.settlers.client.model.ClientModel;
import catan.settlers.client.view.ClientWindow;
import catan.settlers.client.view.game.handlers.Clickable;
import catan.settlers.server.model.map.Intersection;
import catan.settlers.server.model.units.Village;

public class IntersectionImage extends MinuetoImage implements Clickable {

	private int relativeX;
	private int relativeY;
	private Intersection intersectionModel;

	public IntersectionImage(int relativeX, int relativeY, Intersection intersection) {
		super(20, 20);

		this.relativeX = relativeX;
		this.relativeY = relativeY;
		this.intersectionModel = intersection;

		if (intersection.getUnit() == null) {
			if (intersection != ClientModel.instance.getCurrentIntersection()) {
				if (intersectionModel.isMaritime()) {
					drawCircle(new MinuetoColor(182, 215, 255), 0, 0, 20);
				} else {
					drawCircle(new MinuetoColor(204, 204, 204), 0, 0, 20);
				}
			} else {
				drawCircle(MinuetoColor.RED, 0, 0, 20);
			}
		} else {
			try {
				if (intersection.getUnit() instanceof Village) {
					Village village = (Village) intersection.getUnit();

					switch (village.getKind()) {
					case SETTLEMENT:
						draw(new MinuetoImageFile("images/building.png"), 0, 0);
						break;
					case CITY:
						int playerNo = ClientWindow.getInstance().getGameWindow()
								.getPlayerNumber(village.getOwner().getUsername());
						draw(new MinuetoImageFile("images/city" + playerNo + ".png"), 0, 0);
						break;
					default:
						break;
					}
				}
			} catch (MinuetoFileException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isClicked(int x, int y) {
		return x > relativeX && x < relativeX + getWidth() && y > relativeY + 100 && y < relativeY + 100 + getHeight();
	}

	@Override
	public void onclick() {
		if (intersectionModel != ClientModel.instance.getCurrentIntersection()) {
			ClientModel.instance.setCurrentIntersection(intersectionModel);
		} else {
			ClientModel.instance.setCurrentIntersection(null);
		}

	}

	@Override
	public String getName() {
		return "Intersection" + intersectionModel;
	}

}
