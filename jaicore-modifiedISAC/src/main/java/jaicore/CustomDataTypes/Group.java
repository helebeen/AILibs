package jaicore.CustomDataTypes;

import java.util.List;

public class Group <C,I>{
	private List<Instance<I>> instances;
	private Center<C> center;
	
	Group(List<Instance<I>> instanlist,Center<C> cent){
		this.instances = instanlist;
		this.center = cent;
	}
	
	List<Instance<I>> getInstances(){
		return instances;
	}
	Center<C> getCenter(){
		return center;
	}
	void setTripleList(List<Instance<I>> newInstances){
		this.instances = newInstances;
	}
	void setCenter(Center<C> newCenter){
		this.center = newCenter;
	}
	
}
