package jaicore.modifiedISAC;

import java.util.List;

import jaicore.CustomDataTypes.ProblemInstance;
import weka.core.Instance;

public class Normalizer {
	private int numbervaluesToNormalize;
	private double[] maxvalues;
	private List<ProblemInstance<Instance>> basisForNormalization;

	public Normalizer(List<ProblemInstance<Instance>> list) {
		this.numbervaluesToNormalize = list.size(); 
		this.maxvalues= new double[numbervaluesToNormalize];
		this.basisForNormalization = list;
		for (int i = 0; i < maxvalues.length; i++) {
			this.maxvalues[i] = 0;
		}
		System.out.println(maxvalues.length);
		for(double d : maxvalues) {
			System.out.print(d+"||");
		}
		System.out.println("test");
	}

	public void setupnormalize() {
		for (ProblemInstance<Instance> i : basisForNormalization) {
			double[] instacnevector = i.getInstance().toDoubleArray();
			for (int j = 0; j < instacnevector.length; j++)
				if (instacnevector[j] > maxvalues[j]) {
					maxvalues[j] = instacnevector[j];
				}
		}
	}

	public double[] normalize(double[] vectorToNormalize) {
		for (int i = 0; i < vectorToNormalize.length; i++) {
			vectorToNormalize[i] = ((vectorToNormalize[i] / maxvalues[i]) * 2) - 1;
		}
		return vectorToNormalize;
	}
}
