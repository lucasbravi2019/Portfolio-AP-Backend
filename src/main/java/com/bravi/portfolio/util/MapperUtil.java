package com.bravi.portfolio.util;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapperUtil {

    public static <R, T> List<T> streamNullableList(List<R> list, Function<R, T> function) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(function).collect(Collectors.toList());
    }

    public static <R, T> T mapNullableObject(R obj, Function<R, T> function) {
        if (obj == null) {
            return null;
        }
        return function.apply(obj);
    }

    public static <R, T> T mapNullableObjectWithFilter(R obj, Function<R, Boolean> filter, Function<R, T> function) {
        if (obj == null) return null;
        if (filter.apply(obj)) {
            return function.apply(obj);
        }
        return null;
    }




}
