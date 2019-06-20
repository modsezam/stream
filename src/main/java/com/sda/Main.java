package com.sda;

import com.google.common.primitives.Chars;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Main {

    private static final int MAX_SIZE = 100_000;

    public static void main(String[] args) {

//        NAZWISKA:
        System.out.println("NAZWISKA:");
//        1) Wszystkie nazwiska o długości co najwyżej 4 znaków, zapisane wielkimi literami
        List<String> collect1 = DataCollections.getSurnames()
                .stream()
                .filter(s -> s.length() > 4)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("1) " + collect1);

//        2) Wszystkie nazwiska zaczynające się na literę 'B'
        List<String> collect2 = DataCollections.getSurnames()
                .stream()
                .filter(s -> s.substring(0, 1).equals("B"))
                .collect(Collectors.toList());
        System.out.println("2) " + collect2);

//        3) Początkowe trzy litery wszystkich nazwisk, bez powtórzeń, z małych liter
        Set<String> collect3 = DataCollections.getSurnames()
                .stream()
                .map(s -> s.toLowerCase().substring(0, 3))
                .collect(Collectors.toSet());
        System.out.println("3) " + collect3);

//        3_2) Początkowe cztery litery wszystkich nazwisk, bez powtórzeń, z małych liter
        Set<String> collect3_2 = DataCollections.getSurnames()
                .stream()
                .map(s -> (s.length() < 4) ? s.toLowerCase() : s.substring(0, 4).toLowerCase())
                .collect(Collectors.toSet());
//        System.out.println(collect3_2);

//        3_3) Początkowe cztery litery wszystkich nazwisk, bez powtórzeń, z małych liter
        Set<String> collect3_3 = DataCollections.getSurnames()
                .stream()
                .map(Main::checkAndMap)
                .collect(Collectors.toSet());
//        System.out.println(collect3_3);

//        4) 10 najdłuższych nazwisk, posortowanych malejąco według długości
        List<String> collect4 = DataCollections.getSurnames()
                .stream()
                .sorted((o1, o2) -> o2.length() - o1.length())
                .limit(10)
                .collect(Collectors.toList());
        System.out.println("4) " + collect4);

//        4_2*) Obsłuż miejsca "ex aequo"
        List<String> collect4_2 = DataCollections.getSurnames()
                .stream()
                .sorted((o1, o2) -> o2.length() - o1.length())
                .collect(Collectors.toList());
        List<String> collect4_3 = collect4_2.stream()
                .filter(s -> s.length() >= collect4_2.get(10).length())
                .sorted((o1, o2) -> o2.length() - o1.length())
                .collect(Collectors.toList());
//        System.out.println(collect4_2);
        System.out.println("4*) " + collect4_3);

//        5) 20 najkrótszych nazwisk, posortowanych według ostatniej litery
        List<String> collect5 = DataCollections.getSurnames()
                .stream()
                .sorted(Comparator.comparingInt(String::length))
                .limit(20)
                .sorted(Comparator.comparing(o -> o.substring(o.length() - 1)))
                .collect(Collectors.toList());
        System.out.println("5) " + collect5);

//        6) Odwróć kolejność liter we wszystkich nazwiskach i pozstaw jedynie te, które mają literę 'A'
//        wsród pierwszych trzech liter (odwróconego nazwiska)
        List<String> collect6 = DataCollections.getSurnames()
                .stream()
                .map(s -> new StringBuilder(s).reverse().toString())
                .filter(s -> s.substring(0, 3).toLowerCase().contains("a"))
                .collect(Collectors.toList());
        System.out.println("6) " + collect6);

//        7) Policz, ile jest nazwisk zaczynających się na każdą z liter alfabetu
//        (rezultat jako Map<Character, Integer>)
        Map<Character, Integer> map = new TreeMap<>();
        DataCollections.getSurnames()
                .stream()
                .map(s -> s.charAt(0))
                .forEach(c -> map.put(c, map.get(c) == null ? 1 : map.get(c) + 1));
        System.out.println("7) " + map);

//        8*) Jaka litera pojawia się najcześciej we wszystkich nazwiskach?
        Map<Character, Integer> map2 = new TreeMap<>();
        DataCollections.getSurnames()
                .forEach(s -> Chars.asList(s.toLowerCase().toCharArray()).forEach(c -> map2.put(c, map2.get(c) == null ? 1 : map2.get(c) + 1)));
        List<Map.Entry<Character, Integer>> map3 = map2.entrySet()
                .stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .limit(1)
                .collect(Collectors.toList());
        System.out.println("8) " + map3);


//        LICZBY:
        System.out.println("LICZBY:");
//        1) Ile jest liczb parzystych?
        long count1 = DataCollections.getNumbers(MAX_SIZE)
                .stream()
                .filter(i -> i % 2 == 0)
                .count();
        System.out.println("1) " + count1);

//        2) Ile jest liczb pięciocyfrowych?
        long count2 = DataCollections.getNumbers(MAX_SIZE)
                .stream()
                .filter(i -> i >= 10_000 && i < MAX_SIZE)
                .count();
        System.out.println("2) " + count2);

//        3) Jaka jest największa i najmniejsza liczba?
        Optional<Integer> max = DataCollections.getNumbers(MAX_SIZE)
                .stream().max(Integer::compareTo);
        Optional<Integer> min = DataCollections.getNumbers(MAX_SIZE)
                .stream().min(Integer::compareTo);
        System.out.println("3) " + max.get() + ", " + min.get());

//        4) Jaka jest różnica między największa a najmniejszą liczbą?
        List<Integer> collectL4 = DataCollections.getNumbers(MAX_SIZE)
                .stream()
                .sorted()
                .collect(Collectors.toList());
//        System.out.println(collectL4.get(collectL4.size() - 1) - collectL4.get(0));

        IntSummaryStatistics collectL4_2 = DataCollections.getNumbers(MAX_SIZE)
                .stream()
                .collect(Collectors.summarizingInt(Integer::intValue));
        System.out.println("4) " + (collectL4_2.getMax() - collectL4_2.getMin()));


//        5) Jaka jest średnia wszystkich liczb?
        Optional<Double> averageL5 = DataCollections.getNumbers(MAX_SIZE)
                .stream()
                .map(integer -> (long) integer)
                .reduce((integer, integer2) -> integer + integer2)
                .map(integer -> integer / (double) MAX_SIZE);
//        System.out.println(averageL5.get());

        double averageL5_2 = DataCollections.getNumbers(MAX_SIZE)
                .stream()
                .collect(Collectors.summarizingInt(Integer::intValue))
                .getAverage();
//        System.out.println(averageL5_2);

        OptionalDouble averageL5_3 = DataCollections.getNumbers(MAX_SIZE)
                .stream()
                .mapToLong(value -> value)
                .average();
        System.out.println("5)" + averageL5_3.getAsDouble());


//        6*) Jaka jest mediana wszystkich liczb?
        OptionalDouble average = DataCollections.getNumbers(MAX_SIZE)
                .stream()
                .mapToInt(value -> value)
                .sorted()
                .skip(MAX_SIZE % 2 == 0 ? MAX_SIZE / 2 - 1 : MAX_SIZE / 2)
                .limit(MAX_SIZE % 2 == 0 ? 2 : 1)
                .average();
        System.out.println("6)" + average.getAsDouble());

//        7*) Jaka cyfra pojawia się najczęściej we wszystkcih liczbach?
        Map<Character, Integer> characterIntegerMap = new TreeMap<>();
        Chars.asList(DataCollections.getNumbers(MAX_SIZE)
                .stream()
                .map(Object::toString)
                .map(StringBuilder::new)
                .reduce(new StringBuilder(), StringBuilder::append)
                .toString().toCharArray())
                .forEach(c -> characterIntegerMap.put(c, characterIntegerMap.get(c) == null ? 1 : characterIntegerMap.get(c) + 1));
        List<Map.Entry<Character, Integer>> collect = characterIntegerMap.entrySet()
                .stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .limit(1).collect(Collectors.toList());
        System.out.println("7) " + collect.get(0).getKey());


//        8*) Ile jest wystąpień każdej cyfry (rezultat jako Map<Integer, Integer> z kluczami od 0 do 9)
        Map<Integer, Integer> cIntMap = new TreeMap<>();
        StringBuilder reduce = DataCollections.getNumbers(MAX_SIZE)
                .stream()
                .map(Object::toString)
                .map(StringBuilder::new)
                .reduce(new StringBuilder(), StringBuilder::append);
        for (int i = 0; i < reduce.length(); i++) {
            String s = reduce.substring(i, i + 1);
            Integer integer = Integer.valueOf(s);
            Integer val = cIntMap.get(integer);
            cIntMap.put(integer, val == null ? 1 : cIntMap.get(integer) + 1);
        }
//        System.out.println(cIntMap);

        Map<Character, Integer> cIntMap2 = new TreeMap<>();
        Chars.asList(DataCollections.getNumbers(MAX_SIZE)
                .stream()
                .map(Object::toString)
                .map(StringBuilder::new)
                .reduce(new StringBuilder(), StringBuilder::append)
                .toString().toCharArray())
                .forEach(c -> cIntMap2.put(c, cIntMap2.get(c) == null ? 1 : cIntMap2.get(c) + 1));
        System.out.println("8) " + cIntMap2);

//        9) Wypisz wszystkie liczby pierwsze, posortowane rosnąco

        List<Integer> collect9 = DataCollections.getNumbers(MAX_SIZE)
                .stream()
                .filter(Main::isPrimeStream)
                .sorted()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("9) " + collect9);


//        LOREM IPSUM:
        System.out.println("LOREM IPSUM:");
//        1) Ile jest wszystkich słów?
        LinkedHashSet<String> collectL1 = DataCollections.getLoremIpsum()
                .stream()
                .map(s -> s.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""))
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
//        System.out.println(collectL1);
        System.out.println("1) " + collectL1.size());

//        2) Ile słów zaczyna się na literę 'D'?
        LinkedHashSet<String> collectL2 = DataCollections.getLoremIpsum()
                .stream()
                .map(s -> s.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""))
                .filter(s -> s.substring(0, 1).equals("d"))
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
//        System.out.println(collectL2);
        System.out.println("2) " + collectL2.size());

