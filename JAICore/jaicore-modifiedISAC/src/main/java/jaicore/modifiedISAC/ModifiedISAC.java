package jaicore.modifiedISAC;

import jaicore.CustomDataTypes.RankingForGroup;
import jaicore.GroupBasedRanker.GroupBasedRanker;
import weka.classifiers.Classifier;
import weka.core.Instance;

public class ModifiedISAC extends GroupBasedRanker<double [],Instance, Classifier, Double> {

	public ModifiedISAC(ModifiedISACInstanceCollector instcoll,
			ModifiedISACTableGeneratorandCompleter table) {
		super(instcoll, table);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bulidRanker() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RankingForGroup<double[], Classifier> getRanking() {
		// TODO Auto-generated method stub
		return null;
	}

}
