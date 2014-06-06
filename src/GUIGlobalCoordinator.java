import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


public class GUIGlobalCoordinator {
	private JFrame m_guiFrame = new JFrame();
	GUICoordinator m_activeCoordinator;
	ArrayList<GUICoordinator> m_coordinators = new ArrayList<GUICoordinator>();
	GUICoordinator m_mainMenuCoordinator;
	GUICoordinator m_searchScreenCoordinator;
	GUICoordinator m_repartitionScreenCoordinator;
	public GUIGlobalCoordinator()
	{
		initializeFrame();
		m_mainMenuCoordinator = new GUIMainMenuCoordinator(this);
		m_coordinators.add(m_mainMenuCoordinator);
		m_searchScreenCoordinator = new GUISearchScreenCoordinator(this);
		m_coordinators.add(m_searchScreenCoordinator);
		m_repartitionScreenCoordinator = new GUIRepartitionScreenCoordinator(this);
		m_coordinators.add(m_repartitionScreenCoordinator);
		
		switchTo(m_mainMenuCoordinator);
	}
	public void reactToMainMenuGotoSearch()
	{
		switchTo(m_searchScreenCoordinator);
	}
	public void reactToMainMenuGotoRepartition()
	{
		switchTo(m_repartitionScreenCoordinator);
	}
	public void reactToExitCommand()
	{
		int answer = JOptionPane.showConfirmDialog(null, "<html>Etes-vous s&#251r(e) de vouloir quitter ?</html>", "Confirmation", JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION)
		{
			System.exit(0);
		}
	}
	public void reactToBackToMainMenu()
	{
		switchTo(m_mainMenuCoordinator);
	}
	private void reactToApplicationClose()
	{
		for (GUICoordinator e : m_coordinators)
		{
			e.onExit();
		}
	}
	private class ShutdownHook extends Thread
	{
		public void run()
		{
			reactToApplicationClose();
		}
	}
	
	private void initializeFrame()
	{
        m_guiFrame.setTitle("Classification Explorer");
        m_guiFrame.setSize(1100,800);
        m_guiFrame.setLocationRelativeTo(null);
        m_guiFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        m_guiFrame.addWindowListener(new WindowAdapter(){
        	public void windowClosing(WindowEvent evt){
        		reactToExitCommand();
        	}
        });
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { // GTK+ pas mal non plus
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
        m_guiFrame.setVisible(true);
        if (!Authentication.isAuthentified())
        {
        	System.exit(0);
        }
	}
	private void switchTo(GUICoordinator newActiveCoordinator)
	{
		if (m_activeCoordinator != null)
			m_activeCoordinator.inactivate(m_guiFrame);
		newActiveCoordinator.activate(m_guiFrame);
		m_activeCoordinator = newActiveCoordinator;
	}
}
