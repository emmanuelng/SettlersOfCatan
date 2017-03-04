package catan.settlers.server.model;

import java.io.Serializable;

public class Player implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	
	/*
	 *  Non-progress cards (ie: resources and commodities) will be stored in a simple
	 *  integer array. Each value in the array corresponds to the amount of each type
	 *  of card possessed by the player. The convention for card types is alphabetical,
	 *  resource before commodities:
	 *  [0] BRICK, [1] GRAIN, [2] LUMBER, [3] ORE, [4] WOOL, 
	 *  [5] CLOTH, [6] COIN, [7] PAPER
	 */
	private int[] myResources;
	
	public Player(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean comparePassword(String proposedPassword) {
		return password.equals(proposedPassword);
	}
	
	public void giveResource(int resource, int amount) {
		myResources[resource] += amount;
	}
	
	public void takeResource(int resource, int amount) {
		if (myResources[resource] >= amount) {
			myResources[resource] -= amount;
		}
	}
}
