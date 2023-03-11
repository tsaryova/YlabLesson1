package io.ylab.lessons.lesson2.sequences;

import java.util.ArrayList;
import java.util.List;

public class SequenceGeneratorImpl implements SequenceGenerator {
    @Override
    public void a(int n) {
        if (checkN(n)) {
            int startElementValue = 2;
            printSequence(createSequenceList(startElementValue, n, NameRule.A));
        }
    }

    @Override
    public void b(int n) {
        if (checkN(n)) {
            int startElementValue = 1;
            printSequence(createSequenceList(startElementValue, n, NameRule.B));
        }
    }

    @Override
    public void c(int n) {
        if (checkN(n)) {
            int startElementValue = 1;
            printSequence(createSequenceList(startElementValue, n, NameRule.C));
        }
    }

    @Override
    public void d(int n) {
        if (checkN(n)) {
            int startElementValue = 1;
            printSequence(createSequenceList(startElementValue, n, NameRule.D));
        }
    }

    @Override
    public void e(int n) {
        if (checkN(n)) {
            int startElementValue = 1;
            printSequence(createSequenceList(startElementValue, n, NameRule.E));
        }
    }

    @Override
    public void f(int n) {
        if (checkN(n)) {
            int startElementValue = 1;
            printSequence(createSequenceList(startElementValue, n, NameRule.F));
        }
    }

    @Override
    public void g(int n) {
        if (checkN(n)) {
            int startElementValue = 1;
            printSequence(createSequenceList(startElementValue, n, NameRule.G));
        }
    }

    @Override
    public void h(int n) {
        if (checkN(n)) {
            int startElementValue = 1;
            printSequence(createSequenceList(startElementValue, n, NameRule.H));
        }
    }

    @Override
    public void i(int n) {
        if (checkN(n)) {
            int startElementValue = 1;
            printSequence(createSequenceList(startElementValue, n, NameRule.I));
        }
    }

    @Override
    public void j(int n) {
        if (checkN(n)) {
            int startElementValue = 1;
            printSequence(createSequenceList(startElementValue, n, NameRule.J));
        }
    }

    private boolean checkN(int n) {
        if (n < 0) {
            System.out.println("Введите n > 0");
            return false;
        }
        return true;
    }

    private void printSequence(List<Integer> sequence) {
        System.out.println(sequence);
    }

    private List<Integer> createSequenceList(int startValue, int n, NameRule nameRule) {
        List<Integer> sequence = new ArrayList<>();
        int currentElementListValue = 0;
        int currentElementRuleValue = startValue;
        for (int i = 0; i < n; i++) {
            Pair pair = new Pair(currentElementRuleValue, currentElementListValue, nameRule);

            currentElementListValue = pair.getNewValueForList();
            sequence.add(currentElementListValue);

            currentElementRuleValue = pair.getNewValueForRule();
        }
        return sequence;
    }

    class Pair {
        private int newValueForList;
        private int newValueForRule;

        public Pair(int valueForRule, int valueForList, NameRule nameRule) {
            switch (nameRule) {
                case A:
                case B:
                    this.newValueForList = valueForRule;
                    this.newValueForRule = valueForRule + 2;
                    break;
                case C:
                    this.newValueForList = valueForRule * valueForRule;
                    this.newValueForRule = valueForRule + 1;
                    break;
                case D:
                    this.newValueForList = valueForRule * valueForRule * valueForRule;
                    this.newValueForRule = valueForRule + 1;
                    break;
                case E:
                    this.newValueForList = valueForRule;
                    this.newValueForRule = -valueForRule;
                    break;
                case F:
                    this.newValueForList = valueForRule;
                    this.newValueForRule = (valueForRule > 0) ? -(++valueForRule) : Math.abs(valueForRule) + 1;
                    break;
                case G:
                    int squaredValue = valueForRule * valueForRule;
                    this.newValueForList = (valueForRule % 2 > 0) ? squaredValue : -squaredValue;
                    this.newValueForRule = Math.abs(valueForRule) + 1;
                    break;
                case H:
                    this.newValueForList = (valueForList == 0) ? valueForRule : 0;
                    this.newValueForRule = (valueForList == 0) ? ++valueForRule : valueForRule;
                    break;
                case I:
                    if (valueForList == 0) { //т.к. первое формирование началось бы с умножения на 0, необходима проверка
                        valueForList = 1;
                    }
                    this.newValueForList = valueForList * valueForRule;
                    this.newValueForRule = ++valueForRule;
                    break;
                case J:
                    this.newValueForList = valueForList + valueForRule;
                    this.newValueForRule = valueForList;
                    break;
                default:
                    System.out.println("Нет такого правила");
                    break;
            }
        }

        public int getNewValueForList() {
            return this.newValueForList;
        }

        public int getNewValueForRule() {
            return this.newValueForRule;
        }
    }

}


