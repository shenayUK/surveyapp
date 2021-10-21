package com.turkcell.pollservice.model.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollDto {

    private Long id;
    private String username;
    private String question;
    private List<ChoiceDto> choices;
    private LocalDateTime expirationDateTime;

}
