package edu.emory.aims.predict.wherenext.element;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;

public class Trajectory {
	private List<Point> locs;
	private List<Double> tms;
	private int length;

	public List<Point> getLocs() {
		return locs;
	}

	public void setLocs(List<Point> locs) {
		this.locs = locs;
	}

	public List<Double> getTms() {
		return tms;
	}

	public void setTms(List<Double> tms) {
		this.tms = tms;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Trajectory() {
		locs = new ArrayList<Point>();
		tms = new ArrayList<Double>();
		length = 0;
	}

	public MultiLineString getSegments(Interval interval) {
		List<LineString> list = new LinkedList<LineString>();
		GeometryFactory fact = new GeometryFactory();
		Coordinate[] coord = new Coordinate[2];
		for (int i = 0; i < length-1; i++) {
			double t11 = tms.get(i), t12 = tms.get(i+1);
			double t21 = interval.tmin, t22 = interval.tmax;
			double x1 = locs.get(i).getX(), x2 = locs.get(i+1).getX();
			double y1 = locs.get(i).getY(), y2 = locs.get(i+1).getY();
			
			if (t11 <= t21 && t12 >= t22 ){
				coord[0].x = (x2-x1)*(t21-t11)/(t12-t11)+x1;
				coord[0].y = (y2-y1)*(t21-t11)/(t12-t11)+y1;
				coord[1].x = (x2-x1)*(t22-t11)/(t12-t11)+x1;
				coord[1].y = (y2-y1)*(t22-t11)/(t12-t11)+y1;
				list.add(fact.createLineString(coord));
			}
			else if (t21 > t11 && t21 < t12) {
				coord[0].x = (x2-x1)*(t21-t11)/(t12-t11)+x1;
				coord[0].y = (y2-y1)*(t21-t11)/(t12-t11)+y1;
				coord[1].x = x2;
				coord[1].y = y2;
				list.add(fact.createLineString(coord));

			}
			else if (t22 > t11 && t22 < t12) {
				coord[0].x= x1;
				coord[0].y = y1;
				coord[1].x = (x2-x1)*(t22-t11)/(t12-t11)+x1;
				coord[1].y = (y2-y1)*(t22-t11)/(t12-t11)+y1;
				list.add(fact.createLineString(coord));

			}
			else if (t21 < t11 && t22 > t12) {
				coord[0].x = x1;
				coord[0].y = y1;
				coord[1].x = x2;
				coord[1].y = y2;
				list.add(fact.createLineString(coord));
			}
			else if (t12 < t21) {
				break;
			}
		}
		return fact.createMultiLineString((LineString[])list.toArray());
	}

	public LineString getExtension(double tht) {
		Coordinate[] coord = new Coordinate[2];
		GeometryFactory fact = new GeometryFactory();

		double x1 = locs.get(length-2).getX(), y1 = locs.get(length-2).getY();
		double x2 = locs.get(length-1).getX(), y2 = locs.get(length-1).getY();
		double t11 = tms.get(length-2), t12 = tms.get(length-1), t22 = tms.get(length-1)+tht;
		coord[0].x = x1;
		coord[0].y = y1;
		
		coord[1].x = (x2-x1)*(t22-t11)/(t12-t11)+x1;
		coord[1].y = (y2-y1)*(t22-t11)/(t12-t11)+y1;
		return fact.createLineString(coord);
	}
	

}
