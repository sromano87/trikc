package fit_tests;

import java.text.NumberFormat;

import calculator.Exam;
import fit.Fixture;

public class VerifyAverage extends Fixture {
	
	private String name;
	private String cfu;
	private String vote;
	
	public void examName(String t) throws InterruptedException {
		name = t;
	}
	
	public void cfu(String c) throws InterruptedException {
		cfu = c;
	}
	
	public void vote(String v) throws InterruptedException {
		vote = v;
	}
	
	public void insert() throws InterruptedException  {
		//Exam exam = new Exam ();
		//exam.setName(name);
		//exam.setCfu(Integer.parseInt(cfu));
		//exam.setVote(Integer.parseInt(vote));
		//exam.setMaked(true);
		
		Exam exam = Exam.getInstance(name, cfu, vote);
		Populate.f.insertExam(exam);
	}
	
	public String average() throws InterruptedException {
		Populate.f.updateStatistics((Exam[])Populate.f.exams.toArray(new Exam[]{}));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		return nf.format(Populate.f.average);
	 }
}
