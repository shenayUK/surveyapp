package com.turkcell.pollservice.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChoiceResponse {

    private Long optionNo;
    private String option;
    private Long responseCount;

}
