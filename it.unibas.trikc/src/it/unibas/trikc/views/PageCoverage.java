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
	public static Combo comboCoverage;
	private Button btnCoverage;
	private static Button buttonLoad;
	private TestSuite testSuite = new TestSuite();
	private static List<File> listaFile  = null;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PageCoverage(Composite parent, int style) {

		super(parent, style);

		comboCoverage = new Combo(this, SWT.NONE);
		comboCoverage.setBounds(32, 100, 316, 23);

		btnCoverage = new Button(this, SWT.NONE);
		btnCoverage.setBounds(430, 39, 101, 28);
		btnCoverage.setText("New Coverage");

		btnCoverage.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				Shell shell = getShell();
				CoverageFacade cf = new CoverageFacade();
				try {
					//System.out.println("---Prima di runCoverage");
					cf.runCoverage((String) Modello.getInstance().getBean(Constants.DIRECTORY_BIN),
							(String) Modello.getInstance().getBean(Constants.TEST_SUITE),
							(String) Modello.getInstance().getBean(Constants.DIRECTORY_TEST),
							(String) Modello.getInstance().getBean(Constants.DIRECTORY_LIB));
					
					//System.out.println("---Dopo di runCoverage");
					//System.out.println("Test Suite: \n" + cf.getCoverage().getTestSuite().toString());
					testSuite = cf.getCoverage().getTestSuite();
					if (testSuite != null) {
						Modello.getInstance().putBean(Constants.TEST_SUITE_OBJ, testSuite);
						MessageDialog.openInformation(shell, "Information", "Coverage Done");
						DirectoryCoverage dc = (DirectoryCoverage) Modello.getInstance().getBean(Constants.DIRECTORY_COVERAGE);
						dc.setPageComplete(true);
					}
					PageDissimilarity.existingDissimilarity();
				} catch (Exception e) {
					IStatus status = new Status(IStatus.ERROR, "pageCoverage", "error coveraging " + e.getMessage());
					ErrorDialog.openError(shell, "Error", "Coverage Error", status);
					// e.printStackTrace();
				}
			}
		});
		buttonLoad = new Button(this, SWT.NONE);
		buttonLoad.setBounds(430, 96, 101, 28);
		buttonLoad.setText("Load");
		comboCoverage.setEnabled(false);
		buttonLoad.setEnabled(false);
		//listaFile = existingCoverage();
		
		
		
		Label labelNewCoverage = new Label(this, SWT.NONE);
		labelNewCoverage.setBounds(32, 46, 209, 15);
		labelNewCoverage.setText("Push this Button to run a new Coverage");
		buttonLoad.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				Shell shell = getShell();
				try {
					IDAOTestSuite dao = new DAOXmlTestSuite();
					testSuite = dao.load(comboCoverage.getItem(comboCoverage.getSelectionIndex()));
					
					if(testSuite!=null){
						Modello.getInstance().putBean(Constants.TEST_SUITE_OBJ, testSuite);
						MessageDialog.openInformation(shell, "Information", "Load Done");
						DirectoryCoverage dc = (DirectoryCoverage) Modello.getInstance().getBean(Constants.DIRECTORY_COVERAGE);
						dc.setPageComplete(true);
					}
					PageDissimilarity.existingDissimilarity();
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

	public static void existingCoverage() {
		listaFile = new ArrayList<File>();
		
		String path = System.getProperty("user.home"); 
		path = path + "/.TRIKC/";
		
		File directoryStorage = new File(path.toString());
		File[] listaFiles = directoryStorage.listFiles();
		
		if (listaFiles != null) {
			for (int i = 0; i < listaFiles.length; i++) {
				//System.out.print("--- " + listaFiles[i].getName() + "\n"); 
				//System.out.println("---> " + (String)Modello.getInstance().getBean(Constants.TEST_SUITE) + "\n");
					if (listaFiles[i].getName().contains("testSuiteCoverage_"+(String)Modello.getInstance().getBean(Constants.TEST_SUITE)) 
							/*&& !verifyExistence(listaFiles[i])*/) {
					listaFile.add(listaFiles[i]);
					}	
				}
			}
		
	
		if (listaFile.size() > 0) {
			comboCoverage.setEnabled(true);
			buttonLoad.setEnabled(true);
			for (File file : listaFile) {
				comboCoverage.add(file.getName().substring(0, file.getName().length() - 4));
			}
			comboCoverage.select(0);
		}
		//return listaFileCoverage;
	}
	
	public static boolean verifyExistence(File file) {
		String[] comboItems = comboCoverage.getItems();
		//logger.info("dimensione combo "+comboItems.length);
		int count =0;
		for(int j=0; j<comboItems.length; j++) {
			//logger.info("fileName "+file.getName()+" comboItem "+comboItems[j]+ "equals? "+file.getName().equals(comboItems[j]));
			if (file.getName().substring(0, file.getName().length()-4).equals(comboItems[j])) {
				count++;
			}
		}
		if(count==0) {
			return false;
		}
		return true;
	}

	public Button getButtonCoverage() {
		return this.btnCoverage;
	}

	public TestSuite getTestSuite() {
		return this.testSuite;
	}
}
