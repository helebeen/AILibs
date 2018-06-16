package jaicore.CustomDataTypes;

public class Tuple<S,P>{
	private Solution<S> solution;
	private Performance<P> performance;
	
	Tuple(Solution<S> solu, Performance<P> perfor){
		this.solution = solu;
		this.performance = perfor;
	}
	
	Solution<S> getSolution(){
		return this.solution;
	}
	
	Performance<P> getPerformance(){
		return this.performance;
	}
}
