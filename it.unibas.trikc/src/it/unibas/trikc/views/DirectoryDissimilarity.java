package it.unibas.trikc.views;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class DirectoryDissimilarity extends WizardPage{
	
	/**
	 * Create the wizard.
	 */
	public DirectoryDissimilarity() {
		super("wizardPage");
		setTitle("DISSIMILARITY");
		setDescription("Dissimilarity will be calculated in this page. Select from the first combobox an existing dissimilarity measure or a new one. In case of new dissimilarity, select from the second combobox the kind of strategy.");
		setPageComplete(false);
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		 Composite shell = new PageDissimilarity(parent, SWT.NONE);
		 setControl(shell);
	}

}
