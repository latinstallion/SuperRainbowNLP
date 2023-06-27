package rainbownlp.tests.unit.core;

import static org.junit.Assert.*;

import org.junit.Test;

import rainbownlp.core.Artifact;
import rainbownlp.core.Artifact.Type;
import rainbownlp.core.FeatureValuePair.FeatureName;
import rainbownlp.core.FeatureValuePair;
import rainbownlp.machinelearning.MLExample;
import rainbownlp.machinelearning.MLExampleFeature;
import rainbownlp.util.HibernateUtil;

public class MLExampleFeatureTest   {
	
	@Test
	public void testCreateArtifact() {
		Artifact doc_artifact = Artifact.getInstance(Type.Document);
		doc_artifact.setContent( "this is test. hello test.");
		HibernateUtil.save(doc_artifact);

		FeatureValuePair feature1 = 
			FeatureValuePair.getInstance(FeatureName.TWOGram, "test_test");
		
		assertNotNull(feature1);
		assert