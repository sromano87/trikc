package changeReqs_junit;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

import calculator.Exam;
import calculator.MainFrame;
import junit.framework.TestCase;

public class Test_ChangeReqs1 extends TestCase {
	
	public MainFrame f;
	
	protected void setUp() {
		f = new MainFrame();
		f.removeAllAction();
		f.saveAction();
		f.db.load();
	}
	
	public void testOnlyPositive() {
		Exam E1 = new Exam();
		E1 = E1.getInstance("DB", "15", "30");
		f.insertExam(E1);
		f.saveAction();
		f.db.load();
		
		Exam E2 = new Exam();
		E2 = E2.getInstance("SSI", "12", "24");
		f.insertExam(E2);
		f.saveAction();
		f.db.load();
		
		assertEquals(2, f.db.getOnlyPositiveExams().size());
	}
	
	public void testPositiveAndNegative() {
		Exam E1 = new Exam();
		E1 = E1.getInstance("DB", "15", "30");
		f.insertExam(E1);
		f.saveAction();
		f.db.load();
		
		Exam E2 = new Exam();
		E2 = E2.getInstance("SSI", "12", "18");
		f.insertExam(E2);
		f.saveAction();
		f.db.load();
		
		Exam E3 = new Exam();
		E3 = E3.getInstance("Comp", "18", "11");
		f.insertExam(E3);
		f.saveAction();
		f.db.load();
		
		assertEquals(2, f.db.getOnlyPositiveExams().size());
	
	}
	public void testLimitCases() {
		Exam E1 = new Exam();
		E1 = E1.getInstance("DB", "15", "0");
		f.insertExam(E1);
		f.saveAction();
		f.db.load();
		
		Exam E2 = new Exam();
		E2 = E2.getInstance("SSI", "12", "30 cum laude");
		f.insertExam(E2);
		f.saveAction();
		f.db.load();
		
		Exam E3 = new Exam();
		E3 = E3.getInstance("Comp", "18", "18");
		f.insertExam(E3);
		f.saveAction();
		f.db.load();
		
		Exam E4 = new Exam();
		E4 = E4.getInstance("Software", "10", "17");
		f.insertExam(E4);
		f.saveAction();
		f.db.load();
		
		assertEquals(2, f.db.getOnlyPositiveExams().size());
	
	}
}
