package ua.dolofinskyi.common.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<Entity, Dto> {
    Entity toEntity(Dto dto);
    Dto toDto(Entity entity);

    default List<Dto> entitiesToDtos(List<Entity> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    default List<Entity> dtosToEntities(List<Dto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
