package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.model.dto.request.StatisticRequest;
import com.rikkei.managementuser.model.dto.response.StatisticResponse;
import com.rikkei.managementuser.service.IStatisticService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user-management/api/statistic")
@RequiredArgsConstructor
public class StatisticController {
    private final IStatisticService statisticService;
    @PostMapping("")
    public ResponseEntity<?> statistic (@RequestBody StatisticRequest statisticRequest) {
        return ResponseEntity.status(201).body(statisticService.showStatistic(statisticRequest));
    }

    @PostMapping("/export-to-excel")
    public void exportExcel (HttpServletResponse response,@RequestBody List<StatisticResponse> statisticResponses) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Statistic.xlsx";

        response.setHeader(headerKey, headerValue);
        statisticService.statisticExcelExport(response, statisticResponses);
    }
    @GetMapping("/export-to-excel")
    public void exportExcel1 (HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Statistic.xlsx";

        response.setHeader(headerKey, headerValue);
        statisticService.statisticExcelExport(response, new ArrayList<>());
    }
}
