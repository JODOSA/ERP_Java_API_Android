package com.empresa.erp.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.StringConverter;
import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.text.Normalizer;
import java.util.function.Function;

public class ComboBoxUtils {
    public static <T> void enableAutoFilter(ComboBox<T> comboBox, Function<T, String> textoBuscado) {
        ObservableList<T> originalItems = FXCollections.observableArrayList(comboBox.getItems());
        FilteredList<T> filteredItems = new FilteredList<>(originalItems, p -> true);

        comboBox.setEditable(true);
        comboBox.setItems(filteredItems);

        // Muestra nombres legibles en lugar de toString()
        comboBox.setConverter(new StringConverter<T>() {
            @Override
            public String toString(T object){
                return object == null ? "" : textoBuscado.apply(object);
            }

            @Override
            public T fromString(String string){
                String normalizado = normalizar(string);
                for(T item : comboBox.getItems()){
                    if(normalizar(textoBuscado.apply(item)).equalsIgnoreCase(normalizado)){
                        return item;
                    }
                }
                return null;
            }
        });

        comboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            String nuevoTexto = normalizar(newVal);
            if(comboBox.getValue() == null || !normalizar(textoBuscado.apply(comboBox.getValue())).equalsIgnoreCase(nuevoTexto)){
                filteredItems.setPredicate(item -> {
                    if(item == null) return false;
                    return normalizar(textoBuscado.apply(item)).contains(nuevoTexto);
                });
                //comboBox.show();

                if(!newVal.trim().isEmpty()){
                    comboBox.show();
                }else{
                    comboBox.hide();
                }
            }
        });

        // Corrige selección si el usuario escribe manualmente y pulsa enter
        comboBox.setOnAction(e -> {
            String texto = comboBox.getEditor().getText();
            String normalizadoTexto = normalizar(texto);
            for(T item : comboBox.getItems()){
                if(normalizar(textoBuscado.apply(item)).equalsIgnoreCase(normalizadoTexto)){
                    comboBox.setValue(item);
                    return;
                }
            }
        });
    }
    private static String normalizar(String texto){
        if(texto == null) return "";
        String sinAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return sinAcentos.replaceAll("[^\\p{Alnum}]", "").toLowerCase();
    }
}
