package com.restassuredapitemplate.utils;

import com.google.gson.*;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeneralUtils {
    public static String readFileToAString(String path) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, StandardCharsets.ISO_8859_1);
    }

    public static String getNowDate(String mask) {
        DateFormat dateFormat = new SimpleDateFormat(mask);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getMethodNameByLevel(int level) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();

        return stackTrace[level].getMethodName();
    }

    public static String getAllStackTrace(ITestResult result) {
        String allStackTrace = "";

        for (StackTraceElement stackTrace : result.getThrowable().getStackTrace()) {
            allStackTrace = allStackTrace + "<br>" + stackTrace.toString();
        }

        return allStackTrace;
    }

    public static String convertObjectToJsonString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String formatJson(Object object){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(object);
    }

    public static String formatJson(final String json) {
        final int indent_width = 4;
        final char[] chars = json.toCharArray();
        final String newline = System.lineSeparator();

        String ret = "";
        boolean begin_quotes = false;

        for (int i = 0, indent = 0; i < chars.length; i++) {
            char c = chars[i];

            if (c == '\"') {
                ret += c;
                begin_quotes = !begin_quotes;
                continue;
            }

            if (!begin_quotes) {
                switch (c) {
                    case '{':
                    case '[':
                        ret += c + newline + String.format("%" + (indent += indent_width) + "s", "");
                        continue;
                    case '}':
                    case ']':
                        ret += newline + ((indent -= indent_width) > 0 ? String.format("%" + indent + "s", "") : "") + c;
                        continue;
                    case ':':
                        ret += c + " ";
                        continue;
                    case ',':
                        ret += c + newline + (indent > 0 ? String.format("%" + indent + "s", "") : "");
                        continue;
                    default:
                        if (Character.isWhitespace(c)) continue;
                }
            }

            ret += c + (c == '\\' ? "" + chars[++i] : "");
        }

        return ret;
    }

    public static String getValidCPF() {
        int digito1 = 0, digito2 = 0, resto = 0;
        String nDigResult;
        String numerosContatenados;
        String numeroGerado;

        Random numeroAleatorio = new Random();

        //numeros gerados
        int n1 = numeroAleatorio.nextInt(10);
        int n2 = numeroAleatorio.nextInt(10);
        int n3 = numeroAleatorio.nextInt(10);
        int n4 = numeroAleatorio.nextInt(10);
        int n5 = numeroAleatorio.nextInt(10);
        int n6 = numeroAleatorio.nextInt(10);
        int n7 = numeroAleatorio.nextInt(10);
        int n8 = numeroAleatorio.nextInt(10);
        int n9 = numeroAleatorio.nextInt(10);

        int soma = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;

        int valor = (soma / 11) * 11;

        digito1 = soma - valor;

        //Primeiro resto da divisão por 11.
        resto = (digito1 % 11);

        if (digito1 < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        int soma2 = digito1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;

        int valor2 = (soma2 / 11) * 11;

        digito2 = soma2 - valor2;

        //Primeiro resto da divisão por 11.
        resto = (digito2 % 11);

        if (digito2 < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        //Conctenando os numeros
        numerosContatenados = String.valueOf(n1) + String.valueOf(n2) + String.valueOf(n3) + "." + String.valueOf(n4) +
                String.valueOf(n5) + String.valueOf(n6) + "." + String.valueOf(n7) + String.valueOf(n8) +
                String.valueOf(n9) + "-";

        //Concatenando o primeiro resto com o segundo.
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        numeroGerado = numerosContatenados + nDigResult;

        return numeroGerado;
    }
}
