package ua.dolofinskyi.common.mapper;

import java.util.List;

public interface Mapper<Entity, Dto> {
    Entity toEntity(Dto dto);
    Dto toDto(Entity entity);

    default List<Dto> entitiesToDtos(List<Entity> entities) {
        return entities.stream().map(this::toDto).toList();
    }
    default List<Entity> dtosToEntities(List<Dto> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }
}
