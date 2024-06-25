package com.core.auth.service;

import com.core.auth.dto.user.UserResponse;
import com.core.auth.entity.oauth.User;

import java.security.Principal;

public interface IUserService {
    UserResponse getDetailProfile(Principal principal);

    User getUserIdToken(Principal principal);

}
