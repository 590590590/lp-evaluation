package edu.emory.aims.predict.wherenext;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.operation.distance.DistanceOp;

import edu.emory.aims.predict.wherenext.element.Trajectory;
import edu.emory.aims.predict.wherenext.tpattern.TPatternEdge;
import edu.emory.aims.predict.wherenext.tpattern.TPatternNode;
import edu.emory.aims.predict.wherenext.tpattern.TPatternPath;
import edu.emory.aims.predict.wherenext.tpattern.TPatternTree;

public class Predictor {
	private List<Polygon> predictions;
	private List<TPatternPath> best;
	private double tht, ths, thscore, beta, alpha;
	private TPatternTree pt;
	private int aggType = 0;

	public Predictor() {

	}

	public Predictor(double tht, double ths, double thscore, int thagg,
			double beta, double alpha, TPatternTree pt) {
		this.tht = tht;
		this.ths = ths;
		this.thscore = thscore;
		this.aggType = thagg;
		this.beta = beta;
		this.alpha = alpha;
		this.pt = pt;

		predictions = new ArrayList<Polygon>();
		best = new ArrayList<TPatternPath>();
	}

	public void setAggType(int type) {
		aggType = type;
	}

	private void Visit(TPatternNode root, TPatternPath path, Trajectory traj) {
		path.setScore(pathScore(traj, path));
		int i;
		for (i = 0; i < best.size(); i++) {
			if (path.getScore() < best.get(i).getScore())
				break;
		}
		if (i == best.size() && path.getScore() >= thscore)
			best.add(new TPatternPath(path));
		for (TPatternEdge edge : root.getLinks()) {
			path.add(edge);
			Visit(edge.getV(), path, traj);
			path.removeLast();
		}
	}

	public void predict(Trajectory traj) {
		Visit(pt.getRoot(), new TPatternPath(), traj);
	}

	private double pScore(TPatternPath Tpath, TPatternNode u, TPatternNode v,
			Trajectory tr) {
		MultiLineString segments = tr.getSegments(u.getEdge(v).getTime());
		if (segments.isEmpty()) {
			return -1;
		}
		Polygon r = v.getR();
		double score = 0;

		if (r.intersects(segments)) {
			score = v.getSupport();
		} else {
			LineString el = tr.getExtension(tht);
			Point start = el.getStartPoint();
			if (r.intersects(el)) {
				Coordinate[] intersections = r.intersection(el)
						.getCoordinates();
				double dt = Double.MAX_VALUE;
				for (int i = 0; i < intersections.length; i++) {
					Point pt = new GeometryFactory()
							.createPoint(intersections[i]);
					double di = pt.distance(start);
					if (di < dt) {
						dt = di;
					}
				}
				score = (double) v.getSupport() / (beta * dt);
			} else {
				DistanceOp distOp = new DistanceOp(el, r);
				double ds = distOp.distance();

				Coordinate[] closestPt = distOp.nearestPoints();
				LineString closestPtLine = new GeometryFactory()
						.createLineString(closestPt);
				double dt = closestPtLine.getStartPoint().distance(start);
				if (ds > ths)
					score = 0;
				else
					score = v.getSupport() / (beta * dt + alpha * ds);
			}
		}
		return score;
	}

	private double pathScore(Trajectory traj, TPatternPath path) {
		double score = 0;
		if (aggType == 0) {
			score = avgScore(traj, path);
		} else if (aggType == 1) {
			score = sumScore(traj, path);
		} else {
			score = maxScore(traj, path);
		}
		return score;
	}

	private double maxScore(Trajectory traj, TPatternPath Tpath) {
		double score = 0;
		int n = Tpath.getPath().size();
		for (TPatternEdge e : Tpath.getPath()) {
			double s = pScore(Tpath, e.getU(), e.getV(), traj) / n;
			if (s >= 0)
				score += s;
			else
				break;
		}
		return score;
	}

	private double sumScore(Trajectory traj, TPatternPath Tpath) {
		double score = 0;
		for (TPatternEdge e : Tpath.getPath()) {
			double s = pScore(Tpath, e.getU(), e.getV(), traj);
			if (s >= 0)
				score += s;
			else
				break;
		}
		return score;
	}

	private double avgScore(Trajectory traj, TPatternPath Tpath) {
		double score = 0;
		for (TPatternEdge e : Tpath.getPath()) {
			double s = Math.max(score, pScore(Tpath, e.getU(), e.getV(), traj));
			if (s >= 0)
				score += s;
			else
				break;
		}
		return score;
	}
}
