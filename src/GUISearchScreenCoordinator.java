import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GUISearchScreenCoordinator extends GUICoordinator {

	public GUISearchScreenCoordinator(GUIGlobalCoordinator globalCoordinator)
	{
		super(globalCoordinator);
		initialize();
	}
	public void initialize()
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

        setPanel(searchPanel);
	}

}
