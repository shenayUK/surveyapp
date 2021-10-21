package com.turkcell.pollservice.model.converter;

import com.turkcell.pollservice.domain.Poll;
import com.turkcell.pollservice.model.dto.PollDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ChoiceConverter.class },
        injectionStrategy = InjectionStrategy.FIELD)
public interface PollConverter extends BaseConverter<Poll, PollDto> {
}