package rainbownlp.tests.unit.core;

import static org.junit.Assert.*;

import org.junit.Test;

import rainbownlp.core.Artifact;
import rainbownlp.core.Artifact.Type;
import rainbownlp.util.HibernateUtil;

public class ArtifactTest   {
	
	@Test
	public void testCreateArtifact() {
		Artifact doc_artifact = Artifact.getInstance(Type.Document);
		assertNotNull(doc_artifact);

		doc_artifact.setContent( "this is test. hello test.");
		
		HibernateUtil.save(doc_artifact);

		assertEquals(doc_artifact.getContent(), "this is test. hello test.");
		assertEquals(doc_artifact.getArtifactType(), Type.Document);
		assertTrue(doc_artifact.getArtifactId()!=-1);
		
		Artifact sentence_artifact = Artifact.getInstance(Type.Sentence);
		sentence_