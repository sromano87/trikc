package calculator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;


/**
 * Classe MainFrame.
 * This is the Main Frame. It contains all the functionalities of the application.
 * 
 * @author andima
 * 
 * */
public class MainFrame extends JFrame implements Constants {
	
	private final static String[] tableHeader = new String[]{"Exam name", "CFU", "Vote"};
	
	private JTable table;
	private JPanel tablePanel;
	private JPanel internalTablePanel;
	private JLabel averageLabel;
	private JLabel defendThesis;
	private JLabel cfuLabel;
	private JLabel degreeLabel;
	private JButton saveButton;
	public Collection exams;
	private JScrollPane scrollPane;
	
	public double average;
	public int totalCfu;
	public double startDegreeVote;
	public boolean canDefende;
	public float valueF = 0;
	
	public DB db;
	
	private JLabel nexamsLabel;
	
	public MainFrame(){
		super(TITLE + " " + VERSION);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				exitAction();
			}
		});
		
		average = 0.0;
		totalCfu = 0;
		startDegreeVote = 0.0;
		
		db = new DB();
		db.load();
		exams = new ArrayList(db.getExamsList());
		
		init();
		
		pack();
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = dim.width / 4;
		int y = dim.height / 4;
		
		setLocation(x, y);
		setSize(500, 400);
	}
	
	private void init(){
		JPanel panel = new JPanel(new BorderLayout(1, 1));
		
		panel.add(createExamPanel(), BorderLayout.CENTER);
		panel.add(createSouthPanel(), BorderLayout.SOUTH);
		
		setContentPane(panel);
	}
	
	
	private JPanel createExamPanel(){
		JPanel panel = new JPanel(new GridLayout(1, 1));
		panel.setBorder(BorderFactory.createTitledBorder("Exams"));
		
		table = createTable(db.getExamsList());
		scrollPane = new JScrollPane(table);
		tablePanel = new JPanel(new GridLayout(1, 1));
		internalTablePanel = new JPanel(new BorderLayout(0, 0));
		internalTablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
		internalTablePanel.add(scrollPane, BorderLayout.CENTER);
		tablePanel.add(internalTablePanel);
		JButton addButton = new JButton(" Add ", new ImageIcon("images"+java.io.File.separator+"add.jpg"));
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				insertAction();
			}
		});
		
		
		JButton removeButton = new JButton(" Remove ", new ImageIcon("images"+java.io.File.separator+"remove.jpg"));
		removeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				removeAction();
			}
		});
		JButton removeAllButton = new JButton(" Remove all ");
		removeAllButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				removeAllAction();
			}
		});
		
		JButton loadExamsPositive = new JButton(" Load positive exams ");
		loadExamsPositive.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				updateTableAndStatistics(db.getOnlyPositiveExams());
				saveButton.setEnabled(false);
			}
		});
		
		JButton reloadButton = new JButton(" Load all data from the database ");
		reloadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				updateTableAndStatistics(db.getExamsList());
				saveButton.setEnabled(false);
			}
		});
		JButton aboutButton = new JButton(" About AveCalc ");
		aboutButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				new AboutDialog(MainFrame.this).setVisible(true);
			}
		});
		JPanel buttonPanel = new JPanel(new GridLayout(7, 1));
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(removeAllButton);
		// buttonPanel.add(new JPanel());
		buttonPanel.add(loadExamsPositive);
		buttonPanel.add(reloadButton);
		buttonPanel.add(aboutButton);
		
		JPanel p2 = new JPanel();
		p2.add(buttonPanel);
		
		JPanel labelPanel = new JPanel(new GridLayout(5, 1));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		
		Iterator it = db.getExamsList().iterator();
		int totMakedExams = 0;
		Exam e;
		while(it.hasNext()){
			e = (Exam) it.next();
			if(e.isMaked())
				totMakedExams++;
		}
		
		Iterator it2 = db.getExamsList().iterator();
		int totSufficientExams = 0;
		Exam e1;
		while(it2.hasNext()){
			e1= (Exam) it2.next();
			if(e1.isMaked() && e1.getVote() >= 18)
				totSufficientExams++;
		}
		
		canDefende = defendThesis(totSufficientExams,  db.getCfu());
		averageLabel = new JLabel("<html>Average: <b>"+nf.format(db.getAverage())+"</b></html>", JLabel.CENTER);
		cfuLabel = new JLabel("<html>Total number of CFU: <b>"+db.getCfu()+"</b></html>", JLabel.CENTER);
		degreeLabel = new JLabel("<html>Degree vote: <b>"+nf.format(db.getDegree())+"</b></html>", JLabel.CENTER);
		nexamsLabel = new JLabel("<html> number of exams: <b>"+totMakedExams+"</b></html>", JLabel.CENTER);
		defendThesis = new JLabel("<html> Defend thesis: <b>"+canDefende+"</b></html>", JLabel.CENTER);
		labelPanel.add(averageLabel);
		labelPanel.add(cfuLabel);
		labelPanel.add(nexamsLabel);
		labelPanel.add(degreeLabel);
		labelPanel.add(defendThesis);
		
		JPanel p3 = new JPanel();
		p3.add(labelPanel);
		p3.setBorder(BorderFactory.createTitledBorder("Statistics"));
		
		JPanel eastPanel = new JPanel(new GridLayout(2, 1));
		eastPanel.add(p2);
		eastPanel.add(p3);
		
		JPanel p = new JPanel(new BorderLayout(1, 1));
		p.add(tablePanel, BorderLayout.CENTER);
		p.add(eastPanel, BorderLayout.EAST);
		
		panel.add(p);
		
		return panel;
	}
	
	private Boolean defendThesis(int totMakedExams, int cfu) {
		if (totMakedExams >= 12 && cfu >= 90)
			return true;
		else
			return false;
	}
	
	private JTable createTable(Collection c){
		Iterator it = c.iterator();
		Object obj[][] = new Object[c.size()][tableHeader.length];
		Exam exam = null;
		for(int j = 0; it.hasNext(); j++){
			exam = (Exam) it.next();
			obj[j][0] = exam.getName();
			obj[j][1] = exam.getCfu()+"";
			if(exam.isMaked())
				obj[j][2] = exam.getVote()+(exam.isLode() ? " cum laude " : "");
			else obj[j][2] = "---";
		}
		
		MyTableModel model = new MyTableModel(tableHeader, obj);
		TableSorter sorter = new TableSorter(model);
		table = new JTable(sorter);
		//	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sorter.setTableHeader(table.getTableHeader());
		
		TableColumn column = null;
		for (int i = 0; i < 3; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(200);
			} else {
				column.setPreferredWidth(40);
			}
		}
		return table;
	}
	
	public void updateTable(Collection c) {
		internalTablePanel.remove(scrollPane);
		internalTablePanel.remove(table.getTableHeader());
		table = createTable(c);
		scrollPane.setViewportView(table);
		internalTablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
		internalTablePanel.add(scrollPane, BorderLayout.CENTER);
		tablePanel.validate();
		tablePanel.repaint();
	}
	
	private JPanel createSouthPanel() {
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		saveButton = new JButton(" Save ");
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				saveAction();
			}
		});
		JButton button = new JButton(" Exit ");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				exitAction();
			}
		});
		buttonPanel.add(saveButton);
		buttonPanel.add(button);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	public void showMessageError(Exception e) {
		JOptionPane.showMessageDialog(
				null,
				"Error:\n"+e.getMessage()+"\n" +
				"Operation not executed.",
				"Error",
				JOptionPane.ERROR_MESSAGE);
	}
	
	public boolean insertExam(Exam exam){
		if(containsExamName(exam.getName()))
			return false;
		exams.add(exam);
		updateTableAndStatistics(exams);
		saveButton.setEnabled(true);
		return true;
	}
	
	public boolean removeExam(String name){
		Exam[] es = (Exam[]) exams.toArray(new Exam[]{});
		for(int i = 0; i < es.length; i++)
			if(es[i].getName().equals(name)) {
				exams.remove(es[i]);
				return true;
			}
		return false;
	}
	
	public boolean containsExamName(String name){
		Exam[] es = (Exam[]) exams.toArray(new Exam[]{});
		for(int i = 0; i < es.length; i++)
			if(es[i].getName().equalsIgnoreCase(name))
				return true;
		return false;
	}
	
	public void removeAction(){
		/*
		int column;
		int row_index =  table.getSelectedRow();
		
		String name = "";
		String cfu = "";
		String vote = "";
		
		for (int i = 0; i <= row_index; i++) {
			for (column = 0; column < 3; column++) {
				if (table.getColumnName(column).equals(tableHeader[0])) {
					name = (String) table.getValueAt(row_index, column);
				}
			}
			removeExam(name);
		}
		*/
		
		String name = JOptionPane.showInputDialog("Insert the exam name");
		removeExam(name);
		updateTableAndStatistics(exams);
		
		saveButton.setEnabled(true);
	}
	
	public static String inputOutput(String message) {
		System.out.println(message);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String returnString = "";
		try {
			returnString = br.readLine();
		} catch (IOException e) {
			System.out.println("Error!");
		}
		return returnString;
	}
	
	
	public void removeAllAction(){
		int column;
		
		if(exams.size() == 1){
			exams = new ArrayList();
			updateTableAndStatistics(exams);
			saveButton.setEnabled(true);
			return;
		}
		
		String name = "";
		String cfu = "";
		String vote = "";
		
		for (int j = 0; j < table.getRowCount(); j++) {
			for (column = 0; column < 3; column++) {
				if (table.getColumnName(column).equals(tableHeader[0])) {
					name = (String) table.getValueAt(j, column);
				}
			}
			removeExam(name);
		}
		
		updateTableAndStatistics(exams);
		
		saveButton.setEnabled(true);
	}
	
	public Collection getExams() {
		return exams;
	}
	
	public void updateStatistics(Exam[] exams){
		double total = 0;
		totalCfu = 0;
		int totExam = 0;
		int totSufficientExams = 0;
		for(int i = 0; i < exams.length; i++){
			if(!exams[i].isMaked())
				continue;
			System.out.println(exams[i]);
			
			if(exams[i].getVote()>=18) {
				totSufficientExams++;
				total += exams[i].getVote() * exams[i].getCfu();
				totalCfu += exams[i].getCfu();
			}
			totExam++;
		}
		
		average = (totalCfu == 0) ? 0.0 : total / totalCfu;
		startDegreeVote = ComputeStartDegreeVote(exams);
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		
		canDefende = defendThesis(totSufficientExams, totalCfu);
		
		averageLabel.setText("<html>Average: <b>"+nf.format(average)+"</b></html>");
		cfuLabel.setText("<html>Total number of CFU: <b>" + totalCfu+"</b></html>");
		nexamsLabel.setText("<html>number of exams: <b>"+totExam+"</b></html>");
		degreeLabel.setText("<html>Degree Vote: <b>"+nf.format(startDegreeVote)+"</b></html>");	
		defendThesis.setText("<html> Defend thesis: <b>"+canDefende+"</b></html>");
	}
	
	private double ComputeStartDegreeVote(Exam[] exams) {
		List positive_exams = new LinkedList();
		int tot_laude = 0;
		for(int i = 0; i < exams.length; i++) {
			if (exams[i].isLode())
				tot_laude ++;
			if (exams[i].getVote() >= 18) {
				positive_exams.add(Exam.getInstance(exams[i].getName(), exams[i].getCfu()+"", exams[i].getVote()+""));
			}	
		}
		
	 if (positive_exams.size() >=2) {
		int vote_min = 30;
		int cont = 0;
		
		for(int i = 0; i < positive_exams.size(); i++) {
				if (((Exam)positive_exams.get(i)).getVote() < vote_min) {
					vote_min = ((Exam)positive_exams.get(i)).getVote();
					cont = i;
				}
		}
		double total = 0;
		int totalCfu = 0;
		for(int i = 0; i < positive_exams.size(); i++){
		  if (i != cont) {	
			if(!((Exam)positive_exams.get(i)).isMaked())
				continue;
				total += ((Exam)positive_exams.get(i)).getVote() * ((Exam)positive_exams.get(i)).getCfu();
				totalCfu += ((Exam)positive_exams.get(i)).getCfu();
		  }
		}
		double newAverage = (totalCfu == 0) ? 0.0 : total / totalCfu;
		double StartDegreeVote = (newAverage * 11) / 3;
		
		if (tot_laude >=3 ) 
			StartDegreeVote = StartDegreeVote + 1;
		
		return StartDegreeVote;
		
	 } else {
		 if (positive_exams.size() == 1) {
			 double value = (double)(((Exam)positive_exams.get(0)).getVote() * 11) / 3;
			 return value;
		 } else {
			 return 0;
		 }
	 }
	}

	private void insertAction(){
		new InsertDialog(this).setVisible(true);
		updateTable(exams);
	}
	
	private void exitAction() {
		if(saveButton.isEnabled()){
			int r = JOptionPane.showConfirmDialog(this, "Do you want exit without saving?", TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(r == JOptionPane.NO_OPTION)
				return;
		}
		System.exit(0);
	}
	
	public void saveAction(){
		db.save(exams, average, totalCfu, startDegreeVote);
		saveButton.setEnabled(false);
	}
	
	public void updateTableAndStatistics(Collection exams) {
		updateTable(exams);
		updateStatistics((Exam[]) exams.toArray(new Exam[]{}));
		this.exams = new ArrayList(exams);
	}
	
	class MyTableModel extends AbstractTableModel {
		private String[] columnNames;
		private Object[][] data;
		
		public MyTableModel(String[] header, Object[][] content){
			data = content;
			columnNames = header;
		}
		
		public int getColumnCount() {
			return columnNames.length;
		}
		
		public int getRowCount() {
			return data.length;
		}
		
		public String getColumnName(int col) {
			return columnNames[col];
		}
		
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}
		
		
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}
		
		public boolean isCellEditable(int row, int col) {
			return true;
		}
		
		public void setValueAt(Object value, int row, int col) {
			switch(col) {
			case 0:	// exam name
				if(containsExamName((String) value))
					JOptionPane.showMessageDialog(MainFrame.this,
							"The value that you have inserted is not valid.\n" +
							"The Exam has been already inserted.",
							"Error",
							JOptionPane.ERROR_MESSAGE);
				return;
			case 1:	// CFU
				
				try{
					int i = Integer.parseInt((String)value);
					if(i <= 0)
						throw new NumberFormatException();
				} catch(NumberFormatException nfe){
					JOptionPane.showMessageDialog(MainFrame.this,
							"The value that you have inserted is not valid.\n" +
							" Remenber:\n" +
							"<html>Cfu must be a <b>positive</b> number.</html>\n" +
							"<html>The vote must be included between <b>0</b> and <b>30</b> (or equal).</html>\n" +
							"<html> laude can be added only to <b>30</b>.</html>\n" +
							"\nOperation not executed.",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				break;
			case 2: // vote
				try{
					if(value.equals("30 cum laude") || value.equals("---"))
						break;
					
					int i = Integer.parseInt((String)value);
					if(i < 0 || i > 30)
						throw new NumberFormatException();
				} catch(NumberFormatException nfe){
					
					JOptionPane.showMessageDialog(MainFrame.this,
							"The value that you have inserted is not valid." +
							" Remenber:\n" +
							"<html>Cfu must be a <b>positive</b> number.</html>\n" +
							"<html>The vote must be included between <b>0</b> and <b>30</b> (or equal).</html>\n" +
							"<html> laude can be added only to <b>30</b>.</html>\n" +
							"\nOperation not executed.",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				break;
			default:
				throw new IllegalArgumentException();
			}
			
			// old exam
			Exam olde = Exam.getInstance((String) data[row][0], (String) data[row][1], (String) data[row][2]);
			
			Exam newe = null;
			switch(col){
			case 0:
				newe = Exam.getInstance((String)value, (String) data[row][1], (String) data[row][2]);
				break;
			case 1:
				newe = Exam.getInstance((String) data[row][0], (String) value, (String) data[row][2]);
				break;
			case 2:
				newe = Exam.getInstance((String) data[row][0], (String) data[row][1], (String) value);
				break;
				
			}
			
			data[row][col] = value;
			fireTableCellUpdated(row, col);
			
			exams.remove(olde);
			exams.add(newe);
			updateStatistics((Exam[])exams.toArray(new Exam[]{}));
			saveButton.setEnabled(true);
		}
		
	}
	
}
