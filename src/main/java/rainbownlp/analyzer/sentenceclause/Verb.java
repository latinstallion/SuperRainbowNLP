package rainbownlp.analyzer.sentenceclause;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rainbownlp.util.StringUtil;


public class Verb {
	
	public String verbMainPart;
	public Integer offset;
	public ArrayList<String> modifiers;
	public Arr