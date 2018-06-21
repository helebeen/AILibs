package jaicore.modifiedISAC;

import java.util.ArrayList;

import jaicore.CustomDataTypes.GroupIdentifier;
import jaicore.CustomDataTypes.RankingForGroup;
import jaicore.CustomDataTypes.Solution;
import weka.classifiers.Classifier;

public class ClassifierRankingForGroup extends RankingForGroup<double[],Classifier> {

	ClassifierRankingForGroup(GroupIdentifier<double[]> identifier, ArrayList<Solution<Classifier>> solutionsForGroup) {
		super(identifier, solutionsForGroup);
		// TODO Auto-generated constructor stub
	}

}
