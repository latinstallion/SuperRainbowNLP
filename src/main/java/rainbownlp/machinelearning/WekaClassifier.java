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
	String[] reinforcedModels = new String[reinforcedCo