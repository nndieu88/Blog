package com.example.demo.model.mapper;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class ConvertStringToUrl {
    public static String convert(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp)
                    .replaceAll("")
                    .replaceAll(" ", "-")
                    .replaceAll("đ", "d")
                    .replaceAll("Đ", "D")
                    .replaceAll("\\,", "")
                    .replaceAll("\\.", "")
                    .replaceAll("\\?", "")
                    .replaceAll("\\/", "-")
                    .replaceAll("\\'", "")
                    .replaceAll("\\\"", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
