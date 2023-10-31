package com.endava.md.development.java.internship.functional.service;

import com.endava.md.development.java.internship.functional.domain.Privilege;
import com.endava.md.development.java.internship.functional.domain.User;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserServiceImpl implements UserService {

    @Override
    public List<String> getFirstNamesReverseSorted(List<User> users) {
        return users.stream()
            .sorted(Comparator.comparing(User::getFirstName).reversed())
            .map(User::getFirstName)
            .collect(Collectors.toList());
    }

    @Override
    public List<User> sortByAgeDescAndNameAsc(final List<User> users) {
        return users.stream()
            .sorted(Comparator.comparing(User::getAge).reversed().thenComparing(User::getFirstName))
            .collect(Collectors.toList());
    }

    @Override
    public List<Privilege> getAllDistinctPrivileges(final List<User> users) {
        return users
            .stream()
            .map(User::getPrivileges)
            .flatMap(List::stream)
            .distinct()
            .collect(Collectors.toList());
    }

    @Override
    public Optional<User> getUpdateUserWithAgeHigherThan(final List<User> users, final int age) {
        return users
            .stream()
            .filter((u) -> u.getAge() > age)
            .findFirst();
    }

    @Override
    public Map<Integer, List<User>> groupByCountOfPrivileges(final List<User> users) {
        return users
            .stream()
            .collect(Collectors.groupingBy(user -> user.getPrivileges().size()));
    }

    @Override
    public double getAverageAgeForUsers(final List<User> users) {
        return users
            .stream()
            .mapToDouble(User::getAge)
            .average()
            .orElse(-1);
    }

    @Override
    public Optional<String> getMostFrequentLastName(final List<User> users) {
        return users
            .stream()
            .collect(Collectors.groupingBy(User::getLastName, Collectors.counting()))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .filter(u -> u.getValue() > 1)
            .map(Map.Entry::getKey);

    }

    @Override
    public List<User> filterBy(final List<User> users, final Predicate<User>... predicates) {
       Predicate<User> p = Stream.of(predicates).reduce(x-> true, Predicate::and);

       return users
           .stream()
           .filter(p)
           .collect(Collectors.toList());
    }

    @Override
    public String convertTo(final List<User> users, final String delimiter, final Function<User, String> mapFun) {
        return users.stream()
            .map(User::getLastName)
            .collect(Collectors.joining(","));
    }

    @Override
    public Map<Privilege, List<User>> groupByPrivileges(List<User> users) {
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    public Map<String, Long> getNumberOfLastNames(final List<User> users) {
        return users
            .stream()
            .collect(Collectors.groupingBy(User::getLastName, Collectors.counting()))
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }
}
