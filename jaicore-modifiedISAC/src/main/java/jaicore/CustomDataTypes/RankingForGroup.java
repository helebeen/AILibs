package jaicore.CustomDataTypes;

import java.util.List;

public class RankingForGroup <C,S>{
	private Center<C> centerofGroup;
	private List<Solution<S>> rankinforGroup;
	
	RankingForGroup(Center<C> center, List<Solution<S>> solutionsForGroup){
		this.centerofGroup=center;
		this.rankinforGroup=solutionsForGroup;
	}
	
	Center<C> getCenterofGroup(){
		return centerofGroup;
	}
	List<Solution<S>> getRankingofGroup(){
		return rankinforGroup;
	}
}
