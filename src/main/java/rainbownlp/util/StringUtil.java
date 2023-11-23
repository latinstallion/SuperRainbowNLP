package rainbownlp.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Properties;

import weka.core.Stopwords;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StringUtil {

	static Properties props = new Properties();
	static StanfordCoreNLP pipeline = null;
	static{
	  props.put("annotators", "lemma"); 
	  pipeline = new StanfordCoreNLP(props, false);
	}
	/**
	 * 
	 * @param inputString
	 * @return MD5 hash of given string
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getStringDigest(String inputString)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(inputString.getBytes(), 0, inputString.length());

		return new BigInteger(1, md.digest()).toString(16);
	}

	/**
	 * Customized definition of stop words for word
	 * @param word
	 * @return
	 */
	public static boolean isStopWord(String word){
		boolean isStopWord = false;
		
		if(word.length() < 2
				||	Stopwords.isStopword(word)
				|| word.matches("\\W+")
				)
			isStopWord = true;
		
		return isStopWord;
	}
	

	
	/**
	 * Porter stem
	 * @param word
	 * @return stemmed word
	 */
	public static String getWordPorterStem(String word)
	{
		PorterStemmer stemmer = new PorterStemmer();
		String stemmed_word = stemmer.stem(word).toLowerCase();
		return stemmed_word;
	}
	public static String prepareSQLString(String sqlString) {
		sqlString = sqlString.replace("\\", "\\\\").
			replace("'", "''").
			replace("%", "\\%").
			replace("_", "\\_");
		return sqlString;
	}

public static String castForRegex(String textContent) {
		
		return textContent.replace("\\","\\\\").replace("/","\\/").replace("*", "\\*").replace("+", "\\+").replace(".", "\\.").replace("?", "\\?")
			.replace(")", "\\)").replace("{", "\\{").replace("}", "\\}")
			.replace("(", "\\(").replace("[", "\\[").replace("]", "\\]").replace("%", "\\%");
	}
public static String decastRegex(String textContent) {
		
		return textContent.replace("\\\\","\\").replace("\\/","/").replace("\\*", "*").replace("\\+", "+").replace("\\.", ".").replace("\\?", "?")
			.replace("\\)", ")").replace("\\_", "_")
			.replace("\\{", "{").replace("\\}", "}").replace("\\(", "(").
			replace("\\[", "[").replace("\\]", "]").replace("\\%", "%");
	}
	
	public static String getTermByTermPorter(String phrase)
	{
		String[] words = phrase.split(" ");
		String rootString = "";
		for(int i=0;i<words.length;i++){
			rootString += StringUtil.getWordPorterStem(words[i])+" ";
		}
		return rootString.trim();
	}
	
	public static String compress(String text) {
		return text.replace(" ", "").replace(" ", "");
	}
	static HashMap<String, String> lemmaCache = new HashMap<String, String>();

	public static String getTermByTermWordnet(String phrase)
	{
//		if(lemmatiser ==null)
//			lemmatiser = new EngLemmatiser("/home/ehsan/rnlp/nlpdata/lemmatiser",
//					true, false);
//		String[] words = phrase.split(" ");
//		String rootString = "";
//		for(int i=0;i<words.length;i++)
//		{
//			String lemma = l