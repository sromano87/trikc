package it.unibas.trikc.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import it.unibas.trikc.Constants;
import it.unibas.trikc.Modello;

import java.util.StringTokenizer;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

public class PageSetUp extends Composite {
	//private static Logger logger = Logger.getLogger(PageSetUp.class.getName());

	private Label labelDirectory, labelTestSuite;
	private Button buttonDirectory, buttonTestSuite;
	private String selectedDir;
	private String fileFilterPath;
	private String testSuite, directoryBin;
	
	private String os = System.getProperty("os.name"); 

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PageSetUp(Composite parent, int style) {
		
		super(parent, style);

		
		
		
		labelDirectory = new Label(this, SWT.NONE);
		labelDirectory.setEnabled(false);
		labelDirectory.setText("Select the bin folder");
		labelDirectory.setBounds(10, 28, 275, 15);

		buttonDirectory = new Button(this, SWT.PUSH);
		buttonDirectory.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				Shell s = getShell();
				DirectoryDialog directoryDialog = new DirectoryDialog(s);
				directoryDialog.setFilterPath(selectedDir);
				directoryDialog.setMessage("Please select a directory and click OK");

				String dir = directoryDialog.open();
				if (dir != null) {
					labelDirectory.setText("" + dir);
					selectedDir = dir;
					directoryBin = elaboraDirectory(selectedDir);
					if (isWindows(os)) {
						directoryBin = directoryBin.substring(0, directoryBin.length() - 1);
					}
					if (directoryBin != null) {
						Modello.getInstance().putBean(Constants.DIRECTORY_BIN, directoryBin);
					}
					DirectoryPage dm = (DirectoryPage) Modello.getInstance().getBean(Constants.DIRECTORY_PAGE);
					if (Modello.getInstance().getBean(Constants.TEST_SUITE) != null
							&& Modello.getInstance().getBean(Constants.DIRECTORY_BIN) != null) {
						dm.setPageComplete(true);
					}

				}
			}
		});

		buttonDirectory.setBounds(345, 23, 75, 25);
		buttonDirectory.setText("Browse...");

		labelTestSuite = new Label(this, SWT.WRAP);
		labelTestSuite.setEnabled(false);
		labelTestSuite.setBounds(10, 87, 275, 15);
		labelTestSuite.setText("Select TestSuite");

		buttonTestSuite = new Button(this, SWT.PUSH);
		buttonTestSuite.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				Shell s = getShell();
				FileDialog fileDialog = new FileDialog(s, SWT.MULTI);
				fileFilterPath = setFilterPath();
				fileDialog.setFilterPath(fileFilterPath);
				fileDialog.setFilterExtensions(new String[] { "*.java", "*.*" });
				fileDialog.setFilterNames(new String[] { "Java Format", "Any" });

				String firstFile = fileDialog.open();
				if (firstFile != null) {
					fileFilterPath = fileDialog.getFilterPath();
					String[] selectedFiles = fileDialog.getFileNames();
					StringBuffer sb = new StringBuffer("" + fileDialog.getFilterPath()); 	
					if (isWindows(os)) {
						sb.append("\\");
					} else {
						sb.append("/");
					}
					sb.append(selectedFiles[0]);
					labelTestSuite.setText(sb.toString());
					testSuite = elaboraFile(sb.toString());
					testSuite = testSuite.substring(5, testSuite.length() - 1);
					String stringaTestSuite = testSuite.toLowerCase();
					if (testSuite != null && stringaTestSuite.contains("testsuite")) {
						Modello.getInstance().putBean(Constants.TEST_SUITE, testSuite);
					} else {
						Shell s1 = getShell();
						IStatus status = new Status(IStatus.ERROR, "pageSetUp",
								"the name of file not contains \" testSuite\"");
						ErrorDialog.openError(s1, "Error", "Error loading", status);
					}
					DirectoryPage dm = (DirectoryPage) Modello.getInstance().getBean(Constants.DIRECTORY_PAGE);
					if (Modello.getInstance().getBean(Constants.TEST_SUITE) != null
							&& Modello.getInstance().getBean(Constants.DIRECTORY_BIN) != null) {
						dm.setPageComplete(true);
					}
				}
			}
		});
		buttonTestSuite.setBounds(345, 82, 75, 25);
		buttonTestSuite.setText("Browse...");
	}

	public String elaboraDirectory(String dir) {
		return elaboraPercorsi(dir);
	}

	public String elaboraPercorsi(String path) {
		if(isWindows(os)) {
			StringTokenizer string = new StringTokenizer(path, "\\");
			StringBuffer risultato = new StringBuffer();
			while (string.hasMoreElements()) {
				risultato.append(string.nextToken() + "\\");
			}
			return risultato.toString();
		}
		else if(isMac(os)) {
			return path;
		}
		return path;	
	}

	public String elaboraPercorsiFile(String path) {
		boolean flag = false;
		StringBuffer risultato = new StringBuffer();
		if(isWindows(os)) {
			StringTokenizer string = new StringTokenizer(path, "\\");
			while (string.hasMoreElements()) {
				String appaggio = string.nextToken();
				if (appaggio.equals("test")) {
					flag = true;
				}
				if (flag) {
					risultato.append(appaggio + ".");
				}
			}
		}
		else if(isMac(os)) {
			StringTokenizer string = new StringTokenizer(path, "/");
			while (string.hasMoreElements()) {
				String appaggio = string.nextToken();
				if (appaggio.equals("test")) {
					flag = true;
				}
				if (flag) {
					risultato.append(appaggio + ".");
				}
			}
		}
		risultato.substring(0, risultato.length() - 1);
		risultato.substring(4, risultato.length() - 1);
		return risultato.toString();
	}

	public String elaboraFile(String file) {
		return elaboraPercorsiFile(file.substring(0, file.length() - 5));
	}

	public void setTextLabelDirectory(String text) {
		labelDirectory.setText(text);
	}

	public void setTextLabelTestSuite(String text) {
		labelTestSuite.setText(text);
	}

	public String getDirectoryBin() {
		return this.directoryBin;
	}

	public String getTestSuite() {
		return this.testSuite;
	}

	public String setFilterPath() {
		if(isWindows(os)) {
			return "c:/";
		}
		if(isMac(os)) {
			return "/Users";
		}
		return null;
	}
	
	public boolean isWindows(String os) {
		return os.toLowerCase().contains("win");
	}
	
	public boolean isMac(String os) {
		return os.toLowerCase().contains("mac");
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
