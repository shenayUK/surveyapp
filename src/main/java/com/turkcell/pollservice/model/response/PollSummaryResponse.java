package com.turkcell.pollservice.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollSummaryResponse {

    private Long id;
    private String question;
    private List<ChoiceResponse> choices;
    private String username;
    private Boolean isActive;
    private Long totalVotes;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long selectedChoice;
    private LocalDateTime updateTime;

}
