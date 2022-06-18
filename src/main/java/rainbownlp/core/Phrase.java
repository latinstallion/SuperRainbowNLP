package rainbownlp.core;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

import rainbownlp.util.FileUtil;
import rainbownlp.util.HibernateUtil;

@Entity
@Table( name = "Phrase" )
@Inheritance(strategy=InheritanceType.JOINED)
public class Phrase implements Serializable  {
	private String phraseContent;
	
	private Artifact startArtifact;
	private Artifact endArtifact;
	
	private int phraseId;
	
	private String phraseEntityType;
	private String altID;
	private int startCharOffset;
	private int endCharOffset;
	private Artifact headArtifact;
	
	private Integer altLineIndex;
	private Integer altStartWordIndex;
	private Integer altEndWordIndex;
	private String normalizedHead;
	private Integer normalOffset;
	private Artifact govVerb;
	
	public Phrase()
	{
		
	}
	/**
	 * Loads Phrase by id
	 * @param pPhraseID
	 * @return
	 */
	public static Phrase getInstance(int pPhraseID) {
		String hql = "from Phrase where phraseId = "+pPhraseID;
		Phrase phrase_obj = 
			(Phrase)HibernateUtil.executeReader(hql).get(0);
		return phrase_obj;
	}

	
	
	/**
	 * Loads or creates the Phrase
	 * @param pPhraseContent
	 * @param pFilePath
	 * @param pStartIndex
	 * @return
	 */
	public static Phrase getInstance(String pPhraseContent, 
			Artifact pStartArtifact, Artifact pEndArtifact){
		String hql = "from Phrase where phraseContent = :phraseContent "+
			" and startArtifact= :startArtifact and endArtifact = :endArtifact ";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("phraseContent", pPhraseContent);
		params.put("startArtifact", pStartArtifact.getArtifactId());
		params.put("endArtifact", pEndArtifact.getArtifactId());
		
		List<Phrase> phrase_objects = 
				(List<Phrase>) HibernateUtil.executeReader(hql, params);
	    
		Phrase phrase_obj;
	    if(phrase_objects.size()==0)
	    {
	    	phrase_obj = new Phrase();
	    	phrase_obj.setPhraseContent(pPhraseContent);
	    	phrase_obj.setStartArtifact(pStartArtifact);
	    	phrase_obj.setEndArtifact(pEndArtifact);
	    	
	    	HibernateUtil.save(phrase_obj);
	    	FileUtil.logLine("/tmp/NonExistingPhrase.txt","pPhraseContent"+pPhraseContent
	    			+" pStartArtifact "+pStartArtifact.getArtifactId()+ " pEndArtifact "+pEndArtifact.getArtifactId());
	    }else
	    {
	    	phrase_obj = 
	    		phrase_objects.get(0);
	    }
	    return phrase_obj;
	}
	//This is for the MADEUP discharge times NOT TO BE USED dor other purpose
	public static Phrase getMadeUpInstance(String pPhraseContent, String altId,String PhraseType){
		String hql = "from Phrase where phraseContent = :phraseContent "+
			" and altID = :paltID and phraseEntityType =:phraseEntityType ";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("phraseContent", pPhraseContent);
		params.put("paltID",altId);
		params.put("phraseEntityType",PhraseType);
		
		List<Phrase> phrase_objects = 
				(List<Phrase>) HibernateUtil.executeReader(hql, params);
	    
		Phrase phrase_obj;
	    if(phrase_objects.size()==0)
	    {
	    	phrase_obj = new Phrase();
	    	Artifact start = Artifact.getMadeUpInstance(pPhraseContent);
	    	phrase_obj.setStartArtifact(start);
	    	phrase_obj.setEndArtifact(start);
	    	phrase_obj.setPhraseContent(pPhraseContent);
	    	phrase_obj.setAltID(altId);
	    	phrase_obj.setPhraseEntityType(PhraseType);
	    	
	    	HibernateUtil.save(phrase_obj);
	    	
	    }else
	    {
	    	phrase_obj = 
	    		phrase_objects.get(0);
	    }
	    return phrase_obj;
	}
	public static Phrase getInstance(String pPhraseContent, 
			Artifact pStartArtifact, Artifact pEndArtifact, String pPhraseMentionType,
			String pAlt_id){
		String hql = "from Phrase where phraseContent = :phraseContent "+
			" and startArtifact= :startArtifact and endArtifact = :endArtifact " +
			" and phraseEntityType =:phraseEntityType and altID = :altId";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("phraseContent", pPhraseContent);
		params.put("startArtifact", pStartArtifact.getArtifactId());
		params.put("endArtifact", pEndArtifact.getArtifactId());
		params.put("phraseEntityType", pPhraseMentionType);
		params.put("altId", pAlt_id);
		
		List<Phrase> phrase_objects = 
				(List<Phrase>) HibernateUtil.executeReader(hql, params);
	    
		Phrase phrase_obj;
	    if(phrase_objects.size()==0)
	    {
	    	phrase_obj = new Phrase();
	    	phrase_obj.setPhraseContent(pPhraseContent);
	    	phrase_obj.setStartArtifact(pStartArtifact);
	    	phrase_obj.setEndArtifact(pEndArtifact);
	    	phrase_obj.setPhraseEntityType(pPhraseMentionType);
	    	HibernateUtil.save(phrase_obj);
	    	FileUtil.logLine("/tmp/NonExistingPhrase.txt","pPhraseContent"+pPhraseContent
	    			+" pStartArtifact "+pStartArtifact.getArtifactId()+ " pEndArtifact "+pEndArtifact.getArtifactId());
	    }else
	    {
	    	phrase_obj = 
	    		phrase_objects.get(0);
	    }
	    return phrase_obj;
	}
	public static Phrase getInstance(String pPhraseContent, 
			Artifact pStartArtifact, Artifact pEndArtifact, String pPhraseMentionType){
		String hql = "from Phrase where phraseContent = :phraseContent "+
			" and startArtifact"+ ((pStartArtifact!=null)?"= :startArtifact":" is null")
				+" and endArtifact"+ ((pEndArtifact!=null)?"= :endArtifact":" is null")
				+" and phraseEntityType =:phraseEntityType ";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("phraseContent", pPhraseContent);
		if(pStartArtifact!=null)
			params.put("startArtifact", pStartArtifact.getArtifactId());
		if(pEndArtifact!=null)
			params.put("endArtifact", pEndArtifact.getArtifactId());
		params.put("phraseEntityType", pPhraseMentionType);
		
		List<Phrase> phrase_objects = 
				(List<Phrase>) HibernateUtil.executeReader(hql, params);
	    
		Phrase phrase_obj;
	    if(phrase_objects.size()==0)
	    {
	    	phrase_obj = new Phrase();
	    	phrase_obj.setPhraseContent(pPhraseContent);
	    	phrase_obj.setStartArtifact(pStartArtifact);
	    	phrase_obj.setEndArtifact(pEndArtifact);
	    	phrase_obj.setPhraseEntityType(pPhraseMentionType);
	    	HibernateUtil.save(phrase_obj);
	    	FileUtil.logLine("/tmp/NonExistingPhrase.txt","pPhraseContent"+pPhraseContent
	    			+" pStartArtifact "+((pStartArtifact!=null)?pStartArtifact.getArtifactId():"null")+ 
	    			" pEndArtifact "+((pEndArtifact!=null)?pEndArtifact.getArtifactId():"null"));
	    }else
	    {
	    	phrase_obj = 
	    		phrase_objects.get(0);
	    }
	    return phrase_obj;
	}
	public void setPhraseContent(String content) {
		this.phraseContent = content;
	}
	@Index(name = "phraseContent")
	@Column(name = "phraseContent", nullable = false, length = 1000)
	public String getPhraseContent() {
		if(phraseContent==null) return "";
		return phraseContent;
	}
	
	
	
