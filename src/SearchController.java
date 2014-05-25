import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Vector;

public class SearchController {
	ArrayList<Act> m_displayActs = new ArrayList<Act>();
	
	public Vector<String> getAllLabels()
	{
		m_displayActs.clear();
		for (Map.Entry<ActCode, ActContent> entry : ActsDataBase.getActsMap().entrySet())
		{
			m_displayActs.add(new Act(new ActCode(entry.getKey()), new ActContent(entry.getValue())));
		}
		Collections.sort(m_displayActs, new actSorterByLabel());
		
		return buildLabelsFromActs(m_displayActs);
	}
	private class actSorterByLabel implements Comparator<Act>
	{
		public int compare(Act a1, Act a2)
		{
			return a1.getContent().getLabel().compareTo(a2.getContent().getLabel());
		}
	}
	private Vector<String> buildLabelsFromActs(ArrayList<Act> acts)
	{
		Vector<String> displayLabels = new Vector<String>();
		for (Act e : acts)
		{
			displayLabels.add(e.getContent().getLabel());
		}
		return displayLabels;
	}
}
