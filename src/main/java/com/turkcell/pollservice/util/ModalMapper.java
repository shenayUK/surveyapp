package com.turkcell.pollservice.util;

import com.turkcell.pollservice.auth.UserPrincipal;
import com.turkcell.pollservice.domain.Choice;
import com.turkcell.pollservice.domain.Poll;
import com.turkcell.pollservice.model.PollUser;
import com.turkcell.pollservice.model.dto.ChoiceDto;
import com.turkcell.pollservice.model.dto.PollDto;

import java.util.Objects;
import java.util.stream.Collectors;

public class ModalMapper {

    public static PollUser getPollUser(UserPrincipal user) {

        return PollUser.builder().id(user.getId())
                    .username(user.getUsername()).build();
    }

    public static PollDto getPollDto(Poll poll) {

        PollDto pollDto = PollDto.builder().id(poll.getId()) .question(poll.getQuestion())
                .username(poll.getUsername()).build();

        if(!Objects.isNull(poll.getChoices())){
            pollDto.setChoices(poll
                    .getChoices().stream().map(item->getChoiceDto(item))
                    .collect(Collectors.toList()));
        }

        return pollDto;
    }

    public static ChoiceDto getChoiceDto(Choice choice) {

        return ChoiceDto.builder()
                .id(choice.getId())
                .option(choice.getOption())
                .build();
    }

}
