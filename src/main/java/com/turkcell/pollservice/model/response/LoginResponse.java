package com.turkcell.pollservice.model.response;

import com.turkcell.pollservice.model.PollUser;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginResponse {
    Boolean success;
    String accessToken;
    PollUser user;
}
