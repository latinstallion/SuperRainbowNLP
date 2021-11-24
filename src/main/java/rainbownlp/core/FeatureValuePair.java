
/**
 * @author ehsan
 */
package rainbownlp.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import rainbownlp.util.FileUtil;
import rainbownlp.util.HibernateUtil;

/**
 * Store feature and value binded together
 */
@Entity
@Table( name = "FeatureValuePair" )
public class FeatureValuePair {
	public static void main(String[] args){
		FeatureValuePair.resetIndexes();
	}
	public enum FeatureName {
		// Document Features
		JournalTitle,
		CompletedYear,
		CreatedYear,
		RevisedYear,
		MESHHeading,


		// Paragraph Features
		// PositionInDoc,

		// Sentence Features
		ProteinCountInSentence,
		SentenceTFIDF,

		// Words Features
		POS,
		POSNext1,
		POSNext2,
		POSPre1,
		POSPre2,

		PorterStem, 
		WordnetStem, 
		OriginalWord, 
		NameEntity, 
		StartWithUppercase, 
		AllUppercase, 
		AllLowercase, 
		HasSpecialChars, 
		HasDigit, 

		CommaLeftCount,
		CommaRightCount,
		QuoteLeftCount,
		QuoteRightCount,
		ProteinCountInWindow,

		SimilarityToGene_expression, 
		SimilarityToTranscription, 
		SimilarityToProtein_catabolism, 
		SimilarityToLocalization, 
		SimilarityToBinding, 
		SimilarityToPhosphorylation, 
		SimilarityToRegulation, 
		SimilarityToPositive_regulation, 
		SimilarityToNegative_regulation,
		PositionInDoc,

		//coreference features
		AnaphoraIsSubject,
		AntecedentInFirstSubject,
		AntecedentInHeader,
		AntecedentIsSubject,
		Appositive,
		NumberAgreement,
		SentenceDistance, TWOGram, TWOGramBackward, ThreeGram, ThreeGramBackward, NellLink, 
		ProblemCountInSentence, TestCountInSentence, TreatmentCountInSentence, TestsBeforeWord, 
		TreatmentsBeforeWord, ProblemsBeforeWord, ProblemPossibleCountInSentence, ProblemHypoCountInSentence, 
		ProblemConditionalCountInSentence, ProblemAWSECountInSentence, ProblemAbsentCountInSentence, ProblemPresentCountInSentence, EdgeType,
		WordWindowNext, WordWindowPre, EdgeParsePath, EdgeParseDistance, DependencyLinkedTokens,

		TimexCount, ClinicalEventsCount, LinkWordBetween, LinkArgumentType, LinkFromPhrasePolarity, LinkFromPhraseModality, LinkFromPhraseType, LinkToPhraseModality, LinkToPhraseType, 
		LinkToPhrasePolarity, LinkToPhraseTimexMod, LinkFromPhraseTimexMod, 
		InterMentionLocationType, AreDirectlyConnected, HaveCommonGovernors, AreConjunctedAnd,
		//NGrams
		NonNormalizedNGram2, NonNormalizedNGram3, NorBetweenNGram2, NorBetweenNGram3, Link2GramBetween, 
		Link2GramFrom,Link2GramTo,

		//Link Args basic features
		FromPhraseContent, ToPhraseContent, FromPhrasePOS, ToPhrasePOS,

		LinkBetweenWordCount, LinkBetweenPhraseCount, 

		//ParseDependency features
		FromPhraseRelPrep, ToPhraseRelPrep, FromPhraseGovVerb, ToPhraseGovVerb,  FromPhraseGovVerbTense,
		FromPhraseGovVerbAux, toPhraseGovVerbAux, areGovVerbsConnected,
		normalizedDependencies,
		//pattern statistics
		POverlapGivenPattern, PBeforeGivenPattern, PAfterGivenPattern, PNoLinkGivenPattern, hasFeasibleLink, 
		POverlapGivenPatternTTO, PBeforeGivenPatternTTO, PAfterGivenPatternTTO, PNoLinkGivenPatternTTO,
		maxProbClassByPattern,

		ParseTreePath, ParseTreePathSize,


		//sectime features
		relatedSectionInDoc, AdmissionOrDischarge, 
		//normalized 
		fromPrepArg, toPrepArg, isToPhDirectPrepArgOfFromPh, isEventAfterProblem, norToTypeDep,
		fromToToPathExist, toToFromPathExist, fromToToPathSize, toToFromPathSize, customGraphPath,
		//custom graph

