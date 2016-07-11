package it.unibas.trikc.views;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import cc.mallet.grmm.learning.GenericAcrfData2TokenSequence;
import it.unibas.trikc.Constants;
import it.unibas.trikc.Modello;

import org.eclipse.jface.dialogs.Dialog;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;

public class MainWindow extends ApplicationWindow {
	private static Logger logger = Logger.getLogger(MainWindow.class.getName());
	private WizardDialog dialog;
	private static boolean canClose = false;
	private static Shell shell;

	public MainWindow(Shell parentShell) {
		super(parentShell);
		/*Rectangle bounds = parentShell.getBounds(); 
		Rectangle rect = shell.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation (x, y);*/
		 
	}

	 protected Control createContents(Composite parent) {
		/* logger.info(""+ getShell().getMonitor());
		parent.setBounds(150, 150, 512, 256);
		
		URL location = PageCoverage.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("icons/trikc.gif");
		Image image = new Image(null, path.toString());*/
		
		//parent.setBackgroundImage(image);
		/*
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setBounds(270, 213, 75, 25);
		lblNewLabel.setText("New Label");
		lblNewLabel.setBackgroundImage(image);*/
		
		 //return null;
		 Button button = new Button(parent, SWT.PUSH);
		    button.setText("Make a trikc");
		    button.setBounds(270, 350, 75, 25);
		    button.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		    	  MainView vista = new MainView();
		        dialog = new WizardDialog(getShell(), vista);
		        dialog.setBlockOnOpen(true);
		        dialog.open();
		        Modello.getInstance().putBean(Constants.DIALOG_MAINWINDOW, dialog);
		      }
		    });
		/* parent.pack();*/
		 return button;
		  }

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return super.close();
	}

	 
}
