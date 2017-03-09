package catan.settlers.server.model;

import java.io.Serializable;

public class Game extends Thread implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	
	private GamePlayersManager gamePlayersManager;
	private GameBoardManager gameBoardManager;
	private GamePhase phase;
	
	private int redDie;
	private int yellowDie;
	
	public enum GamePhase {
		READYTOJOIN, SETUPPHASEONE, SETUPPHASETWO, TURNPHASEONE, TURNDICEROLL,TURNPHASETWO, COMPLETED
	}

	public Game(int id, Player owner) {
		this.id = id;
		this.gamePlayersManager = new GamePlayersManager(owner);
		this.gameBoardManager = new GameBoardManager();
		this.phase = GamePhase.READYTOJOIN;
	}

	public int getGameId() {
		return id;
	}
	
	public GamePlayersManager getPlayersManager() {
		return gamePlayersManager;
	}
	
	public GameBoardManager getGameBoardManager() {
		return gameBoardManager;
	}
	
	public GamePhase getPhase() {
		return phase;
	}
	
	public void setPhase(GamePhase p) {
		phase = p;
	}
	
	@Override
	public void run() {
		// TODO
	}
	
	public void rollDice() {
		redDie = (int)Math.ceil((Math.random()*6));
		yellowDie = (int)Math.ceil((Math.random()*6));
	}
	
	public int getRedDie(){
		return redDie;
	}
	
	public int getYellowDie(){
		return yellowDie;
	}
}
