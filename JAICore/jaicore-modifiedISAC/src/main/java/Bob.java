import jaicore.modifiedISAC.ModifiedISACInstanceCollector;
import jaicore.modifiedISAC.Normalizer;


public class Bob {
	public static void main(String[] args) {
		try {
			ModifiedISACInstanceCollector coll = new ModifiedISACInstanceCollector();
			//for(ProblemInstance<Instance> i : coll.getProblemInstances()) {	
			//	System.out.println( i.getInstance());
			//}
			Normalizer norm = new Normalizer(coll.getProblemInstances());
			//norm.setupnormalize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
