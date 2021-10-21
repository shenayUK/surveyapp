package com.turkcell.pollservice;

import com.turkcell.pollservice.domain.Role;
import com.turkcell.pollservice.domain.User;
import com.turkcell.pollservice.domain.enums.RolesEnum;
import com.turkcell.pollservice.exception.BadRequestException;
import com.turkcell.pollservice.repository.RoleRepository;
import com.turkcell.pollservice.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
public class PollserviceApplication implements ApplicationRunner {

    public PollserviceApplication(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(PollserviceApplication.class, args);
    }

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role role1=new Role(RolesEnum.ROLE_ADMIN);
        Role role2=new Role(RolesEnum.ROLE_USER);

        User u1= User.builder().name("mehmet").username("000373").password(passwordEncoder.encode("Sifre123")).email("mehmet.ozkan@gmail.com").isActive(true).build();
        User u2= User.builder().name("banu").username("000374").password(passwordEncoder.encode("Sifre123")).email("banu.ozkan@gmail.com").isActive(true).build();

        roleRepository.save(role1);
        roleRepository.save(role2);

        Set<String> uRol1 =new HashSet<String>();
        uRol1.add("ROLE_USER");
        u1.setRoles(rolesFromStrings(uRol1));
        userRepository.save(u1);

        Set<String> uRol2 =new HashSet<String>();
        uRol2.add("ROLE_ADMIN");
        u2.setRoles(rolesFromStrings(uRol2));
        userRepository.save(u2);

       // passwordEncoder.encode(accountDto.getPassword())
    }

    public Set<Role> rolesFromStrings(Set<String> roles) {

        Set<Role> rolesSet = new HashSet<>();

        List<String> collects = roles.stream().map(s ->
                s.split(",")).flatMap(Arrays::stream).collect(Collectors.toList());

        collects.forEach((String item) -> {

            Role byRoleCode = roleRepository.findByName(RolesEnum.valueOf(item));
            Role role = null;
            if (byRoleCode != null) {
                role = byRoleCode;
            } else {
                new BadRequestException("Role not found");

            }
            rolesSet.add(role);
        });
        return rolesSet;

    }
}
