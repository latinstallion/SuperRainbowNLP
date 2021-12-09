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
	private Artifact go