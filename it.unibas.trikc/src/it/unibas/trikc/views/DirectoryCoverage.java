package it.unibas.trikc.views;

import java.util.logging.Logger;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class DirectoryCoverage  extends WizardPage{

	
	private static Logger logger = Logger.getLogger(DirectoryPage.class.getName()); 

	/**
	 * Create the wizard.
	 */
	public DirectoryCoverage() {
		super("wizardPageCoverage");
		setTitle("COVERAGE");
		setDescription("This page is calculated the coverage. Select from the combobox an existing covered measure or a new");
		setPageComplete(false);	
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		 Composite shell = new PageCoverage(parent, SWT.NONE);
		 setControl(shell);
	}
	
}
