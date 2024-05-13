package com.rikkei.managementuser.service;

import com.rikkei.managementuser.model.dto.request.UserSignUp;

public interface IUserService {
    void save (UserSignUp userSignUp);
}
