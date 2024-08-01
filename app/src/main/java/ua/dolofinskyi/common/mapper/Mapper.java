package ua.dolofinskyi.features.mapper;

public interface Mapper<Entity, Dto> {
    Entity toEntity(Dto dto);
    Dto toDto(Entity entity);
}
