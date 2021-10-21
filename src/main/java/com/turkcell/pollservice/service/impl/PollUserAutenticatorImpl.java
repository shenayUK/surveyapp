package com.turkcell.pollservice.service.impl;

import com.turkcell.pollservice.domain.Role;
import com.turkcell.pollservice.domain.User;
import com.turkcell.pollservice.exception.AccountNotActiveException;
import com.turkcell.pollservice.exception.AccountNotFoundException;
import com.turkcell.pollservice.exception.BadCredentialsException;
import com.turkcell.pollservice.model.PollUser;
import com.turkcell.pollservice.repository.UserRepository;
import com.turkcell.pollservice.service.UserAuthenticator;
import com.turkcell.pollservice.util.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PollUserAutenticatorImpl implements UserAuthenticator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PollUser authenticate(String userNameOrEmail, String password) {

        Optional<User> user = userRepository.findByUsernameOrEmail(userNameOrEmail,userNameOrEmail);

        System.out.println();
        if (user.isPresent()) {
            User account = user.get();

            if (Objects.equals(account.getIsActive(), true)) {
                if (passwordEncoder.matches(password,account.getPassword())) {
                    log.info("Authentication successful. [userNameOrEmail: {}]", userNameOrEmail);
                    Set<Role> roles = account.getRoles();
                    return AccountMapper.getUser(account,roles);
                } else {
                    log.info("Password mismatched. [userNameOrEmail: {}]", userNameOrEmail);
                    throw new BadCredentialsException();
                }
            } else {
                log.info("Account is not active. [userNameOrEmail: {}]", userNameOrEmail);
                throw new AccountNotActiveException();
            }
        } else {
            log.info("Account not found. [userNameOrEmail: {}]", userNameOrEmail);
            throw new AccountNotFoundException();
        }


    }
}
