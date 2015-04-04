package edu.emory.aims.predict.wherenext.tpattern;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Polygon;

public class TPatternNode {
	private int id;
	private Polygon r;
	private int support;
	private List<TPatternNode> children;
	private List<TPatternEdge> links;
	private boolean isRoot;
	private double score;
	
	public TPatternNode() {
		children = new ArrayList<TPatternNode>();
		links = new ArrayList<TPatternEdge>();
		score = 0.0;
	}
	
	public void updateSupport(int supp) {
		support = Math.max(support, supp);
	}
	
	public TPatternEdge getEdge(TPatternNode n) {
		for (TPatternEdge ed:links) {
			if (ed.getV()==n) {
				return ed;
			}
		}
		return null;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Polygon getR() {
		return r;
	}
	public void setR(Polygon r) {
		this.r = r;
	}
	public int getSupport() {
		return support;
	}
	public void setSupport(int support) {
		this.support = support;
	}
	public List<TPatternNode> getChildren() {
		return children;
	}
	public void setChildern(List<TPatternNode> children) {
		this.children = children;
	}
	public boolean isRoot() {
		return isRoot;
	}
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public List<TPatternEdge> getLinks() {
		return links;
	}

	public void setLinks(List<TPatternEdge> links) {
		this.links = links;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
