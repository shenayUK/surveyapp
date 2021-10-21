package com.turkcell.pollservice.model.dto;

import com.turkcell.pollservice.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto extends BaseEntity {

    private PollDto poll;
    private ChoiceDto choice;


}
