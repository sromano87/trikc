package fit_tests;

import calculator.Exam;
import fit.Fixture;

public class RemoveExam extends Fixture {
	
	private String name;
	private String cfu;
	private String vote;
	
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
		//Exam exam = new Exam ();
		//exam.setName(name);
		//exam.setCfu(Integer.parseInt(cfu));
		//exam.setVote(Integer.parseInt(vote));
		Exam exam = Exam.getInstance(name, cfu, vote);
		Populate.f.insertExam(exam);
		Populate.f.saveAction();
	}
	
	public void removeExam(String e) throws InterruptedException  {
		Populate.f.removeExam(e);
		Populate.f.saveAction();
	}
	
	public int numberOfExams() throws InterruptedException {
		Populate.f.db.load();
		Populate.f.updateTableAndStatistics(Populate.f.db.getExamsList());
		return Populate.f.exams.size();
	 }
}
