import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
        guiFrame.setTitle("GridBagLayout Example");
        guiFrame.setSize(800,800);
        guiFrame.setLocationRelativeTo(null);
        guiFrame.setVisible(true);
	}
	private void addWidgetInGridBag(GridBagLayout gridBag, JComponent component, int gridx, int gridy, int gridwidth, int gridheight)
	{
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.BOTH;
        cons.gridx = gridx;
        cons.gridy = gridy;
        cons.gridwidth = gridwidth;
        cons.gridheight = gridheight;
        gridBag.setConstraints(component, cons);
	}
	private JPanel createMenuPanel()
    {
        //creating a border to highlight the component areas
        Border outline = BorderFactory.createLineBorder(Color.black);
        
        //create GridBagLayout and the GridBagLayout Constraints
        JPanel menuPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        menuPanel.setLayout(gridBag);
        
        JLabel titleLbl = new JLabel("Classification Explorer");
        addWidgetInGridBag(gridBag, titleLbl, 0, 0, 2, 1);
        menuPanel.add(titleLbl);
        
        JButton gotoSearch = new JButton("Recherche d'actes");
        addWidgetInGridBag(gridBag, gotoSearch, 0, 1, 1, 1);
        menuPanel.add(gotoSearch);

        JButton exit = new JButton("Quitter");
        addWidgetInGridBag(gridBag, exit, 1, 1, 1, 1);
        menuPanel.add(exit);

        //randomLbl.setBorder(outline);
        
        return menuPanel;
    }
}
