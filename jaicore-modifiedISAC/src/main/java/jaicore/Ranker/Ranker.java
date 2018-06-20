package jaicore.Ranker;


import jaicore.CustomDataTypes.Ranking;


public interface Ranker<S> {
	public void bulidRanker();
	public Ranking<S> getRanking();
}
