package changeReqs_junit;

import java.text.NumberFormat;

import calculator.Exam;
import calculator.MainFrame;
import junit.framework.TestCase;

public class Test_ChangeReqs4 extends TestCase {
	
private MainFrame f;
	
	protected void setUp() {
		f = new MainFrame();
		f.removeAllAction();
		f.saveAction();
		
		Exam E1 = new Exam();
		E1 = E1.getInstance("DB", "10", "30");
		f.insertExam(E1);
		f.saveAction();
		
		Exam E2 = new Exam();
		E2 = E2.getInstance("ssi", "10", "24");
		f.insertExam(E2);
		f.saveAction();
	}
	
	public void testDefendaNumberOfExamsNotSufficient() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software", "12", "28");
		f.insertExam(E3);
		f.saveAction();
		
		assertEquals(3, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		assertEquals(false, f.canDefende);
	}
	
	public void testDefendNumberOfExamsSufficientCFULessThan70() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software 1", "1", "28");
		f.insertExam(E3);
		f.saveAction();
		
		Exam E4 = new Exam();
		E4 = E4.getInstance("Software 2", "1", "28");
		f.insertExam(E4);
		f.saveAction();
		
		Exam E5 = new Exam();
		E5 = E5.getInstance("Software 3", "1", "28");
		f.insertExam(E5);
		f.saveAction();
		
		Exam E6 = new Exam();
		E6 = E6.getInstance("Software 4", "1", "28");
		f.insertExam(E6);
		f.saveAction();
		
		Exam E7 = new Exam();
		E7 = E7.getInstance("Software 5", "1", "28");
		f.insertExam(E7);
		f.saveAction();
		
		Exam E8 = new Exam();
		E8 = E8.getInstance("Software 6", "1", "28");
		f.insertExam(E8);
		f.saveAction();
		
		Exam E9 = new Exam();
		E9 = E9.getInstance("Software 7", "1", "28");
		f.insertExam(E9);
		f.saveAction();
		
		Exam E10 = new Exam();
		E10 = E10.getInstance("Software 8", "1", "28");
		f.insertExam(E10);
		f.saveAction();
		
		assertEquals(10, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		assertEquals(false, f.canDefende);
	}
	
	public void testDefendNumberOfExamsSufficientandCFUGreaterThan70() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software 1", "10", "28");
		f.insertExam(E3);
		f.saveAction();
		
		Exam E4 = new Exam();
		E4 = E4.getInstance("Software 2", "10", "28");
		f.insertExam(E4);
		f.saveAction();
		
		Exam E5 = new Exam();
		E5 = E5.getInstance("Software 3", "10", "28");
		f.insertExam(E5);
		f.saveAction();
		
		Exam E6 = new Exam();
		E6 = E6.getInstance("Software 4", "10", "28");
		f.insertExam(E6);
		f.saveAction();
		
		Exam E7 = new Exam();
		E7 = E7.getInstance("Software 5", "10", "28");
		f.insertExam(E7);
		f.saveAction();
		
		Exam E8 = new Exam();
		E8 = E8.getInstance("Software 6", "10", "28");
		f.insertExam(E8);
		f.saveAction();
		
		Exam E9 = new Exam();
		E9 = E9.getInstance("Software 7", "1", "28");
		f.insertExam(E9);
		f.saveAction();
		
		Exam E10 = new Exam();
		E10 = E10.getInstance("Software 8", "1", "28");
		f.insertExam(E10);
		f.saveAction();
		
		assertEquals(10, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		assertEquals(false, f.canDefende);
	}
	
	public void testDefendNumberOfExamsSufficientandCFUGreaterThan70Flessthan3() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software 1", "100", "30");
		f.insertExam(E3);
		f.saveAction();
		
		Exam E4 = new Exam();
		E4 = E4.getInstance("Software 2", "10", "30");
		f.insertExam(E4);
		f.saveAction();
		
		Exam E5 = new Exam();
		E5 = E5.getInstance("Software 3", "10", "30 cum laude");
		f.insertExam(E5);
		f.saveAction();
		
		Exam E6 = new Exam();
		E6 = E6.getInstance("Software 4", "10", "30 cum laude");
		f.insertExam(E6);
		f.saveAction();
		
		Exam E7 = new Exam();
		E7 = E7.getInstance("Software 5", "10", "30 cum laude");
		f.insertExam(E7);
		f.saveAction();
		
		Exam E8 = new Exam();
		E8 = E8.getInstance("Software 6", "10", "30");
		f.insertExam(E8);
		f.saveAction();
		
