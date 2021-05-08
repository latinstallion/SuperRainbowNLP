package rainbownlp.analyzer.evaluation.classification;

import java.util.ArrayList;
import java.util.List;
import rainbownlp.machinelearning.MLExample;
import rainbownlp.util.SystemUtil;


public class Evaluator {
	public static boolean saveResult = false;
	public static String evaluation_mode = "HybridTest";

	public static ResultRow evaluateByClass(List<MLExample> pExamplesToTest, 
			String exampleClassToEvaluate)
	{
		ResultRow rr = new ResultRow();
		
		for(MLExample example : pExamplesToTest)
		{
			String expected_class = example.getExpectedClass();
			String predicted_class = example.getPredictedClass();
			if(exampleClassToEvaluate.equals(expected_class))
			{
				if(expected_class.equals(predicted_class))
					rr.TP++;
				else
					rr.FN++;
			}
			else
			{
				if(exampleClassToEvaluate.equals(predicted_class))
					rr.FP++;
				else
					rr.TN++;
			}
		}
		
		return rr;
	}

	public static void evaluateDevelopementResult(String resultsRoot)
	{
		SystemUtil.runShellCommand("a2-evaluate.pl -g gold-devel "+res