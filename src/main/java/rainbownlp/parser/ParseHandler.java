package rainbownlp.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.uima.tokenize.WhitespaceTokenizer;
import rainbownlp.core.Artifact;
import rainbownlp.util.HibernateUtil;
//This class will read all the training sentences and parse them and put penn tree and dependency and POS in the databse
import rainbownlp.util.ConfigurationUtil;

public class ParseHandler {
	public ArrayList<WordTag> sentenceWords = new ArrayList<WordTag>();
	POSModel posModel;
	POSTaggerME tagger;
	ChunkerME chunkerME;

	public ParseHandler() throws  IOException
	{
		POSModel posModel = new POSModelLoader()
			.load(new File(ConfigurationUtil.getResourcePath("en-pos-maxent.bin")));
		tagger = new POSTaggerME(posModel);
//		// chunker
		InputStream is = ConfigurationUtil.getResourceStream("en-chunker.bin");
		ChunkerModel cModel = new ChunkerModel(is);
 
		chunkerME = new ChunkerME(cModel);
	}
	public static StanfordParser s_parser = new StanfordParser();
	public static void main(String[] args) throws Exception
	{

		
////		StanfordParser s_parser = new StanfordParser();
//		//get all sentence artifact
		List<Artif