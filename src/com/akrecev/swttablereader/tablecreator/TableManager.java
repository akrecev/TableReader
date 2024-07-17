package com.akrecev.swttablereader.tablecreator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TableManager {
	private List<String[]> tableData = new ArrayList<>();
	private Table table;

	public TableManager(Composite parent) {
		table = new Table(parent, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn colFirstName = new TableColumn(table, SWT.NONE);
		colFirstName.setText("Имя");
		colFirstName.setWidth(150);

		TableColumn colLastName = new TableColumn(table, SWT.NONE);
		colLastName.setText("Фамилия");
		colLastName.setWidth(150);

		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	public void addRow(String firstName, String lastName) {
		tableData.add(new String[] { firstName, lastName });
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] { firstName, lastName });
	}

	public void deleteSelectedRow() {
		int index = table.getSelectionIndex();
		if (index != -1) {
			table.remove(index);
			tableData.remove(index);
		}
	}

	public void editRow(int index, String firstName, String lastName) {
		tableData.set(index, new String[] { firstName, lastName });
		table.getItem(index).setText(new String[] { firstName, lastName });
	}

	public void moveRowUp() {
		int index = table.getSelectionIndex();
		if (index > 0) {
			String[] row = tableData.get(index);
			tableData.remove(index);
			tableData.add(index - 1, row);

			TableItem item = table.getItem(index);
			String[] texts = { item.getText(0), item.getText(1) };
			table.remove(index);
			TableItem newItem = new TableItem(table, SWT.NONE, index - 1);
			newItem.setText(texts);
			table.setSelection(index - 1);
		}
	}

	public void moveRowDown() {
		int index = table.getSelectionIndex();
		if (index < tableData.size() - 1) {
			String[] row = tableData.get(index);
			tableData.remove(index);
			tableData.add(index + 1, row);

			TableItem item = table.getItem(index);
			String[] texts = { item.getText(0), item.getText(1) };
			table.remove(index);
			TableItem newItem = new TableItem(table, SWT.NONE, index + 1);
			newItem.setText(texts);
			table.setSelection(index + 1);
		}
	}

	public void sortTable() {
	    tableData = tableData.stream()
	            .sorted(Comparator.comparing(row -> row[0] + row[1]))
	            .collect(Collectors.toList());
	    table.removeAll();
	    for (String[] row : tableData) {
	        TableItem item = new TableItem(table, SWT.NONE);
	        item.setText(row);
	    }
	}

	public List<String[]> getTableData() {
		return tableData;
	}

	public int getSelectionIndex() {
		return table.getSelectionIndex();
	}

	public void clearTable() {
		table.removeAll();
		tableData.clear();
	}
}
