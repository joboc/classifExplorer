import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GUIMainMenuCoordinator extends GUICoordinator {
	public GUIMainMenuCoordinator(GUIGlobalCoordinator globalCoordinator)
	{
		super(globalCoordinator);
		initialize();
	}
	public void initialize()
    {
        JPanel menuPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        menuPanel.setLayout(gridBag);
        
        JLabel titleLbl = GUIWidgetFactory.createLabel("Classification");
        titleLbl.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 36));
        addWidgetInGridBagPanel(menuPanel, gridBag, titleLbl, 0, 0, 2, 1,0);

        JLabel titleLbl2 = GUIWidgetFactory.createLabel("Explorer");
        titleLbl2.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 36));
        addWidgetInGridBagPanel(menuPanel, gridBag, titleLbl2, 0, 1, 2, 1,0);

        JPanel titleLblEmpty = new JPanel();
        titleLblEmpty.setPreferredSize(new Dimension(10, 30));
        addWidgetInGridBagPanel(menuPanel, gridBag, titleLblEmpty, 0, 2, 1, 1,0);
        
        JButton gotoSearch = GUIWidgetFactory.createButton("Recherche d'actes");
        gotoSearch.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		getGlobalCoordinator().reactToMainMenuGotoSearch();
        	}
        });
        addWidgetInGridBagPanel(menuPanel, gridBag, gotoSearch, 0, 3, 1, 1,100);

        JButton exit = GUIWidgetFactory.createButton("Quitter");
        exit.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		getGlobalCoordinator().reactToExitCommand();
        	}
        });
        addWidgetInGridBagPanel(menuPanel, gridBag, exit, 0, 5, 1, 1,100);

        JPanel titleLblEmpty2 = new JPanel();
        titleLblEmpty2.setPreferredSize(new Dimension(10, 160));
        addWidgetInGridBagPanel(menuPanel, gridBag, titleLblEmpty2, 0, 6, 1, 1,0);
        
        JLabel versionLbl = GUIWidgetFactory.createLabel("<html>Bas&#233 sur la version 35 de la CCAM applicable au 1er juin 2014</html>");
        versionLbl.setFont(new Font(getDefaultFontName(), Font.PLAIN, 10));
        addWidgetInGridBagPanel(menuPanel, gridBag, versionLbl, 0, 7, 1, 1,0);

        setPanel(menuPanel);
    }
}
