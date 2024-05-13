package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.exception.CourseExistException;
import com.rikkei.managementuser.model.dto.request.AttendanceDetailRequest;
import com.rikkei.managementuser.model.dto.request.AttendanceRequest;
import com.rikkei.managementuser.model.dto.request.StatisticRequest;
import com.rikkei.managementuser.model.dto.response.StatisticResponse;
import com.rikkei.managementuser.model.entity.*;
import com.rikkei.managementuser.repository.IAttendanceDetailRepository;
import com.rikkei.managementuser.repository.IAttendanceRepository;
import com.rikkei.managementuser.repository.IScheduleRepository;
import com.rikkei.managementuser.repository.IStudentRepository;
import com.rikkei.managementuser.service.IAttendanceDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AttendanceDetailService implements IAttendanceDetailService {
    private final IAttendanceDetailRepository attendanceDetailRepository;
    private final StatisticService statisticService;
    private final EmailService emailService;
    private final IStudentRepository studentRepository;
    private final IScheduleRepository scheduleRepository;

//    @Override
//    public void updateAttendanceDetails(AttendanceDetailRequest attDetail) throws CourseExistException {
//        Iterator<Long> studentIdIter = attDetail.getStudentId().iterator();
//        Iterator<String> statusIter = attDetail.getStatus().iterator();
//
//        // Kiểm tra xem có phần tử tiếp theo hay không
//        while (studentIdIter.hasNext() && statusIter.hasNext()) {
//            // Lấy phần tử tiếp theo
//            Long studentId = studentIdIter.next();
//            String status = statusIter.next();
//
//            AttendanceDetail attNew = attendanceDetailRepository.findByStudentIdAndAttendanceId(studentId, attDetail.getId());
//
//            if (attNew != null) {
//                switch (status) {
//                    case "d":
//                        attNew.setAttendanceStatus(AttendanceStatus.PRESENT);
//                        break;
//                    case "cp":
//                        attNew.setAttendanceStatus(AttendanceStatus.ABSENCE_WITH_PERMISSION);
//                        break;
//                    case "kp":
//                        attNew.setAttendanceStatus(AttendanceStatus.ABSENCE_WITHOUT_PERMISSION);
//                        break;
//                    default:
//                        throw new CourseExistException("Không tồn tại trạng thái này");
//                }
//                attendanceDetailRepository.save(attNew);
//            } else {
//                throw new NoSuchElementException("Không tìm thấy chi tiết điểm danh của sinh viên này");
//            }
//        }
//    }

    @Override
    public void updateAttendanceDetails(List<AttendanceDetailRequest> attDetails) throws CourseExistException {
        for (AttendanceDetailRequest attDetail : attDetails) {
            AttendanceDetail attNew = attendanceDetailRepository.findById(attDetail.getId()).orElseThrow(() -> new NoSuchElementException("Không tìm thấy chi tiết điểm danh"));
            if (attNew != null) {
                switch (attDetail.getStatus()) {
                    case "d":
                        attNew.setAttendanceStatus(AttendanceStatus.PRESENT);
                        break;
                    case "cp":
                        attNew.setAttendanceStatus(AttendanceStatus.ABSENCE_WITH_PERMISSION);
                        break;
                    case "kp":
                        attNew.setAttendanceStatus(AttendanceStatus.ABSENCE_WITHOUT_PERMISSION);
                        break;
                    default:
                        throw new CourseExistException("Không tồn tại trạng thái này");
                }
                Schedule schedule = scheduleRepository.findById(attDetail.getScheduleId()).orElseThrow(()-> new NoSuchElementException("Không tìm thấy lịch học này"));

                StatisticRequest statisticRequest = new StatisticRequest(schedule.getAClass().getId(), schedule.getModuleCourse().getId());
                List<StatisticResponse> statisticResponses = statisticService.showStatistic(statisticRequest);
                for (StatisticResponse statisticResponse : statisticResponses) {
                    if (statisticResponse.getStudentId().equals(attNew.getStudent().getId()) && statisticResponse.getPercentAbsent() > 20) {
                        if (attDetail.getStatus().equals("cp") || attDetail.getStatus().equals("kp")) {
                            Student student = studentRepository.findById(attDetail.getStudentId()).orElseThrow(()-> new NoSuchElementException("Không tìm thấy sinh viên này"));
                            emailService.sendMail(student, statisticResponse);
                        }
                    }
                }
                attendanceDetailRepository.save(attNew);
            }
        }
    }
    @Override
    public List<AttendanceDetail> findAttendanceDetailByAttendance(Long id) {
        return attendanceDetailRepository.findAttendanceDetailByAttendance_Id(id);
    }
}
