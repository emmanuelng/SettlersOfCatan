package catan.settlers.server.model.map;

import java.io.Serializable;
import java.util.ArrayList;

public class Edge implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Edge> leftEdges;
	private ArrayList<Edge> rightEdges;
	private Intersection[] myIntersections;
	
	private int id;
	
	public Edge(int id) {
		this.id = id;
		myIntersections = new Intersection[2];
		leftEdges = new ArrayList<Edge>();
		rightEdges = new ArrayList<Edge>();
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
}
