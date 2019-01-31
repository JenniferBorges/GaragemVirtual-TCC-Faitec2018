package com.academico.garagem.model.util;

public class CNPHelper {

    private static final int[] PESO_CPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESO_CNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calcDigit(String str, int[] peso) {
        int sum = 0;
        for (int index = str.length() - 1, digit; index >= 0; index--) {
            digit = Integer.parseInt(str.substring(index, index + 1));
            sum += digit * peso[peso.length - str.length() + index];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }

    public static boolean isValidCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11)) {
            return false;
        }

        Integer digit1 = calcDigit(cpf.substring(0, 9), PESO_CPF);
        Integer digit2 = calcDigit(cpf.substring(0, 9) + digit1, PESO_CPF);
        return cpf.equals(cpf.substring(0, 9) + digit1.toString() + digit2.toString());
    }

    public static boolean isValidCNPJ(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14)) {
            return false;
        }

        Integer digit1 = calcDigit(cnpj.substring(0, 12), PESO_CNPJ);
        Integer digit2 = calcDigit(cnpj.substring(0, 12) + digit1, PESO_CNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digit1.toString() + digit2.toString());
    }

}
