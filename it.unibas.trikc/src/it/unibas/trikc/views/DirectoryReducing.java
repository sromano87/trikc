package it.unibas.trikc.views;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class DirectoryReducing extends WizardPage{
	
	/**
	 * Create the wizard.
	 */
	public DirectoryReducing() {
		super("wizardPage");
		setTitle("REDUCING");
		setDescription("Reducing will be calculated in this page. Select from the first combobox an existing reducing or a new one. In case of new reducing, select from the second combobox the kind of strategy.");
		setPageComplete(false);
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		 Composite shell = new PageReducing(parent, SWT.NONE);
		 setControl(shell);
	}

}

