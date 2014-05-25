import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class GUISearchScreenCoordinator extends GUICoordinator
{
	private SearchController searchController = new SearchController();
    private JList resultsList;
    private JTextField txtInput;
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

        txtInput = new JTextField("");
        txtInput.setPreferredSize(new Dimension(400, 30));
        txtInput.getDocument().addDocumentListener(new TxtInputListener());
        addWidgetInGridBagPanel(searchPanel, gridBag, txtInput, 1, 2, 1, 1,0);

        resultsList = new JList();
        JScrollPane resultsListScrollPane = new JScrollPane(resultsList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultsListScrollPane.setPreferredSize(new Dimension(900, 600));
        resultsListScrollPane.setBorder(txtInput.getBorder());
        addWidgetInGridBagPanel(searchPanel, gridBag, resultsListScrollPane, 1, 3, 2, 1, 0);

        fillListWithAllLabels();
        
        setPanel(searchPanel);
	}
	private class TxtInputListener implements DocumentListener
	{
		public void insertUpdate(DocumentEvent e) { reactToTxtInputChange();}
		public void removeUpdate(DocumentEvent e) { reactToTxtInputChange();}
		public void changedUpdate(DocumentEvent e) { reactToTxtInputChange();}
	}
	private void reactToTxtInputChange()
	{
		resultsList.setListData(searchController.getLabelsFilteredByInput(txtInput.getText()));
	}
	private void fillListWithAllLabels()
	{
		resultsList.setListData(searchController.getAllLabels());
	}
}
