package rainbownlp.preprocess;

import java.util.List;

import rainbownlp.core.Artifact;
import rainbownlp.core.RainbowEngine.DatasetType;

public abstract class DocumentAnalyzer {
	static public enum InputType {
		TextFiles
	}
	/**
	 * Process given documents and load them into Artifact table
	 * @param rootPath root of al