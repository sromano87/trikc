package fit_tests;

import calculator.Exam;
import calculator.MainFrame;
import fit.ColumnFixture;

public class VerifyNumberOfExams extends ColumnFixture{
	   
	   public String name;
	   public String cfu;
	   public String vote;
	   
	   public int examsInserted(){
		   //Exam e = new Exam();
		   //e.setName(name);
		   //e.setVote(vote);
		   //e.setCfu(cfu);
		   //e.setMaked(true);
		   
		   Exam e = Exam.getInstance(name, cfu, vote);
		   Populate.f.insertExam(e);
		   Populate.f.updateStatistics((Exam[])Populate.f.exams.toArray(new Exam[]{}));
		   return Populate.f.exams.size();
	   }
}