	public void setStartArtifact(Artifact startArtifact) {
		this.startArtifact = startArtifact;
	}
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
	@JoinColumn(name="startArtifact")
	public Artifact getStartArtifact() {
		return startArtifact;
	}
	public void setEndArtifact(Artifact endArtifactId) {
		this.endArtifact = endArtifactId;
	}
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch=FetchType.LAZY)
	@JoinColumn(name="endArtifact")
	public Artifact getEndArtifact() {
		return endArtifact;
	}
	
	public void setPhraseId(int phraseId) {
		this.phraseId = phraseId;
	}
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getPhraseId() {
		return phraseId;
	}
//	public void setAssociatedFilePath(String _associatedFilePath) {
//		this.associatedFilePath = _associatedFilePath;
//	}
//
//	public String getAssociatedFilePath() {
//		return associatedFilePath;
//	}
	public void setPhraseEntityType(String phraseEntityType) {
		this.phraseEntityType = phraseEntityType;
	}
	public String getPhraseEntityType() {
		return phraseEntityType;
	}
	public static Phrase findInstance(String pFilePath, 
			int pStartLineOffset, int pStartWordOffset,
			int pEndLineOffset, int pEndWordOffset){
		Artifact startArtifcat =
				Artifact.findInstance(Artifact.Type.Word, pFilePath, pStartWordOffset, pStartLineOffset);
		
		Artifact endArtifcat =
			Artifact.findInstance(Artifact.Type.Word, pFilePath, pEndWordOffset, pEndLineOffset);
		
		String hql = "from Phrase where startArtifact =" +
			":startArtifact and endArtifact= :endArtifact";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("startArtifact", startArtifcat.getArtifactId());
		params.put("endArtifact", endArtifcat.getArtifactId());
		
		List<Phrase> phrase_objects = 
				(List<Phrase>) HibernateUtil.executeReader(hql, params);
	    
	    
		Phrase phrase_obj=null;
	    if(phrase_objects.size()!=0)
	    {
	    	phrase_obj = 
	    		phrase_objects.get(0);
	    }
	    return phrase_obj;
	}
	public static Phrase findInstance(String pPartialFileName, 
			String altId){
		
		String hql = "from Phrase where startArtifact.associatedFilePath like" +
			":filePath and altID = :altID";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("filePath", '%'+pPartialFileName+'%');
		params.put("altID", altId);
		
		List<Phrase> phrase_objects = 
				(List<Phrase>) HibernateUtil.executeReader(hql, params);
	    
	    
		Phrase phrase_obj=null;
	    if(phrase_objects.size()!=0)
	    {
	    	phrase_obj = 
	    		phrase_objects.get(0);
	    }
	    return phrase_obj;
	}
	public static Phrase findInstance(Artifact startArtifact, Artifact endArtifact,
			String phraseEntityType){
		
		String hql = "from Phrase where startArtifact = :start" +
			" and endArtifact= :end and PhraseEntityType = :phraseEntityType ";
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("start", startArtifact.getArtifactId());
		params.put("end", endArtifact.getArtifactId());
		params.put("phraseEntityType", phraseEntityType);
		
		List<Phrase> phrase_objects = 
				(List<Phrase>) HibernateUtil.executeReader(hql, params);
	    
	    
		Phrase phrase_obj=null;
	    if(phrase_objects.size()!=0)
	    {
	    	phrase_obj = 
	    		phrase_objects.get(0);
	    }
	    return phrase_obj;
	}
	
