
package rainbownlp.core;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

import rainbownlp.core.Artifact.Type;
import rainbownlp.util.HibernateUtil;


@Entity
@Table( name = "Artifact" )
public class Artifact {
	String content;
	String generalized_content;
	List<Artifact> childsArtifact;
	Artifact parentArtifact;
	Artifact nextArtifact;
	Artifact previousArtifact;
	Type artifactType;
	private String artifactOptionalCategory;
	
	@Transient
	boolean isNew;
	@Transient
//	List<ArtifactFeature> features = new ArrayList<ArtifactFeature>();
	
	String POS;
	
	// These two variable are lazy and will be initialized in getter
	Integer startIndex = null;
	Integer endIndex = null;

	Integer lineOffset = null;
	Integer wordOffset = null;
	
	String stanDependency =null;
	private String stanPennTree = null;
	
	int artifactId = -1;
	/*
	 * Physical address of file which artifact loaded from that
	 */
	String associatedFilePath;
	

	public enum Type {
		Document, Sentence, Phrase, Word, Passage
	}

	
	public Artifact()
	{
		
	}
	/**
	 * Loads Artifact by id
	 * @param pArtifactID
	 * @return
	 */
	public static Artifact getInstance(int pArtifactID) {
		String hql = "from Artifact where artifactId = "+pArtifactID;
		Artifact artifact_obj = null;
		List<Artifact> artifacts = (List<Artifact>) HibernateUtil.executeReader(hql);
		if(artifacts.size()>0)
			artifact_obj = artifacts.get(0);
		return artifact_obj;
	}
	/**
	 * Creates completely empty Artifact object
	 * @return
	 */
	public static Artifact getInstance(Type pArtifactType) {
		Artifact artifact_obj = new Artifact();
		artifact_obj.setArtifactType(pArtifactType);
		HibernateUtil.save(artifact_obj);
		return artifact_obj;
	}
	
	/**
	 * Loads or creates the Artifact
	 * @param pArtifactType
	 * @param pFilePath
	 * @param pStartIndex
	 * @return
	 */
	public static Artifact getInstance(Type pArtifactType, String pFilePath, 
			int pStartIndex){
		String hql = "from Artifact where artifactType = "+
			pArtifactType.ordinal()+" and associatedFilePath ='" +
			pFilePath + "' and startIndex="+pStartIndex;
		List<Artifact> artifact_objects = 
				(List<Artifact>) HibernateUtil.executeReader(hql);
	    
	    
	    Artifact artifact_obj;
	    if(artifact_objects.size()==0)
	    {
	    	artifact_obj = new Artifact();
	    	artifact_obj.setStartIndex(pStartIndex);
	    	artifact_obj.setAssociatedFilePath(pFilePath);
	    	artifact_obj.setArtifactType(pArtifactType);
	    	HibernateUtil.save(artifact_obj);
	    }else
	    {
	    	artifact_obj = 
				artifact_objects.get(0);
	    }
	    return artifact_obj;
	}
	public static Artifact getMadeUpInstance(String content){
		String hql = "from Artifact where content = '"+content+"'";
		List<Artifact> artifact_objects = 
				(List<Artifact>) HibernateUtil.executeReader(hql);
	    
	    
	    Artifact artifact_obj;
	    if(artifact_objects.size()==0)
	    {
	    	artifact_obj = new Artifact();
	    	artifact_obj.setContent(content);
	    	HibernateUtil.save(artifact_obj);
	    }else
	    {
	    	artifact_obj = 
				artifact_objects.get(0);
	    }
	    return artifact_obj;
	}
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
//	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="parentArtifact")
	public Artifact getParentArtifact()  {
		return parentArtifact;
	}
	public void setParentArtifact(Artifact _parentArtifact) {
		parentArtifact = _parentArtifact;
	}
	
//	@OneToMany(mappedBy="parentArtifact")
//    @OrderBy("startIndex")
	@Transient
	public synchronized List<Artifact>  getChildsArtifact() {
		if(childsArtifact==null && artifactType != Type.Word)
			childsArtifact = 
				(List<Artifact>) HibernateUtil.executeReader( "from Artifact where parentArtifact = "+artifactId+" order by artifactId");

		return childsArtifact;
	}
	

	
	
