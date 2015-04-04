package edu.emory.aims.predict.wherenext.tpattern;

import com.vividsolutions.jts.geom.Polygon;

import edu.emory.aims.predict.wherenext.element.Interval;

public class TPatternPair {
	private Interval intval;
	private Polygon region;
	public Polygon getRegion() {
		return region;
	}
	public void setRegion(Polygon region) {
		this.region = region;
	}
	public Interval getIntval() {
		return intval;
	}
	public void setIntval(Interval intval) {
		this.intval = intval;
	}
}
