
public class ModificateurContent extends ActContent {
	private String m_action;
	public ModificateurContent(String label, String action)
	{
		super(label, 0);
		m_action = action;
	}
	public String getModificateurAction()
	{
		return m_action;
	}
}