	@OneToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
//	@OneToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
	@JoinColumn(name="nextArtifact")
    public Artifact getNextArtifact() {
		return nextArtifact;
	}
	public void setNextArtifact(Artifact pNextArtifact){
		nextArtifact = pNextArtifact;
	}
	

	@OneToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
//	@OneToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
	@JoinColumn(name="previousArtifact")
    public Artifact getPreviousArtifact() {
		return previousArtifact;
	}
	public void setPreviousArtifact(Artifact pPreviousArtifact){
		previousArtifact = pPreviousArtifact;
	}
	/**
	 * Return oldest parent of this Artifact which is usually a Document
	 */
	@Transient
	public Artifact grandFather() throws SQLException {
		Artifact grandFather = this;

		while (grandFather.getParentArtifact() != null) {
			grandFather = grandFather.getParentArtifact();
		}

		return grandFather;
	}

	@Index(name = "index_start_index")
	public Integer getStartIndex() {
		if (this.getArtifactType() == Type.Document) {
			startIndex = 0;
		} else if (startIndex == null) {
			int _previousArtifactsLength = 0;
			Artifact previous = this.getPreviousArtifact();
			while (previous != null) {
				_previousArtifactsLength += previous.getContent().length() + 1;
				previous = previous.getPreviousArtifact();
			}
			if(getParentArtifact()!=null)
				startIndex = parentArtifact.getStartIndex()
						+ _previousArtifactsLength + 1;
			
		}
		return startIndex;
	}

	public void setEndIndex(Integer _endIndex) {
		if (_endIndex == null && this.getContent()!=null && 
				!this.getContent().isEmpty()) {
			_endIndex = startIndex + this.getContent().length();
		}
		endIndex = _endIndex;
	}

	@Index(name = "index_end_index")
	public Integer getEndIndex() {
		if (endIndex == null)
		{
			if(getStartIndex()!=null && getContent()!=null)
				endIndex = startIndex + getContent().length();
		}
		
		return endIndex;
	}

	

	public String getPOS()
	{
//		if(POS==null){
////			try {
////				POS = 
////					ArtifactAttributeTable.getAttributeValue(artifactId, 
////							AttributeValuePair.AttributeType.POS.name());
////			} catch (SQLException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//			if(POS==null && artifactType == Type.Word){
//				String contentRegexCasted = StringUtil.castForRegex(getContent());
//				lineOffset = getLineIndex();
//				if(lineOffset==null) return "";
//				String tagged = 
//					StanfordParser.getTagged(associatedFilePath, lineOffset);
//				String[] taggedTokens = 
//					tagged.split(" ");
//				wordOffset = getWordIndex();
//				boolean index_invalid = false;
//				if(wordOffset!=null && 
//						taggedTokens.length>wordOffset)
//				{
//					String[] parts = taggedTokens[wordOffset].replace("\\/", "/"). // '/' is tagger splitter; decast to match					
//							split(contentRegexCasted+"/");
//					if(parts.length==2)
//						POS = parts[1];
//					else
//						index_invalid = true;
//				}
//				else
//					index_invalid= true;
//
//				if(index_invalid)
//				{
//					
//					nextArtifact = getNextArtifact();
//					previousArtifact = getPreviousArtifact();
//					if(previousArtifact != null && nextArtifact!=null)
//					{
//						String preContent=previousArtifact.getContent();
//						String nextContent=nextArtifact.getContent();
//						if(nextContent.equals("-"))
//						{
//							Artifact nextnext = nextArtifact.getNextArtifact();
//							if(nextnext!=null)
//							{
//								contentRegexCasted = 
//									contentRegexCasted+
//									nextContent+
//									nextnext.getContent();
//							}
//						}
//						// to make sure not casted before
////							contentRegexCasted = 
////								Util.castForRegex(Util.decastRegex(contentRegexCasted));
//						
//						Pattern p = Pattern.compile("(.* )?"+contentRegexCasted+"/(\\S+)( .*)?");
//						Pattern p2 = Pattern.compile("(.* )?\\S*"+contentRegexCasted+"\\S*/(\\S+)( .*)?");
//						Matcher m = p.matcher(tagged);
//						if(m.matches())
//						{
//							POS=m.group(2);
////								Util.log("_POS found: "+_POS+
////										" for "+contentRegexCasted, Level.INFO);
//						}else
//						{
//							m = p2.matcher(tagged);
//							if(m.matches())
//							{
//								POS=m.group(2);
////									Util.log("_POS found: "+_POS+
////											" for "+contentRegexCasted, Level.INFO);
//							}
//						}
//						
//					}
//					
//					if(POS==null &&  previousArtifact != null)
//					{
//						POS = previousArtifact.getPOS();
//					}
//					if(POS==null)
//					{
//						POS = getContent();
////						Util.log("Error in _POS: word index = "+_wordOffset+
////							", tagged:"+tagged+", tagged token:"+
////							" "+contentRegexCasted+" -> " 
////							+getContent(), Level.WARNING);
//					}
//				}
//			}
//		}
//		if(getContent().equals("-"))
//			POS = "-";

		
		return POS;
	}
	public void setPOS(String pPOS) {
		POS = pPOS;
	}
	/**
	 * starts from 0
	 * @return
	 */
	@Index(name = "index_word_index")
	public Integer getWordIndex() {
		if(wordOffset==null)
		{
			if(artifactType==Type.Word)
			{
			
				wordOffset=0;
				Artifact preWord = getPreviousArtifact();
				while(preWord!=null)
				{
					wordOffset++;
					preWord = preWord.getPreviousArtifact();
					previousArtifact = null;
				}
			}
			
		}
		return wordOffset;
	}
	
