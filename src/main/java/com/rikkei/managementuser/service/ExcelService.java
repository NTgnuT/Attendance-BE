package com.rikkei.managementuser.service;

import com.rikkei.managementuser.model.entity.Student;
import com.rikkei.managementuser.repository.IStudentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class ExcelService {
    private final IStudentRepository studentRepository;

    public void importToDatabase(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream))
        {
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = sheet.iterator();
            if (iterator.hasNext()) {
                iterator.next();
            }
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Student entity = createStudentFromRow(currentRow);
                if (entity != null) {
                    studentRepository.save(entity);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private Student createStudentFromRow(Row row) throws ParseException {

        Student student = new Student();
        student.setName(row.getCell(0).getStringCellValue());
        Cell cellSDT = row.getCell(1); // Cột "SDT" là cột 1
        String sdt;
        if (cellSDT.getCellType() == CellType.NUMERIC) {
            // Nếu là dạng số mũ (exponential) thì chuyển đổi sang chuỗi
            sdt = String.format("%.0f", cellSDT.getNumericCellValue());

        } else {
            // Nếu không phải là dạng số mũ thì đọc như chuỗi
            sdt = cellSDT.getStringCellValue();
        }
        String test = "0" + sdt;
        student.setPhoneNumber(sdt);
        student.setAddress(row.getCell(2).getStringCellValue());
        student.setEmail(row.getCell(3).getStringCellValue());
//        Cell birthdateCell = row.getCell(4);
//       Date test1= birthdateCell.getDateCellValue();
        student.setDob(row.getCell(4).getDateCellValue());
//        if(birthdateCell != null && DateUtil.isCellDateFormatted(birthdateCell)) {
//            Date birthdate = birthdateCell.getDateCellValue();
//            student.setDob(birthdate);
//        } else if(birthdateCell != null && birthdateCell.getCellType() == CellType.STRING) {
//            String dateStr = birthdateCell.getStringCellValue();
//
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            try {
//                Date birthdate = simpleDateFormat.parse(dateStr);
//                student.setDob(birthdate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//
//            }
//        } else {
//            // Handle cell with no or invalid date value
//        }

        return student;
    }
}
