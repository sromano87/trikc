package it.unibas.trikc.views;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

public class prova extends WizardPage {

	/**
	 * Create the wizard.
	 */
	public prova() {
		super("wizardPage");
		setTitle("Wizard Page title");
		setDescription("Wizard Page description");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		
		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.setBounds(270, 213, 75, 25);
		btnNewButton.setText("New Button");
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(190, 58, 55, 15);
		lblNewLabel.setText("");
		
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setBounds(270, 68, 75, 25);
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.setBounds(0, 0, 75, 25);
		btnNewButton_1.setText("New Button");
	}
}
