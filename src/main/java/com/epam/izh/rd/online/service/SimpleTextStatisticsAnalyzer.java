package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.helper.Direction;

import java.util.*;
import java.util.regex.Pattern;

import static java.util.Collections.*;

/**
 * Совет:
 * Начните с реализации метода {@link SimpleTextStatisticsAnalyzer#getWords(String)}.
 * Затем переиспользуйте данный метод при реализации других.
 * <p>
 * При необходимости, можно создать внутри данного класса дополнительные вспомогательные приватные методы.
 */
public class SimpleTextStatisticsAnalyzer implements TextStatisticsAnalyzer {

    /**
     * Необходимо реализовать функционал подсчета суммарной длины всех слов (пробелы, знаким препинания итд не считаются).
     * Например для текста "One, I - tWo!!" - данный метод должен вернуть 7.
     *
     * @param text текст
     */
    @Override
    public int countSumLengthOfWords(String text) {
        int sumLength = 0;
        for (int i = 0; i < getWords(text).size(); i++) {
            sumLength += getWords(text).get(i).length();
        }
        return sumLength;
    }

    /**
     * Необходимо реализовать функционал подсчета количества слов в тексте.
     * Например для текста "One, two, three, three - one, tWo, tWo!!" - данный метод должен вернуть 7.
     *
     * @param text текст
     */
    @Override
    public int countNumberOfWords(String text) {
        return getWords(text).size();
    }

    /**
     * Необходимо реализовать функционал подсчета количества уникальных слов в тексте (с учетом регистра).
     * Например для текста "One, two, three, three - one, tWo, tWo!!" - данный метод должен вернуть 5.
     * param text текст
     */
    @Override
    public int countNumberOfUniqueWords(String text) {
        return getUniqueWords(text).size();
    }

    /**
     * Необходимо реализовать функционал получения списка слов из текста.
     * Пробелы, запятые, точки, кавычки и другие знаки препинания являются разделителями слов.
     * Например для текста "One, two, three, three - one, tWo, tWo!!" должен вернуться список :
     * {"One", "two", "three", "three", "one", "tWo", "tWo"}
     *
     * @param text текст
     */
    @Override
    public List<String> getWords(String text) {
        List<String> emptyList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\W+");
        for (String words : pattern.split(text)) {
            emptyList.add(words);
        }
        return emptyList;
    }

    /**
     * Необходимо реализовать функционал получения списка уникальных слов из текста.
     * Пробелы, запятые, точки, кавычки и другие знаки препинания являются разделителями слов.
     * Например для текста "One, two, three, three - one, tWo, tWo!!" должен вернуться список :
     * {"One", "two", "three", "one", "tWo"}
     *
     * @param text текст
     */
    @Override
    public Set<String> getUniqueWords(String text) {
        Set<String> set = new HashSet<>();
        for (String elem : getWords(text)) {
            if (!set.contains(elem)) {
                set.add(elem);
            }
        }
        return set;
    }

    private int countNumberOfRepetitions(String text, String elem) {
        int number = 0;
        for (int i = 0; i < getWords(text).size(); i++) {
            if (elem.equals(getWords(text).get(i))) {
                number++;
            }
        }
        return number;
    }

    /**
     * Необходимо реализовать функционал подсчета количества повторений слов.
     * Например для текста "One, two, three, three - one, tWo, tWo!!" должны вернуться результаты :
     * {"One" : 1, "two" : 1, "three" : 2, "one" : 1, "tWo" : 2}
     *
     * @param text текст
     */
    @Override
    public Map<String, Integer> countNumberOfWordsRepetitions(String text) {
        Map<String, Integer> map = new HashMap<>();
        for (String elem : getUniqueWords(text)) {
            map.put(elem, countNumberOfRepetitions(text, elem));
        }
        return map;
    }

    private int compareToASC(String e1, String e2) {
        int result = e1.length() - e2.length();
        int value = 0;
        if (result > 0) {
            value = 1;
        } else if (result < 0) {
            value = -1;
        }
        return value;
    }

    private int compareToDESC(String e1, String e2) {
        int result = e2.length() - e1.length();
        int value = 0;
        if (result > 0) {
            value = 1;
        } else if (result < 0) {
            value = -1;
        }
        return value;
    }

    /**
     * Необходимо реализовать функционал вывода слов из текста в отсортированном виде (по длине) в зависимости от параметра direction.
     * Например для текста "Hello, Hi, mother, father - good, cat, c!!" должны вернуться результаты :
     * ASC : {"mother", "father", "Hello", "good", "cat", "Hi", "c"}
     * DESC : {"c", "Hi", "cat", "good", "Hello", "father", "mother"}
     *
     * @param text текст
     */
    @Override
    public List<String> sortWordsByLength(String text, Direction direction) {
        List<String> sortList = new ArrayList<>();
        sortList.addAll(getWords(text));
        if (direction == Direction.ASC) {
            sortList.sort((e1, e2) -> compareToASC(e1, e2));
        } else {
            sortList.sort((e1, e2) -> compareToDESC(e1, e2));
        }
        return sortList;
    }
}
