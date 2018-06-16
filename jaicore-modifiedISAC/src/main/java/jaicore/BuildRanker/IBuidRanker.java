package jaicore.BuildRanker;

import java.util.List;

import jaicore.CustomDataTypes.InformationOrigin;
import jaicore.CustomDataTypes.RankingForGroup;

public interface IBuidRanker<C,S,O> {
	List<RankingForGroup<C,S>> buildRanker(InformationOrigin<O> originofInformation);
}
