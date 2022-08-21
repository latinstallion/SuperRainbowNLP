package rainbownlp.core;

import java.util.List;

import rainbownlp.analyzer.evaluation.ICrossfoldValidator;
import rainbownlp.analyzer.evaluation.IEvaluationResult;
import rainbownlp.analyzer.evaluation.classification.Evaluator;
import rainbownlp.machinelearning.IMLExampleBuilder;
import rainbownlp.machinelearning.LearnerEngine;
import rainbownlp.machinelearning.MLExample;
import rainbownlp.preprocess.DocumentAnalyzer.InputType;
import rainbownlp.preprocess.SimpleDocumentLoader;

/**
 * This includes several sugar methods to make using RNLP easier 
 * @author eemadzadeh
 *
 */
public class RainbowEngine {
	List<Artifact> documentsInPipe = null;
	List<MLExample> trainExamplesInPipe = null;
	List<MLExample> testExamplesInPipe = null;
	public static enum DatasetType{
		TRAIN_SET,
		TEST_SET
	}
	/**
	 * Load the input data
	 * @param inputRootPath
	 * @param inputType
	 * @return
	 */
	public RainbowEngine readInput(String inputRootPath, InputType inputType, DatasetType datasetType){
		switch (inputType) {
			case TextFiles:
				SimpleDocumentLoader loader = new SimpleDocumentLoader();
				loader.setDatasetType(datasetType);
				