//	public static Phrase findInstance(String pFilePath, 
//			int pStartCharIndex,String pContent){
//		
//		String hql = "from Phrase where startArtifact.startIndex =" +
//			":startCharIndex and startArtifact.associatedFilePath = "+
//			":filePath and phraseContent= :phraseContent";
//		HashMap<String, Object> params = new HashMap<String, Object>();
//		params.put("startCharIndex", pStartCharIndex);
//		params.put("phraseContent", pContent);
//		params.put("filePath", pFilePath);
//		
//		
//		List<Phrase> phrase_objects = 
//				(List<Phrase>) HibernateUtil.executeReader(hql, params);
//	    
//	    
//		Phrase phrase_obj=null;
//	    if(phrase_objects.size()!=0)
//	    {
//	    	phrase_obj = 
//	    		phrase_objects.get(0);
//	    }
//	    return phrase_obj;
//	}
	public static Phrase findInstance(Artifact pStartArtifact,String pContent){
		
		String hql = "from Phrase where startArtifact =" +
			":startArtifact and phraseContent= :phraseContent";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("startArtifact", pStartArtifact.getArtifactId());
		params.put("phraseContent", pContent);
		
		
		
		List<Phrase> phrase_objects = 
				(List<Phrase>) HibernateUtil.executeReader(hql, params);
	    
	    
		Phrase phrase_obj=null;
	    if(phrase_objects.size()!=0)
	    {
	    	phrase_obj = 
	    		phrase_objects.get(0);
	    }
	    return phrase_obj;
	}
	public void setAltID(String altID) {
		this.altID = altID;
	}
	public String getAltID() {
		return altID;
	}
	public void setStartCharOffset(int startCharOffset) {
		this.startCharOffset = startCharOffset;
	}
	public int getStartCharOffset() {
		return startCharOffset;
	}
	public void setEndCharOffset(int endCharOffset) {
		this.endCharOffset = endCharOffset;
	}
	public int getEndCharOffset() {
		return endCharOffset;
	}
	
	//This method returns all artifacts that are annotated
	@Transient
	public static ArrayList<Artifact> getAnnotatedWordsInSentence(Artifact sentence)
	{
		List<Phrase> phrase_objects = getPhrasesInSentence(sentence);
		
		ArrayList<Artifact> annotated_artifacts = new ArrayList<Artifact>();
		
		for (Phrase p:phrase_objects)
		{
			Artifact start_artifact = p.getStartArtifact();
			Artifact end_artifact =  p.getEndArtifact();
//			annotated_artifacts.add(start_artifact);
			
			Artifact next_artifact =start_artifact;
			while(next_artifact!=null &&
					!next_artifact.equals(end_artifact))
			{
				annotated_artifacts.add(next_artifact);
				next_artifact = next_artifact.getNextArtifact();
			}
			annotated_artifacts.add(end_artifact);
		}
    
	    return annotated_artifacts;
	}
	
	public static List<Phrase> getPhrasesInSentence(Artifact sentence)
	{
		String hql = "from Phrase p where p.startArtifact.parentArtifact =" +
			":sentId and p.endArtifact.parentArtifact =:sentId";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("sentId", sentence.getArtifactId());
		
		List<Phrase> phrase_objects = 
			(List<Phrase>) HibernateUtil.executeReader(hql, params);
		
		return phrase_objects;
	}
	public static List<Phrase> getOrderedPhrasesInSentence(Artifact sentence)
	{	
		String hql = "from Phrase p where p.startArtifact.parentArtifact =" +
		":sentId and p.endArtifact.parentArtifact =:sentId and phraseEntityType <> 'SECTIME'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("sentId", sentence.getArtifactId());
		
		List<Phrase> phrase_objects = (List<Phrase>) HibernateUtil.executeReader(hql, params);
		Collections.sort(phrase_objects, new phraseComparator());
		
		
		return phrase_objects;
	}
	public static Phrase getNextPhraseInSentence(Phrase p,Artifact sentence)
	{	
		Phrase next_