import javax.swing.JOptionPane;



public class Act {
	public Act(ActCode code, ActContent content)
	{
		m_code = code;
		m_content = content;
	}
	public Act()
	{
		JOptionPane.showMessageDialog(null, "Empty Act created", "Empty Act created", JOptionPane.INFORMATION_MESSAGE);
	}
	public ActCode getCode()
	{
		return m_code;
	}
	public ActContent getContent()
	{
		return m_content;
	}
	private ActCode m_code;
	private ActContent m_content;
}
