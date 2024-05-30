package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.exception.NoPermissionToDelete;
import com.rikkei.managementuser.exception.ScheduleException;
import com.rikkei.managementuser.model.dto.request.AttendanceRequest;
import com.rikkei.managementuser.model.dto.request.ScheduleRequest;
import com.rikkei.managementuser.model.dto.response.ScheduleResponse;
import com.rikkei.managementuser.model.entity.*;
import com.rikkei.managementuser.model.entity.Class;
import com.rikkei.managementuser.repository.*;
import com.rikkei.managementuser.service.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {
    private final IScheduleRepository scheduleRepository;
    private final ITeacherRepository teacherRepository;
    private final IClassRepository classRepository;
    private final IModuleCourseRepository moduleCourseRepository;
    private final IAttendanceRepository attendanceRepository;
    private final IStudentRepository studentRepository;
    private final IAttendanceDetailRepository attendanceDetailRepository;

    @Override
    public void save(ScheduleRequest scheduleRequest) throws ScheduleException {
        List<Student> students = studentRepository.findByAClass_Id(scheduleRequest.getClassId());
        Teacher teacher = teacherRepository.findById(scheduleRequest.getTeacherId())
                .orElseThrow(() -> new NoSuchElementException("Không tồn tại giảng viên này "));
        Class aClass = classRepository.findById(scheduleRequest.getClassId())
                .orElseThrow(() -> new NoSuchElementException("Không tồn tại lớp học này "));
        ModuleCourse moduleCourse = moduleCourseRepository.findById(scheduleRequest.getModuleId())
                .orElseThrow(() -> new NoSuchElementException("Không tồn tại module học này "));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate = formatter.parse(scheduleRequest.getTimeStart());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (scheduleRepository.findSchedule(scheduleRequest.getTeacherId(), scheduleRequest.getClassId(), scheduleRequest.getModuleId()) != null) {
            throw new NoSuchElementException("Lịch học này đã tồn tại");
        }

        if (students.isEmpty()) {
            throw new NoSuchElementException("Lớp học này chưa có sinh viên nào ");
        }

        if (startDate != null && startDate.after(new Date())) {
            Schedule schedule = Schedule.builder()
                    .teacher(teacher)
                    .aClass(aClass)
                    .moduleCourse(moduleCourse)
                    .timeStart(startDate)
                    .build();
            scheduleRepository.save(schedule);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate); // Set ngày bắt đầu

            int lessonCount = 0;
            // Xử lý ngày đầu tiên
            if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                Attendance firstAttendance = Attendance.builder()
                        .schedule(schedule)
                        .timeAttendance(calendar.getTime())
                        .build();
                attendanceRepository.save(firstAttendance);
                for (Student student : students) {
                    AttendanceDetail attendanceDetail = AttendanceDetail.builder()
                            .attendance(firstAttendance)
                            .student(student)
                            .build();
                    attendanceDetailRepository.save(attendanceDetail);
                }
                lessonCount++;
            }

            // Vòng lặp cho các ngày tiếp theo
            while (lessonCount < moduleCourse.getLesson()) {
                // Tăng ngày
                calendar.add(Calendar.DATE, 1);
                Date currentDate = calendar.getTime();

                // Kiểm tra xem ngày hiện tại có phải là ngày cuối tuần không
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                    Attendance attendance = Attendance.builder()
                            .schedule(schedule)
                            .timeAttendance(currentDate)
                            .build();
                    attendanceRepository.save(attendance);
                    for (Student student : students) {
                        AttendanceDetail attendanceDetail = AttendanceDetail.builder()
                                .attendance(attendance)
                                .student(student)
                                .build();
                        attendanceDetailRepository.save(attendanceDetail);
                    }
                    lessonCount++;
                }
            }
        } else {
            throw new NoSuchElementException("Thời gian tạo lịch học không hợp lệ");
        }
    }



    @Override
    public ScheduleResponse findById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy lịch học với Id cung cấp"));
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .teacherName(schedule.getTeacher().getName())
                .className(schedule.getAClass().getName())
                .moduleName(schedule.getModuleCourse().getModuleName())
                .timeStart(schedule.getTimeStart())
                .build();
    }

    @Override
    public List<ScheduleResponse> findAllSchedule() {
        return scheduleRepository.findAll().stream().map(s -> ScheduleResponse.builder()
                .id(s.getId())
                .teacherName(s.getTeacher().getName())
                .className(s.getAClass().getName())
                .moduleName(s.getModuleCourse().getModuleName())
                .timeStart(s.getTimeStart())
                .build()).collect(Collectors.toList());
    }

    @Override
    public void editSchedule(ScheduleRequest scheduleRequest, Long id) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(scheduleRequest.getTimeStart());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại lịch học này "));
        schedule.setTeacher(teacherRepository.findById(scheduleRequest.getTeacherId()).orElseThrow(() -> new NoSuchElementException("Không tồn tại giảng viên này ")));
        schedule.setAClass(classRepository.findById(scheduleRequest.getClassId()).orElseThrow(() -> new NoSuchElementException("Không tồn tại lớp học này ")));
        schedule.setModuleCourse(moduleCourseRepository.findById(scheduleRequest.getModuleId()).orElseThrow(() -> new NoSuchElementException("Không tồn tại module này ")));
        schedule.setTimeStart(date);

        scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Long scheduleId) throws NoPermissionToDelete {
        List<Attendance> attendances = attendanceRepository.findAllBySchedule_Id(scheduleId);

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy lịch học bạn muốn xóa"));

        for (Attendance attendance : attendances) {
            List<AttendanceDetail> attendanceDetails = attendanceDetailRepository.findAttendanceDetailByAttendance_Id(attendance.getId());
            attendanceDetailRepository.deleteAll(attendanceDetails);
            attendanceRepository.delete(attendance);
        }

        scheduleRepository.delete(schedule);

//        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
//        if (isAdmin) {
//            scheduleRepository.delete(scheduleRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tòn tại lịch học này!")));
//        } else {
//            throw new NoPermissionToDelete("Bạn không có quyền xóa lịch học này!");
//        }
    }

    @Override
    public ScheduleResponse findScheduleByTeacherAndClass(ScheduleRequest s) {
        Schedule schedule = scheduleRepository.findSchedule(s.getTeacherId(), s.getClassId(), s.getModuleId());
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .teacherName(schedule.getTeacher().getName())
                .className(schedule.getAClass().getName())
                .moduleName(schedule.getModuleCourse().getModuleName())
                .timeStart(schedule.getTimeStart()).build();
    }
}
