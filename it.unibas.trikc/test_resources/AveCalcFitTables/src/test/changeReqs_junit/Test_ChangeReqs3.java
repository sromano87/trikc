package changeReqs_junit;

import java.text.NumberFormat;

import calculator.Exam;
import calculator.MainFrame;
import junit.framework.TestCase;

public class Test_ChangeReqs3 extends TestCase {
	
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
	}
	
	public void testDegreeVote3exams() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software", "12", "28");
		f.insertExam(E3);
		f.saveAction();
		
		assertEquals(3, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		System.out.println(f.startDegreeVote);
		assertEquals("93,33", nf.format(f.startDegreeVote));
	}
	
	public void testDegreeVote4examsOneNegative() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software", "12", "28");
		f.insertExam(E3);
		f.saveAction();
		
		Exam E4 = new Exam();
		E4 = E4.getInstance("Software 2", "12", "8");
		f.insertExam(E4);
		f.saveAction();
		
		assertEquals(4, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		System.out.println(f.startDegreeVote);
		assertEquals("93,33", nf.format(f.startDegreeVote));
	}
	
	public void testDegreeWithThree30cumLode() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software", "12", "30 cum laude");
		f.insertExam(E3);
		f.saveAction();
		
		Exam E4 = new Exam();
		E4 = E4.getInstance("Software 1", "12", "30 cum laude");
		f.insertExam(E4);
		f.saveAction();
		
		Exam E5 = new Exam();
		E5 = E5.getInstance("Software 2", "12", "30 cum laude");
		f.insertExam(E5);
		f.saveAction();
		
		assertEquals(5, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		System.out.println(f.startDegreeVote);
		assertEquals("100,5", nf.format(f.startDegreeVote));
	}
	
	public void testDegreeWithThree30cumLodeAndOneNegative() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software", "12", "30 cum laude");
		f.insertExam(E3);
		f.saveAction();
		
		Exam E4 = new Exam();
		E4 = E4.getInstance("Software 1", "12", "30 cum laude");
		f.insertExam(E4);
		f.saveAction();
		
		Exam E5 = new Exam();
		E5 = E5.getInstance("Software 2", "12", "30 cum laude");
		f.insertExam(E5);
		f.saveAction();
		
		Exam E6 = new Exam();
		E6 = E6.getInstance("Software 3", "10", "17");
		f.insertExam(E6);
		f.saveAction();
		
		assertEquals(6, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		System.out.println(f.startDegreeVote);
		assertEquals("100,5", nf.format(f.startDegreeVote));
	}
	
	public void testDegreeWithOnly30cumLode() {
		f.removeExam("DB");
		f.saveAction();
		
		f.removeExam("ssi");
		f.saveAction();
		
		Exam E3 = new Exam();
		E3 = E3.getInstance("Software", "12", "30 cum laude");
		f.insertExam(E3);
		f.saveAction();
		
		Exam E4 = new Exam();
		E4 = E4.getInstance("Software 1", "12", "30 cum laude");
		f.insertExam(E4);
		f.saveAction();
		
		Exam E5 = new Exam();
		E5 = E5.getInstance("Software 2", "12", "30 cum laude");
		f.insertExam(E5);
		f.saveAction();
		
		assertEquals(3, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		System.out.println(f.startDegreeVote);
		assertEquals("100,5", nf.format(f.startDegreeVote));
	}
	
	public void testDegreeZeroVote() {
		f.removeExam("DB");
		f.saveAction();
		
		f.removeExam("ssi");
		f.saveAction();
		
		assertEquals(0, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		System.out.println(f.startDegreeVote);
		assertEquals("0", nf.format(f.startDegreeVote));
	}
	
	public void testDegreeOnlyOneVote() {
		f.removeExam("ssi");
		f.saveAction();
		
		assertEquals(1, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		System.out.println(f.startDegreeVote);
		assertEquals("110", nf.format(f.startDegreeVote));
	}
	
	public void testDegreeOnlyTwoVote() {
		Exam E3 = new Exam();
		E3 = E3.getInstance("Compilers", "15", "30");
		f.insertExam(E3);
		f.saveAction();
		
		f.removeExam("ssi");
		f.saveAction();
		
		assertEquals(2, f.getExams().size());
		
		f.updateStatistics((Exam[])f.exams.toArray(new Exam[]{}));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		System.out.println(f.startDegreeVote);
		assertEquals("110", nf.format(f.startDegreeVote));
	}
	
}
