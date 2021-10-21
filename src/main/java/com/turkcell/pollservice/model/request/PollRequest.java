package com.turkcell.pollservice.model.request;

import com.turkcell.pollservice.model.dto.PollDuration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollRequest {

    @NotBlank
    @Size(max = 200)
    private String question;

    @NotNull
    @Size(min = 4, max = 4)
    @Valid
    private List<ChoiceRequest> choices;

    private PollDuration pollDuration;

}
