package br.com.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final String DELIMITER_PATTERN = "(\\[.+\\])+";

    public int add(final String numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("Not a sequence of numbers");
        }
        String delimiters = ",|\n";
        String numbersWithoutDelimiter = numbers.trim();

        if (numbers.startsWith("//")) {

            int indexOfDelimiterStartPattern = numbers.indexOf("//") + 2;
            int indexOfDelimitersEndPattern = numbers.indexOf("\n");

            String delimiterOptions = numbers.substring(indexOfDelimiterStartPattern, indexOfDelimitersEndPattern);

            boolean foundMatch = hasMatchesWithPattern(delimiterOptions);

            if (foundMatch) {
                Matcher matcher = createPatternMatcher(delimiterOptions);
                while (matcher.find()) {
                    String newDelimiters = findDelimiters(matcher.group());
                    delimiters += "|" + newDelimiters;
                }
            } else {
                delimiters = delimiterOptions;
            }

            numbersWithoutDelimiter = numbersWithoutDelimiter.substring(indexOfDelimitersEndPattern + 1);

        }

        return add(delimiters, numbersWithoutDelimiter);
    }

    private String findDelimiters(String group) {
        List<String> result = new ArrayList<>();
        String[] groups = group.split("]\\[");
        for (String item : groups) {
            result.add(item.replaceAll("\\[", "").replaceAll("]", ""));
        }
        return result.stream().collect(Collectors.joining("|"));
    }

    private boolean hasMatchesWithPattern(String delimiterOptions) {
        Matcher matcher = createPatternMatcher(delimiterOptions);
        return matcher.matches();
    }

    private Matcher createPatternMatcher(String delimiterOptions) {
        Pattern pattern = Pattern.compile(StringCalculator.DELIMITER_PATTERN, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(delimiterOptions);
        return matcher;
    }

    private int add(final String delimiter, final String numbers) {
        String[] values = numbers.split(delimiter);
//        if (values.length > 2) {
//            throw new IllegalArgumentException("Up to 2 numbers separated by comma (,) are allowed");
//        } else {
        int result = 0;
        List<String> negativeNumbers = new ArrayList<>();
        for (String value : values) {
            if (!value.trim().isEmpty()) {
                int parcel = Integer.parseInt(value.trim());
                if (parcel < 0) {
                    negativeNumbers.add(value.trim());
                } else if (parcel <= 1000){
                    result += parcel;
                }
            }
        }
        if (negativeNumbers.size() > 0) {
            final String msg = "Negatives not allowed: " + negativeNumbers.toString();
            throw new IllegalArgumentException(msg);
        }
        return result;
        //}
    }
}
