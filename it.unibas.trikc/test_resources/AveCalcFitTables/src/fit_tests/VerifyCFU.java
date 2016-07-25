package fit_tests;

import calculator.Exam;
import fit.ColumnFixture;

public class VerifyCFU extends ColumnFixture {
	   
	   public String name;
	   public String cfu;
	   public String vote;
	   
	   public int totalCfu(){
		   //Exam e = new Exam();
		   //e.setName(name);
		   //e.setVote(vote);
		   //e.setCfu(cfu);
		   //e.setMaked(true);
		   
		   Exam e = Exam.getInstance(name, cfu, vote);
		   Populate.f.insertExam(e);		   
		   Populate.f.updateStatistics((Exam[])Populate.f.exams.toArray(new Exam[]{}));
		   return Populate.f.totalCfu;
	   }
}
