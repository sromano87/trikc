package it.unibas.trikc.views;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class DirectoryShowResult extends WizardPage {

	/**
	 * Create the wizard.
	 */
	public DirectoryShowResult() {
		super("wizardShowResult");
		setTitle("Wizard Show Result");
		setDescription("The result is shown in the following labels: The first one indicates the reduced Test Suite; the second one the original Test Suite");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new PageShowResult(parent, SWT.NONE);
		setControl(container);
	}

}
