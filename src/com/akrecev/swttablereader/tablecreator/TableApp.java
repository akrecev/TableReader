package com.akrecev.swttablereader.tablecreator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.akrecev.swttablereader.filehandler.FileHandler;

public class TableApp {

	private TableManager tableManager;

	public void createContent(Shell shell) {
		shell.setLayout(new GridLayout(2, false));
		tableManager = new TableManager(shell);

		Composite panel = new Composite(shell, SWT.NONE);
		panel.setLayout(new GridLayout(1, false));
		panel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));

		createButtons(shell, panel);
	}

	private void createButtons(Shell shell, Composite panel) {
		Button loadButton = new Button(panel, SWT.PUSH);
		loadButton.setText("Загрузить файл");
		loadButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileHandler.loadFile(shell, tableManager);
			}
		});

		Button saveButton = new Button(panel, SWT.PUSH);
		saveButton.setText("Сохранить файл");
		saveButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileHandler.saveFile(shell, tableManager.getTableData());
			}
		});

		Text firstNameText = new Text(panel, SWT.BORDER);
		firstNameText.setMessage("Имя");

		Text lastNameText = new Text(panel, SWT.BORDER);
		lastNameText.setMessage("Фамилия");

		Button addButton = new Button(panel, SWT.PUSH);
		addButton.setText("Добавить строку");
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String firstName = firstNameText.getText();
				String lastName = lastNameText.getText();
				if (!firstName.isEmpty() && !lastName.isEmpty()) {
					tableManager.addRow(firstName, lastName);
					firstNameText.setText("");
					lastNameText.setText("");
				} else {
					showError(shell, "Имя и Фамилия не могут быть пустыми.");
				}
			}
		});

		Button deleteButton = new Button(panel, SWT.PUSH);
		deleteButton.setText("Удалить строку");
		deleteButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (tableManager.getSelectionIndex() == -1) {
					showError(shell, "Не выбрана строка для удаления.");
				} else {
					tableManager.deleteSelectedRow();
				}
			}
		});

		Button editButton = new Button(panel, SWT.PUSH);
		editButton.setText("Изменить строку");
		editButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = tableManager.getSelectionIndex();
				if (selectionIndex == -1) {
					showError(shell, "Не выбрана строка для изменения.");
				} else {
					String firstName = firstNameText.getText();
					String lastName = lastNameText.getText();
					if (!firstName.isEmpty() && !lastName.isEmpty()) {
						tableManager.editRow(selectionIndex, firstName, lastName);
						firstNameText.setText("");
						lastNameText.setText("");
					} else {
						showError(shell, "Имя и Фамилия не могут быть пустыми.");
					}
				}
			}
		});

		Button moveUpButton = new Button(panel, SWT.PUSH);
		moveUpButton.setText("Переместить вверх");
		moveUpButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (tableManager.getSelectionIndex() == -1) {
					showError(shell, "Не выбрана строка для перемещения вверх.");
				} else {
					tableManager.moveRowUp();
				}
			}
		});

		Button moveDownButton = new Button(panel, SWT.PUSH);
		moveDownButton.setText("Переместить вниз");
		moveDownButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (tableManager.getSelectionIndex() == -1) {
					showError(shell, "Не выбрана строка для перемещения вниз.");
				} else {
					tableManager.moveRowDown();
				}
			}
		});

		Button sortButton = new Button(panel, SWT.PUSH);
		sortButton.setText("Сортировать");
		sortButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tableManager.sortTable();
			}
		});
	}

	private void showError(Shell shell, String message) {
		MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		dialog.setText("Ошибка");
		dialog.setMessage(message);
		dialog.open();
	}
}
