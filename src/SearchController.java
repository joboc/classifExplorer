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
			String displayLabel = new String(act.getContent().getLabel());
			displayLabel = addViewedColorToLabel(displayLabel, act.getContent().getTimesViewed());
			displayLabels.add(displayLabel);
		}
		return displayLabels;
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
}
