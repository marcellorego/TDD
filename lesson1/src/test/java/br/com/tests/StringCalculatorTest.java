package br.com.tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StringCalculatorTest {

    private static StringCalculator stringCalculator;

    @BeforeClass
    public static void beforeClassInitCalculator() {
        StringCalculatorTest.stringCalculator = new StringCalculator();
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void whenMoreThan2NumbersAreUsedThenExceptionIsThrown() {
//        stringCalculator.add("1,2,3");
//    }

    @Test(expected = IllegalArgumentException.class)
    public final void whenANonNumberIsUsedThenExceptionIsThrown() {
        stringCalculator.add("1,A");
    }

    @Test(expected = IllegalArgumentException.class)
    public final void whenNullValueIsUsedThenExceptionIsThrown() {
        stringCalculator.add(null);
    }

    @Test
    public final void when2ValidNumbersAreUsedThenNoExceptionIsThrown() {
        stringCalculator.add("1,2");
        Assert.assertTrue(true);
    }

    @Test
    public final void whenEmptyStringIsUsedThenReturnedValueIs0() {
        int result = stringCalculator.add("");
        Assert.assertEquals(0, result);
    }

    @Test
    public final void whenOneNumberIsUsedThenReturnTheSameNumber() {
        int result = stringCalculator.add("2");
        Assert.assertEquals(2, result);
    }

    @Test
    public final void whenValidNumbersAreUsedReturnTheSumOfTheseNumbers() {
        int result = stringCalculator.add("2,2");
        Assert.assertEquals(2+2, result);
    }

    @Test
    public final void whenAnyNumberOfNumbersIsUsedThenReturnValuesAreTheirSums() {
        Assert.assertEquals(3+6+15+18+46+33, stringCalculator.add("3,6,15,18,46,33"));
    }

    @Test
    public final void whenNewLineIsUsedBetweenNumbersThenReturnValuesAreTheirSums() {
        Assert.assertEquals(3+6+15, stringCalculator.add("3,6\n15"));
    }

    @Test
    public final void whenValidNumbersAreUsedWithSpacesBetweenNumbersThenReturnValuesAreTheirSums() {
        Assert.assertEquals(3+6+15, stringCalculator.add(" 3, 6 , 15 "));
    }

    @Test
    public final void whenDelimiterIsSpecifiedThenItIsUsedToSeparateNumbers() {
        Assert.assertEquals(3+6+15, stringCalculator.add("//;\n3;6;15"));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void whenNegativeNumberIsUsedThenExceptionIsThrown() {
        stringCalculator.add("1,2,-5");
    }

    @Test
    public final void whenNegativeNumbersAreUsedThenExceptionIsThrown() {
        Exception exception = null;
        try {
            stringCalculator.add("1,-6, -18");
        } catch (IllegalArgumentException iex) {
            exception = iex;
        }

        Assert.assertNotNull(exception);
        Assert.assertEquals("Negatives not allowed: [-6, -18]", exception.getMessage());
    }

    @Test
    public final void whenNumbersAreGreaterThan1000ThenIgnoreThem() {
        int result = stringCalculator.add("2,2000,4, 1000");
        Assert.assertEquals(2+4+1000, result);
    }

    @Test
    public final void whenDelimitersHasAnyLengthSpecifiedThenItIsUsedToSeparateNumbers() {
        Assert.assertEquals(1+2+3, stringCalculator.add("//[—]\n1—2—3"));
    }


    @Test
    public final void whenMultipleDelimitersAreSpecifiedThenItIsUsedToSeparateNumbers() {
        Assert.assertEquals(1+2+3, stringCalculator.add("//[-][%]\n1-2%3"));
    }

    @Test
    public final void whenMultipleDelimitersLongerThanOneCharAreSpecifiedThenItIsUsedToSeparateNumbers() {
        Assert.assertEquals(1+2+3, stringCalculator.add("//[--][%%]\n1--2%%3"));
    }
}
