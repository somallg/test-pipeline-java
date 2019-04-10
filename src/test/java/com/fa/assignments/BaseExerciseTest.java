package com.fa.assignments;

import com.fa.assignments.model.TestCase;
import com.fa.assignments.model.TestFixture;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public abstract class BaseExerciseTest {

    @SuppressWarnings("unchecked")
    public static <InputType, OutputType> TestFixture<InputType, OutputType> setupTestCases(String fixtureJsonFile) throws IOException {
        ClassLoader classLoader = BaseExerciseTest.class.getClassLoader();
        URL resource = classLoader.getResource(fixtureJsonFile);
        assert resource != null;
        File fixtureFile = new File(resource.getFile());
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(fixtureFile, TestFixture.class);
    }

    @SuppressWarnings("unused")
    public abstract void testWithExampleTests(TestCase<List<Integer>, Integer> testCase);

    @SuppressWarnings("unused")
    public abstract void testWithCorrectnessTests(TestCase<List<Integer>, Integer> testCase);

    @SuppressWarnings("unused")
    public abstract void testWithPerformanceTests(TestCase<List<Integer>, Integer> testCase);
}
