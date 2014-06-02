import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class GUISearchScreenCoordinator extends GUICoordinator
{
	private SearchController searchController = new SearchController();
    private JList resultsList;
    private JTextField txtInput;
    private JLabel lblNbActsFound;
	public GUISearchScreenCoordinator(GUIGlobalCoordinator globalCoordinator)
	{
		super(globalCoordinator);
		initialize();
	}
	public void initialize()
	{
        JPanel headerPanel = createStandardHeaderPanel("Recherche d'actes");

		JPanel mainPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        mainPanel.setLayout(gridBag);
        
        JPanel titleLblEmpty = new JPanel();
        titleLblEmpty.setPreferredSize(new Dimension(10, 40));
        addWidgetInGridBagPanel(mainPanel, gridBag, titleLblEmpty, 0, 1, 1, 1,0);
        
        JLabel lblSearch = new JLabel("Trouver un acte :");
        lblSearch.setFont(new Font(getDefaultFontName(), Font.PLAIN, lblSearch.getFont().getSize()));
        addWidgetInGridBagPanel(mainPanel, gridBag, lblSearch, 0, 2, 1, 1,0);

        txtInput = new JTextField("");
        txtInput.setPreferredSize(new Dimension(400, 30));
        txtInput.getDocument().addDocumentListener(new TxtInputListener());
        addWidgetInGridBagPanel(mainPanel, gridBag, txtInput, 1, 2, 1, 1,0);

        lblNbActsFound = new JLabel("actes trouvés");
        lblNbActsFound.setPreferredSize(new Dimension(475, 30));
        lblNbActsFound.setFont(new Font(getDefaultFontName(), Font.PLAIN, lblSearch.getFont().getSize()));
        addWidgetInGridBagPanel(mainPanel, gridBag, lblNbActsFound, 2, 2, 1, 1, 0);

        resultsList = new JList();
        resultsList.setModel(new AbstractListModel(){
			public int getSize()
			{
				return searchController.getNumberOfDisplayLabels();
			}
			public Object getElementAt(int index)
			{
				return searchController.getDisplayLabel(index);
			}
		});
        resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultsList.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e){
        		reactToListSelection();
        	}
        });
        JScrollPane resultsListScrollPane = new JScrollPane(resultsList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultsListScrollPane.setPreferredSize(new Dimension(900, 600));
        resultsListScrollPane.setBorder(txtInput.getBorder());
        addWidgetInGridBagPanel(mainPanel, gridBag, resultsListScrollPane, 1, 3, 2, 1, 0);

        fillListWithAllLabels();
        
        JPanel globalPanel = new JPanel();
        globalPanel.setLayout(new BorderLayout());
        globalPanel.add(headerPanel, BorderLayout.NORTH);
        globalPanel.add(mainPanel, BorderLayout.CENTER);
        setPanel(globalPanel);
	}
	public void activate(JFrame frame)
	{
		super.activate(frame);
		txtInput.requestFocus();
	}
	public void onExit()
	{
		super.onExit();
		ActsData.save();
	}
	private class TxtInputListener implements DocumentListener
	{
		public void insertUpdate(DocumentEvent e) { reactToTxtInputChange();}
		public void removeUpdate(DocumentEvent e) { reactToTxtInputChange();}
		public void changedUpdate(DocumentEvent e) { reactToTxtInputChange();}
	}
	private void fillListWithAllLabels()
	{
		searchController.queryAllLabels();
		resultsList.revalidate();
		resultsList.repaint();
		updateLabelNbActsFound();
	}
	private void reactToTxtInputChange()
	{
		searchController.queryLabelsFilteredByInput(txtInput.getText());
		resultsList.revalidate();
		resultsList.repaint();
		updateLabelNbActsFound();
	}
	private void updateLabelNbActsFound()
	{
		lblNbActsFound.setText(searchController.getNumberOfDisplayLabels() + " actes trouvés");
	}
	private void reactToListSelection()
	{
		searchController.reactToSelection(resultsList.getSelectedIndex());
		String code = searchController.getCodeAt(resultsList.getSelectedIndex());
		double price = searchController.getPriceAt(resultsList.getSelectedIndex());
		String displayPrice = price != 0.0 ? Double.toString(price) + "€" : "Non pris en charge";
		String codeInfo = "Code : " + code + "\nTarif : " + displayPrice;
		JOptionPane.showMessageDialog(null, codeInfo, "Informations sur l'acte", JOptionPane.PLAIN_MESSAGE);
		txtInput.requestFocus();
	}
}
