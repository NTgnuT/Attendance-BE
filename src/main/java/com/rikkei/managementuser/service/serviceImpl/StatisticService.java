package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.model.dto.request.StatisticRequest;
import com.rikkei.managementuser.model.dto.response.StatisticResponse;
import com.rikkei.managementuser.model.entity.ModuleCourse;
import com.rikkei.managementuser.model.entity.Schedule;
import com.rikkei.managementuser.repository.IModuleCourseRepository;
import com.rikkei.managementuser.repository.IScheduleRepository;
import com.rikkei.managementuser.repository.IStudentRepository;
import com.rikkei.managementuser.service.ExcelExportUtils;
import com.rikkei.managementuser.service.ExcelService;
import com.rikkei.managementuser.service.IStatisticService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {
    private final IStudentRepository studentRepository;
    private final IScheduleRepository scheduleRepository;
    private final IModuleCourseRepository moduleCourseRepository;
    @Override
    public List<StatisticResponse> showStatistic(StatisticRequest request) {
         Schedule schedule = scheduleRepository.findScheduleByClassIdAndModuleId(request.getClassId(), request.getModuleId());
        if (schedule != null) {
            ModuleCourse moduleCourse = moduleCourseRepository.findById(request.getModuleId()).orElseThrow(() -> new NoSuchElementException("Không tìm thấy module học này"));
            List<Object[]> objects = studentRepository.getAttendanceStatistics(request.getClassId(), request.getModuleId());
            List<StatisticResponse> responseList = new ArrayList<>();
            for (Object[] row : objects) {
                Long studentId = (Long) row[0];
                String studentName = (String) row[1];
                int present = ((BigDecimal) row[2]).intValue();
                int absenceWithOutPermission = ((BigDecimal) row[3]).intValue();
                int absenceWithPermission = ((BigDecimal) row[4]).intValue();
                int percentAbsent = ((absenceWithPermission + absenceWithOutPermission) * 100 ) / moduleCourse.getLesson();
                StatisticResponse response = new StatisticResponse(studentId, studentName, present, absenceWithOutPermission, absenceWithPermission, percentAbsent);
                responseList.add(response);
            }
            return responseList;
        } else {
            throw new NoSuchElementException("Không tìm thấy lịch học này");
        }
    }

    @Override
    public void statisticExcelExport(HttpServletResponse response, List<StatisticResponse> statisticResponses) throws IOException {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Statistic");
            XSSFRow row = sheet.createRow(0);

            row.createCell(0).setCellValue("Student ID");
            row.createCell(1).setCellValue("Student name");
            row.createCell(2).setCellValue("Present");
            row.createCell(3).setCellValue("Absence With Out Permission");
            row.createCell(4).setCellValue("Absence With Permission");
            row.createCell(5).setCellValue("Percent Absent");

            int dataRowIndex = 1;
            System.out.println(statisticResponses);
            for (StatisticResponse statisticResponse: statisticResponses) {
                XSSFRow dataRow = sheet.createRow(dataRowIndex);

                dataRow.createCell(0).setCellValue(statisticResponse.getStudentId());
                dataRow.createCell(1).setCellValue(statisticResponse.getStudentName());
                dataRow.createCell(2).setCellValue(statisticResponse.getPresent());
                dataRow.createCell(3).setCellValue(statisticResponse.getAbsenceWithOutPermission());
                dataRow.createCell(4).setCellValue(statisticResponse.getAbsenceWithPermission());
                dataRow.createCell(5).setCellValue(statisticResponse.getPercentAbsent());

                dataRowIndex++;
            }

            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
//        ExcelExportUtils excelExportUtils = new ExcelExportUtils();
//        excelExportUtils.exportDataToExcel(response, statisticResponses);
    }
}
