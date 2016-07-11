package it.unibas.trikc.views;

import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import it.unibas.trikc.Constants;
import it.unibas.trikc.Modello;
import it.unibas.trikc.handlers.SampleHandler;

public class MainView extends Wizard {
	// static final String DIALOG_SETTING_FILE = "userInfo.xml";
	private WizardPage page1 = new DirectoryPage();
	private WizardPage page2 = new DirectoryCoverage();
	private WizardPage page3 = new DirectoryDissimilarity();
	private WizardPage page4 = new DirectoryClustering();
	private WizardPage page5 = new DirectoryReducing();
	private WizardPage page6 = new DirectoryShowResult();

	public MainView() {
		setWindowTitle("wizard");
		setNeedsProgressMonitor(true);
		// setDefaultPageImageDescriptor(ImageDescriptor.createFromFile(null,
		// "icons/trikc.gif"));

		// needsPreviousAndNextButtons();
		DialogSettings dialogSettings = new DialogSettings("userInfo");
		// try {
		// loads existing settings if any.
		// dialogSettings.load(DIALOG_SETTING_FILE);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		setDialogSettings(dialogSettings);
		Modello.getInstance().putBean(Constants.DIRECTORY_PAGE, page1);
		Modello.getInstance().putBean(Constants.DIRECTORY_COVERAGE, page2);
		Modello.getInstance().putBean(Constants.DIRECTORY_DISSIMILARITY, page3);
		Modello.getInstance().putBean(Constants.DIRECTORY_CLUSTERING, page4);
		Modello.getInstance().putBean(Constants.DIRECTORY_REDUCING, page5);
	}

	@Override
	public boolean performFinish() {
		/*WizardDialog dialog = (WizardDialog) Modello.getInstance().getBean(Constants.DIALOG_MAINWINDOW);
		
		SampleHandler.trikc.close();
		dialog.close();*/
		System.exit(0);
		return true;
	}

	public void addPages() {
		addPage(page1);
		addPage(page2);
		addPage(page3);
		addPage(page4);
		addPage(page5);
		addPage(page6);
	}

	@Override
	public boolean canFinish() {
		if (getContainer().getCurrentPage().equals(page6)) {
			return true;
		}
		return false;
	}

}
