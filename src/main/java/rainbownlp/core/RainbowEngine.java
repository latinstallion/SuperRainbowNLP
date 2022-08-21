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
public 