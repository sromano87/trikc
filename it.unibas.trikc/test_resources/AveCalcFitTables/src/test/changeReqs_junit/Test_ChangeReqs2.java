package changeReqs_junit;

import calculator.Exam;
import calculator.MainFrame;
import fit_tests.Populate;
import junit.framework.TestCase;

public class Test_ChangeReqs2 extends TestCase {
	
private MainFrame f;
	
	protected void setUp() {
		f = new MainFrame();
		f.removeAllAction();
		f.saveAction();
		
		Exam E1 = new Exam();
		E1 = E1.getInstance("DB", "15", "30");
		f.insertExam(E1);
		f.saveAction();
		
		Exam E2 = new Exam();
		E2 = E2.getInstance("ssi", "12", "24");
		f.insertExam(E2);
		f.saveAction();
		
		Exam E3 = new Exam();
		E3 = E3.getInstance("software", "12", "14");
		f.insertExam(E3);
		f.saveAction();
	}
	
	public void testRemoveExact() {
		f.removeExam("software");
		f.saveAction();
		f.db.load();
		f.updateTableAndStatistics(f.db.getExamsList());
		assertEquals(2, f.db.getExamsList().size());
	}
	
	public void testRemoveOnlyFirstLetter() {
		f.removeExam("Ssi");
		f.saveAction();
		f.db.load();
		f.updateTableAndStatistics(f.db.getExamsList());
		assertEquals(2, f.db.getExamsList().size());
	}
	
	public void testRemoveCaseInsensitive() {
		f.removeExam("db");
		f.saveAction();
		
		f.removeExam("SSI");
		f.saveAction();
		
		f.removeExam("Software1");
		f.saveAction();
		
		f.removeExam("softwar");
		f.saveAction();
		
		f.db.load();
		f.updateTableAndStatistics(f.db.getExamsList());
		assertEquals(1, f.db.getExamsList().size());
	}
}
