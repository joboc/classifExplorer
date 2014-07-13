import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.View;


public class GUIDevisScreenCoordinator extends GUICoordinator {
	private DevisController m_devisController;
	
	private JTextField m_achatTxt;
	private JTextField m_honorairesTxt;
	private JTextField m_pourcentageStructureTxt;
	private JButton m_changePourcentageStructureBtn;
	private JButton m_computeBtn;
	
	private JLabel m_actLabelLbl;
	private JLabel m_actCodeLbl;
	private JLabel m_prixDispositifValueLbl;
	private JLabel m_prestationSoinsValueLbl;
	private JLabel m_chargesStructureValueLbl;
	private JLabel m_honorairesValueLbl;
	private JLabel m_remboursementValueLbl;
	private JLabel m_nonRemboursableValueLbl;
	
	static private String CHANGE_POURCENTAGE_STRUCTURE_BUTTON_LABEL = "Modifier";
	public GUIDevisScreenCoordinator(DevisController devisController, GUIGlobalCoordinator globalCoordinator)
	{
		super(globalCoordinator);
		m_devisController = devisController;
		initialize();
	}
	private void initialize()
	{
		JPanel headerPanel = createStandardHeaderPanel("Devis unique");
		
        JPanel mainPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        mainPanel.setLayout(gridBag);

        // input components
        addWidgetInGridBagPanel(mainPanel, gridBag, buildInputPanel(), 0, 0, 1, 1,0);
        
        // separation line
        JLabel lblLine = GUIWidgetFactory.createLabel();
        lblLine.setPreferredSize(new Dimension(1, 400));
        lblLine.setBackground(Color.BLACK);
        lblLine.setOpaque(true);
        addWidgetInGridBagPanel(mainPanel, gridBag, lblLine, 1, 0, 1, 1,0);

        // output components
        addWidgetInGridBagPanel(mainPanel, gridBag, buildOutputPanel(), 2, 0, 1, 1,0);

        JLabel lblEmpty2 = GUIWidgetFactory.createLabel();
        lblEmpty2.setPreferredSize(new Dimension(10, 100));
        addWidgetInGridBagPanel(mainPanel, gridBag, lblEmpty2, 0, 1, 1, 1,0);

        JPanel globalPanel = new JPanel();
        globalPanel.setLayout(new BorderLayout());
        globalPanel.add(headerPanel, BorderLayout.NORTH);
        globalPanel.add(mainPanel, BorderLayout.CENTER);
        setPanel(globalPanel);
	}
	private JPanel buildInputPanel()
	{
        JPanel inputPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        inputPanel.setLayout(gridBag);
        Dimension txtFieldsDimension = new Dimension(70, 30);
        
        JLabel achatLbl = GUIWidgetFactory.createLabel("<html>Prix d'achat pay&#233 au fournisseur :</html>");
        addWidgetInGridBagPanel(inputPanel, gridBag, achatLbl, 0, 0, 1, 1,0);

        m_achatTxt = GUIWidgetFactory.createTextField();
        int a = m_achatTxt.getFont().getSize();
        m_achatTxt.setPreferredSize(txtFieldsDimension);
        m_achatTxt.getDocument().addDocumentListener(new TxtInputListener());
        AbstractDocument achatTxtDoc = (AbstractDocument)m_achatTxt.getDocument();
        achatTxtDoc.setDocumentFilter(new NumbersFilter());
        addWidgetInGridBagPanel(inputPanel, gridBag, m_achatTxt, 1, 0, 1, 1,0);

        JLabel achatUnit = GUIWidgetFactory.createLabel("<html>&#8364</html>");
        addWidgetInGridBagPanel(inputPanel, gridBag, achatUnit, 2, 0, 1, 1,0);

        JLabel honorairesLbl = GUIWidgetFactory.createLabel("<html>Honoraires pay&#233s par le patient :</html>");
        addWidgetInGridBagPanel(inputPanel, gridBag, honorairesLbl, 0, 1, 1, 1,0);

        m_honorairesTxt = GUIWidgetFactory.createTextField();
        m_honorairesTxt.setPreferredSize(txtFieldsDimension);
        m_honorairesTxt.getDocument().addDocumentListener(new TxtInputListener());
        AbstractDocument honorairesTxtDoc = (AbstractDocument)m_achatTxt.getDocument();
        honorairesTxtDoc.setDocumentFilter(new NumbersFilter());
        addWidgetInGridBagPanel(inputPanel, gridBag, m_honorairesTxt, 1, 1, 1, 1,0);

        JLabel honorairesUnit = GUIWidgetFactory.createLabel("<html>&#8364</html>");
        addWidgetInGridBagPanel(inputPanel, gridBag, honorairesUnit, 2, 1, 1, 1,0);

        JLabel pourcentageStructureLbl = GUIWidgetFactory.createLabel("<html>% de co&#251ts de structure :</html>");
        addWidgetInGridBagPanel(inputPanel, gridBag, pourcentageStructureLbl, 0, 2, 1, 1,0);

        m_pourcentageStructureTxt = GUIWidgetFactory.createTextField();
        m_pourcentageStructureTxt.setPreferredSize(txtFieldsDimension);
        m_pourcentageStructureTxt.setEditable(false);
        m_pourcentageStructureTxt.setText(Double.valueOf(m_devisController.getPourcentageStructure()).toString());
        AbstractDocument percentageStructureTxtDoc = (AbstractDocument)m_pourcentageStructureTxt.getDocument();
        percentageStructureTxtDoc.setDocumentFilter(new NumbersFilter());
        addWidgetInGridBagPanel(inputPanel, gridBag, m_pourcentageStructureTxt, 1, 2, 1, 1,0);

        JLabel pourcentageStructureUnit = GUIWidgetFactory.createLabel("%");
        addWidgetInGridBagPanel(inputPanel, gridBag, pourcentageStructureUnit, 2, 2, 1, 1,0);

        m_changePourcentageStructureBtn = GUIWidgetFactory.createButton(CHANGE_POURCENTAGE_STRUCTURE_BUTTON_LABEL);
        m_changePourcentageStructureBtn.addActionListener(new ActionListener(){
        	private boolean beingModified = false;
        	public void actionPerformed(ActionEvent e){
        		beingModified = !beingModified;
        		if (beingModified)
        			reactToChangePourcentageStructureButton();
        		else
        			reactToValidatePourcentageStructureButton();
        	}
        });
        addWidgetInGridBagPanel(inputPanel, gridBag, m_changePourcentageStructureBtn, 3, 2, 1, 1, 0);
        
        m_computeBtn = GUIWidgetFactory.createButton("<html>Calculer la r&#233partition</html>");
        m_computeBtn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		reactToComputeButton();
        	}
        });
        addWidgetInGridBagPanel(inputPanel, gridBag, m_computeBtn, 0, 3, 2, 1,100);
        
        return inputPanel; 
	}
	private JPanel buildOutputPanel()
	{
        JPanel outputPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        outputPanel.setLayout(gridBag);

        // libelle de l'acte
        JLabel actLabelTitleLbl = GUIWidgetFactory.createLabel("<html>Libell&#233 de l'acte :</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, actLabelTitleLbl, 0, 0, 1, 1,0);        
        m_actLabelLbl = GUIWidgetFactory.createLabel("<html>" + m_devisController.getActLabel() + "</html>");
        int actLabelWidth = 300;
        m_actLabelLbl.setFont(new Font(getDefaultFontName(), Font.BOLD, m_actLabelLbl.getFont().getSize()));
        m_actLabelLbl.setPreferredSize(getPreferredSize(m_actLabelLbl.getText(), true, actLabelWidth));
        addWidgetInGridBagPanel(outputPanel, gridBag, m_actLabelLbl, 1, 0, 3, 1,0);
        
        // code de l'acte
        JLabel actCodeTitleLbl = GUIWidgetFactory.createLabel("<html>Code de l'acte :</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, actCodeTitleLbl, 0, 1, 1, 1,0);        
        m_actCodeLbl = GUIWidgetFactory.createLabel(m_devisController.getActCode());
        addWidgetInGridBagPanel(outputPanel, gridBag, m_actCodeLbl, 1, 1, 1, 1,0);
        
        // prix de vente du dispositif medical
        JLabel prixDispositifTitleLbl = GUIWidgetFactory.createLabel("<html>Prix de vente du dispositif m&#233dical <strong>(A)</strong> :</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, prixDispositifTitleLbl, 0, 2, 1, 1,0);
        m_prixDispositifValueLbl = new OutputLabel();
        addWidgetInGridBagPanel(outputPanel, gridBag, m_prixDispositifValueLbl, 1, 2, 1, 1,0);
        JLabel prixDispositifUnit = GUIWidgetFactory.createLabel("<html>&#8364</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, prixDispositifUnit, 2, 2, 1, 1,0);

        // montant de la prestation de soins
        JLabel prestationSoinsTitleLbl = GUIWidgetFactory.createLabel("<html>Montant des prestations de soins <strong>(B1)</strong> :</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, prestationSoinsTitleLbl, 0, 3, 1, 1,0);
        m_prestationSoinsValueLbl = new OutputLabel();
        addWidgetInGridBagPanel(outputPanel, gridBag, m_prestationSoinsValueLbl, 1, 3, 1, 1,0);
        JLabel prestationSoinsUnit = GUIWidgetFactory.createLabel("<html>&#8364</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, prestationSoinsUnit, 2, 3, 1, 1,0);

        // charges de structure
        JLabel chargesStructureTitleLbl = GUIWidgetFactory.createLabel("<html>Charges de structure <strong>(B2)</strong> :</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, chargesStructureTitleLbl, 0, 4, 1, 1,0);
        m_chargesStructureValueLbl = new OutputLabel();
        addWidgetInGridBagPanel(outputPanel, gridBag, m_chargesStructureValueLbl, 1, 4, 1, 1,0);
        JLabel chargesStructureUnit = GUIWidgetFactory.createLabel("<html>&#8364</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, chargesStructureUnit, 2, 4, 1, 1,0);
        
        // honoraires
        JLabel honorairesTitleLbl = GUIWidgetFactory.createLabel("<html>Montant des honoraires <strong>(C)</strong> :</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, honorairesTitleLbl, 0, 5, 1, 1,0);
        m_honorairesValueLbl = new OutputLabel();
        addWidgetInGridBagPanel(outputPanel, gridBag, m_honorairesValueLbl, 1, 5, 1, 1,0);
        JLabel honorairesUnit = GUIWidgetFactory.createLabel("<html>&#8364</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, honorairesUnit, 2, 5, 1, 1,0);

        // base de remboursement
        JLabel remboursementTitleLbl = GUIWidgetFactory.createLabel("<html>Base de remboursement <strong>(D)</strong> :</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, remboursementTitleLbl, 0, 6, 1, 1,0);
        m_remboursementValueLbl = new OutputLabel(Double.toString(m_devisController.getActPrice()));
        addWidgetInGridBagPanel(outputPanel, gridBag, m_remboursementValueLbl, 1, 6, 1, 1,0);
        JLabel remboursementUnit = GUIWidgetFactory.createLabel("<html>&#8364</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, remboursementUnit, 2, 6, 1, 1,0);

        // montant non remboursable
        JLabel nonRemboursableTitleLbl = GUIWidgetFactory.createLabel("<html>Montant non remboursable <strong>(E)</strong> :</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, nonRemboursableTitleLbl, 0, 7, 1, 1,0);
        m_nonRemboursableValueLbl = new OutputLabel();
        addWidgetInGridBagPanel(outputPanel, gridBag, m_nonRemboursableValueLbl, 1, 7, 1, 1,0);
        JLabel nonRemboursableUnit = GUIWidgetFactory.createLabel("<html>&#8364</html>");
        addWidgetInGridBagPanel(outputPanel, gridBag, nonRemboursableUnit, 2, 7, 1, 1,0);

        return outputPanel;
	}
	private static java.awt.Dimension getPreferredSize(String html, boolean width, int prefSize)
	{
		JLabel resizer = GUIWidgetFactory.createLabel(html);
		View view = (View) resizer.getClientProperty(BasicHTML.propertyKey);
		view.setSize(width ? prefSize : 0, width ? 0 : prefSize);
		float w = view.getPreferredSpan(View.X_AXIS);
		float h = view.getPreferredSpan(View.Y_AXIS) + 30; // sinon certains labels sont rognés
		return new Dimension((int) Math.ceil(w), (int) Math.ceil(h));
	}
	private class OutputLabel extends JLabel
	{
		OutputLabel()
		{
			init();
		}
		OutputLabel(String s)
		{
			super(s);
			init();
		}
		private void init()
		{
	        setBorder(BorderFactory.createLineBorder(Color.GRAY));
	        GUIWidgetFactory.adapt(this);
	        setPreferredSize(new Dimension(70, 30));
	        setHorizontalAlignment(JLabel.RIGHT);
		}
	}
	public void activate(JFrame frame)
	{
		super.activate(frame);
		m_achatTxt.requestFocus();
	}
	private void reactToComputeButton()
	{
		double achat = m_achatTxt.getText().length() > 0 ? Double.parseDouble(m_achatTxt.getText()) : 0;
		double honoraires = m_honorairesTxt.getText().length() > 0 ? Double.parseDouble(m_honorairesTxt.getText()) : 0;
		double pourcentageStructure = m_pourcentageStructureTxt.getText().length() > 0 ? Double.parseDouble(m_pourcentageStructureTxt.getText()) : 0;

		String dispositifMedical = Double.valueOf(m_devisController.computeDispositifMedical(achat, honoraires, pourcentageStructure)).toString();
		m_prixDispositifValueLbl.setText(dispositifMedical);
		String prestationsSoin = Double.valueOf(m_devisController.computePrestationsSoin(achat, honoraires, pourcentageStructure)).toString();
		m_prestationSoinsValueLbl.setText(prestationsSoin);
		String chargesStructure = Double.valueOf(m_devisController.computeChargeStructure(honoraires, pourcentageStructure)).toString();
		m_chargesStructureValueLbl.setText(chargesStructure);
		m_honorairesValueLbl.setText(Double.valueOf(honoraires).toString());
		String nonRemboursable = Double.valueOf(m_devisController.computeMontantNonRemboursable(honoraires)).toString();
		m_nonRemboursableValueLbl.setText(nonRemboursable);
	}
	private void reactToTxtInputChange()
	{
		m_prixDispositifValueLbl.setText("");
		m_prestationSoinsValueLbl.setText("");
		m_chargesStructureValueLbl.setText("");
		m_honorairesValueLbl.setText("");
		m_nonRemboursableValueLbl.setText("");
	}
	private void reactToChangePourcentageStructureButton()
	{
		m_changePourcentageStructureBtn.setText("OK");
		m_achatTxt.setEnabled(false);
		m_honorairesTxt.setEnabled(false);
		m_computeBtn.setEnabled(false);
		m_pourcentageStructureTxt.setEditable(true);
		m_pourcentageStructureTxt.requestFocus();
		m_pourcentageStructureTxt.setSelectionStart(0);
		m_pourcentageStructureTxt.setSelectionEnd(m_pourcentageStructureTxt.getText().length());
		m_prixDispositifValueLbl.setText("");
		m_prestationSoinsValueLbl.setText("");
		m_chargesStructureValueLbl.setText("");
	}
	private void reactToValidatePourcentageStructureButton()
	{
		String oldPourcentageStructure = Double.valueOf(m_devisController.getPourcentageStructure()).toString();
		String newPourcentageStructure = m_pourcentageStructureTxt.getText();
		if (!Double.valueOf(oldPourcentageStructure).equals(Double.valueOf(newPourcentageStructure)))
		{
			String confirmationMessage = "<html>Etes-vous s&#251r de vouloir modifier le % de charge de structure <br>de " + oldPourcentageStructure +"% &#224 " + newPourcentageStructure + "% ?</html>";
			int answer = JOptionPane.showConfirmDialog(null, confirmationMessage, "Confirmation", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION)
			{
				m_devisController.setPourcentageStructure(Double.parseDouble(m_pourcentageStructureTxt.getText()));
			}
			else
			{
				m_pourcentageStructureTxt.setText(oldPourcentageStructure);
			}
		}
		m_changePourcentageStructureBtn.setText(CHANGE_POURCENTAGE_STRUCTURE_BUTTON_LABEL);
		m_achatTxt.setEnabled(true);
		m_honorairesTxt.setEnabled(true);
		m_computeBtn.setEnabled(true);
		m_pourcentageStructureTxt.setEditable(false);
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
