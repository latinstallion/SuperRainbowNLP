/**
 * 
 */
package rainbownlp.machinelearning.featurecalculator.link;

import java.util.List;
import rainbownlp.machinelearning.IFeatureCalculator;
import rainbownlp.machinelearning.MLExample;
import rainbownlp.util.FileUtil;

/**
 * @author ehsan
 * 
 */
public class ConceptsDistance implements IFeatureCalculator {
	public static void main (String[] args) throws Exception
	{
		String experimentgroup = "LinkClassificationEventEvent";
		List<MLExample> 