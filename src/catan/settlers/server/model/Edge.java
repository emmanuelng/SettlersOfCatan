package catan.settlers.server.model;

import java.util.ArrayList;

public class Edge {
	private ArrayList<Edge> myEdges;
	private Hex[] myHexes;
	private Intersection[] myIntersections;
	private boolean hasRoad;
	
	public Edge() {
		hasRoad = false;
	}
	
	public void addEdge(Edge e) { myEdges.add(e); }
	
	public void setIntersection(Intersection t, int i) { myIntersections[i] = t; }
	public Intersection getIntersection(int i) { return myIntersections[i]; }
}