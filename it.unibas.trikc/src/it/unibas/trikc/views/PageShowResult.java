package it.unibas.trikc.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import it.unibas.trikc.Constants;
import it.unibas.trikc.Modello;
import it.unibas.trikc.modelEntity.TestSuite;

import java.util.logging.Logger;

import org.eclipse.swt.SWT;

public class PageShowResult extends Composite {
	//private static Logger logger = Logger.getLogger(PageShowResult.class.getName());
	private static Text textResult;
	private static Text textOld;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PageShowResult(Composite parent, int style) {
		super(parent, style);

		Label labelResult = new Label(this, SWT.NONE);
		labelResult.setBounds(49, 23, 230, 15);
		labelResult.setText("Result Reducing Test Suite");

		Label labelOldTestSuite = new Label(this, SWT.NONE);
		labelOldTestSuite.setText("Not Reducing Test Suite");
		labelOldTestSuite.setBounds(462, 23, 182, 15);

		textResult = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		textResult.setBounds(49, 63, 348, 213);

		textOld = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		textOld.setBounds(460, 63, 348, 213);
	}

	public static void caricaText() {

		TestSuite tsOld = (TestSuite) Modello.getInstance().getBean(Constants.TEST_SUITE_OBJ);
		TestSuite tsResult = (TestSuite) Modello.getInstance().getBean(Constants.REDUCING_TEST_SUITE);
		if (tsOld != null && tsResult != null) {
			textOld.setText(tsOld.toString());
			textResult.setText(tsResult.toString());
		}

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
