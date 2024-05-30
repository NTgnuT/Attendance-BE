package com.rikkei.managementuser.service;

import com.rikkei.managementuser.model.dto.response.StatisticResponse;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class ExcelExportUtils {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
//    private List<StatisticResponse> statisticResponses;
//    public void ExcelExportUtils(List<StatisticResponse> statisticResponses) {
//        this.statisticResponses = statisticResponses;
//        workbook = new XSSFWorkbook();
//    }

    private void createCells (Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void createHeaderRow() {
        sheet = workbook.createSheet("Statistic");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCells(row, 0, "Statistic Attendance", style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        font.setFontHeight((short) 10);

        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCells(row, 0, "student ID", style);
        createCells(row, 1, "Student name", style);
        createCells(row, 2, "Present", style);
        createCells(row, 3, "Absence With Out Permission", style);
        createCells(row, 4, "Absence With Permission", style);
        createCells(row, 5, "Percent Absent", style);
    }

    private void writeData (List<StatisticResponse> statisticResponses) {
        int rowCount = 2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (StatisticResponse statistic: statisticResponses) {
            Row row = sheet.createRow(rowCount++);
            int columCount = 0;
            createCells(row, columCount++, statistic.getStudentId(), style);
            createCells(row, columCount++, statistic.getStudentName(), style);
            createCells(row, columCount++, statistic.getPresent(), style);
            createCells(row, columCount++, statistic.getAbsenceWithOutPermission(), style);
            createCells(row, columCount++, statistic.getAbsenceWithPermission(), style);
            createCells(row, columCount++, statistic.getPercentAbsent(), style);
        }
    }

    public void exportDataToExcel (HttpServletResponse response, List<StatisticResponse> statisticResponses) throws IOException {
        workbook = new XSSFWorkbook();

        createHeaderRow();
        writeData(statisticResponses);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
