package rainbownlp.analyzer.evaluation.classification;

import java.text.DecimalFormat;

public class ResultRow
{
	public int TP = 0;
	public int FP = 0;
	public int TN = 0;
	public int FN = 0;
	public String getReport() {
		String resultStr = "==================\n";
		resultStr += "\nTP : "+TP;
		resultStr += "\nFP : "+FP;
		resultStr += "\nTN : "+TN;
		resultStr += "\nFN : "+FN;
	