package br.com.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final String BASIC_DELIMITERS = ",|\n";
    private static final String START_DELIMITER_PATTERN = "//";
    private static final String END_DELIMITER_PATTERN = "\n";
    private static final String DELIMITER_PATTERN = "(\\[.+\\])+";

    public int add(final String numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("Not a sequence of numbers");
        }

        String delimiters = StringCalculator.BASIC_DELIMITERS;
        String numbersWithoutDelimiter = numbers.trim();

        if (numbers.startsWith(START_DELIMITER_PATTERN)) {

            int indexOfDelimiterStartPattern = numbers.indexOf(START_DELIMITER_PATTERN) + 2;
            int indexOfDelimitersEndPattern = numbers.indexOf(END_DELIMITER_PATTERN);

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

    private Integer parseStringNumber(String number) {
        try {
            return Integer.parseInt(number.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(String.format("Invalid number representation: %s", number));
        }
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
                int parcel = parseStringNumber(value.trim());
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
