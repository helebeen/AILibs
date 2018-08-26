package jaicore.CustomDataTypes;

/**
 * Tuple.java - Stores a solution as well as the according performance of that solution
 * 
 * @author Helen Beieling
 *
 * @param <S> A solution
 * @param <P> A performance value
 */
public class Tuple<S,P>{
	private Solution<S> solution;
	private Performance<P> performance;
	
	Tuple(Solution<S> solu, Performance<P> perfor){
		this.solution = solu;
		this.performance = perfor;
	}
	
	public Solution<S> getSolution(){
		return this.solution;
	}
	
	public Performance<P> getPerformance(){
		return this.performance;
	}
}
