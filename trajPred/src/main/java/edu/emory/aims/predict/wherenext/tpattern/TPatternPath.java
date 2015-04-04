package edu.emory.aims.predict.wherenext.tpattern;

import java.util.ArrayList;
import java.util.List;

public class TPatternPath {
	private List<TPatternEdge> path;	
	private double score;
	
	public TPatternPath() {
		path = new ArrayList<TPatternEdge>();
		score = 0.0;
	}
	
	public TPatternPath(TPatternPath pa) {
		path = new ArrayList<TPatternEdge>();
		for (TPatternEdge e:pa.getPath()) {
			path.add(e);
		}
		score = pa.score;
	}
	
	public List<TPatternEdge> getPath() {
		return path;
	}
	
	public void setPath(List<TPatternEdge> path) {
		this.path = path;
	}
	
	public void add(TPatternEdge edge) {
		path.add(edge);
	}
	
	public TPatternEdge removeLast() {
		return path.remove(path.size()-1);
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}


}
