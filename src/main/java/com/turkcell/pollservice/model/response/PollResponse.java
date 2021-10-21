package com.turkcell.pollservice.model.response;

import com.turkcell.pollservice.model.dto.PollDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollResponse {

    private Boolean success;
    private PollDto pollDto;

}
