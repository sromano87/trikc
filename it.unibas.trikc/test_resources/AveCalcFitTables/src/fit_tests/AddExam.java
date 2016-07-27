package fit_tests;

import calculator.Exam;
import fit.Fixture;

public class AddExam extends Fixture {

	private String name;
	private String cfu;
	private String vote = "-1";
	
	public void examName(String t) throws InterruptedException {
		name = t;
	}
	
	public void cfu(String x) throws InterruptedException {
		cfu = x;
	}
	
	public void vote(String v) throws InterruptedException {
		vote = v;
	}
	
	public void insert() throws InterruptedException  {
		try {
			Exam exam = Exam.getInstance(name, cfu, vote);
			Populate.f.insertExam(exam);
			Populate.f.saveAction();
		} catch (IllegalArgumentException iae) {
			System.out.println("insert: IllegalArgumentException");
		}
	}
	
	public int numberOfExams() throws InterruptedException {
		Populate.f.db.load();
		Populate.f.updateTableAndStatistics(Populate.f.db.getExamsList());
		return Populate.f.exams.size();
	 }
	
    public boolean containExamName() throws InterruptedException {
		return Populate.f.containsExamName(name);
	 }
}
