package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.exception.NoPermissionToDelete;
import com.rikkei.managementuser.exception.ScheduleException;
import com.rikkei.managementuser.model.dto.request.ScheduleRequest;
import com.rikkei.managementuser.model.dto.response.ResponseAPI;
import com.rikkei.managementuser.service.IScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user-management/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final IScheduleService scheduleService;

    @GetMapping("")
    public ResponseEntity<?> getAll () {
        return ResponseEntity.status(200).body(scheduleService.findAllSchedule());
    };
    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody ScheduleRequest scheduleRequest) throws ScheduleException {
        scheduleService.save(scheduleRequest);
        return ResponseEntity.status(201).body(new ResponseAPI(true,"Thêm mới thành công !"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest, @PathVariable Long id) {
        scheduleService.editSchedule(scheduleRequest, id);
        return ResponseEntity.status(201).body(new ResponseAPI(true, "Thay đổi lịch học thàng công"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) throws NoPermissionToDelete {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.status(204).body(null);
    }

}
