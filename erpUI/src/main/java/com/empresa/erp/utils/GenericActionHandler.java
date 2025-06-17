package com.empresa.erp.utils;

    @FunctionalInterface
    public interface GenericActionHandler<T>{
        void handle(T entity);
    }
