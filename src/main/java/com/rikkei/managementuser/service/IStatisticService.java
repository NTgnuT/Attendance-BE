package com.rikkei.managementuser.service;

import com.rikkei.managementuser.model.dto.request.StatisticRequest;
import com.rikkei.managementuser.model.dto.response.StatisticResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface IStatisticService {
    List<StatisticResponse> showStatistic (StatisticRequest request);

    void statisticExcelExport (HttpServletResponse response, List<StatisticResponse> statisticResponses) throws IOException;
}
