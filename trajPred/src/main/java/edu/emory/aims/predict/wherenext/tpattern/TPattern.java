package edu.emory.aims.predict.wherenext.tpattern;

import java.util.LinkedList;
import java.util.List;

import edu.emory.aims.predict.wherenext.element.Trajectory;

public class TPattern {
	private LinkedList<TPatternPair> pairs;
	private int supp;
	
	public static List<TPattern> generateTPatterns(List<Trajectory> trs) {
		return null;
		
	}
	public LinkedList<TPatternPair> getPairs() {
		return pairs;
	}

	public void setPairs(LinkedList<TPatternPair> pairs) {
		this.pairs = pairs;
	}

	public int getSupp() {
		return supp;
	}

	public void setSupp(int supp) {
		this.supp = supp;
	}
	
	
	
}
