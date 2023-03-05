/**
 * 
 */
package rainbownlp.machinelearning.featurecalculator.link;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rainbownlp.analyzer.sentenceclause.Clause;
import rainbownlp.analyzer.sentenceclause.SentenceClauseManager;
import rainbownlp.core.Artifact;
import rainbownlp.core.FeatureValuePair;
import rainbownlp.core.Phrase;
import rainbownlp.core.PhraseLink;
import rainbownlp.core.FeatureValuePair.FeatureName;
import rainbownlp.machinelearning.IFeatureCalculator;
import rainbownlp.machinelearning.MLExample;
import rainbownlp.machinelearning.MLExampleFeature;
import rainbownlp.parser.DependencyLine;
import rainbownlp.util.HibernateUtil;
import rainbownlp.util.StanfordDependencyUtil;
import rainbownlp.util.StringUtil;

/**
 * @author Azadeh
 * 
 */
public class ParseDependencyFeatures implements IFeatureCalculator {
	
	public static void main (String[] args) throws Exception
	{
////		List<MLExample> trainExamples = 
////			MLExample.getAllExamples(LinkExampleBuilder.ExperimentGroupTimexEvent, true);
//		List<MLExample> trainExamples2 = 
//			MLExample.getAllExamples(LinkExampleBuilder.ExperimentGroupEventEve