package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.model.dto.request.StatisticRequest;
import com.rikkei.managementuser.model.dto.response.StatisticResponse;
import com.rikkei.managementuser.model.entity.ModuleCourse;
import com.rikkei.managementuser.model.entity.Schedule;
import com.rikkei.managementuser.repository.IModuleCourseRepository;
import com.rikkei.managementuser.repository.IScheduleRepository;
import com.rikkei.managementuser.repository.IStudentRepository;
import com.rikkei.managementuser.service.IStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
