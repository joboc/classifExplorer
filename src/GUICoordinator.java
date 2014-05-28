import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public abstract class GUICoordinator
{	
	public GUICoordinator(GUIGlobalCoordinator globalCoordinator) {
		m_globalCoordinator = globalCoordinator;
	}
	
	public void activate(JFrame frame)
	{
		frame.add(m_panel);
		m_panel.revalidate();
	}
	public void inactivate(JFrame frame)
	{
		frame.remove(m_panel);
	}
	public void onExit()
	{
		
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
		JLabel lblDummy = new JLabel();
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

	private JPanel m_panel;
	private GUIGlobalCoordinator m_globalCoordinator;

}
