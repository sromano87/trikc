package fit_tests;

import calculator.Exam;
import calculator.MainFrame;
import fit.Fixture;

public class ComputeDefendeThesis extends Fixture {
	
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
	
	public void insert() throws InterruptedException{
			Exam exam = Exam.getInstance(name, cfu, vote);
			Populate.f.insertExam(exam);
			System.out.println(Populate.f.totalCfu);
	}
	
	 public int totalCfu(){
		   return Populate.f.totalCfu;
	   }
	 
	 public int examsInserted(){
		   return Populate.f.exams.size();
	  }
	 
	 public float valueOfF(){
		   return Populate.f.valueF;
	  }
	 
	public boolean defendThesis() throws InterruptedException {
		Populate.f.updateStatistics((Exam[])Populate.f.exams.toArray(new Exam[]{}));
		return Populate.f.canDefende;
	 }
	}