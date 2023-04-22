
package rainbownlp.machinelearning.featurecalculator.sentence;

import rainbownlp.core.Artifact;
import rainbownlp.core.Artifact.Type;
import rainbownlp.core.FeatureValuePair;
import rainbownlp.core.PhraseLink;
import rainbownlp.machinelearning.IFeatureCalculator;
import rainbownlp.machinelearning.MLExample;
import rainbownlp.machinelearning.MLExampleFeature;
import rainbownlp.util.StringUtil;

public class SentenceNGram implements IFeatureCalculator {

	@Override
	public void calculateFeatures(MLExample exampleToProcess) {
		if(exampleToProcess.getRelatedPhrase() == null)
		{
			PhraseLink sentencesLink = exampleToProcess.getRelatedPhraseLink();
			Artifact firstSentence = sentencesLink.getFirstPhrase().getStartArtifact();
			Artifact secondSentence = sentencesLink.getSecondPhrase().getStartArtifact();
			calculateSentenceNGram(1, firstSentence, exampleToProcess, "FirstSentence1Gram");
			calculateSentenceNGram(1, secondSentence, exampleToProcess, "SecondSentence1Gram");
			calculateSentenceNGram(2, firstSentence, exampleToProcess, "FirstSentence2Gram");
			calculateSentenceNGram(2, secondSentence, exampleToProcess, "SecondSentence2Gram");
		}else
		{
			Artifact sentence = exampleToProcess.getRelatedPhrase().getStartArtifact();
			if(sentence.getArtifactType() ==  Type.Sentence)
			{
				calculateSentenceNGram(1, sentence, exampleToProcess, "Sentence1Gram");
				calculateSentenceNGram(2, sentence, exampleToProcess, "Sentence2Gram");
			}
		}