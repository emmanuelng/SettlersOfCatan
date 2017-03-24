package catan.settlers.server.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import catan.settlers.network.client.commands.ServerToClientCommand;
import catan.settlers.network.server.Credentials;
import catan.settlers.network.server.Server;
import catan.settlers.network.server.Session;
import catan.settlers.server.model.units.Knight.KnightType;

public class Player implements Serializable {

	public enum ResourceType {
		BRICK, GRAIN, LUMBER, ORE, WOOL, CLOTH, COIN, PAPER
	}

	private static final long serialVersionUID = 1L;
	private HashMap<ResourceType, Integer> resources;
	private Credentials credentials;
	private int basicKnightCount, strongKnightCount, mightyKnightCount;

	public Player(Credentials credentials) {
		this.credentials = credentials;
		this.resources = new HashMap<>();

		for (ResourceType resType : ResourceType.values()) {
			resources.put(resType, 0);
		}
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public String getUsername() {
		return credentials.getUsername();
	}

	public HashMap<ResourceType, Integer> getResources() {
		HashMap<ResourceType, Integer> res = new HashMap<>();
		for (ResourceType type : resources.keySet())
			res.put(type, resources.get(type));
		return res;
	}

	public int getResourceAmount(ResourceType res) {
		return resources.get(res);
	}

	public void giveResource(ResourceType r, int amount) {
		int currentAmount = resources.get(r);
		resources.put(r, currentAmount + amount);
	}

	public void removeResource(ResourceType r, int amount) {
		int previous = resources.get(r);
		resources.put(r, previous - amount);
	}

	public int getNbResourceCards() {
		int count = 0;
		for (ResourceType rt : ResourceType.values()) {
			count += resources.get(rt);
		}
		return count;
	}

	public int getKnightCount(KnightType kType) {
		if (kType == KnightType.BASIC_KNIGHT) {
			return basicKnightCount;
		} else if (kType == KnightType.STRONG_KNIGHT) {
			return strongKnightCount;
		} else if (kType == KnightType.MIGHTY_KNIGHT) {
			return mightyKnightCount;
		}
		return 0;
	}

	public void setKnightCount(KnightType kType, int kCount) {
		if (kType == KnightType.BASIC_KNIGHT) {
			basicKnightCount = kCount;
		} else if (kType == KnightType.STRONG_KNIGHT) {
			strongKnightCount = kCount;
		} else if (kType == KnightType.MIGHTY_KNIGHT) {
			mightyKnightCount = kCount;
		}
	}

	public Session getSession() {
		return Server.getInstance().getAuthManager().getSessionByCredentials(credentials);
	}

	public void sendCommand(ServerToClientCommand cmd) {
		Session s = Server.instance.getAuthManager().getSessionByCredentials(credentials);
		try {
			s.sendCommand(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Player) {
			Player other = (Player) obj;
			return credentials.getUsername().equals(other.credentials.getUsername());
		}
		return false;
	}

}