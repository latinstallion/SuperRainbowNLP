package rainbownlp.machinelearning;

import rainbownlp.util.ConfigurationUtil;

public class SVMMultiClass extends SVMLightBasedLearnerEngine {
	String modelFile;
	String taskName;
	String trainFile;
	String testFile;
	
	private SVMMultiClass()
	{
		
	}

	public static LearnerEngine getLearnerEngine(String pTaskName) {
		SVMMultiClass learnerEngine = new SVMMultiClass();
		learnerEngine.setTaskName(pT