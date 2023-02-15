
/**
 * 
 */
package rainbownlp.machinelearning.featurecalculator.link;

import java.util.ArrayList;
import java.util.List;

import rainbownlp.core.Artifact;
import rainbownlp.core.FeatureValuePair;
import rainbownlp.core.Phrase;
import rainbownlp.core.PhraseLink;
import rainbownlp.core.FeatureValuePair.FeatureName;
import rainbownlp.machinelearning.IFeatureCalculator;
import rainbownlp.machinelearning.MLExample;
import rainbownlp.machinelearning.MLExampleFeature;

/**
 * @author ehsan
 * 
 */
public class ConceptsBetweenWords implements IFeatureCalculator {
	public static void main(String[] args) throws Exception
	{
//		List<MLExample> trainExamples = 
//		MLExample.getAllExamples(LinkExampleBuilder.ExperimentGroupTimexEvent, true);
//		List<MLExample> trainExamples2 = 
//			MLExample.getAllExamples(LinkExampleBuilder.ExperimentGroupEventEvent, true);
//		List<MLExample> all_train_examples = new ArrayList<MLExample>();
//		all_train_examples.addAll(trainExamples);
//		all_train_examples.addAll(trainExamples2);