package edu.emory.aims.predict.wherenext.tpattern;

import edu.emory.aims.predict.wherenext.element.Interval;

public class TPatternEdge {
	private TPatternNode u, v;
	private Interval time;
	private boolean isRootEdge;
	
	public void updateInterval(Interval i) {
		time.merge(i);
	}
	public TPatternNode getU() {
		return u;
	}

	public void setU(TPatternNode u) {
		this.u = u;
	}

	public TPatternNode getV() {
		return v;
	}

	public void setV(TPatternNode v) {
		this.v = v;
	}

	public boolean isRootEdge() {
		return isRootEdge;
	}

	public void setRootEdge(boolean isRootEdge) {
		this.isRootEdge = isRootEdge;
	}

	public Interval getTime() {
		return time;
	}

	public void setTime(Interval time) {
		this.time = time;
	}

	
	
}
