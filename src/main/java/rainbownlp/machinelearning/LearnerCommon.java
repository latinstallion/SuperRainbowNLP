package rainbownlp.machinelearning;

import java.sql.SQLException;

import rainbownlp.util.ConfigurationUtil;
import rainbownlp.util.ConfigurationUtil.OperationMode;

public class LearnerCommon {

	public static void includeExamples(String updateTo) throws SQLException {
//		if(Setting.Mode==OperationMode.EDGE)
//		{
//			RelationExampleTable.setTestAsTrain();
//			RelationExampleTable.include(updateTo);
//		}
//		if(Setting.Mode==OperationMode.TRIGGER)
//		{
//			ArtifactExampleTable.setTest