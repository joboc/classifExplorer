
public class ActContent {
	private String m_label;
	private double m_price;
	public ActContent(String label, double price)
	{
		m_label = label;
		m_price = price;
	}
	public ActContent(ActContent other)
	{
		m_label = new String(other.m_label);
		m_price = other.m_price;
	}
	public String getLabel()
	{
		return m_label;
	}
	public double getPrice()
	{
		return m_price;
	}
}
