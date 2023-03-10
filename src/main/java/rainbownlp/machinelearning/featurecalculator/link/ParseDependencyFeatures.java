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
//			MLExample.getAllExamples(LinkExampleBuilder.ExperimentGroupEventEvent, true,100);
//		List<MLExample> all_train_examples = new ArrayList<MLExample>();
////		all_train_examples.addAll(trainExamples);
//		all_train_examples.addAll(trainExamples2);
//		for (MLExample example:all_train_examples)
//		{
////			if(example.getExampleId() != 4893) continue;
//			ParseDependencyFeatures lbf = new ParseDependencyFeatures();
//			lbf.calculateFeatures(example);
//		}
		
	}
	@Override
	public void calculateFeatures(MLExample exampleToProcess) throws Exception {
			
			PhraseLink phraseLink = exampleToProcess.getRelatedPhraseLink();
			Phrase phrase1 = Phrase.getInstance(phraseLink.getFromPhrase().getPhraseId());
			Phrase phrase2 = Phrase.getInstance(phraseLink.getToPhrase().getPhraseId());
			Artifact parent_sent = phrase1.getStartArtifact().getParentArtifact();
			SentenceClauseManager clauseManager =
					new SentenceClauseManager(parent_sent);
	
			ArrayList<DependencyLine> dep_lines = clauseManager.sentDepLines;
			
			String rel_prep1 = getRelPrepositionToPhrase(phrase1