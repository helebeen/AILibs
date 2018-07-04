package jaicore.modifiedISAC;

import java.util.List;

import jaicore.CustomDataTypes.ProblemInstance;
import weka.core.Instance;

public class Normalizer {
	private int numbervaluesToNormalize;
	private double[] maxvalues;
	private List<ProblemInstance<Instance>> basisForNormalization;

	public Normalizer(List<ProblemInstance<Instance>> list) {
		this.numbervaluesToNormalize = list.get(0).getInstance().numAttributes();
		this.maxvalues = new double[numbervaluesToNormalize];
		this.basisForNormalization = list;
	}

	public void setupnormalize() {
		for (ProblemInstance<Instance> i : basisForNormalization) {
			double[] instacnevector = i.getInstance().toDoubleArray();
			for (int j = 0; j < instacnevector.length; j++) {
				if (Double.isNaN(instacnevector[j])) {
					if (Double.isNaN(maxvalues[j])) {
						maxvalues[j] = Double.NaN;
					}
				} else {
					if (Double.isNaN(maxvalues[j])) {
						maxvalues[j] = instacnevector[j];
					} else {
						if (instacnevector[j] > maxvalues[j]) {
							maxvalues[j] = instacnevector[j];
						}
					}
				}
			}
		}
	}
	//TODO Auch darauf achten das eine neue Instance größer sein kann als das bisher gefundene Maximum. Gilt nur für neu die anderen sind
	//Basis für das Setup ist deren globales Maximum.

	public double[] normalize(double[] vectorToNormalize) {
		for (int i = 0; i < vectorToNormalize.length; i++) {
			if (Double.isNaN(maxvalues[i])) {
				if (Double.isNaN(vectorToNormalize[i])) {
					vectorToNormalize[i] = Double.NaN;
				}
			} else {
				vectorToNormalize[i] = ((vectorToNormalize[i] / maxvalues[i]) * 2) - 1;
			}
		}

		return vectorToNormalize;
	}

	public double[] getbasis() {
		return maxvalues;
	}
}
