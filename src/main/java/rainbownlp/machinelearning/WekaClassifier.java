package rainbownlp.machinelearning;

import java.util.ArrayList;
import java.util.List;

import rainbownlp.machinelearning.convertor.WekaFormatConvertor;
import rainbownlp.util.ConfigurationUtil;
import rainbownlp.util.HibernateUtil;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaClassifier extends LearnerEngine {
	String modelFile;
	String taskName;
	String trainFile;
	String testFile;
	
	int reinforcedCount = 0;
	String[] reinforcedModels = new String[reinforcedCount];
	public Classifier wekaAlgorithm = new NaiveBayes();
	public String[] options = null;
	public String wekaAlgorithmName = "NaiveBayes";
	private WekaClassifier()
	{
		
	}

	@Override
	public void train(List<MLExample> pTrainExamples) throws Exception {
		ConfigurationUtil.TrainingMode = true;
		setPaths();

		//This part added since the session was so slow
		List<Integer> train_example_ids = new ArrayList<Integer>();
		for(MLExample example : pTrainExamples)
		{
			train_example_ids.add(example.getExampleId());
		}
		WekaFormatConvertor.writeToFile(train_example_ids, trainFile,taskName, new String[]{"1", "2"});

		DataSource source = new DataSource(trainFile);
		Instances data = source.getDataSet();
		// setting class attribute if the data format does not provide this infor