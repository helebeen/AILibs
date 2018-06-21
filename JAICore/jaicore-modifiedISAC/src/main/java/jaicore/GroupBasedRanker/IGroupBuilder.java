package jaicore.GroupBasedRanker;

import java.util.List;

import jaicore.CustomDataTypes.Group;
import jaicore.CustomDataTypes.ProblemInstance;

/**IGroupBuilder discribes the act of building groups out of probleminstances
 * 
 * @author Helen Beierling
 *
 * @param <C> Centers of the found groups
 * @param <I> Probleminstaces to group and grouped instances
 */
interface IGroupBuilder<C,I>{
	
	List<Group<C,I>> buildGroup(List<ProblemInstance<I>> allInstances);
}
