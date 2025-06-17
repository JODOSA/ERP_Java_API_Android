package com.empresa.erp.utils;

import java.lang.reflect.Method;

public class ReflectionUtils {

    /**
     * Busca y devuelve el método getter para una propiedad concreta
     *
     * @param clazz Clase de la entidad
     * @param fieldName Nombre de la propiedad, tal y como aparece en getPropertyOrder
     * @return Method si se encuentra, null si no
     */
    public static Method getGetter(Class<?> clazz, String fieldName) {
        try {
            String methodName = "get" + capitalize(fieldName);
            return clazz.getMethod(methodName);
        }catch(NoSuchMethodException e){
            System.out.println("No se encontró el getter: get" + fieldName + " en " + clazz.getSimpleName());
            return null;
        }
    }

    /**
     * Busca y devuelve el método setter para una propiedad concreta
     *
     * @param clazz Clase de la entidad
     * @param fieldName Nombre de la propiedad, tal y como aparece en getPropertyOrder
     * @return Method si se encuentra, null si no
     */
    public static Method getSetter(Class<?> clazz, String fieldName) {
        String expectedSetter = "set" + capitalize(fieldName);
        for(Method m : clazz.getMethods()) {
            if(m.getName().equals(expectedSetter) && m.getParameterCount() == 1) {
                return m;
            }
        }
        System.out.println("No se encontró el setter: " + expectedSetter + " en " + clazz.getSimpleName());
        return null;
    }
    /**
     * Convierte la primera letra en mayúscula
     */
    private static String capitalize(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {return fieldName;}
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
