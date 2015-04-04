package edu.emory.aims.predict.wherenext.tpattern;

import java.util.LinkedList;
import java.util.List;

import com.vividsolutions.jts.geom.Polygon;

import edu.emory.aims.predict.wherenext.element.Trajectory;

public class TPatternTree {
	private LinkedList<TPatternNode> nodes;
	private LinkedList<TPatternEdge> edges;
	private TPatternNode root;
	public static int idcount = 1;

	@SuppressWarnings("null")
	public void construct(List<TPattern> tp) {
		root = new TPatternNode();
		root.setRoot(true);
		for (TPattern p : tp) {
			TPatternNode node = root;
			for (TPatternPair pa : p.getPairs()) {
				TPatternNode child = null;
				TPatternEdge e = null;
				if(findChild(node, pa.getRegion(), child, e)){
					e.updateInterval(pa.getIntval());
					child.updateSupport(p.getSupp());
					node = child;
				}
				else {
					TPatternNode n = new TPatternNode();
					n.setSupport(p.getSupp());
					node.getChildren().add(n);
					node = n;
				}
			}
		}
	}

	private boolean findChild(TPatternNode node, Polygon r, TPatternNode child,
			TPatternEdge edge) {
		for (int i = 0; i < node.getChildren().size(); i++) {
			TPatternNode n = node.getChildren().get(i);
			TPatternEdge e = node.getLinks().get(i);
			if (n.getR() == r) {
				child = n;
				edge = e;
				return true;
			}
		}
		return false;
	}

	public LinkedList<TPatternNode> getNodes() {
		return nodes;
	}

	public void setNodes(LinkedList<TPatternNode> nodes) {
		this.nodes = nodes;
	}

	public LinkedList<TPatternEdge> getEdges() {
		return edges;
	}

	public void setEdges(LinkedList<TPatternEdge> edges) {
		this.edges = edges;
	}

	public TPatternNode getRoot() {
		return root;
	}

	public void setRoot(TPatternNode root) {
		this.root = root;
	}

	public List<TPatternPath> getPaths(Trajectory traj) {
		// TODO Auto-generated method stub
		return null;
	}
}
