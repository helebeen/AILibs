package jaicore.modifiedISAC;

import java.util.Map;

import jaicore.CustomDataTypes.ProblemInstance;
import jaicore.ml.metafeatures.GlobalCharacterizer;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class HellFormater {

	public static ProblemInstance<Instance> formate(Instances input) {
		GlobalCharacterizer chara = null;
		try {
			chara = new GlobalCharacterizer();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Double> toFormat = chara.characterize(input);

		return formatInstance(toFormat);
	}

	private static ProblemInstance<Instance> formatInstance(Map<String, Double> toFormatInstance) {
		double[] collectedAttributes = new double[ModifiedISACInstanceCollector.getAtributesofTrainingsdata().size()];
		for (int i = 0; i < ModifiedISACInstanceCollector.getAtributesofTrainingsdata().size(); i++) {
			collectedAttributes[i] = toFormatInstance.get(ModifiedISACInstanceCollector.getAtributesofTrainingsdata().get(i));
		}
		DenseInstance inst = new DenseInstance(1,collectedAttributes);
		ProblemInstance<Instance> formated = new ProblemInstance<Instance>(inst);
		return formated;
	}
}
