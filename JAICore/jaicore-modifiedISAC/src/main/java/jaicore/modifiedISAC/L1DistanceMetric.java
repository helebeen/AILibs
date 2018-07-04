package jaicore.modifiedISAC;

public class L1DistanceMetric implements IDistanceMetric<Double, double[], double[]> {

	@Override
	public Double computeDistance(double[] start, double[] end) throws Exception {
		if (start.length == end.length) {
			double distance = 0;
			for (int i = 0; i < start.length; i++) {
				if (Double.isNaN(start[i]) || Double.isNaN(end[i])) {
					// TODO fragen ob summant weggelassen werden soll wenn einer von beiden
					// NAN ist
					distance += 0;
				} else {
					distance += Math.abs((start[i] - end[i]));
				}
			}
			return distance;
		} else {
			throw new Exception();
		}
	}
}
