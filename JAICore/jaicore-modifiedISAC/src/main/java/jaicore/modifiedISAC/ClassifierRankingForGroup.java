package jaicore.modifiedISAC;

import java.util.ArrayList;

import jaicore.CustomDataTypes.GroupIdentifier;
import jaicore.CustomDataTypes.RankingForGroup;
import jaicore.CustomDataTypes.Solution;

public class ClassifierRankingForGroup extends RankingForGroup<double[],String> {

	ClassifierRankingForGroup(GroupIdentifier<double[]> identifier, ArrayList<Solution<String>> solutionsForGroup) {
		super(identifier, solutionsForGroup);
		// TODO Auto-generated constructor stub
	}

}
