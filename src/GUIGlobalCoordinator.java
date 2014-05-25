import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


public class GUIGlobalCoordinator {
	private JFrame m_guiFrame = new JFrame();
	GUICoordinator m_activeCoordinator;
	GUICoordinator m_mainMenuCoordinator;
	GUICoordinator m_searchScreenCoordinator;
	public GUIGlobalCoordinator()
	{
		initializeFrame();
		m_mainMenuCoordinator = new GUIMainMenuCoordinator(this);
		m_searchScreenCoordinator = new GUISearchScreenCoordinator(this);

		switchTo(m_mainMenuCoordinator);
	}
	public void reactToMainMenuGotoSearch()
	{
		switchTo(m_searchScreenCoordinator);
	}
	public void reactToExit()
	{
		System.exit(0);
	}
	
	private void initializeFrame()
	{
        m_guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m_guiFrame.setTitle("Classification Explorer");
        m_guiFrame.setSize(1100,800);
        m_guiFrame.setLocationRelativeTo(null);
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { // GTK+ pas mal non plus
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
        m_guiFrame.setVisible(true);
	}
	private void switchTo(GUICoordinator newActiveCoordinator)
	{
		if (m_activeCoordinator != null)
			m_activeCoordinator.inactivate(m_guiFrame);
		newActiveCoordinator.activate(m_guiFrame);
		m_activeCoordinator = newActiveCoordinator;
	}
}
