package com.rikkei.managementuser.service;

import com.rikkei.managementuser.model.dto.request.StatisticRequest;
import com.rikkei.managementuser.model.dto.response.StatisticResponse;

import java.util.List;

public interface IStatisticService {
    List<StatisticResponse> showStatistic (StatisticRequest request);
}
