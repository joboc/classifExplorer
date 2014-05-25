import java.lang.Comparable;

public class ActCode implements Comparable<ActCode> {
	private String m_code;

	public ActCode(String code)
	{
		m_code = code;
	}
	public ActCode(ActCode other)
	{
		m_code = new String(other.m_code);
	}
	public String getCode()
	{
		return m_code;
	}
	public int compareTo(ActCode other)
	{
		return m_code.compareTo(other.m_code);
	}
}
