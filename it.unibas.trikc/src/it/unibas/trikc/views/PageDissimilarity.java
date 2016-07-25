package it.unibas.trikc.views;

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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import it.unibas.trikc.Constants;
import it.unibas.trikc.Modello;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.dissimilarity.IStrategyDissimilarity;
import it.unibas.trikc.modelEntity.dissimilarity.StrategyStringKernelDissimilarity;
import it.unibas.trikc.repository.XMLException;
import it.unibas.trikc.repository.dissimilarity.DAOXmlDissimilarityMatrix;
import it.unibas.trikc.repository.dissimilarity.IDAODissimilarityMatrix;
import it.unibas.trikc.repository.reduction.DAOXmlTestSuite;
import it.unibas.trikc.repository.reduction.IDAOTestSuite;

public class PageDissimilarity extends Composite {
	private static Logger logger = Logger.getLogger(PageDissimilarity.class.getName());
	private static Button buttonDissimilarity, buttonLoad;
	private static Combo comboDissimilarity;
	private Combo comboStrategy;
	private IStrategyDissimilarity strategy;
	private int indexDissimilarity, indexStrategy;
	private TestSuite testSuite; 
	private static List<File> listaFile = null;
	private Label lblNewLabel;
	private Label lblSelectAnExisting;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PageDissimilarity(Composite parent, int style) {
		super(parent, style);

		buttonDissimilarity = new Button(this, SWT.NONE);
		buttonDissimilarity.setBounds(385, 73, 109, 28);
		buttonDissimilarity.setText("New Dissimilarity");

		comboDissimilarity = new Combo(this, SWT.NONE);
		comboDissimilarity.setBounds(29, 148, 301, 23);

		comboStrategy = new Combo(this, SWT.NONE);
		comboStrategy.setBounds(29, 77, 301, 23);

		buttonLoad = new Button(this, SWT.NONE);
		buttonLoad.setBounds(385, 144, 109, 28);
		buttonLoad.setText("Load");

		//List<File> listaFile = existingDissimilarity();

		List<String> strategyList = strategySearch();
		for (String strategy : strategyList) {
			comboStrategy.add(strategy.substring(0, strategy.length() - 5));
		}
		comboStrategy.select(0);
		
		lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(29, 56, 301, 15);
		lblNewLabel.setText("Select the Strategy and run New Dissimilarity");
		
		lblSelectAnExisting = new Label(this, SWT.NONE);
		lblSelectAnExisting.setText("Select an existing Dissimilarity and load it");
		lblSelectAnExisting.setBounds(29, 127, 301, 15);

		/*
		comboDissimilarity.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int index = comboDissimilarity.getSelectionIndex();
				if (comboDissimilarity.getItem(index).equals("New Dissimilarity")) {
					testSuite = (TestSuite)Modello.getInstance().getBean(Constants.TEST_SUITE_OBJ);
					buttonDissimilarity.setEnabled(true);
					comboStrategy.setEnabled(true);
					buttonLoad.setEnabled(false);
				} else {
					IDAOTestSuite daoTs = new DAOXmlTestSuite();
					//try {
						//testSuite = daoTs.load(comboDissimilarity.getItem(comboDissimilarity.getSelectionIndex()).substring(0, 
						//		comboDissimilarity.getItem(comboDissimilarity.getSelectionIndex()).length() - 4));
						testSuite = (TestSuite)Modello.getInstance().getBean(Constants.TEST_SUITE_OBJ);
					//} catch (XMLException e1) {
					//	e1.printStackTrace();
					//} 
					buttonDissimilarity.setEnabled(false);
					comboStrategy.setEnabled(false);
					buttonLoad.setEnabled(true);
				//}
			}
		});
		
		comboStrategy.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				buttonLoad.setEnabled(false);
			}
		});
		*/

		buttonDissimilarity.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				testSuite = (TestSuite)Modello.getInstance().getBean(Constants.TEST_SUITE_OBJ);
				int indexStrategy = comboStrategy.getSelectionIndex();
				switch (comboStrategy.getItem(indexStrategy)) {
				case "StrategyStringKernelDissimilarity":
					strategy = new StrategyStringKernelDissimilarity();
					break;
				default:
					break;
				}

				Shell shell = getShell();
				if (strategy == null) {
					IStatus status = new Status(IStatus.ERROR, "pageDissimilarity",
							"non � stata selezionata alcuna strategia");
					ErrorDialog.openError(shell, "Error", "Dissimilarity Error", status);
				} else {
					DissimilarityMatrix matrix = strategy.computeDissimilarity(testSuite);

					try {
						IDAODissimilarityMatrix dao = new DAOXmlDissimilarityMatrix();
						dao.save(matrix, "DissimilarityMatrix_"+testSuite.getFullName());
						Modello.getInstance().putBean(Constants.DISSIMILARITY_MATRIX, matrix);
						MessageDialog.openInformation(shell, "Information", "Dissimilarity Done");
						if(matrix!=null){
							DirectoryDissimilarity dd = (DirectoryDissimilarity) Modello.getInstance().getBean(Constants.DIRECTORY_DISSIMILARITY);
							dd.setPageComplete(true);
						}
						PageClustering.existingClustering();
					} catch (XMLException e) {
						IStatus status = new Status(IStatus.ERROR, "pageDissimilarity",
								"errore nel salvataggio della matrice");
						ErrorDialog.openError(shell, "Error", "Dissimilarity Error", status);
						e.printStackTrace();
					}
					
				}
			}
		});

		buttonLoad.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				Shell shell = getShell();
				DissimilarityMatrix matrix = null;
				try {
					IDAODissimilarityMatrix dao = new DAOXmlDissimilarityMatrix();
					matrix = dao.load(comboDissimilarity.getItem(comboDissimilarity.getSelectionIndex()).substring(0,
							comboDissimilarity.getItem(comboDissimilarity.getSelectionIndex()).length() - 4));
					Modello.getInstance().putBean(Constants.DISSIMILARITY_MATRIX, matrix);
					MessageDialog.openInformation(shell, "Information", "Load Done");
					if(matrix!=null){
						DirectoryDissimilarity dd = (DirectoryDissimilarity) Modello.getInstance().getBean(Constants.DIRECTORY_DISSIMILARITY);
						dd.setPageComplete(true);
					}
					PageClustering.existingClustering();
				} catch (XMLException e) {
					IStatus status = new Status(IStatus.ERROR, "pageDissimilarity",
							"errore nel caricamnto della matrice");
					ErrorDialog.openError(shell, "Error", "Dissimilarity Error", status);
					e.printStackTrace();
				}
				
			}
		});
	}

	public static void existingDissimilarity() {
		listaFile = new ArrayList<File>();
		
		String path = System.getProperty("user.home"); 
		path = path + "/.TRIKC/";
		
		File directoryStorage = new File(path.toString());
		File[] listaFiles = directoryStorage.listFiles();
		if (listaFiles != null) {
			for (int i = 0; i < listaFiles.length; i++) {
				if (listaFiles[i].getName().contains("DissimilarityMatrix_"+(String)Modello.getInstance().getBean(Constants.TEST_SUITE))
						&& !verifyExistence(listaFiles[i])) {
					listaFile.add(listaFiles[i]);
				}
			}
		}
		if (listaFile.size() > 0) {
			for (File file : listaFile) {
				comboDissimilarity.add(file.getName());
			}
			
		}
		if(comboDissimilarity.getItemCount()>0) {
			comboDissimilarity.select(0);
			comboDissimilarity.setEnabled(true);
			buttonLoad.setEnabled(true);
		} else {
			comboDissimilarity.setEnabled(false);
			buttonLoad.setEnabled(false);
		}
		
		//return listaFile;
	}

	public List<String> strategySearch() {
		List<String> risultato = new ArrayList<String>();
		URL location = PageDissimilarity.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("src/it/unibas/trikc/modelEntity/dissimilarity"); 
		File directoryStorage = new File(path.toString());
		File[] listaFile = directoryStorage.listFiles();
		if (listaFile != null) {
			for (int i = 0; i < listaFile.length; i++) {
				if (!listaFile[i].getName().contains("IStrategy") && listaFile[i].getName().contains("Strategy")) {
					risultato.add(listaFile[i].getName());
				}
			}
		}
		return risultato;
	}
	
	public static boolean verifyExistence(File file) {
		String[] comboItems = comboDissimilarity.getItems();
		int count =0;
		for(int j=0; j<comboItems.length; j++) {
			logger.info("fileName "+file.getName()+" comboItem "+comboItems[j]+ " equals? "+file.getName().equals(comboItems[j]));
			if (file.getName().substring(0, file.getName().length()-4).equals(comboItems[j].substring(0, comboItems[j].length()-4))) {
				count++;
			}
		}
		logger.info("count "+count);
		if(count==0) {
			return false;
		}
		return true;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
