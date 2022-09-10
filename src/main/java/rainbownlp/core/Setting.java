package rainbownlp.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import rainbownlp.parser.ParseHandler;


public class Setting {
	public static final String RuleSureCorpus = "RuleSure";
	public static boolean SaveInGetInstance = true;
	static Properties configFile;
	public enum OperationMode
	{
		TRIGGER,
		EDGE,
		ARTIFACT
	}
	public static OperationMode Mode = OperationMode.TRIGGER;
	public static boolean TrainingMode = true;
	// This switch between using Development set or Test set for evaluation, set to true if you want to generate test submission files
	public static boolean ReleaseMode = false;
	public static int NotTriggerNumericValue = 10;
	public st