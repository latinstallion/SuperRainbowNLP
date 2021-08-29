package rainbownlp.analyzer.sentenceclause;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rainbownlp.util.StringUtil;


public class Verb {
	
	public String verbMainPart;
	public Integer offset;
	public ArrayList<String> modifiers;
	public ArrayList<String> auxs; 
	public boolean isNegated = false;
	public String prt;
	public boolean isPassive = false;
	
	Integer polarity = null;
	HashMap<String, Integer> iObjHash;
	
	public Verb()
	{
		verbMainPart = new String();
		modifiers= new ArrayList<String> ();
		auxs = new ArrayList<String>();
		offset = new Integer(0);
		prt = new String();
		iObjHash = null;
	}
	public String getPhrasalVerbLemma()
	{
		String p_verb= verbMainPart;
		if (!prt.isEmpty())
		{
			p_verb = StringUtil.getWordLemma(verbMainPart).concat(" "+prt);
		}
		else
		{
			p_verb = StringUtil.getWordLemma(verbMainPart);
		}
		return p_verb;
	}
	 
	 public boolean isDoubted(Clause clause)
	 {
		 boolean is_doubted = false;
		 if (auxs.contains("may") || auxs.contains("should") || auxs.contains("might")
				 || auxs.contains("would") || clause.isMarked )
		 {
			 is_doubted = true;
		 }
		 
		 return is_doubted;
	 }

	
	 public boolean isTOBe()
	 {
		 boolean is_tobe = false;
		 String lemma = StringUtil.getTermByTermWordnet(verbMainPart);
		 if (lemma.equals("be") || lemma.equals("wa"))
		 {
			 is_tobe = true;
		 }
		 return is_tobe;
	 }
	

	public static boolean isToBeVerb(String verb)
	{
		Pattern p = Pattern.compile("(is|am|are|being|been|be|do|does|did|will|was|were|can|could|shall|become|became|has|had|have|would|may|might|must)");
		Matcher m = p.matcher(verb);
		return m.matches();
	}
	
	 public Integer getTense(SentenceClauseManager relatedSentence) {
		 if (verbMainPart.isEmpty())
		 {
			 return 0;
		 }
		 // Tense number can be 1: past 2:present 3:future 4:present+ing 
		 Integer verb_tense_number = 2;
		 if (auxs.contains("will")|| auxs.contains("wo"))
		 {
			 verb_tense_number = 3;
		 }
		 else if(auxs.contains("do") || auxs.contains("have") || auxs.contains("to") ||
				 auxs.contains("am") || auxs.contains("are") || auxs.contains("can"))
		 {
			 verb_tense_number = 2;
		 }
		 else if(auxs.