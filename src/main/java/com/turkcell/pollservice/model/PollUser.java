package com.turkcell.pollservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.pollservice.domain.enums.RolesEnum;
import lombok.*;

import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollUser {

    private Long id;
    private String name;
    private String username;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Set<String> authorities;
    private String type = "anonymous";


    public String getType() {

        if(Optional.ofNullable(authorities).isPresent()){
            if(authorities.contains(RolesEnum.ROLE_ADMIN.name())){
                type = "admin";
            }else if(authorities.contains(RolesEnum.ROLE_USER.name())){
                type = "user";
            }
        }

       return type;
    }


}