		LabeledGraphNorDepPath,  customGraphIndividualPath, customGraphPathString,


		TemporalSignal, FromPhrasePOS1By1, ToPhrasePOS1By1, 
		FromPhrasePOSWindowBefore, ToPhrasePOSWindowBefore,  
		FromPhrasePOSWindowAfter, ToPhrasePOSWindowAfter, 
		ToPhrasePOSBigramAfter, FromPhrasePOSBigramBefore,
		ToPhrasePOSBigramBefore, FromToPhrasePOSBigram, LinkFromToWordDistance, LinkPOSBetween,


		betweenChunck,




	}

	String featureName;
	String featureValue;

	//For string multi features this can be used to handle real value 
	// othewise 1 or 0 would be used for values(for tf_idfs)
	private String featureValueAuxiliary;

	private int featureValuePairId = -1;

	//reset every time used for training a model;-1 means not used in training
	private int tempFeatureIndex = -1;



	public int getTempFeatureIndex() {
		return tempFeatureIndex;
	}
	public void setTempFeatureIndex(int tempFeatureIndex) {
		this.tempFeatureIndex = tempFeatureIndex;
	}
	public void setFeatureName(String _featureName) {
		featureName = _featureName;
	}
	@NaturalId
	public String getFeatureName() {
		return featureName;
	}


	public void setFeatureValue(String _featureValue) {
		featureValue = _featureValue;
	}
	@NaturalId
	public String getFeatureValue() {
		return featureValue;
	}

	public FeatureValuePair()
	{

	}



	public void setFeatureValuePairId(int featureValuePairId) {
		this.featureValuePairId = featureValuePairId;
	}
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getFeatureValuePairId() {
		return featureValuePairId;
	}


	void setFeatureValueAuxiliary(String featureValueAuxiliary) {
		this.featureValueAuxiliary = featureValueAuxiliary;
	}
	@NaturalId
	public String getFeatureValueAuxiliary() {
		return featureValueAuxiliary;
	}

	@Override public String toString()
	{
		return featureName+" = "+ featureValue + 
				" ("+featureValueAuxiliary+")";
	}

	@Override public boolean equals(Object pFeatureValuePair)
	{
		if(!(pFeatureValuePair instanceof FeatureValuePair))
			return false;
		FeatureValuePair fvp = (FeatureValuePair)pFeatureValuePair;
		if(fvp.getFeatureValuePairId() == featureValuePairId ||
				(fvp.getFeatureName() == featureName &&
				fvp.getFeatureValue().equals(featureValue)&&
				fvp.getFeatureValueAuxiliary() == featureValueAuxiliary))
			return true;
		else 
			return false;

	}
	@Override public int hashCode()
	{
		return featureValuePairId;
	}

	public static synchronized FeatureValuePair getInstance(String pFeatureName, 
			String pFeatureValue,
			String pFeatureValueAuxiliary)
	{
		FeatureValuePair feature_value;


		String hql = "from FeatureValuePair where featureName= :featureName "+
				" AND featureValue= :featureValue ";

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("featureValue", pFeatureValue);
		params.put("featureName", pFeatureName);

		if(pFeatureValueAuxiliary!=null)
		{
			hql += " AND featureValueAuxiliary= :featureValueAuxiliary ";
			params.put("featureValueAuxiliary", pFeatureValueAuxiliary);
		}

		List<FeatureValuePair> featurev_list = 
				(List<FeatureValuePair>) HibernateUtil.executeReader(hql, params);

		if(featurev_list.size() == 0)
		{
			feature_value = new FeatureValuePair();
			feature_value.setFeatureName(pFeatureName);
			feature_value.setFeatureValue(pFeatureValue);
			if(pFeatureValueAuxiliary!=null)
				feature_value.setFeatureValueAuxiliary(pFeatureValueAuxiliary);
			HibernateUtil.save(feature_value);
		}else
			feature_value = featurev_list.get(0);
		return feature_value;
	}

	public static FeatureValuePair getInstance(String pFeatureName, 
			String pFeatureValue)
	{

		return getInstance(pFeatureName, pFeatureValue, null);
	}

	public static List<String> multiValueFeatures = new ArrayList<String>();
	static
	{
		multiValueFeatures.add(FeatureName.MESHHeading.name());
		multiValueFeatures.add(FeatureName.SentenceTFIDF.name());
		multiValueFeatures.add(FeatureName.LinkWordBetween.name());