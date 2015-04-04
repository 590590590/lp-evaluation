package edu.emory.aims.predict.wherenext;

import java.util.ArrayList;
import java.util.List;

import edu.emory.aims.predict.wherenext.element.Trajectory;
import edu.emory.aims.predict.wherenext.tpattern.TPattern;
import edu.emory.aims.predict.wherenext.tpattern.TPatternTree;

public class Process {
	public static void main(String[] args) {
		TPatternTree tpt = new TPatternTree();
		List<Trajectory> trs = new ArrayList<Trajectory>();
		List<TPattern> tpList = TPattern.generateTPatterns(trs);
		tpt.construct(tpList);
		
		Predictor pred = new Predictor();
		
	}
}
