
package rainbownlp.machinelearning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import rainbownlp.machinelearning.convertor.SVMLightFormatConvertor;
import rainbownlp.util.ConfigurationUtil;
import rainbownlp.util.HibernateUtil;
import rainbownlp.util.SystemUtil;


public abstract class SVMLightBasedLearnerEngine extends LearnerEngine {

	
	public void train(List<MLExample> pTrainExamples) throws Exception {
		ConfigurationUtil.TrainingMode = true;
		HibernateUtil.clearLoaderSession();
		
		//This part added since the session was so slow
		List<Integer> train_example_ids = new ArrayList<Integer>();
		for(MLExample example : pTrainExamples)
		{
			train_example_ids.add(example.getExampleId());
		}
		if(isBinaryClassification())
			trainFile = SVMLightFormatConvertor.writeToFileBinary(train_example_ids, taskName);
		else
			trainFile = SVMLightFormatConvertor.writeToFile(train_example_ids, taskName);

		String myShellScript = getTrainCommand();

		SystemUtil.runShellCommand(myShellScript);
	}
	
	public void test(List<MLExample> pTestExamples) throws Exception{
		File model = new File(getModelFilePath());
		if(!model.exists()) {
			throw(new Exception("Model file is missing, train before test: "+modelFile));
		}
		
		ConfigurationUtil.TrainingMode = false;
		List<Integer> test_example_ids = new ArrayList<Integer>();
		String exampleids = "";
		for(MLExample example : pTestExamples)
		{
			exampleids = exampleids.concat(","+example.getExampleId());
			test_example_ids.add(example.getExampleId());
		}
			
		exampleids = exampleids.replaceFirst(",", "");
		String resetQuery = "update MLExample set predictedClass = -1 where exampleId in ("+ exampleids +")";
		HibernateUtil.executeNonReader(resetQuery, true);
					
		String resultFile = modelFile+"_result.txt";
		
		if(isBinaryClassification())
			testFile = SVMLightFormatConvertor.writeToFileBinary(test_example_ids, taskName);
		else 
			testFile = SVMLightFormatConvertor.writeToFile(test_example_ids, taskName);