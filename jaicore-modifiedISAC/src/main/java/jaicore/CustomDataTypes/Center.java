package jaicore.CustomDataTypes;

public class Center<C> {
	
	private C position;
	
	Center(C pos){
		this.position = pos;
	}
	C getCenter(){
		return this.position;
	}

}