		Exam E9 = new Exam();
		E9 = E9.getInstance("Software 7", "1", "28");
		f.insertExam(E9);
		f.saveAction();
		
		Exam E10 = new Exam();
		E10 = E10.getInstance("Software 8", "1", "28");
		f.insertExam(E10);
		f.saveAction();
		
		assertEquals(10, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		assertEquals(false, f.canDefende);
	}
	
	public void testDefendNumberOfExamsSufficientandCFUGreaterThan70Fgreater3() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software 1", "10", "30 cum laude");
		f.insertExam(E3);
		f.saveAction();
		
		Exam E4 = new Exam();
		E4 = E4.getInstance("Software 2", "10", "30 cum laude");
		f.insertExam(E4);
		f.saveAction();
		
		Exam E5 = new Exam();
		E5 = E5.getInstance("Software 3", "10", "30 cum laude");
		f.insertExam(E5);
		f.saveAction();
		
		Exam E6 = new Exam();
		E6 = E6.getInstance("Software 4", "10", "30 cum laude");
		f.insertExam(E6);
		f.saveAction();
		
		Exam E7 = new Exam();
		E7 = E7.getInstance("Software 5", "10", "30 cum laude");
		f.insertExam(E7);
		f.saveAction();
		
		Exam E8 = new Exam();
		E8 = E8.getInstance("Software 6", "10", "30 cum laude");
		f.insertExam(E8);
		f.saveAction();
		
		Exam E9 = new Exam();
		E9 = E9.getInstance("Software 7", "1", "28");
		f.insertExam(E9);
		f.saveAction();
		
		Exam E10 = new Exam();
		E10 = E10.getInstance("Software 8", "1", "28");
		f.insertExam(E10);
		f.saveAction();
		
		assertEquals(10, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		assertEquals(true, f.canDefende);
	}
	
	public void testDefendNumberOfExamsNotSufficientandCFUGreaterThan70Fgreater3() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software 1", "10", "30 cum laude");
		f.insertExam(E3);
		f.saveAction();
		
		Exam E4 = new Exam();
		E4 = E4.getInstance("Software 2", "10", "30 cum laude");
		f.insertExam(E4);
		f.saveAction();
		
		Exam E5 = new Exam();
		E5 = E5.getInstance("Software 3", "10", "30 cum laude");
		f.insertExam(E5);
		f.saveAction();
		
		Exam E6 = new Exam();
		E6 = E6.getInstance("Software 4", "10", "30 cum laude");
		f.insertExam(E6);
		f.saveAction();
		
		Exam E7 = new Exam();
		E7 = E7.getInstance("Software 5", "10", "30 cum laude");
		f.insertExam(E7);
		f.saveAction();
		
		Exam E8 = new Exam();
		E8 = E8.getInstance("Software 6", "10", "30 cum laude");
		f.insertExam(E8);
		f.saveAction();
		
		Exam E9 = new Exam();
		E9 = E9.getInstance("Software 7", "1", "28");
		f.insertExam(E9);
		f.saveAction();
		
		Exam E10 = new Exam();
		E10 = E10.getInstance("Software 8", "1", "12");
		f.insertExam(E10);
		f.saveAction();
		
		assertEquals(10, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		assertEquals(false, f.canDefende);
	}
	
	public void testDefendNumberOfExamsSufficientandCFUGreaterThan70Fequal25() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software 1", "10", "30 cum laude");
		f.insertExam(E3);
		f.saveAction();
		
		Exam E4 = new Exam();
		E4 = E4.getInstance("Software 2", "10", "30 cum laude");
		f.insertExam(E4);
		f.saveAction();
		
		Exam E5 = new Exam();
		E5 = E5.getInstance("Software 3", "10", "30 cum laude");
		f.insertExam(E5);
		f.saveAction();
		
		Exam E6 = new Exam();
		E6 = E6.getInstance("Software 4", "10", "30 cum laude");
		f.insertExam(E6);
		f.saveAction();
		
		Exam E7 = new Exam();
		E7 = E7.getInstance("Software 5", "10", "30 cum laude");
		f.insertExam(E7);
		f.saveAction();
		
		Exam E8 = new Exam();
		E8 = E8.getInstance("Software 6", "10", "30");
		f.insertExam(E8);
		f.saveAction();
		
		Exam E9 = new Exam();
		E9 = E9.getInstance("Software 7", "1", "28");
		f.insertExam(E9);
		f.saveAction();
		
		Exam E10 = new Exam();
		E10 = E10.getInstance("Software 8", "1", "28");
		f.insertExam(E10);
		f.saveAction();
		
		assertEquals(10, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		assertEquals(false, f.canDefende);
	}
	
