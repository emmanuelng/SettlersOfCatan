package catan.settlers.server.model.map;

import java.io.Serializable;
import java.util.ArrayList;

import catan.settlers.server.model.Player;
import catan.settlers.server.model.units.IntersectionUnit;
import catan.settlers.server.model.units.Village;

public class Edge implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<Edge> leftEdges;
	private ArrayList<Edge> rightEdges;
	private Intersection[] myIntersections;

	private Player roadOwner;

	private int id;

	public Edge(int id) {
		this.id = id;
		myIntersections = new Intersection[2];
		leftEdges = new ArrayList<Edge>();
		rightEdges = new ArrayList<Edge>();
		roadOwner = null;
	}

	public void addLeftEdge(Edge e) {
		if (e != null) {
			if (!leftEdges.contains(e)) {
				leftEdges.add(e);
			}
		}
	}

	public void addRightEdge(Edge e) {
		if (e != null) {
			if (!rightEdges.contains(e)) {
				rightEdges.add(e);
			}
		}
	}

	public void setIntersections(Intersection a, Intersection b) {
		if (a != null && b != null) {
			if (myIntersections[0] == null || myIntersections[1] == null) {
				myIntersections[0] = a;
				myIntersections[1] = b;
			}
		}
	}

	public boolean hasIntersection(Intersection i) {
		return (myIntersections[0] == i || myIntersections[1] == i);
	}

	public Intersection getOppIntersection(Intersection i) {
		if (myIntersections[0] == i) {
			return myIntersections[1];
		} else if (myIntersections[1] == i) {
			return myIntersections[0];
		} else {
			return null;
		}
	}

	public int getId() {
		return id;
	}

	public Player getOwner() {
		return roadOwner;
	}

	public void setOwner(Player p) {
		roadOwner = p;
	}

	public boolean isMaritime() {
		boolean isMaritime = false;
		for (Intersection i : myIntersections) {
			isMaritime = isMaritime || i.isMaritime();
		}
		return isMaritime;
	}

	/**
	 * Returns true is the player can build a road.
	 */
	public boolean canBuild(String username) {
		if (getOwner() != null)
			return false;

		/*
		 * If there is a village owned by the player on one of the
		 * intersections, the player can build a road
		 */
		for (int i = 0; i < myIntersections.length; i++) {
			IntersectionUnit unit = myIntersections[i].getUnit();
			if (unit != null) {
				// There is a unit here
				if (unit instanceof Village) {
					// The unit is a village
					Village village = (Village) unit;
					if (village.getOwner().getUsername().equals(username))
						// The player owns the village
						return true;
				}
			}
		}

		/*
		 * If there is an adjacent road owned by the player, the player can
		 * build
		 */
		for (int i = 0; i < myIntersections.length; i++) {
			Intersection intersection = myIntersections[i];
			
			for (Edge edge : intersection.getEdges()) {
				if (edge.getOwner() != null)
					if (edge.getOwner().getUsername().equals(username)) {
						return true;
					}
			}
		}

		return false;
	}

	/**
	 * Returns true is the player can build a road. This method is safer than
	 * the method using the user name, as it directly takes the player instance,
	 * and not an arbitrary user name, that could easily be modified by the
	 * client. Therefore, use this for server-side validation.
	 */
	public boolean canBuild(Player player) {
		return canBuild(player.getUsername());
	}
}
