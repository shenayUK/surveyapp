package com.turkcell.pollservice.model.converter;

import com.turkcell.pollservice.domain.Choice;
import com.turkcell.pollservice.model.dto.ChoiceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChoiceConverter extends BaseConverter<Choice, ChoiceDto> {
}