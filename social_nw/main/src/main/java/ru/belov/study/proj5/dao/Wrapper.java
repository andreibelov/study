package ru.belov.study.proj5.dao;

@FunctionalInterface
public interface Wrapper<T> {
    @Private
    T toSrc();
}
