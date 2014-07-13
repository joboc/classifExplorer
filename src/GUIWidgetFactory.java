import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class GUIWidgetFactory {
	static public JLabel createLabel()
	{
		return createLabel("");
	}
	static public JLabel createLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.putClientProperty("JComponent.sizeVariant", "large");
		SwingUtilities.updateComponentTreeUI(label);
		//label.setFont(new Font(label.getFont().getName(), Font.PLAIN, label.getFont().getSize() + ADDITIONAL_SIZE));
		return label;
	}
	static public JButton createButton()
	{
		return new JButton("");
	}
	static public JButton createButton(String text)
	{
		JButton button = new JButton(text);
		button.putClientProperty("JComponent.sizeVariant", "large");
		SwingUtilities.updateComponentTreeUI(button);
		//button.setFont(new Font(button.getFont().getName(), Font.PLAIN, button.getFont().getSize() + ADDITIONAL_SIZE));
		return button;
	}
	static public JTextField createTextField()
	{
		return createTextField("");
	}
	static public JTextField createTextField(String text)
	{
		JTextField textField = new JTextField(text);
		textField.putClientProperty("JComponent.sizeVariant", "large");
		SwingUtilities.updateComponentTreeUI(textField);
		//textField.setFont(new Font(textField.getFont().getName(), Font.PLAIN, textField.getFont().getSize() + ADDITIONAL_SIZE));
		return textField;
	}
	static public JList createList()
	{
		JList list = new JList();
		list.putClientProperty("JComponent.sizeVariant", "large");
		SwingUtilities.updateComponentTreeUI(list);
		//list.setFont(new Font(list.getFont().getName(), Font.PLAIN, list.getFont().getSize() + ADDITIONAL_SIZE));
		return list;
	}
	static public void adapt(JComponent component)
	{
		component.putClientProperty("JComponent.sizeVariant", "large");
		SwingUtilities.updateComponentTreeUI(component);
	}
	//static private int ADDITIONAL_SIZE = 4;
}
