package jaicore.CustomDataTypes;

/**
 * @author Helen Beierling
 *
 * @param <C> An identifier of a group
 */
public class GroupIdentifier<C> {
	
	private C identifier;
	
	GroupIdentifier(C id){
		this.identifier = id;
	}
	C getCenter(){
		return this.identifier;
	}

}
