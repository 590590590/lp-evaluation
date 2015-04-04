package edu.emory.aims.predict.wherenext.element;

public class Interval {
	public double tmin, tmax;
	public void merge(Interval i) {
		tmin = Math.min(tmin, i.tmin);
		tmax = Math.max(tmax, i.tmax);
	}
}
