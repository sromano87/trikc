package it.unibas.trikc.views;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class DirectoryPage extends WizardPage {
	//private static Logger logger = Logger.getLogger(DirectoryPage.class.getName()); 

	/**
	 * Create the wizard.
	 */
		
	public DirectoryPage() {
		super("wizardPage");
		setTitle("Select bin folder and Test Suite");
		setDescription("In this page you need to select the bin folder of the project and the Test Suite ");
		setPageComplete(false);	
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		 Composite shell = new PageSetUp(parent, SWT.NONE);
		 setControl(shell);
	}
	
	
	
	
}
