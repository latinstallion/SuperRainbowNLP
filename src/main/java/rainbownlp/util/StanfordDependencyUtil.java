
package rainbownlp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rainbownlp.core.Phrase;
import rainbownlp.parser.DependencyLine;


public class StanfordDependencyUtil {
	public static Boolean haveDirectRelation(String target1, String target2,
			ArrayList<DependencyLine> dependencies)
	{
		Boolean has_relation = false;
		for (DependencyLine dep_line:dependencies)
		{
//			TODO: remove
//			if (target1==null || target2==null)
//				continue;
			if ((dep_line.firstPart.equals(target1) &&
					dep_line.secondPart.equals(target2))
					|| (dep_line.firstPart.equals(target2) &&
							dep_line.secondPart.equals(target1)))
			{
				has_relation =true;
				break;
			}
				
		}
		return has_relation;
	}
	public static Boolean haveDirectRelation(Phrase p1, Phrase p2,
			ArrayList<DependencyLine> dependencies)
	{
		Boolean has_relation = false;
		String target1 = p1.getNormalizedHead();
		String target2 = p2.getNormalizedHead();
		Integer offset1 = p1.getNormalOffset()+1;
		Integer offset2 = p2.getNormalOffset()+1;
		for (DependencyLine dep_line:dependencies)
		{
			if ((dep_line.firstPart.equals(target1) &&
					dep_line.firstOffset==offset1 &&
					dep_line.secondPart.equals(target2)
					&& dep_line.secondOffset == offset2)
					|| (dep_line.firstPart.equals(target2) &&
						dep_line.firstOffset == offset2 &&
						dep_line.secondOffset ==offset1 &&
							dep_line.secondPart.equals(target1)))
			{
				has_relation =true;
				break;
			}
				
		}
		return has_relation;
	}
	public static Boolean haveDirectRelation(String target1, Integer offset1,
			String target2, Integer offset2,
			ArrayList<DependencyLine> dependencies)
	{
		Boolean has_relation = false;
		for (DependencyLine dep_line:dependencies)
		{
			if ((dep_line.firstPart.equals(target1) &&
					dep_line.firstOffset==offset1 &&
					dep_line.secondPart.equals(target2)
					&& dep_line.secondOffset == offset2)
					|| (dep_line.firstPart.equals(target2) &&
						dep_line.firstOffset == offset2 &&
						dep_line.secondOffset ==offset1 &&
							dep_line.secondPart.equals(target1)))
			{
				has_relation =true;
				break;
			}
				
		}
		return has_relation;
	}
	//this method returns all governor Dependency lines to a target
	public static List<DependencyLine> getAllGovernors(
			ArrayList<DependencyLine> dep_lines, String target)
	{
		List<DependencyLine> gov_deps = new ArrayList<DependencyLine> ();
		for (DependencyLine dep: dep_lines)
		{
			if (dep.secondPart.equals(target))
			{
				gov_deps.add(dep);
			}
		}
		return gov_deps;
	}
	public static List<DependencyLine> getAllGovernors(
			ArrayList<DependencyLine> dep_lines, String target, int offset)
	{
		List<DependencyLine> gov_deps = new ArrayList<DependencyLine> ();
		for (DependencyLine dep: dep_lines)
		{
//			if (dep.secondPart.equals(target) && dep.secondOffset==offset)
			if (dep.secondOffset==offset)
			{
				gov_deps.add(dep);
			}
		}
		return gov_deps;
	}

	public static List<DependencyLine> getAllDependents(
			ArrayList<DependencyLine> dep_lines, String target, int offset)
	{
		List<DependencyLine> dependent_deps = new ArrayList<DependencyLine> ();
		for (DependencyLine dep: dep_lines)
		{
//			if (dep.firstPart.equals(target) && dep.firstOffset==offset)
			if (dep.firstOffset==offset)