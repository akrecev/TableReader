package com.akrecev.swttablereader;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.akrecev.swttablereader.tablecreator.TableApp;

public class MainApp {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Table App");
		shell.setSize(600, 400);

		TableApp tableApp = new TableApp();
		tableApp.createContent(shell);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
