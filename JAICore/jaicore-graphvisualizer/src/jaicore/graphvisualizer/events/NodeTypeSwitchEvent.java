package jaicore.graphvisualizer.events;

public class NodeTypeSwitchEvent<T> {

	private final T node;
	private final String type;
	public final String name = "NodeTypeSwitchEvent";

	public NodeTypeSwitchEvent(T node, String type) {
		super();
		this.node = node;
		this.type = type;
	}

	public T getNode() {
		return node;
	}

	public String getType() {
		return type;
	}

}
