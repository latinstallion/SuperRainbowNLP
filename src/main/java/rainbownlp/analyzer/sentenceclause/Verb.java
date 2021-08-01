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
			 is_tobe = tru