package jaicore.modifiedISAC;

public interface IDistanceMetric<D,A,B> {
	public D computeDistance(A start, B End)throws Exception;
}
