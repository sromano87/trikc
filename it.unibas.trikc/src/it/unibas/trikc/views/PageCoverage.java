package it.unibas.trikc.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Combo;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import it.unibas.trikc.Constants;
import it.unibas.trikc.Modello;
import it.unibas.trikc.coverage.CoverageFacade;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.repository.reduction.DAOXmlTestSuite;
import it.unibas.trikc.repository.reduction.IDAOTestSuite;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class PageCoverage extends Composite {
	private static Logger logger = Logger.getLogger(PageCoverage.class.getName());
	private Label labelCoverage;
	private Combo comboCoverage;
	private Button btnCoverage, buttonLoad;
	private TestSuite testSuite;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PageCoverage(Composite parent, int style) {

		super(parent, style);

		comboCoverage = new Combo(this, SWT.NONE);
		comboCoverage.setBounds(29, 42, 203, 22);

		labelCoverage = new Label(this, SWT.NONE);
		labelCoverage.setBounds(293, 46, 164, 14);

		btnCoverage = new Button(this, SWT.NONE);
		btnCoverage.setBounds(29, 94, 101, 28);
		btnCoverage.setText("GettingCoverage");

		btnCoverage.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				Shell shell = getShell();
				CoverageFacade cf = new CoverageFacade();
				try {
					cf.runCoverage((String) Modello.getInstance().getBean(Constants.DIRECTORY_BIN),
							(String) Modello.getInstance().getBean(Constants.TEST_SUITE));
					testSuite = cf.getCoverage().getTestSuite();
					if (testSuite != null) {
						Modello.getInstance().putBean(Constants.TEST_SUITE_OBJ, testSuite);
						MessageDialog.openInformation(shell, "Information", "Coverage Done");
						DirectoryCoverage dc = (DirectoryCoverage) Modello.getInstance().getBean(Constants.DIRECTORY_COVERAGE);
						dc.setPageComplete(true);
					}
				} catch (Exception e) {
					IStatus status = new Status(IStatus.ERROR, "pageCoverage", "error coveraging " + e.getMessage());
					ErrorDialog.openError(shell, "Error", "Coverage Error", status);
					// e.printStackTrace();
				}
			}
		});
		buttonLoad = new Button(this, SWT.NONE);
		buttonLoad.setBounds(155, 94, 70, 28);
		buttonLoad.setText("Load");

		if (comboCoverage.isEnabled() == true) {
			btnCoverage.setEnabled(true);
			buttonLoad.setEnabled(false);
		}
		List<File> listaFile = existingCoverage();
		if (listaFile.size() > 0) {
			comboCoverage.setEnabled(true);
			btnCoverage.setEnabled(false);
			buttonLoad.setEnabled(true);
			labelCoverage.setText("existing coverage");
			for (File file : listaFile) {
				comboCoverage.add(file.getName().substring(0, file.getName().length() - 4));
			}

			comboCoverage.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					int index = comboCoverage.getSelectionIndex();
					if (comboCoverage.getItem(index).equals("New Coverage")) {
						btnCoverage.setEnabled(true);
						buttonLoad.setEnabled(false);
					} else {
						btnCoverage.setEnabled(false);
						buttonLoad.setEnabled(true);
					}
				}
			});
		} else {
			btnCoverage.setEnabled(true);
		}
		comboCoverage.add("New Coverage");
		comboCoverage.select(0);
		buttonLoad.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				Shell shell = getShell();
				TestSuite ts= null;
				try {
					IDAOTestSuite dao = new DAOXmlTestSuite();
					ts = dao.load(comboCoverage.getItem(comboCoverage.getSelectionIndex()));
					
					if(ts!=null){
						Modello.getInstance().putBean(Constants.TEST_SUITE_OBJ, ts);
						MessageDialog.openInformation(shell, "Information", "Load Done");
						DirectoryCoverage dc = (DirectoryCoverage) Modello.getInstance().getBean(Constants.DIRECTORY_COVERAGE);
						dc.setPageComplete(true);
					}
				} catch (Exception e) {
					IStatus status = new Status(IStatus.ERROR, "pageCoverage", "error coveraging " + e.getMessage());
					ErrorDialog.openError(shell, "Error", "Coverage Error", status);
					// e.printStackTrace();
				}
				
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public List<File> existingCoverage() {
		List<File> listaFileCoverage = new ArrayList<File>();
		URL location = PageCoverage.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("storage");
		File directoryStorage = new File(path.toString());
		File[] listaFile = directoryStorage.listFiles();
		if (listaFile != null) {
			for (int i = 0; i < listaFile.length; i++) {
				if (listaFile[i].getName().contains("testSuiteCoverage_")) {
					listaFileCoverage.add(listaFile[i]);
				}
			}
		}
		return listaFileCoverage;
	}

	public Button getButtonCoverage() {
		return this.btnCoverage;
	}

	public TestSuite getTestSuite() {
		return this.testSuite;
	}

}
