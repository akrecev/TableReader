package com.akrecev.swttablereader.filehandler;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.akrecev.swttablereader.tablecreator.TableManager;

import java.io.*;
import java.util.List;

public class FileHandler {

	public static void loadFile(Shell shell, TableManager tableManager) {
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.txt" });
		String filePath = dialog.open();
		if (filePath != null) {
			try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
				String line;
				tableManager.clearTable();
				while ((line = br.readLine()) != null) {
					String[] parts = line.split(" ");
					if (parts.length == 2) {
						tableManager.addRow(parts[0], parts[1]);
					}
				}
			} catch (IOException e) {
				showError(shell, "Ошибка при загрузке файла: " + e.getMessage());
			}
		}
	}

	public static void saveFile(Shell shell, List<String[]> tableData) {
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFilterExtensions(new String[] { "*.txt", "*.xls" });
		String filePath = dialog.open();
		if (filePath != null) {
			if (filePath.endsWith(".txt")) {
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
					for (String[] row : tableData) {
						bw.write(row[0] + " " + row[1]);
						bw.newLine();
					}
				} catch (IOException e) {
					showError(shell, "Ошибка при сохранении файла: " + e.getMessage());
				}
			} else if (filePath.endsWith(".xls")) {
				try (Workbook workbook = new HSSFWorkbook()) {
					Sheet sheet = workbook.createSheet("Table Data");
					for (int i = 0; i < tableData.size(); i++) {
						Row row = sheet.createRow(i);
						row.createCell(0).setCellValue(tableData.get(i)[0]);
						row.createCell(1).setCellValue(tableData.get(i)[1]);
					}
					try (FileOutputStream fos = new FileOutputStream(filePath)) {
						workbook.write(fos);
					}
				} catch (IOException e) {
					showError(shell, "Ошибка при сохранении файла: " + e.getMessage());
				}
			}
		}
	}

	private static void showError(Shell shell, String message) {
		MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		dialog.setText("Ошибка");
		dialog.setMessage(message);
		dialog.open();
	}
}
