package jaicore.modifiedISAC;

import java.util.Map;

import jaicore.CandidateProvider.IRankedSolutionCandidateProvider;
import jaicore.CustomDataTypes.ProblemInstance;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;

public class CandidateProviderModifiedISAC implements IRankedSolutionCandidateProvider<Instance, Classifier> {

	double consideredInstnace [];
	@Override
	public ClassifierRankingForGroup getCandidate(ProblemInstance<Instance> instance) {
		// GlobalCharacterizer chara = new GlobalCharacterizer();
		// chara.characterize(instance);
		//formatInstance();
		return null;
	}

	private void formatInstance(Map<String, Double> toFormatInstance) {
		for (int i = 0; i< ModifiedISACInstanceCollector.getAtributesofTrainingsdata().size();i++) {
			consideredInstnace[i]=toFormatInstance.get(ModifiedISACInstanceCollector.getAtributesofTrainingsdata().get(i));
		}
	}
}
