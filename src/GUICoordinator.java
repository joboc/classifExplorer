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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;


public class GUICoordinator
{
	private JFrame m_guiFrame = new JFrame();
	private JPanel m_menuPanel;
	private JPanel m_searchPanel;
	private LinkedList<JPanel> m_panels = new LinkedList<JPanel>();
	
	public GUICoordinator()
	{
		initializeFrame();
		m_menuPanel = createMenuPanel();
		m_panels.add(m_menuPanel);
		m_searchPanel = createSearchPanel();
		m_panels.add(m_searchPanel);

		switchTo(m_searchPanel);
	}
	private void switchTo(JPanel panelToShow)
	{
		for (JPanel p : m_panels){
			m_guiFrame.remove(p);
			p.setVisible(false);
		}
		m_guiFrame.add(panelToShow);
		panelToShow.setVisible(true);
	}
	private void initializeFrame()
	{
        m_guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m_guiFrame.setTitle("Classification Explorer");
        m_guiFrame.setSize(800,800);
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
	private String getDefaultFontName()
	{
		JLabel lblDummy = new JLabel();
		return lblDummy.getFont().getName();
	}
	private JPanel createMenuPanel()
    {
        JPanel menuPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        menuPanel.setLayout(gridBag);
        
        JLabel titleLbl = new JLabel("Classification");
        titleLbl.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 36));
        addWidgetInGridBagPanel(menuPanel, gridBag, titleLbl, 0, 0, 2, 1,0);

        JLabel titleLbl2 = new JLabel("Explorer");
        titleLbl2.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 36));
        addWidgetInGridBagPanel(menuPanel, gridBag, titleLbl2, 0, 1, 2, 1,0);

        JPanel titleLblEmpty = new JPanel();
        titleLblEmpty.setPreferredSize(new Dimension(10, 70));
        addWidgetInGridBagPanel(menuPanel, gridBag, titleLblEmpty, 0, 2, 1, 1,0);
        
        JButton gotoSearch = new JButton("Recherche d'actes");
        addWidgetInGridBagPanel(menuPanel, gridBag, gotoSearch, 0, 3, 1, 1,100);

        JButton exit = new JButton("Quitter");
        addWidgetInGridBagPanel(menuPanel, gridBag, exit, 1, 3, 1, 1,100);

        return menuPanel;
    }
	private JPanel createSearchPanel()
	{
		JPanel searchPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        searchPanel.setLayout(gridBag);
        
        JLabel titleLbl = new JLabel("Recherche d'actes");
        titleLbl.setFont(new Font(getDefaultFontName(), Font.BOLD + Font.ITALIC, 36));
        titleLbl.setForeground(Color.gray);
        addWidgetInGridBagPanel(searchPanel, gridBag, titleLbl, 0, 0, 3, 1,0);

        JPanel titleLblEmpty = new JPanel();
        titleLblEmpty.setPreferredSize(new Dimension(10, 40));
        addWidgetInGridBagPanel(searchPanel, gridBag, titleLblEmpty, 0, 1, 1, 1,0);
        
        JLabel lblSearch = new JLabel("Trouver un acte :");
        lblSearch.setFont(new Font(getDefaultFontName(), Font.PLAIN, lblSearch.getFont().getSize()));
        addWidgetInGridBagPanel(searchPanel, gridBag, lblSearch, 0, 2, 1, 1,0);

        JTextField txtInput = new JTextField("");
        txtInput.setPreferredSize(new Dimension(400, 30));
        addWidgetInGridBagPanel(searchPanel, gridBag, txtInput, 1, 2, 1, 1,0);

        JList resultsList = new JList();
        resultsList.setPreferredSize(new Dimension(600, 600));
        resultsList.setBorder(txtInput.getBorder());
        addWidgetInGridBagPanel(searchPanel, gridBag, resultsList, 1, 3, 2, 1, 0);

        return searchPanel;
	}

}