//        3) Policz, ile jest słów o danej długości (Map<Integer, Integer>)
        Map<Integer, Integer> numOfWords = new TreeMap<>();
        DataCollections.getLoremIpsum()
                .stream()
                .map(s -> s.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""))
                .distinct()
                .forEach(s -> numOfWords.put(s.length(), numOfWords.get(s.length()) == null ? 1 : numOfWords.get(s.length()) + 1));
        System.out.println("3) " + numOfWords);
        //check
//        System.out.println(numOfWords.entrySet().stream().mapToInt(Map.Entry::getValue).sum());

//        4) Jaka litera pojawia się narzadziej?
        Map<Character, Integer> mapL3 = new TreeMap<>();
        DataCollections.getLoremIpsum()
                .stream()
                .map(s -> s.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""))
                .forEach(s -> s.chars().forEach(c -> mapL3.put((char) c, mapL3.get((char) c) == null ? 1 : mapL3.get((char) c) + 1)));
        List<Map.Entry<Character, Integer>> countL3 = mapL3.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .limit(1)
                .collect(Collectors.toList());
//        System.out.println(mapL3);
        System.out.println("4) " + countL3);

//        5*) Ile jest słów, które posiadają dwie identyczne litery obok siebie (np. 'g' w "debugger")?
        char[] charArrayL5 = {' '};
        int[] intArrayL5 = {0};
        DataCollections.getLoremIpsum()
                .forEach(s -> s.chars().forEach(c -> {
                    if (charArrayL5[0] == (char) c) intArrayL5[0]++;
                    charArrayL5[0] = (char) c;
                }));
        System.out.println("5) " + intArrayL5[0]);

//        6**) Ile jest słów, które są palindromami?
        long countL6 = DataCollections.getLoremIpsum()
                .stream()
                .map(s -> s.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""))
                .distinct()
                .filter(s -> s.equals(new StringBuilder(s).reverse().toString()))
                .count();
        System.out.println("6) " + countL6);

    }

    private static String checkAndMap(String s) {
        String result;
        if (s.length() < 4) result = s.toLowerCase();
        else result = s.substring(0, 4).toLowerCase();
        return result;
    }

    static boolean isPrimeStream(int n) {
        return IntStream.rangeClosed(2, (int) Math.sqrt(n))
                .noneMatch(i -> n % i == 0);
    }


}
