package it.unibas.trikc.views;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import it.unibas.trikc.Constants;
import it.unibas.trikc.Modello;

public class PageSetUp extends Composite {
	private static Logger logger = Logger.getLogger(PageSetUp.class.getName());

	private Label labelDirectory, labelTestSuite;
	private Button buttonDirectory, buttonTestSuite;
	private String selectedDir;
	private String fileFilterPath;
	private String testSuite, directoryBin, directoryLib, directoryTest;
	
	private String os = System.getProperty("os.name"); 
	private Label lblSelectFolder;
	private Button buttonTestFolder;
	private Label lblSelectLib;
	private Button buttonLib;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PageSetUp(Composite parent, int style) {
		
		super(parent, style);
		selectedDir = setFilterPath();

		labelDirectory = new Label(this, SWT.NONE);
		labelDirectory.setEnabled(false);
		labelDirectory.setText("Select the bin folder");
		labelDirectory.setBounds(10, 28, 346, 15);

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
					setPageComplete();
				}
			}
		});

		buttonDirectory.setBounds(416, 23, 75, 25);
		buttonDirectory.setText("Browse...");

		labelTestSuite = new Label(this, SWT.WRAP);
		labelTestSuite.setEnabled(false);
		labelTestSuite.setBounds(10, 87, 346, 15);
		labelTestSuite.setText("Select TestSuite");

		buttonTestSuite = new Button(this, SWT.PUSH);
		
		buttonTestSuite.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				Shell s = getShell();
				FileDialog fileDialog = new FileDialog(s, SWT.MULTI);
				
				fileDialog.setFilterPath(selectedDir);
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
					Modello.getInstance().putBean(Constants.TEST_SUITE, testSuite);
					PageCoverage.existingCoverage();
					setPageComplete();
				}
			}
		});
		buttonTestSuite.setBounds(416, 77, 75, 25);
		buttonTestSuite.setText("Browse...");
		
		lblSelectFolder = new Label(this, SWT.WRAP);
		lblSelectFolder.setText("Select Test Folder with .class extension");
		lblSelectFolder.setEnabled(false);
		lblSelectFolder.setBounds(10, 205, 346, 15);
		
		buttonTestFolder = new Button(this, SWT.NONE);
		buttonTestFolder.setText("Browse...");
		buttonTestFolder.setBounds(416, 195, 75, 25);
		
		buttonTestFolder.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				Shell s = getShell();
				DirectoryDialog directoryDialog = new DirectoryDialog(s);
				
				directoryDialog.setFilterPath(selectedDir);
				directoryDialog.setMessage("Please select a directory and click OK");

				String dir = directoryDialog.open();
				if (dir != null) {
					lblSelectFolder.setText("" + dir);
					selectedDir = dir;
					directoryTest = elaboraDirectory(selectedDir);
					if (isWindows(os)) {
						directoryTest = directoryTest.substring(0, directoryTest.length() - 1);
					}
					if (directoryTest != null) {
						Modello.getInstance().putBean(Constants.DIRECTORY_TEST, directoryTest);
					}
					setPageComplete();
				}
			}
		});
		
		lblSelectLib = new Label(this, SWT.WRAP);
		lblSelectLib.setText("Select lib Folder");
		lblSelectLib.setEnabled(false);
		lblSelectLib.setBounds(10, 146, 346, 15);
		
		buttonLib = new Button(this, SWT.NONE);
		buttonLib.setText("Browse...");
		buttonLib.setBounds(416, 136, 75, 25);
		
		buttonLib.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				Shell s = getShell();
				DirectoryDialog directoryDialog = new DirectoryDialog(s);
				selectedDir = setFilterPath();
				directoryDialog.setFilterPath(selectedDir);
				directoryDialog.setMessage("Please select a directory and click OK");

				String dir = directoryDialog.open();
				if (dir != null) {
					lblSelectLib.setText("" + dir);
					selectedDir = dir;
					directoryLib = elaboraDirectory(selectedDir);
					if (isWindows(os)) {
						directoryLib = directoryLib.substring(0, directoryLib.length() - 1);
					}
					if (directoryLib != null) {
						Modello.getInstance().putBean(Constants.DIRECTORY_LIB, directoryLib);
					}
					setPageComplete();

				}
			}
		});
	}
	
	public void setPageComplete() {
		DirectoryPage dm = (DirectoryPage) Modello.getInstance().getBean(Constants.DIRECTORY_PAGE);
		if (Modello.getInstance().getBean(Constants.TEST_SUITE) != null
				&& Modello.getInstance().getBean(Constants.DIRECTORY_BIN) != null) {
			dm.setPageComplete(true);
		}
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
		System.out.println("---path: " + path);
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
		if (isWindows(os)) {
			String command = System.getProperty("eclipse.commands");
			String subCommand = command.substring(command.indexOf("-launcher") + 10, command.indexOf("eclipse.exe"));
			subCommand +="configuration\\.settings\\";
			
			String s;
			String risultato = null;
			FileReader f = null;
			BufferedReader b = null;
			StringBuilder  sb = null;
			try {
				f = new FileReader(subCommand+"org.eclipse.ui.ide.prefs");
				b = new BufferedReader(f);
				while((s = b.readLine())!=null) {
						if(s.contains("RECENT_WORKSPACES=") && !s.contains("MAX")) {
							risultato = s.substring(s.indexOf("=")+1, s.length());
						}
					}
				sb = new StringBuilder();
				StringTokenizer sT = new StringTokenizer(risultato, "\\");
				while (sT.hasMoreElements()) {
					String appoggio = sT.nextToken();
					if(appoggio.equals("C")) {
						appoggio = "c";
						sb.append(appoggio);
					}else {
					sb.append( appoggio + "/");
				}}
			} catch (FileNotFoundException e1) {
					return "c:/";
			} catch (IOException e) {
				e.printStackTrace();
			}
			return sb.toString();
		}
		if(isMac(os)) {
			String command = System.getProperty("java.class.path");
			String subCommand = command.substring(command.indexOf("OutPut= ") + 1, command.indexOf(".app"));
			subCommand +=".app/Contents/Eclipse/configuration/.settings/";
			String risultato = null;
			FileReader f = null;
			BufferedReader b = null;
			StringBuilder  sb = null;
			try {
				f = new FileReader(subCommand+"org.eclipse.ui.ide.prefs");
				b = new BufferedReader(f);
				String s = null;
				while((s = b.readLine())!=null) {
						if(s.contains("RECENT_WORKSPACES=") && !s.contains("MAX")) {
							risultato = s.substring(s.indexOf("=")+1,s.indexOf("\\n"));
						}
					}
			} catch (FileNotFoundException e1) {
					return "/Users";
			} catch (IOException e) {
				e.printStackTrace();
			} 
			return risultato;
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