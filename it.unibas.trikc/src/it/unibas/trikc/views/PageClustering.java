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
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.clustering.IStrategyClustering;
import it.unibas.trikc.modelEntity.clustering.StrategyHierarchicalClustering;
import it.unibas.trikc.repository.XMLException;
import it.unibas.trikc.repository.clusters.DAOXmlClusters;
import it.unibas.trikc.repository.clusters.IDAOClusters;

public class PageClustering extends Composite {
	private static Logger logger = Logger.getLogger(PageClustering.class.getName());
	private Button buttonClustering, buttonLoad;
	private Label labelClustering;
	private Combo comboClustering, comboStrategy;
	private IStrategyClustering strategy;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PageClustering(Composite parent, int style) {
		super(parent, style);

		buttonClustering = new Button(this, SWT.NONE);
		buttonClustering.setBounds(29, 150, 109, 28);
		buttonClustering.setText("GettingClustering");

		labelClustering = new Label(this, SWT.NONE);
		labelClustering.setBounds(265, 45, 175, 14);

		comboClustering = new Combo(this, SWT.NONE);
		comboClustering.setBounds(29, 42, 203, 22);

		comboStrategy = new Combo(this, SWT.NONE);
		comboStrategy.setBounds(29, 98, 203, 23);

		buttonLoad = new Button(this, SWT.NONE);
		buttonLoad.setBounds(155, 150, 70, 28);
		buttonLoad.setText("Load");

		List<File> listaFile = existingClustering();

		if (listaFile.size() > 0) {
			for (File file : listaFile) {
				comboClustering.add(file.getName());
			}
			labelClustering.setText("existing clustering");
			comboStrategy.setEnabled(false);
			comboClustering.setEnabled(true);
			buttonClustering.setEnabled(false);
		} else {
			comboClustering.setEnabled(true);
			buttonClustering.setEnabled(true);
			buttonLoad.setEnabled(false);
			comboStrategy.setEnabled(true);
		}
		comboClustering.add("New Cluster");
		comboClustering.select(0);

		if (comboClustering.getItem(comboClustering.getSelectionIndex()).equals("New Cluster")) {
			comboStrategy.setEnabled(true);
		}

		List<String> strategyList = strategySearch();
		for (String strategy : strategyList) {
			comboStrategy.add(strategy.substring(0, strategy.length() - 5));
		}
		comboStrategy.select(0);

		comboClustering.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int index = comboClustering.getSelectionIndex();
				if (comboClustering.getItem(index).equals("New Cluster")) {
					buttonClustering.setEnabled(true);
					comboStrategy.setEnabled(true);
					buttonLoad.setEnabled(false);
				} else {
					buttonClustering.setEnabled(false);
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

		buttonClustering.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				int indexStrategy = comboStrategy.getSelectionIndex();
				switch (comboStrategy.getItem(indexStrategy)) {
				case "StrategyHierarchicalClustering":
					strategy = new StrategyHierarchicalClustering();
					break;
				default:
					break;
				}

				Shell shell = getShell(); 
				//Shell shell = new Shell();
				if (strategy == null) {
					IStatus status = new Status(IStatus.ERROR, "pageDissimilarity",
							"non � stata selezionata alcuna strategia");
					ErrorDialog.openError(shell, "Error", "Dissimilarity Error", status);
				} else {
					Clusters clusters = strategy.clusterTestCases(
							(DissimilarityMatrix) Modello.getInstance().getBean(Constants.DISSIMILARITY_MATRIX));
					try {
						IDAOClusters dao = new DAOXmlClusters();
						dao.save(clusters, "Clustering_"
								+ ((TestSuite) Modello.getInstance().getBean(Constants.TEST_SUITE_OBJ)).getFullName());
						Modello.getInstance().putBean(Constants.CLUSTER, clusters);
						MessageDialog.openInformation(shell, "Information", "Clustering Done");
						DirectoryClustering dc = (DirectoryClustering) Modello.getInstance()
								.getBean(Constants.DIRECTORY_CLUSTERING);
						if (clusters != null) {
							dc.setPageComplete(true);
						}
					} catch (XMLException e) {
						IStatus status = new Status(IStatus.ERROR, "pageCLustering",
								"errore nel salvataggio dei cluster");
						ErrorDialog.openError(shell, "Error", "Clustering Error", status);
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
					IDAOClusters dao = new DAOXmlClusters();
					Clusters cluster = dao.load(comboClustering.getItem(comboClustering.getSelectionIndex())
							.substring(0, comboClustering.getItem(comboClustering.getSelectionIndex()).length() - 4));
					Modello.getInstance().putBean(Constants.CLUSTER, cluster);
					MessageDialog.openInformation(shell, "Information", "Load Done");
					if (cluster != null) {
						DirectoryClustering dc = (DirectoryClustering) Modello.getInstance()
								.getBean(Constants.DIRECTORY_CLUSTERING);
						dc.setPageComplete(true);
					}
				} catch (XMLException e) {
					IStatus status = new Status(IStatus.ERROR, "pageCluster", "errore nel caricamento del cluster");
					ErrorDialog.openError(shell, "Error", "Cluster Error", status);
					e.printStackTrace();
				}
			}
		});

	}

	public List<File> existingClustering() {
		List<File> listaFileCoverage = new ArrayList<File>();
		URL location = PageClustering.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("storage");
		File directoryStorage = new File(path.toString());
		File[] listaFile = directoryStorage.listFiles();
		if (listaFile != null) {
			for (int i = 0; i < listaFile.length; i++) {
				if (listaFile[i].getName().contains("Clustering_")) {
					listaFileCoverage.add(listaFile[i]);
				}
			}
		}
		return listaFileCoverage;
	}

	public List<String> strategySearch() {
		List<String> risultato = new ArrayList<String>();
		URL location = PageClustering.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("src/it/unibas/trikc/modelEntity/clustering");
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
