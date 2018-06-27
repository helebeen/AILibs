package jaicore.modifiedISAC;

import jaicore.CandidateProvider.IRankedSolutionCandidateProvider;
import jaicore.CustomDataTypes.ProblemInstance;
import weka.classifiers.Classifier;
import weka.core.Instance;

public class CandidateProviderModifiedISAC implements IRankedSolutionCandidateProvider<Instance,Classifier>{

	@Override
	public ClassifierRankingForGroup getCandidate(ProblemInstance<Instance> instance) {
		// TODO Auto-generated method stub
		return null;
	}
}
