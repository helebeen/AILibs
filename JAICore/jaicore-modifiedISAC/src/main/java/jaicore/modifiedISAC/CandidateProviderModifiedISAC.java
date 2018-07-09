package jaicore.modifiedISAC;

import jaicore.CandidateProvider.IRankedSolutionCandidateProvider;
import jaicore.CustomDataTypes.ProblemInstance;
import weka.classifiers.Classifier;
import weka.core.Instance;

public class CandidateProviderModifiedISAC implements IRankedSolutionCandidateProvider<Instance, Classifier> {

	@Override
	public ClassifierRankingForGroup getCandidate(ProblemInstance<Instance> instance) {
			double[] vectorFormInstance = instance.getInstance().toDoubleArray();
		return null;
	}

}
