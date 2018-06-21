package jaicore.CustomDataTypes;

import java.util.List;

/**
 * Group.java - Stores a group with it center as ID and the associated instances
 * 
 * @author Helen Bierling
 *
 * @param <C> The identifier of the group
 * @param <I> The instances in the group
 */
public class Group <C,I>{
	private List<ProblemInstance<I>> problemInstances;
	private GroupIdentifier<C> groupIdentifier;
	
	public Group(List<ProblemInstance<I>> instanlist,GroupIdentifier<C> id){
		this.problemInstances = instanlist;
		this.groupIdentifier = id;
	}
	
	List<ProblemInstance<I>> getInstances(){
		return problemInstances;
	}
	GroupIdentifier<C> getIdentifier(){
		return groupIdentifier;
	}
	void setInstances(List<ProblemInstance<I>> newInstances){
		this.problemInstances = newInstances;
	}
	void setGroupIdentifier(GroupIdentifier<C> newIdentifer){
		this.groupIdentifier = newIdentifer;
	}
	 void addInstance(ProblemInstance<I> newInstance){
		 problemInstances.add(newInstance);
	 }
	
}
