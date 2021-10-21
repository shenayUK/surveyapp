package com.turkcell.pollservice.service;


import com.turkcell.pollservice.model.PollUser;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthenticator {

    PollUser authenticate(String uid, String password);
}
