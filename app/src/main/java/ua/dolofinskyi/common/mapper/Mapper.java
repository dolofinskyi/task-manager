package ua.dolofinskyi.common.mapper;

public interface Mapper<Entity, Dto> {
    Entity toEntity(Dto dto);
    Dto toDto(Entity entity);
}
