import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public abstract class GUICoordinator
{	
	public GUICoordinator(GUIGlobalCoordinator globalCoordinator) {
		m_globalCoordinator = globalCoordinator;
	}
	
	public void activate(JFrame frame)
	{
		m_scrollPane = new JScrollPane(m_panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(m_scrollPane);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}
	public void inactivate(JFrame frame)
	{
		frame.remove(m_scrollPane);
	}

	protected void addWidgetInGridBagPanel(JPanel panel, GridBagLayout gridBag, JComponent component, int gridx, int gridy, int gridwidth, int gridheight, int ipadx)
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
//      localPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        gridBag.setConstraints(localPanel, cons);
        panel.add(localPanel);
	}
	protected String getDefaultFontName()
	{
		JLabel lblDummy = GUIWidgetFactory.createLabel();
		return lblDummy.getFont().getName();
	}
	
	protected GUIGlobalCoordinator getGlobalCoordinator()
	{
		return m_globalCoordinator;
	}
	protected void setPanel(JPanel panel)
	{
		m_panel = panel;
	}
	protected JPanel createStandardHeaderPanel(String screenTitle)
	{
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JLabel titleLabel = GUIWidgetFactory.createLabel(screenTitle);
        titleLabel.setFont(new Font(getDefaultFontName(), Font.BOLD + Font.ITALIC, 36));
        titleLabel.setForeground(Color.gray);
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);

//        JLabel paddingLabel = GUIWidgetFactory.createLabel();
//        paddingLabel.setPreferredSize(new Dimension(10,20));
        JPanel paddingPanel = new JPanel();
//        paddingPanel.add(paddingLabel);

        JPanel paddedTitlePanel = new JPanel();
        paddedTitlePanel.setLayout(new BorderLayout());
        paddedTitlePanel.add(paddingPanel, BorderLayout.NORTH);
        paddedTitlePanel.add(titlePanel, BorderLayout.CENTER);

        JButton backToPreviousScreen = GUIWidgetFactory.createButton("<html>&#8592 Retour</html>");
        backToPreviousScreen.setPreferredSize(new Dimension(100, 40));
        backToPreviousScreen.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		getGlobalCoordinator().backToPreviousScreen();
        	}
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backToPreviousScreen);

        panel.add(paddedTitlePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.WEST);
        return panel;
	}

	private JScrollPane m_scrollPane;
	private JPanel m_panel;
	private GUIGlobalCoordinator m_globalCoordinator;

}
