import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;


public class GUIRepartitionScreenCoordinator extends GUICoordinator {
	private RepartitionController repartitionController = new RepartitionController();
	private JTextField achatTxt;
	private JTextField honorairesTxt;
	private JTextField pourcentageStructureTxt;
	private JLabel prixDispositifValueLbl;
	private JLabel prestationSoinsValueLbl;
	private JLabel chargesStructureValueLbl;
	private JButton changePourcentageStructureBtn;
	private JButton computeBtn;
	
	static private String CHANGE_POURCENTAGE_STRUCTURE_BUTTON_LABEL = "Modifier";
	public GUIRepartitionScreenCoordinator(GUIGlobalCoordinator globalCoordinator)
	{
		super(globalCoordinator);
		initialize();
	}
	private void initialize()
	{
        JPanel repartitionPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        repartitionPanel.setLayout(gridBag);
        Dimension txtFieldsDimension = new Dimension(70, 30);
        
        int formOffset = 0;
        JLabel achatLbl = new JLabel("Prix d'achat payé au fournisseur :");
        addWidgetInGridBagPanel(repartitionPanel, gridBag, achatLbl, 0, formOffset + 0, 1, 1,0);

        achatTxt = new JTextField();
        achatTxt.setPreferredSize(txtFieldsDimension);
        achatTxt.getDocument().addDocumentListener(new TxtInputListener());
        AbstractDocument achatTxtDoc = (AbstractDocument)achatTxt.getDocument();
        achatTxtDoc.setDocumentFilter(new NumbersFilter());
        addWidgetInGridBagPanel(repartitionPanel, gridBag, achatTxt, 1, formOffset + 0, 1, 1,0);

        JLabel honorairesLbl = new JLabel("Honoraires payés par le patient :");
        addWidgetInGridBagPanel(repartitionPanel, gridBag, honorairesLbl, 0, formOffset + 1, 1, 1,0);

        honorairesTxt = new JTextField();
        honorairesTxt.setPreferredSize(txtFieldsDimension);
        AbstractDocument honorairesTxtDoc = (AbstractDocument)achatTxt.getDocument();
        honorairesTxtDoc.setDocumentFilter(new NumbersFilter());
        addWidgetInGridBagPanel(repartitionPanel, gridBag, honorairesTxt, 1, formOffset + 1, 1, 1,0);

        JLabel pourcentageStructureLbl = new JLabel("% de coûts de structure :");
        addWidgetInGridBagPanel(repartitionPanel, gridBag, pourcentageStructureLbl, 0, formOffset + 2, 1, 1,0);

        pourcentageStructureTxt = new JTextField();
        pourcentageStructureTxt.setPreferredSize(txtFieldsDimension);
        pourcentageStructureTxt.setEditable(false);
        AbstractDocument percentageStructureTxtDoc = (AbstractDocument)achatTxt.getDocument();
        percentageStructureTxtDoc.setDocumentFilter(new NumbersFilter());
        addWidgetInGridBagPanel(repartitionPanel, gridBag, pourcentageStructureTxt, 1, formOffset + 2, 1, 1,0);

        changePourcentageStructureBtn = new JButton(CHANGE_POURCENTAGE_STRUCTURE_BUTTON_LABEL);
        changePourcentageStructureBtn.addActionListener(new ActionListener(){
        	private boolean beingModified = false;
        	public void actionPerformed(ActionEvent e){
        		beingModified = !beingModified;
        		if (beingModified)
        			reactToChangePourcentageStructureButton();
        		else
        			reactToValidatePourcentageStructureButton();
        	}
        });
        addWidgetInGridBagPanel(repartitionPanel, gridBag, changePourcentageStructureBtn, 2, formOffset + 2, 1, 1, 0);
        
        computeBtn = new JButton("Calculer la répartition");
        computeBtn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		reactToComputeButton();
        	}
        });
        addWidgetInGridBagPanel(repartitionPanel, gridBag, computeBtn, 0, formOffset + 3, 2, 1,100);

        Dimension lblFieldsDimension = new Dimension(70, 30);
        JPanel lblEmpty = new JPanel();
        lblEmpty.setPreferredSize(new Dimension(10, 50));
        addWidgetInGridBagPanel(repartitionPanel, gridBag, lblEmpty, 0, formOffset + 4, 1, 1,0);

        JLabel prixDispositifTitleLbl = new JLabel("Prix de vente du dispositif médical :");
        addWidgetInGridBagPanel(repartitionPanel, gridBag, prixDispositifTitleLbl, 0, formOffset + 5, 1, 1,0);

        prixDispositifValueLbl = new JLabel();
        prixDispositifValueLbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        prixDispositifValueLbl.setPreferredSize(lblFieldsDimension);
        addWidgetInGridBagPanel(repartitionPanel, gridBag, prixDispositifValueLbl, 1, formOffset + 5, 1, 1,0);

        JLabel prestationSoinsTitleLbl = new JLabel("Montant des prestations de soins :");
        addWidgetInGridBagPanel(repartitionPanel, gridBag, prestationSoinsTitleLbl, 0, formOffset + 6, 1, 1,0);

        prestationSoinsValueLbl = new JLabel();
        prestationSoinsValueLbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        prestationSoinsValueLbl.setPreferredSize(lblFieldsDimension);
        addWidgetInGridBagPanel(repartitionPanel, gridBag, prestationSoinsValueLbl, 1, formOffset + 6, 1, 1,0);

        JLabel chargesStructureTitleLbl = new JLabel("Charges de structure :");
        addWidgetInGridBagPanel(repartitionPanel, gridBag, chargesStructureTitleLbl, 0, formOffset + 7, 1, 1,0);

        chargesStructureValueLbl = new JLabel();
        chargesStructureValueLbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        chargesStructureValueLbl.setPreferredSize(lblFieldsDimension);
        addWidgetInGridBagPanel(repartitionPanel, gridBag, chargesStructureValueLbl, 1, formOffset + 7, 1, 1,0);

        repartitionController.setPourcentageStructure(25);
        pourcentageStructureTxt.setText(Double.valueOf(repartitionController.getPourcentageStructure()).toString());
        setPanel(repartitionPanel);
	}
	public void activate(JFrame frame)
	{
		super.activate(frame);
		achatTxt.requestFocus();
	}
	private void reactToComputeButton()
	{
		double achat = Double.parseDouble(achatTxt.getText());
		double honoraires = Double.parseDouble(honorairesTxt.getText());
		double pourcentageStructure = Double.parseDouble(pourcentageStructureTxt.getText());

		String dispositifMedical = Double.valueOf(repartitionController.computeDispositifMedical(achat, honoraires, pourcentageStructure)).toString();
		prixDispositifValueLbl.setText(dispositifMedical);
		String prestationsSoin = Double.valueOf(repartitionController.computePrestationsSoin(achat, honoraires, pourcentageStructure)).toString();
		prestationSoinsValueLbl.setText(prestationsSoin);
		String chargesStructure = Double.valueOf(repartitionController.computeChargeStructure(honoraires, pourcentageStructure)).toString();
		chargesStructureValueLbl.setText(chargesStructure);
	}
	private void reactToTxtInputChange()
	{
		prixDispositifValueLbl.setText("");
		prestationSoinsValueLbl.setText("");
		chargesStructureValueLbl.setText("");
	}
	private void reactToChangePourcentageStructureButton()
	{
		changePourcentageStructureBtn.setText("OK");
		achatTxt.setEnabled(false);
		honorairesTxt.setEnabled(false);
		computeBtn.setEnabled(false);
		pourcentageStructureTxt.setEditable(true);
		pourcentageStructureTxt.requestFocus();
		pourcentageStructureTxt.setSelectionStart(0);
		pourcentageStructureTxt.setSelectionEnd(pourcentageStructureTxt.getText().length());
		prixDispositifValueLbl.setText("");
		prestationSoinsValueLbl.setText("");
		chargesStructureValueLbl.setText("");
	}
	private void reactToValidatePourcentageStructureButton()
	{
		String oldPourcentageStructure = Double.valueOf(repartitionController.getPourcentageStructure()).toString();
		String newPourcentageStructure = pourcentageStructureTxt.getText();
		if (!Double.valueOf(oldPourcentageStructure).equals(Double.valueOf(newPourcentageStructure)))
		{
			String confirmationMessage = "Etes-vous sûr de vouloir modifier le % de charge de structure\n de " + oldPourcentageStructure +"% à " + newPourcentageStructure + "% ?";
			int answer = JOptionPane.showConfirmDialog(null, confirmationMessage, "Confirmation", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION)
			{
				repartitionController.setPourcentageStructure(Double.parseDouble(pourcentageStructureTxt.getText()));
			}
			else
			{
				pourcentageStructureTxt.setText(oldPourcentageStructure);
			}
		}
		changePourcentageStructureBtn.setText(CHANGE_POURCENTAGE_STRUCTURE_BUTTON_LABEL);
		achatTxt.setEnabled(true);
		honorairesTxt.setEnabled(true);
		computeBtn.setEnabled(true);
		pourcentageStructureTxt.setEditable(false);
	}
	public class NumbersFilter extends DocumentFilter {

	    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
	        StringBuilder buffer = new StringBuilder(string);
	        for (int i = buffer.length() - 1; i >= 0; --i) {
	            char ch = buffer.charAt(i);
	            if (!Character.isDigit(ch) && ch != '.') {
	                buffer.deleteCharAt(i);
	            }
	        }
	        super.insertString(fb, offset, buffer.toString(), attr);
	    }

	    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
	        if (length > 0)
	        	fb.remove(offset, length);
	        insertString(fb, offset, string, attr);
	    }
	}	private class TxtInputListener implements DocumentListener
	{
		public void insertUpdate(DocumentEvent e) { reactToTxtInputChange();}
		public void removeUpdate(DocumentEvent e) { reactToTxtInputChange();}
		public void changedUpdate(DocumentEvent e) { reactToTxtInputChange();}
	}
}
