package io.ylab.lessons.lesson2.snils;

public class SnilsValidatorImpl implements SnilsValidator {

    private static final int SNILS_SYMBOLS_COUNT = 11;
    private static final int LENGTH_ARR_FOR_CONTROL_SUM = 9;
    private static final int VALUE_TO_CHECK_CONTROL_SUM = 100;
    private static final int VALUE_TO_CHECK_MOD_CONTROL_SUM = 101;
    private static final int NUM_START_POSITION_CONTROL_NUMBER = 9;

    @Override
    public boolean validate(String snils) {

        if (snils.length() != SNILS_SYMBOLS_COUNT) {
            return false;
        }
        char[] arrSnilsSymbols = snils.toCharArray();
        int[] arrForControlSum = new int[LENGTH_ARR_FOR_CONTROL_SUM];
        for (int i = 0; i < arrSnilsSymbols.length; i++) {
            if (!Character.isDigit(arrSnilsSymbols[i])) {
                return false;
            }
            if (i < arrForControlSum.length) {
                arrForControlSum[i] = Character.digit(arrSnilsSymbols[i], 10);
            }

        }

        int controlSum = getControlSum(arrForControlSum);
        int controlNumber = getControlNumber(snils);

        return controlNumber == controlSum;
    }

    private int getControlSum(int[] arrForControlSum) {
        int sumMultiplications = 0;
        int controlSum = 0;

        for (int i = 0; i < arrForControlSum.length; i++) {
            int coefficient = arrForControlSum.length - i;
            sumMultiplications += (arrForControlSum[i] * coefficient);
        }

        controlSum = sumMultiplications;
        if (sumMultiplications == VALUE_TO_CHECK_CONTROL_SUM) {
            controlSum = 0;
        } else if (sumMultiplications > VALUE_TO_CHECK_CONTROL_SUM) {
            controlSum = (sumMultiplications % VALUE_TO_CHECK_MOD_CONTROL_SUM == VALUE_TO_CHECK_CONTROL_SUM)
                    ? 0
                    : sumMultiplications % VALUE_TO_CHECK_MOD_CONTROL_SUM;
        }

        return controlSum;
    }

    private int getControlNumber(String snils) {
        return Integer.valueOf(snils.substring(NUM_START_POSITION_CONTROL_NUMBER), 10);
    }
}