	public void testDefendNumberOfExamsSufficientandCFUGreaterThan70Fequal25PlusSoftwareV() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software", "10", "30 cum laude");
		f.insertExam(E3);
		f.saveAction();
		
		Exam E4 = new Exam();
		E4 = E4.getInstance("Software 2", "10", "30 cum laude");
		f.insertExam(E4);
		f.saveAction();
		
		Exam E5 = new Exam();
		E5 = E5.getInstance("Software 3", "10", "30 cum laude");
		f.insertExam(E5);
		f.saveAction();
		
		Exam E6 = new Exam();
		E6 = E6.getInstance("Software 4", "10", "30 cum laude");
		f.insertExam(E6);
		f.saveAction();
		
		Exam E7 = new Exam();
		E7 = E7.getInstance("Software 5", "10", "30 cum laude");
		f.insertExam(E7);
		f.saveAction();
		
		Exam E8 = new Exam();
		E8 = E8.getInstance("Software 6", "10", "30");
		f.insertExam(E8);
		f.saveAction();
		
		Exam E9 = new Exam();
		E9 = E9.getInstance("Software 7", "1", "28");
		f.insertExam(E9);
		f.saveAction();
		
		Exam E10 = new Exam();
		E10 = E10.getInstance("Software 8", "1", "28");
		f.insertExam(E10);
		f.saveAction();
		
		Exam E11 = new Exam();
		E11 = E11.getInstance("Software 9", "1", "28");
		f.insertExam(E11);
		f.saveAction();
		
		Exam E12 = new Exam();
		E12 = E12.getInstance("Software 10", "1", "28");
		f.insertExam(E12);
		f.saveAction();
		
		Exam E13 = new Exam();
		E13 = E13.getInstance("Software 11", "1", "28");
		f.insertExam(E13);
		f.saveAction();
		
		assertEquals(13, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		assertEquals(true, f.canDefende);
	}
	
	public void testDefendNumberOfExamsSufficientandCFUGreaterThan70Fequal25PlusSoftwareF() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software", "10", "30 cum laude");
		f.insertExam(E3);
		f.saveAction();
		
		Exam E4 = new Exam();
		E4 = E4.getInstance("Software 2", "10", "30 cum laude");
		f.insertExam(E4);
		f.saveAction();
		
		Exam E5 = new Exam();
		E5 = E5.getInstance("Software 3", "10", "30 cum laude");
		f.insertExam(E5);
		f.saveAction();
		
		Exam E6 = new Exam();
		E6 = E6.getInstance("Software 4", "10", "30 cum laude");
		f.insertExam(E6);
		f.saveAction();
		
		Exam E7 = new Exam();
		E7 = E7.getInstance("Software 5", "10", "30 cum laude");
		f.insertExam(E7);
		f.saveAction();
		
		Exam E8 = new Exam();
		E8 = E8.getInstance("Software 6", "10", "30");
		f.insertExam(E8);
		f.saveAction();
		
		Exam E9 = new Exam();
		E9 = E9.getInstance("Software 7", "1", "28");
		f.insertExam(E9);
		f.saveAction();
		
		Exam E10 = new Exam();
		E10 = E10.getInstance("Software 8", "1", "28");
		f.insertExam(E10);
		f.saveAction();
		
		Exam E11 = new Exam();
		E11 = E11.getInstance("Software 9", "1", "28");
		f.insertExam(E11);
		f.saveAction();
		
		Exam E12 = new Exam();
		E12 = E12.getInstance("Software 10", "1", "28");
		f.insertExam(E12);
		f.saveAction();
		
		assertEquals(12, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		assertEquals(false, f.canDefende);
	}
	
	public void testDefendNumberOfExamsSufficientandCFUGreaterThan70No30CumLaude() {
		
		for (int i=1 ; i<=38; i++) {
			Exam E = new Exam();
			E = E.getInstance("Software "+i, "10", "22");
			f.insertExam(E);
			f.saveAction();
		}
		assertEquals(40, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		assertEquals(true, f.canDefende);
	}
	
	public void testDefendNumberOfExamsSufficientandCFUGreaterThan70No30CumLaudeFlessthan3() {
		
		for (int i=1 ; i<=25; i++) {
			Exam E = new Exam();
			E = E.getInstance("Software "+i, "10", "22");
			f.insertExam(E);
			f.saveAction();
		}
		
		Exam E = new Exam();
		E = E.getInstance("Software ", "10", "22");
		f.insertExam(E);
		f.saveAction();
		
		System.out.println(f.valueF);
		
		assertEquals(28, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		assertEquals(false, f.canDefende);
	}
}
