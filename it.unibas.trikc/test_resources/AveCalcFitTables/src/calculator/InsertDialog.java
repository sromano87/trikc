package calculator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 * Classe InsertDialog.
 * To insert a new exam
 * 
 * @author andima
 */
public class InsertDialog extends JDialog {
	
	private MainFrame frame;
	
	private JTextField examNameJTF;
	
	private JTextField examCfuJTF;
	
	private JTextField examVoteJTF;
	
	private JCheckBox examLodeJCB;
	
	private JCheckBox examJCB;
	
	public InsertDialog(MainFrame parent) {
		super(parent, "", true);
		setResizable(false);
		
		frame = parent;
		init();
		pack();
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = dim.width / 4;
		int y = dim.height / 4;
		
		setLocation(x, y);
	}
	
	private void init() {
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		
		panel.add(createCenterPanel(), BorderLayout.CENTER);
		panel.add(createSouthPanel(), BorderLayout.SOUTH);
		
		setContentPane(panel);
	}
	
	private JPanel createCenterPanel() {
		JPanel panel = new JPanel(new BorderLayout(1, 1));
		
		JLabel label1 = new JLabel("Exam Name: ");
		JLabel label2 = new JLabel("CFU: ");
		
		examNameJTF = new JTextField(15);
		examCfuJTF = new JTextField(5);
		examVoteJTF = new JTextField(5);
		examLodeJCB = new JCheckBox("Laude");
		JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		row2.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
		JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		row3.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		row1.add(label1);
		row1.add(examNameJTF);
		
		row2.add(label2);
		row2.add(examCfuJTF);
		
		examJCB = new JCheckBox("Vote");
		examJCB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(examJCB.isSelected()){
					examVoteJTF.setEnabled(true);
					examLodeJCB.setEnabled(true);
				} else {
					examVoteJTF.setEnabled(false);
					examLodeJCB.setEnabled(false);
				}
			}
		});
		examJCB.setSelected(false);
		examVoteJTF.setEnabled(false);
		examLodeJCB.setEnabled(false);
		
		row3.add(examJCB);
		row3.add(examVoteJTF);
		row3.add(examLodeJCB);
		
		JLabel title = new JLabel("Insert new exam: ", JLabel.LEFT);
		Font f = title.getFont();
		
		title.setFont(new Font(f.getName(), Font.BOLD, f.getSize() + 2));
		
		JPanel p2 = new JPanel(new GridLayout(3, 1));
		p2.setBorder(BorderFactory.createEtchedBorder());
		p2.add(row1);
		p2.add(row2);
		p2.add(row3);
		
		panel.add(title, BorderLayout.NORTH);
		panel.add(p2, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createSouthPanel() {
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton button1 = new JButton(" Insert ");
		JButton button2 = new JButton(" Close ");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				insertAction();
			}
		});
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				cancelAction();
			}
		});
		buttonPanel.add(button1);
		buttonPanel.add(button2);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private void cancelAction() {
		setVisible(false);
		dispose();
	}
	
	private void insertAction() {
		Exam e = new Exam();
		try {
			e.setName(examNameJTF.getText());
			e.setCfu(Integer.parseInt(examCfuJTF.getText()));
			if(!examJCB.isSelected())
				e.setMaked(false);
			else {
				e.setMaked(true);
				e.setVote(Integer.parseInt(examVoteJTF.getText()));
				e.setLode(examLodeJCB.isSelected());
			}
		} catch (NumberFormatException nef) {
			JOptionPane.showMessageDialog(this, "NumberFormatException",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(this, "the value that you have inserted is not valid." +
					" Remenber:\n" +
					"<html>Cfu must be a <b>positive</b> number.</html>\n" +
					"<html>The vote must be included between <b>0</b> e <b>30</b> (or equal).</html>\n" +
					"<html> laude can be added only to a vote <b>equals to </b>30.</html>\n" +
					"\nOperation not executed.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(frame.insertExam(e))
			cancelAction();
		else JOptionPane.showMessageDialog(this, "the value that you have inserted is not valid.\n" +
				"Exam already inserted.",
				"Error",
				JOptionPane.ERROR_MESSAGE); 
	}
}
