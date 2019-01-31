package com.refunits.service.converter;

@FunctionalInterface
public interface Converter<T, R> {

    R convert(T object);
}