package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.helper.Direction;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.*;

/**
 * Данный класс обязан использовать StreamApi из функционала Java 8. Функциональность должна быть идентична
 * {@link SimpleTextStatisticsAnalyzer}.
 */
public class StreamApiTextStatisticsAnalyzer implements TextStatisticsAnalyzer {
    @Override
    public int countSumLengthOfWords(String text) {
        int sumLength = getWords(text)
                .stream()
                .map(String::length)
                .reduce((s1, s2) -> s1 + s2).orElse(0);
        return sumLength;
    }

    @Override
    public int countNumberOfWords(String text) {
        return getWords(text).size();
    }

    @Override
    public int countNumberOfUniqueWords(String text) {
        return getUniqueWords(text).size();
    }

    @Override
    public List<String> getWords(String text) {
        Stream<String> stream = Pattern.compile("\\W+").splitAsStream(text);
        return stream.collect(Collectors.toList());
    }

    @Override
    public Set<String> getUniqueWords(String text) {
        return getWords(text)
                .stream()
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String, Integer> countNumberOfWordsRepetitions(String text) {
        Map<String, Integer> map = getWords(text).stream()
                .collect(Collectors.toMap(
                        e -> e,
                        e -> 1,
                        Integer::sum
                ));
        return map;
    }

    @Override
    public List<String> sortWordsByLength(String text, Direction direction) {
        return getWords(text).stream()
                .sorted(((o1, o2) ->
                {
                    int result = 0;
                    if (direction == Direction.ASC) {
                        result = o1.length() - o2.length();
                    } else {
                        result = o2.length() - o1.length();
                    }
                    int value = 0;
                    if (result > 0) {
                        value = 1;
                    } else if (result < 0) {
                        value = -1;
                    }
                    return value;
                }))
                .collect(Collectors.toList());
    }
}
