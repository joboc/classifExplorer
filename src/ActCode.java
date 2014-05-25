import java.lang.Comparable;

public class ActCode implements Comparable<ActCode> {
	private String m_value;

	public ActCode(String value)
	{
		m_value = value;
	}
	public ActCode(ActCode other)
	{
		m_value = new String(other.m_value);
	}
	public String getValue()
	{
		return m_value;
	}
	public int compareTo(ActCode other)
	{
		return m_value.compareTo(other.m_value);
	}
}
