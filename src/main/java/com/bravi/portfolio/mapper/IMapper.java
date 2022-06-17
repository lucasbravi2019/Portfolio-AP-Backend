package com.bravi.portfolio.mapper;

import java.util.List;

public interface IMapper<E, R, T> {

    E toEntity(R request);
    E toEntity(E entity, R request);
    T toDto(E entity);
    List<T> toDtoList(List<E> list);

}
