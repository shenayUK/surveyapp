package com.turkcell.pollservice.model.converter;

import java.util.List;
import java.util.Set;

public interface BaseConverter<Entity, Dto> {

    Entity toEntity(Dto var1);
    Dto toDto(Entity var1);
    List<Entity> toEntity(List<Dto> var1);
    List<Dto> toDto(List<Entity> var1);
    Set<Entity> toEntity(Set<Dto> var1);
    Set<Dto> toDto(Set<Entity> var1);

}
