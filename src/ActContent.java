
public class ActContent {
	private String m_label;
	private double m_price;
	private int m_timesViewed;
	public ActContent(String label, double price)
	{
		m_label = label;
		m_price = price;
		m_timesViewed = 0;
	}
	public ActContent(ActContent other)
	{
		m_label = new String(other.m_label);
		m_price = other.m_price;
		m_timesViewed = other.m_timesViewed;
	}
	public String getLabel()
	{
		return m_label;
	}
	public double getPrice()
	{
		return m_price;
	}
	public int getTimesViewed()
	{
		return m_timesViewed;
	}
	public void IncrementTimesViewed()
	{
		++m_timesViewed;
	}
	public void setTimesViewed(int timesViewed)
	{
		m_timesViewed = timesViewed;
	}
}
