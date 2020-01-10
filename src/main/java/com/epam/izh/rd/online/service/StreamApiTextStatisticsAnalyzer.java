package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.helper.Direction;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingInt;

/**
 * Данный класс обязан использовать StreamApi из функционала Java 8. Функциональность должна быть идентична
 * {@link SimpleTextStatisticsAnalyzer}.
 */
public class StreamApiTextStatisticsAnalyzer implements TextStatisticsAnalyzer {
    @Override
    public int countSumLengthOfWords(String text) {
        return getWords(text).stream()
                .mapToInt(String::length)
                .sum();
    }

    @Override
    public int countNumberOfWords(String text) {
        return getWords(text).stream()
                .mapToInt(s -> 1)
                .sum();
    }

    @Override
    public int countNumberOfUniqueWords(String text) {
        return getWords(text).stream()
                .distinct()
                .mapToInt(s -> 1)
                .sum();
    }

    @Override
    public List<String> getWords(String text) {
        return Arrays.stream(splitTextOnWords(text))
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getUniqueWords(String text) {
        return Arrays.stream(splitTextOnWords(text))
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String, Integer> countNumberOfWordsRepetitions(String text) {
        return getWords(text).stream()
                .collect(Collectors.groupingBy(s -> s, summingInt(s -> 1)));
    }

    @Override
    public List<String> sortWordsByLength(String text, Direction direction) {
        return getWords(text).stream()
                .sorted((s1, s2) -> {
                    int compare = Integer.compare(s2.length(), s1.length());
                    if (direction.equals(Direction.DESC)) {
                        return compare;
                    }
                    return -compare;
                })
                .collect(Collectors.toList());
    }

    private String[] splitTextOnWords(String text) {
        if (text == null || text.isEmpty()) {
            return new String[0];
        }
        return text.split("[^\\p{Alnum}]+");
    }
}
