package fit_tests;

import calculator.Exam;
import fit.RowFixture;

public class LoadPositiveExams extends RowFixture {
	   
	   @Override
	   public Object[] query() throws Exception {
	      return Populate.f.db.getOnlyPositiveExams().toArray();
	   }
	   
	   @Override
	   public Class getTargetClass() {
	      return Exam.class;
	   }
}
