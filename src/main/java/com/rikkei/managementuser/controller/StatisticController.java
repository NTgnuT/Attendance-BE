package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.model.dto.request.StatisticRequest;
import com.rikkei.managementuser.service.IStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user-management/api/statistic")
@RequiredArgsConstructor
public class StatisticController {
    private final IStatisticService statisticService;
    @PostMapping("")
    public ResponseEntity<?> statistic (@RequestBody StatisticRequest statisticRequest) {
        return ResponseEntity.status(201).body(statisticService.showStatistic(statisticRequest));
    }
}
