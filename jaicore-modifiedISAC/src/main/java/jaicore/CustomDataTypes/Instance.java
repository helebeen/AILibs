package jaicore.CustomDataTypes;

public class Instance<I> {
	/**
	 * @author Helen Beierling
	 *
	 * @param <I> stands for the observed instance
	 */
		private I instance;
		
		public Instance() {}
		public Instance(I inst) {
			this.instance = inst;
		}
		
		public I getInstance() {
			return instance;
		}
		public void setInstance(I newinstance) {
			this.instance = newinstance;
		}
		public boolean isEmpty() {
			return instance != null;
		}

}

