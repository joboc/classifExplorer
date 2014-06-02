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
        gotoSearch.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		getGlobalCoordinator().reactToMainMenuGotoSearch();
        	}
        });
        addWidgetInGridBagPanel(menuPanel, gridBag, gotoSearch, 0, 3, 1, 1,100);

        JButton goToRepartition = new JButton("Répartition des frais");
        goToRepartition.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		getGlobalCoordinator().reactToMainMenuGotoRepartition();
        	}
        });
        addWidgetInGridBagPanel(menuPanel, gridBag, goToRepartition, 0, 4, 1, 1,100);

        JButton exit = new JButton("Quitter");
        exit.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		getGlobalCoordinator().reactToExitCommand();
        	}
        });
        addWidgetInGridBagPanel(menuPanel, gridBag, exit, 0, 5, 1, 1,100);

        setPanel(menuPanel);
    }
}
