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
import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.reduction.IStrategyReduction;
import it.unibas.trikc.modelEntity.reduction.StrategyMostCoveringReduction;
import it.unibas.trikc.repository.XMLException;
import it.unibas.trikc.repository.reduction.DAOXmlTestSuite;
import it.unibas.trikc.repository.reduction.IDAOTestSuite;

public class PageReducing extends Composite {
	private static Logger logger = Logger.getLogger(PageReducing.class.getName());

	private Label labelReducing;
	private Button buttonReducing, buttonLoad;
	private Combo comboReducing, comboStrategy;
	private IStrategyReduction strategy;
	//private int indexDissimilarity, indexStrategy;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PageReducing(Composite parent, int style) {
		super(parent, style);

		buttonReducing = new Button(this, SWT.NONE);
		buttonReducing.setBounds(29, 150, 109, 28);
		buttonReducing.setText("GettingReducing");

		labelReducing = new Label(this, SWT.NONE);
		labelReducing.setBounds(265, 45, 175, 14);

		comboReducing = new Combo(this, SWT.NONE);
		comboReducing.setBounds(29, 42, 203, 22);

		comboStrategy = new Combo(this, SWT.NONE);
		comboStrategy.setBounds(29, 98, 203, 23);

		buttonLoad = new Button(this, SWT.NONE);
		buttonLoad.setBounds(155, 150, 70, 28);
		buttonLoad.setText("Load");

		List<File> listaFile = existingReducing();
		if (listaFile.size() > 0) {
			for (File file : listaFile) {
				comboReducing.add(file.getName());
			}
			labelReducing.setText("existing reducing");
			comboStrategy.setEnabled(false);
			comboReducing.setEnabled(true);
			buttonReducing.setEnabled(false);
		} else {
			comboReducing.setEnabled(true);
			buttonReducing.setEnabled(true);
			buttonLoad.setEnabled(false);
			comboStrategy.setEnabled(true);
		}
		comboReducing.add("New Reducing");
		comboReducing.select(0);

		if (comboReducing.getItem(comboReducing.getSelectionIndex()).equals("New Reducing")) {
			comboStrategy.setEnabled(true);
		}

		List<String> strategyList = strategySearch();
		for (String strategy : strategyList) {
			comboStrategy.add(strategy.substring(0, strategy.length() - 5));
		}
		comboStrategy.select(0);

		comboReducing.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int index = comboReducing.getSelectionIndex();
				if (comboReducing.getItem(index).equals("New Reducing")) {
					buttonReducing.setEnabled(true);
					comboStrategy.setEnabled(true);
					buttonLoad.setEnabled(false);
				} else {
					buttonReducing.setEnabled(false);
					comboStrategy.setEnabled(false);
					buttonLoad.setEnabled(true);
				}
			}
		});

		comboStrategy.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				buttonLoad.setEnabled(false);
			}
		});

		buttonReducing.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				int indexStrategy = comboStrategy.getSelectionIndex();
				switch (comboStrategy.getItem(indexStrategy)) {
				case "StrategyMostCoveringReduction":
					strategy = new StrategyMostCoveringReduction();
					break;
				default:
					break;
				}

				Shell shell = getShell();
				if (strategy == null) {

					IStatus status = new Status(IStatus.ERROR, "pageReducing",
							"non � stata selezionata alcuna strategia");
					ErrorDialog.openError(shell, "Error", "Reducing Error", status);
				} else {
					TestSuite ts = strategy
							.reduceTestSuite((Clusters) Modello.getInstance().getBean(Constants.CLUSTER));
					ts.setFullName("Reduction_"
							+ ((TestSuite) Modello.getInstance().getBean(Constants.TEST_SUITE_OBJ)).getFullName());
					try {
						IDAOTestSuite dao = new DAOXmlTestSuite();
						if(ts!=null) {
						dao.save(ts, "Reduction_"
								+ ((TestSuite) Modello.getInstance().getBean(Constants.TEST_SUITE_OBJ)).getFullName());
						Modello.getInstance().putBean(Constants.REDUCING_TEST_SUITE, ts);
						MessageDialog.openInformation(shell, "Information", "Reducing Done");
						DirectoryReducing dr = (DirectoryReducing) Modello.getInstance().getBean(Constants.DIRECTORY_REDUCING);
						dr.setPageComplete(true);
						
						PageShowResult.caricaText();
						}
					} catch (XMLException e) {
						IStatus status = new Status(IStatus.ERROR, "pageReducing",
								"errore nel salvataggio della testSuite ridotta");
						ErrorDialog.openError(shell, "Error", "Reducing Error", status);
						e.printStackTrace();
					}
				}
			}
		});

		buttonLoad.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				Shell shell = getShell();
				try {

					IDAOTestSuite dao = new DAOXmlTestSuite();
					TestSuite ts = dao.load(comboReducing.getItem(comboReducing.getSelectionIndex()).substring(0,
							comboReducing.getItem(comboReducing.getSelectionIndex()).length() - 4));
					if(ts!=null) {
					Modello.getInstance().putBean(Constants.REDUCING_TEST_SUITE, ts);
					MessageDialog.openInformation(shell, "Information", "Load Done");
					DirectoryReducing dr = (DirectoryReducing) Modello.getInstance().getBean(Constants.DIRECTORY_REDUCING);
					dr.setPageComplete(true);
					PageShowResult.caricaText();
					}
				} catch (XMLException e) {
					IStatus status = new Status(IStatus.ERROR, "pageReducing",
							"errore nel caricamnto della testSuite ridotta");
					ErrorDialog.openError(shell, "Error", "Reducing Error", status);
					e.printStackTrace();
				}
			}
		});

	}

	public List<File> existingReducing() {
		List<File> listaFileCoverage = new ArrayList<File>();
		URL location = PageReducing.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("storage");
		File directoryStorage = new File(path.toString());
		File[] listaFile = directoryStorage.listFiles();
		if (listaFile != null) {
			for (int i = 0; i < listaFile.length; i++) {
				if (listaFile[i].getName().contains("Reduction_")) {
					listaFileCoverage.add(listaFile[i]);
				}
			}
		}
		return listaFileCoverage;
	}

	public List<String> strategySearch() {
		List<String> risultato = new ArrayList<String>();
		URL location = PageReducing.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("src/it/unibas/trikc/modelEntity/reduction");
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

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