	public void setWordIndex(Integer _wordOffset) {
		wordOffset = _wordOffset;
	}
	public void setLineIndex(Integer _lineIndex) {
		lineOffset = _lineIndex;
	}
	public void setStartIndex(Integer _startIndex) {
		startIndex = _startIndex;
	}
	
	/**
	 * sentence index, starts from 1
	 * @return
	 */
	@Index(name = "index_line_index")
	public Integer getLineIndex() {
		if(lineOffset==null)
		{
			if(artifactType==Type.Word)
			{
				Artifact sentence = getParentArtifact();
				if(sentence==null) 
				{
					nextArtifact = getNextArtifact();
					if(nextArtifact!=null)
						sentence = nextArtifact.getParentArtifact();
				}
				if(sentence==null) 
				{
					previousArtifact = getPreviousArtifact();
					if(previousArtifact!=null)
						sentence = previousArtifact.getParentArtifact();
				}
				if(sentence==null) 
				{
//						Util.log("Error in getLineIndex: word '"+ _artifactId 
//								+"' doesn't have parent!", Level.SEVERE);
						
					return null;
				}
				lineOffset = sentence.getLineIndex();
			}
			if(artifactType==Type.Sentence)
			{
				lineOffset=1;
				Artifact preSentence = getPreviousArtifact();
				while(preSentence!=null)
				{
					lineOffset++;
					preSentence = preSentence.getPreviousArtifact();
				}
			}
			
		}
		return lineOffset;
	}

	public void setContent(String _content) {
		this.content = _content;
	}
	@Column(columnDefinition="TEXT")
	@Index(name = "index_content")
	public String getContent() {
//		if(content==null) {
//			List<Artifact> children = getChildsArtifact();
//			for(Artifact child: children){
//				content+=child.getContent()+" ";
//			}
//			content = content.trim();
//		}
		return content;
	}

	public void setAssociatedFilePath(String _associatedFilePath) {
		this.associatedFilePath = _associatedFilePath;
	}

	public String getAssociatedFilePath() {
		return associatedFilePath;
	}
	
	@Column(nullable = false)
	public void setArtifactType(Type _artifactType) {
		this.artifactType = _artifactType;
	}

	public Type getArtifactType() {
		return artifactType;
	}

	public void setArtifactId(int _artifactId) {
		this.artifactId = _artifactId;
	}
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getArtifactId() {
		return artifactId;
	}
	
	@Override public String toString()
	{
		return associatedFilePath.substring(associatedFilePath.length()-15, 
				associatedFilePath.length()-1) + ":"+
			artifactId+"-"+artifactType.toString()+"-"
			+parentArtifact.artifactId+"-"+
			nextArtifact.artifactId+"-"+
			previousArtifact.artifactId;
	}
	
	@Override public boolean equals(Object pArtifact)
	{
		if(!(pArtifact instanceof Artifact))
			return false;
		Artifact a = (Artifact)pArtifact;