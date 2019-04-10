package com.fa.assignments.model;

import java.util.List;
import java.util.stream.Stream;

public class TestFixture<InputType, OutputType> {
    public String name;
    public List<TestCategory<InputType, OutputType>> testCategories;
    public boolean skip;

    public Stream<TestCase<InputType, OutputType>> getExampleTests() {
        return getTestsByIndex(0);
    }

    public Stream<TestCase<InputType, OutputType>> getCorrectnessTests() {
        return getTestsByIndex(1);
    }

    public Stream<TestCase<InputType, OutputType>> getPerformanceTests() {
        return getTestsByIndex(2);
    }

    private Stream<TestCase<InputType, OutputType>> getTestsByIndex(int index) {
        return testCategories.get(index).testCases.stream();
    }
}
