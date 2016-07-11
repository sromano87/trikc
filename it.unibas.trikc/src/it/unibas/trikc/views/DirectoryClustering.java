package it.unibas.trikc.views;

import java.util.logging.Logger;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class DirectoryClustering extends WizardPage{
	
	private static Logger logger = Logger.getLogger(DirectoryPage.class.getName()); 

	/**
	 * Create the wizard.
	 */
	public DirectoryClustering() {
		super("wizardPage");
		setTitle("CLUSTERING");
		setDescription("This page is calculated the clustering. Select from the first combobox an existing clustering or a new. In the case of new clustering select from the second combobox the type of strategy");
		setPageComplete(false);
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		 Composite shell = new PageClustering(parent, SWT.NONE);
		 setControl(shell);
	}

}
