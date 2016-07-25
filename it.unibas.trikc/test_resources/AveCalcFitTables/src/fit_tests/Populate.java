package fit_tests;

import calculator.Exam;
import calculator.MainFrame;
import fitnesse.fixtures.RowEntryFixture;

public class Populate extends RowEntryFixture {
	
	static MainFrame f;
	
	public String name;
	public String cfu;
	public String vote = "-1";

	public Populate(){
		super();
		f = new MainFrame();
		f.removeAllAction();
		f.saveAction();
	}
	
	@Override
	public void enterRow() throws Exception {
		try {
			Exam exam = Exam.getInstance(name, cfu, vote);
			f.insertExam(exam);
			f.saveAction();
		} catch (IllegalArgumentException iae) {
			System.out.println("insert: IllegalArgumentException");
		}
	}
}
