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
import org.eclipse.swt.widgets.Slider;

public class PageClustering extends Composite {
	private static Logger logger = Logger.getLogger(PageClustering.class.getName());
	private Button buttonClustering;
	private static Button buttonLoad;
	private static Combo comboClustering;
	private Combo comboStrategy;
	private IStrategyClustering strategy;
	private Label lblNewLabel;
	private Label lblNewLabel_1;
	private static List<File> listaFile = null;
	private double sliderValue;
	private String linkageLevel;
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PageClustering(Composite parent, int style) {
		super(parent, style);

		buttonClustering = new Button(this, SWT.NONE);
		buttonClustering.setBounds(393, 206, 109, 28);
		buttonClustering.setText("New Clustering");

		comboClustering = new Combo(this, SWT.NONE);
		comboClustering.setBounds(29, 279, 301, 23);

		comboStrategy = new Combo(this, SWT.NONE);
		comboStrategy.setBounds(29, 68, 301, 23);

		buttonLoad = new Button(this, SWT.NONE);
		buttonLoad.setBounds(393, 275, 109, 28);
		buttonLoad.setText("Load");

		//listaFile = existingClustering();

		

		List<String> strategyList = strategySearch();
		for (String strategy : strategyList) {
			comboStrategy.add(strategy.substring(0, strategy.length() - 5));
		}
		comboStrategy.select(0);
		
		lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(29, 47, 301, 15);
		lblNewLabel.setText("Select the Strategy");
		
		lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setBounds(29, 115, 301, 15);
		lblNewLabel_1.setText("Select the level");
		
		final Combo comboLevel = new Combo(this, SWT.NONE);
		comboLevel.setBounds(29, 136, 301, 23);
		comboLevel.add("SINGLE LINKAGE",0);
		comboLevel.add("AVERAGE LINKAGE",1);
		comboLevel.add("COMPLETE LINKAGE",2);
		comboLevel.select(0);
		linkageLevel = comboLevel.getItem(0);
		comboLevel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//comboDissimilarity.getItem(comboDissimilarity.getSelectionIndex())
				linkageLevel = comboLevel.getItem(comboLevel.getSelectionIndex());
			}
		});
		
		Label lblNewLabel_3 = new Label(this, SWT.NONE);
		lblNewLabel_3.setBounds(27, 258, 303, 15);
		lblNewLabel_3.setText("Select an existing Clustering and load it");
		
		final Label labelSlider = new Label(this, SWT.NONE);
		labelSlider.setBounds(301, 206, 29, 15);
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setBounds(29, 183, 301, 15);
		lblNewLabel_2.setText("Select the threshold and run New Clustering");
		
		final Slider slider = new Slider(this, SWT.NONE);
		slider.setBounds(29, 204, 266, 17);
		slider.setMaximum(110);
	    slider.setMinimum(0);
	    slider.setSelection(50);
	    slider.setIncrement(10);
	    sliderValue = slider.getSelection();
	    sliderValue = sliderValue/100;
	    labelSlider.setText(""+Double.toString(sliderValue));
	    
	    slider.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event event) {
	        	sliderValue = slider.getSelection();
	        	sliderValue = sliderValue/100; 
	        	labelSlider.setText("" + Double.toString(sliderValue));
	        }
	      });
	   
		
		

		comboClustering.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			}
		});

		/*
		comboStrategy.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				buttonLoad.setEnabled(false);
			}
		});
		*/

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
							(DissimilarityMatrix) Modello.getInstance().getBean(Constants.DISSIMILARITY_MATRIX), sliderValue, linkageLevel);
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
						PageReducing.existingReducing();
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
					PageReducing.existingReducing();
				} catch (XMLException e) {
					IStatus status = new Status(IStatus.ERROR, "pageCluster", "errore nel caricamento del cluster");
					ErrorDialog.openError(shell, "Error", "Cluster Error", status);
					e.printStackTrace();
				}
			}
		});

	}

	public static void existingClustering() {
		listaFile = new ArrayList<File>();
		
		String path = System.getProperty("user.home"); 
		path = path + "/.TRIKC/";
		
		File directoryStorage = new File(path.toString());
		File[] listaFiles = directoryStorage.listFiles();
		if (listaFiles != null) {
			for (int i = 0; i < listaFiles.length; i++) {
				if (listaFiles[i].getName().contains("Clustering_"+(String)Modello.getInstance().getBean(Constants.TEST_SUITE))
						&& !verifyExistence(listaFiles[i])) {
					listaFile.add(listaFiles[i]);
				}
			}
		}
		if (listaFile.size() > 0) {
			for (File file : listaFile) {
				comboClustering.add(file.getName());
			}
			
		} else {
			comboClustering.setEnabled(false);
			buttonLoad.setEnabled(false);
		}
		if(comboClustering.getItemCount()>0) {
			comboClustering.select(0);
			comboClustering.setEnabled(true);
			buttonLoad.setEnabled(true);
		} else {
			comboClustering.setEnabled(false);
			buttonLoad.setEnabled(false);
		}
		
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
	
	public static boolean verifyExistence(File file) {
		String[] comboItems = comboClustering.getItems();
		//logger.info("dimensione combo "+comboItems.length);
		int count =0;
		for(int j=0; j<comboItems.length; j++) {
			logger.info("fileName "+file.getName()+" comboItem "+comboItems[j]+ " equals? "+file.getName().equals(comboItems[j]));
			if (file.getName().substring(0, file.getName().length()-4).equals(comboItems[j].substring(0, comboItems[j].length()-4))) {
				count++;
			}
		}
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
