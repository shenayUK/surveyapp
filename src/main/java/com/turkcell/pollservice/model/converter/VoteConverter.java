package com.turkcell.pollservice.model.converter;

import com.turkcell.pollservice.domain.Vote;
import com.turkcell.pollservice.model.dto.VoteDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {PollConverter.class, ChoiceConverter.class })
public interface VoteConverter extends BaseConverter<Vote, VoteDto> {
}