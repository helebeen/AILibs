package jaicore.modifiedISAC;

import java.util.ArrayList;

import java.math.*;

import jaicore.CustomDataTypes.ProblemInstance;
import weka.core.Instance;

public class Normalizer {
	private int numbervaluesToNormalize;
	private double[] maxvalues = new double[numbervaluesToNormalize];
	private ArrayList<ProblemInstance<Instance>> basisForNormalization;

	Normalizer(int values, ArrayList<ProblemInstance<Instance>> initalvalues) {
		this.numbervaluesToNormalize = values;
		this.basisForNormalization = initalvalues;
		for (int i = 0; i < numbervaluesToNormalize; i++) {
			this.maxvalues[i] = 0;
		}
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
			vectorToNormalize[i] = ((vectorToNormalize[i]/maxvalues[i])*2)-1; 
		}
		return vectorToNormalize;
	}
}
