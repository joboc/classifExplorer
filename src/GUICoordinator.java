import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;


public class GUICoordinator
{
	private JFrame guiFrame = new JFrame();
	private JPanel menuPanel;
	private LinkedList<JPanel> panels = new LinkedList<JPanel>();
	
	public GUICoordinator()
	{
		initializeFrame();
		menuPanel = createMenuPanel();
		panels.add(menuPanel);
		for (JPanel p : panels){
			guiFrame.add(p);
		}
		switchTo(menuPanel);
	}
	private void switchTo(JPanel panelToShow)
	{
		for (JPanel p : panels){
			p.setVisible(false);
		}
		panelToShow.setVisible(true);
	}
	private void initializeFrame()
	{
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Classification Explorer");
        guiFrame.setSize(800,800);
        guiFrame.setLocationRelativeTo(null);
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { // GTK+ pas mal non plus
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
        guiFrame.setVisible(true);
	}
	private void addWidgetInGridBagPanel(JPanel panel, GridBagLayout gridBag, JComponent component, int gridx, int gridy, int gridwidth, int gridheight, int ipadx)
	{
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.CENTER;
        cons.gridx = gridx;
        cons.gridy = gridy;
        cons.gridwidth = gridwidth;
        cons.gridheight = gridheight;
        cons.ipadx = ipadx;
        JPanel localPanel = new JPanel();
        localPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        localPanel.add(component);
        //localPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        gridBag.setConstraints(localPanel, cons);
        panel.add(localPanel);
	}
	private JPanel createMenuPanel()
    {
        //creating a border to highlight the component areas
        
        //create GridBagLayout and the GridBagLayout Constraints
        JPanel aMenuPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        aMenuPanel.setLayout(gridBag);
        
        JLabel titleLbl = new JLabel("Classification");
        titleLbl.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 36));
        addWidgetInGridBagPanel(aMenuPanel, gridBag, titleLbl, 0, 0, 2, 1,0);

        JLabel titleLbl2 = new JLabel("Explorer");
        titleLbl2.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 36));
        addWidgetInGridBagPanel(aMenuPanel, gridBag, titleLbl2, 0, 1, 2, 1,0);

        JPanel titleLblEmpty = new JPanel();
        titleLblEmpty.setPreferredSize(new Dimension(10, 70));
        addWidgetInGridBagPanel(aMenuPanel, gridBag, titleLblEmpty, 0, 2, 2, 1,0);
        
        JButton gotoSearch = new JButton("Recherche d'actes");
        addWidgetInGridBagPanel(aMenuPanel, gridBag, gotoSearch, 0, 3, 1, 1,100);

        JButton exit = new JButton("Quitter");
        addWidgetInGridBagPanel(aMenuPanel, gridBag, exit, 1, 3, 1, 1,100);

        return aMenuPanel;
    }
}
