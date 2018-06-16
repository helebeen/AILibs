package jaicore.BuildRanker;

import java.util.HashMap;
import java.util.List;

import jaicore.CustomDataTypes.Group;
import jaicore.CustomDataTypes.RankingForGroup;
import jaicore.CustomDataTypes.Tuple;

public interface IGroupSolutionRankingSelect<C,S,I,P> {
	RankingForGroup<C,S> selectGroupsolutionRanking(Group<C,I> consideredGroup,HashMap<I,List<Tuple<S,P>>>InstancesinGourp);
}
