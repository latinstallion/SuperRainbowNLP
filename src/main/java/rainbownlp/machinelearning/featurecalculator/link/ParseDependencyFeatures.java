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
			
			String rel_prep1 = getRelPrepositionToPhrase(phrase1,dep_lines);
			if (rel_prep1==null)
			{
				rel_prep1="no-preposition";
			}
			
			FeatureValuePair fromPhraserelPrep = FeatureValuePair.getInstance
			(FeatureName.FromPhraseRelPrep, rel_prep1,"1");
		
			MLExampleFeature.setFeatureExample(exampleToProcess, fromPhraserelPrep);
			
			
			//To phrase related prep
			String rel_prep2 = getRelPrepositionToPhrase(phrase2,dep_lines);
			if (rel_prep2==null)
			{
				rel_prep2="no-preposition";
			}
			
			FeatureValuePair toPhraserelPrep = FeatureValuePair.getInstance
			(FeatureName.ToPhraseRelPrep, rel_prep2,"1");
		
		    MLExampleFeature.setFeatureExample(exampleToProcess, toPhraserelPrep);
			
			////////////////////////////////////////////////////////////////
			boolean are_gov_connected = areGovVerbsDirectlyConnected(phrase1, phrase2, clauseManager);
			FeatureValuePair gov_connected_feature = FeatureValuePair.getInstance
			(FeatureName.areGovVerbsConnected, are_gov_connected?"1":"0");
		
		    MLExampleFeature.setFeatureExample(exampleToProcess, gov_connected_feature);
		   ///// ///////////////////////////////////////////////////////////
		    String gov_verb1 = getGovernorVerb(phrase1,clauseManager);
		    String gov_verb2 = getGovernorVerb(phrase2,clauseManager);
		    if (gov_verb1 ==null)
		    {
		    	gov_verb1 ="#notFound#";
		    }
		    if (gov_verb2 ==null)
		    {
		    	gov_verb2 ="#notFound#";
		    }
		   
	    	FeatureValuePair fromGovVerb = FeatureValuePair.getInstance
			(FeatureName.FromPhraseGovVerb, gov_verb1, "1");
		
		    MLExampleFeature.setFeatureExample(exampleToProcess, fromGovVerb);
	    
	 
	    	FeatureValuePair toGovVerb = FeatureValuePair.getInstance
			(FeatureName.ToPhraseGovVerb, gov_verb2, "1");
		
		    MLExampleFeature.setFeatureExample(exampleToProcess, toGovVerb);
		    
		    
		    //get verb ausxilaries
		    List<String> from_verb_auxs = getVerbA