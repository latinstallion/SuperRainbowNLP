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
	    	FileUtil.log