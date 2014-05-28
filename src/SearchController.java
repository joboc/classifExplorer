import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchController {
	ArrayList<Act> m_displayActs = new ArrayList<Act>();
	
	public Vector<String> getAllLabels()
	{
		m_displayActs.clear();
		for (Map.Entry<ActCode, ActContent> entry : ActsData.getActsMap().entrySet())
		{
			m_displayActs.add(new Act(new ActCode(entry.getKey()), entry.getValue()));
		}
		Collections.sort(m_displayActs, new actSorterByDescendingViewsAscendingLabel());
		
		return buildLabelsFromActs(m_displayActs);
	}
	public Vector<String> getLabelsFilteredByInput(String input)
	{
		m_displayActs.clear();
		String[] inputWords = input.split(" ");
		for (Map.Entry<ActCode, ActContent> entry : ActsData.getActsMap().entrySet())
		{
			String actLabel = entry.getValue().getLabel();
			boolean actLabelContainsAllInputWords = true;
			for (String word : inputWords)
			{
				// tous les mots saisis doivent etre dans le label
				boolean wordFound = Pattern.compile(Pattern.quote(word), Pattern.CASE_INSENSITIVE).matcher(actLabel).find();
				actLabelContainsAllInputWords = actLabelContainsAllInputWords && wordFound;
			}
			if (actLabelContainsAllInputWords)
			{
				m_displayActs.add(new Act(new ActCode(entry.getKey()), entry.getValue()));
			}
		}
		Collections.sort(m_displayActs, new actSorterByDescendingViewsAscendingLabel());
		
		return buildLabelsFromActsWithInputs(m_displayActs, inputWords);
	}
	public void reactToSelection(int index)
	{
		m_displayActs.get(index).getContent().IncrementTimesViewed();
	}
	public String getCodeAt(int index)
	{
		return m_displayActs.get(index).getCode().getValue();
	}
	public double getPriceAt(int index)
	{
		return m_displayActs.get(index).getContent().getPrice();
	}

	private class actSorterByDescendingViewsAscendingLabel implements Comparator<Act>
	{
		public int compare(Act a1, Act a2)
		{
			int comp = -1 * Integer.valueOf(a1.getContent().getTimesViewed()).compareTo(a2.getContent().getTimesViewed());
			if (comp == 0)
			{
				comp = a1.getContent().getLabel().compareTo(a2.getContent().getLabel());
			}
			return comp;
		}
	}
	private Vector<String> buildLabelsFromActs(ArrayList<Act> acts)
	{
		Vector<String> displayLabels = new Vector<String>();
		for (Act act : acts)
		{
			String displayLabel = new String(act.getContent().getLabel());
			displayLabel = addViewedColorToLabel(displayLabel, act.getContent().getTimesViewed());
			displayLabels.add(displayLabel);
		}
		return displayLabels;
	}
	private Vector<String> buildLabelsFromActsWithInputs(ArrayList<Act> acts, String[] inputWords)
	{
		Vector<String> displayLabels = new Vector<String>();
		for (Act act : acts)
		{
			ArrayList<Interval> matchIntervals = new ArrayList<Interval>();
			for (String word : inputWords)
			{
				Pattern wordPattern = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
				String actLabel = act.getContent().getLabel();
				Matcher wordMatcher = wordPattern.matcher(actLabel);
				while (wordMatcher.find())
				{
					matchIntervals.add(new Interval(wordMatcher.start(), wordMatcher.end()));
				}
			}
			mergeIntervals(matchIntervals);
			String displayLabel = new String(act.getContent().getLabel());
			displayLabel = addMatchColorToLabel(displayLabel, matchIntervals);
			displayLabel = addViewedColorToLabel(displayLabel, act.getContent().getTimesViewed());
			displayLabels.add(displayLabel);
		}
		return displayLabels;
	}
	private void mergeIntervals(ArrayList<Interval> intervals)
	{
		Collections.sort(intervals);
		ArrayList<Interval> intervalsToRemove = new ArrayList<Interval>();
		int i = 0;
		while (i < intervals.size())
		{
			Interval initialInterval = intervals.get(i); 
			boolean disjoint = false;
			while (!disjoint && i < intervals.size() - 1)
			{
				++i;
				Interval currentInterval = intervals.get(i);
				if (currentInterval.start <= initialInterval.end)
				{
					initialInterval.end = Math.max(initialInterval.end, currentInterval.end);
					intervalsToRemove.add(currentInterval);
				}
				else
				{
					disjoint = true;
				}
			}
			++i;
		}
		intervals.removeAll(intervalsToRemove);
	}
	private String addViewedColorToLabel(String label, int timesViewed)
	{
		StringBuilder sb = new StringBuilder(label);
		if (timesViewed > 0)
		{
			sb.insert(label.length(), "</font>");
			sb.insert(0, "<font color=blue>");
		}
		String labelWithoutHtml = sb.toString();
		sb = new StringBuilder(labelWithoutHtml);
		sb.insert(labelWithoutHtml.length(), "</html>");
		sb.insert(0, "<html>");

		return sb.toString();
	}
	private String addMatchColorToLabel(String label, ArrayList<Interval> matches)
	{
		String coloredLabel = null;
		if (matches.size() > 0)
		{
			ListIterator<Interval> it = matches.listIterator(matches.size()); // reverse iteration
			StringBuilder sb = new StringBuilder(label);
			while (it.hasPrevious())
			{
				Interval matchInterval = it.previous(); 
				sb.insert(matchInterval.end, "</font>");
				sb.insert(matchInterval.start, "<font color=red>");
			}
			coloredLabel = sb.toString();
		}
		else
		{
			coloredLabel = new String(label);
		}
		return coloredLabel;
	}
	private class Interval implements Comparable<Interval>
	{
		public int start;
		public int end;
		public Interval(int start, int end)
		{
			this.start = start;
			this.end = end;
		}
		public int compareTo(Interval other)
		{
			return Integer.valueOf(start).compareTo(other.start);
		}
	}
}
