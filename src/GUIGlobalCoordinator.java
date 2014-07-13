import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ListIterator;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


public class GUIGlobalCoordinator {
	private JFrame m_guiFrame = new JFrame();
	Stack<GUICoordinator> m_coordinators = new Stack<GUICoordinator>();
	
	public GUIGlobalCoordinator()
	{
		initializeFrame();		
		display(new GUIMainMenuCoordinator(this));
	}
	public void display(GUICoordinator newActiveCoordinator)
	{
		if (m_coordinators.size() > 0)
			m_coordinators.peek().inactivate(m_guiFrame);
		m_coordinators.push(newActiveCoordinator);
		newActiveCoordinator.activate(m_guiFrame);
	}
	public void backToPreviousScreen()
	{
		m_coordinators.peek().inactivate(m_guiFrame);
		m_coordinators.pop();
		if (m_coordinators.size() > 0)
			m_coordinators.peek().activate(m_guiFrame);
	}
	public void reactToMainMenuGotoSearch()
	{
		display(new GUISearchScreenCoordinator(this));
	}
	public void reactToExitCommand()
	{
		int answer = JOptionPane.showConfirmDialog(null, "<html>Etes-vous s&#251r(e) de vouloir quitter ?</html>", "Confirmation", JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION)
		{
			System.exit(0);
		}
	}
	private void reactToApplicationClose()
	{
		ListIterator<GUICoordinator> it = m_coordinators.listIterator(m_coordinators.size());
		while (it.hasPrevious())
		{
			it.previous().inactivate(m_guiFrame);
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
        m_guiFrame.setSize(1125,825);
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
}
