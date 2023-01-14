
package rainbownlp.machinelearning;

import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import rainbownlp.core.FeatureValuePair;
import rainbownlp.util.HibernateUtil;

@Entity
@Table( name = "MLExampleFeature" )
public class MLExampleFeature {
	private Integer exampleFeatureId;
	
	private MLExample relatedExample;
	
	private FeatureValuePair featureValuePair;
	
	public MLExampleFeature()
	{
		
	}
	
	public void setExampleFeatureId(Integer _artifactFeatureId) {
		this.exampleFeatureId = _artifactFeatureId;
	}
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Integer getExampleFeatureId() {
		return exampleFeatureId;
	}
	
	
	public void setRelatedExample(MLExample relatedExample) {
		this.relatedExample = relatedExample;
	}
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="relatedExample")
	public MLExample getRelatedExample() {
		return relatedExample;
	}
	
	
	public void setFeatureValuePair(FeatureValuePair _featureValuePair) {
		featureValuePair = _featureValuePair;
	}
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="featureValuePair")
	public FeatureValuePair getFeatureValuePair() {
		return featureValuePair;
	}

	public synchronized static MLExampleFeature setFeatureExample(MLExample pExample,
			FeatureValuePair pNewFeature) {


		if(pNewFeature.getFeatureValueAuxiliary()!=null)
			//is multi value
			deleteExampleFeatures(pExample, pNewFeature.getFeatureName()
					,pNewFeature.getFeatureValue());
		else
			deleteExampleFeatures(pExample, pNewFeature.getFeatureName());
			
		
		MLExampleFeature artifact_feature = new MLExampleFeature();
		artifact_feature.setFeatureValuePair(pNewFeature);
		artifact_feature.setRelatedExample(pExample);
		
		
		Session session = HibernateUtil.sessionFactory.openSession();
		
		HibernateUtil.save(artifact_feature, session);
		
		session.clear();