package com.rikkei.managementuser.service;

import com.rikkei.managementuser.model.dto.response.StatisticResponse;
import com.rikkei.managementuser.model.entity.Student;

public interface IEmailService {
    String sendMail(Student student, StatisticResponse statistic);
}
