package rainbownlp.analyzer.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rainbownlp.core.FeatureValuePair;
import rainbownlp.machinelearning.MLExample;
import rainbownlp.machinelearning.convertor.SVMLightFormatConvertor;

public class FeatureEvaluator {
	public void evaluateFeatures(ICrossfoldValidator cfV