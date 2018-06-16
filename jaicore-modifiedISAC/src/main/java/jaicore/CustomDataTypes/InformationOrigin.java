package jaicore.CustomDataTypes;

public class InformationOrigin<O> {
	private O originOfInformation;
	
	InformationOrigin(O origin){
		this.originOfInformation = origin;
	}
	
	O getOriginofInformation() {
		return originOfInformation;
	}
	
	public boolean isEmpty() {
		return originOfInformation != null;
	}
}
