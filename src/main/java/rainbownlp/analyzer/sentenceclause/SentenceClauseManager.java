
package rainbownlp.analyzer.sentenceclause;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rainbownlp.core.Artifact;
import rainbownlp.parser.DependencyLine;
import rainbownlp.parser.StanfordParser;
import rainbownlp.util.StanfordDependencyUtil;


public class SentenceClauseManager {

	private Artifact relatedSentence;
	private String sentContent;
	
	private String posTags;
	private String stanDependenciesStr;
	
	public ArrayList<DependencyLine> sentDepLines = new ArrayList<DependencyLine>();

	ArrayList<Clause> clauses;

	public HashMap<Integer, String> offsetMap = new HashMap<Integer, String>();
	
	//this keeps the offsets as the key and the value is the lemma
	public HashMap<Integer, String> lemmaMap = new HashMap<Integer, String>();
	
	// the same as above just has all the original tokens
	public HashMap<Integer, String> tokenMap = new HashMap<Integer, String>();
	
	//the mapping of lemmas to originals
	//TODO: if we have repeated that are different originals it will be overwritten
	public HashMap<String, String> lemmaTokenMap = new HashMap<String, String>();
	
	// this hash keep the location of each observed offset in the sentence
	public HashMap<Integer, Clause> clauseMap = new HashMap<Integer, Clause>();
	//////////////////////////////////////
	//this array will keep all the lines that the governor or dependent clause could not be resolved
	ArrayList<DependencyLine> phrases = new ArrayList<DependencyLine>();
	public String filename;
	public String[] normalized_dependencies;
	public ArrayList<String> getPhrases()
	{
		ArrayList<String> phrase_strings = new ArrayList<String>();
		for (DependencyLine depLine:phrases)
		{
			if (depLine.firstOffset<depLine.secondOffset)
			{
				phrase_strings.add(depLine.firstPart+" "+depLine.secondPart);
			}
			else
			{
				phrase_strings.add(depLine.secondPart+" "+depLine.firstPart);
			}
		}
		return phrase_strings;
	}
	
	public SentenceClauseManager(Artifact relatedSentence) throws Exception
	{
		setRelatedSentence(relatedSentence);
		setSentContent(relatedSentence.getContent());
		setPosTags(relatedSentence.getPOS());
		loadClauses();
	}

	
	void loadClauses() throws Exception
	{
		
		if (relatedSentence.getPOS() ==null)
		{
			StanfordParser s_parser = new StanfordParser();
			s_parser.parse(sentContent);
			setPosTags(s_parser.getTagged());
			setStanDependenciesStr(s_parser.getDependencies());
		
		}
		else
		{
			setPosTags(relatedSentence.getPOS());
			setStanDependenciesStr(relatedSentence.getStanDependency());
		}
		
		tokenMap = StanfordDependencyUtil.getTokens(posTags);

		//populate lemma
		lemmaMap = StanfordDependencyUtil.getLemmaMap(posTags);

		lemmaTokenMap = StanfordDependencyUtil.getLemmaTokenmaps(posTags);
		
		analyzeSentence();
	}
	
//	TODO: generally improve this method, it is not perfect
private void analyzeSentence() throws Exception {
		
		sentDepLines =StanfordDependencyUtil.parseDepLinesFromString(getStanDependenciesStr());
		clauses = new ArrayList<Clause>();
		Clause curClause = new Clause();
		
		ArrayList<DependencyLine> toBeProcessesd = sentDepLines;

		for(int i=0; i<sentDepLines.size();i++)
		{
			DependencyLine curLine = sentDepLines.get(i);
			
			if(curLine.relationName == null) continue;
			offsetMap.put(curLine.firstOffset, curLine.firstPart);
			offsetMap.put(curLine.secondOffset, curLine.secondPart);
			
			if(curLine.relationName.equals("nsubj") || curLine.relationName.equals("xsubj"))
			{
//				if (curLine.firstOffset -curLine.secondOffset>10)
//					continue;
				Clause governor_cl = clauseMap.get(curLine.firstOffset);
				Artifact related_word = relatedSentence.getChildByWordIndex(curLine.firstOffset-1);
				String pos = related_word.getPOS();
				
				//if the verb is already observed
				if (governor_cl !=null)
				{
					governor_cl.clauseSubject.add(curLine);
					clauseMap.put(curLine.secondOffset, governor_cl);
				}
				else
				{
					governor_cl = new Clause();

					// subj and verb will be added to the new clause
					governor_cl.clauseSubject.add(curLine);
					
					if (pos!= null && (pos.startsWith("VB") || pos.startsWith("MD")))
					{
						governor_cl.clauseVerb.verbMainPart = curLine.firstPart;
						governor_cl.clauseVerb.offset = curLine.firstOffset;
						clauseMap.put(curLine.firstOffset, governor_cl);
						clauseMap.put(curLine.secondOffset, governor_cl);
						
					}
					//TODO: process more
					else if(pos!= null && (pos.startsWith("JJ") || pos.startsWith("NN")))
					{
						//if the relation cop also is present where the first part is the complement
						boolean is_comp = false;
						for (DependencyLine d:sentDepLines)
						{
							if (d.relationName.equals("cop") && d.firstOffset==curLine.firstOffset)
								is_comp = true;
						}
						if (is_comp==true)
						{
							governor_cl.complement = curLine.firstPart;
							governor_cl.complementOffset = curLine.firstOffset;
							clauseMap.put(curLine.firstOffset, governor_cl);
							clauseMap.put(curLine.secondOffset, governor_cl);
						}

						
					}
	
					
				}
				//get all dep lines that are related to this

			}
			if(curLine.relationName.equals("dobj")|| 
					curLine.relationName.equals("iobj")||
					curLine.relationName.equals("nsubjpass"))
			{
		
				Clause governor_cl = getGovernorVerbOrComplement(curLine);
				
//				String dep_tag = getPOSTag(curLine.secondOffset);
//				if (dep_tag != null && dep_tag.startsWith("JJ"))
//				{
//					governor_cl.complement = curLine.secondPart;
//					governor_cl.complementOffset= curLine.secondOffset;
//					
//				}
//				else
//				{
					SentenceObject new_object = new SentenceObject();
					new_object.content = curLine.secondPart;
					new_object.contentOffset = curLine.secondOffset;
					governor_cl.clauseObject.add(new_object);

//				}
		
				clauseMap.put(curLine.secondOffset, governor_cl);
			}
	
			if(curLine.relationName.equals("cop"))
			{
				Clause governor = clauseMap.get(curLine.firstOffset);
				Clause dependent = clauseMap.get(curLine.secondOffset);
				
				if (governor != null ||dependent != null)
						
				{
					// it means that we have observed the verb 
					if (dependent != null && governor == null)
					{
						dependent.complement = curLine.firstPart;
						dependent.complementOffset = curLine.firstOffset;
						
						clauseMap.put(curLine.firstOffset, dependent);
					}
					else if(governor != null)
					{
						governor.clauseVerb.verbMainPart = curLine.secondPart;
						governor.clauseVerb.offset = curLine.secondOffset;
						clauseMap.put(curLine.secondOffset, governor);
					}
		
				}
				//we should add the verb and the complement
				else
				{
					curClause = new Clause();
					// complement and verb will be added to the new clause
					curClause.complement =curLine.firstPart;
					curClause.complementOffset = curLine.firstOffset;
					
					curClause.clauseVerb.verbMainPart = curLine.secondPart;
					curClause.clauseVerb.offset = curLine.secondOffset;