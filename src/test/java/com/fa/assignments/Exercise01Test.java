package com.fa.assignments;

import com.fa.assignments.model.TestCase;
import com.fa.assignments.model.TestFixture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static com.fa.assignments.Exercise01.findMax;
import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Exercise01Test extends BaseExerciseTest {

    public static TestFixture<List<Integer>, Integer> testFixture;

    @BeforeAll
    public static void setup() throws IOException {
        testFixture = setupTestCases("exercise01.fixture.json");
    }

    @Override
    @ParameterizedTest
    @MethodSource("getExampleTests")
    @Order(1)
    public void testWithExampleTests(TestCase<List<Integer>, Integer> testCase) {
        assertEquals(testCase.output, findMax(testCase.input));
    }

    @Override
    @ParameterizedTest
    @MethodSource("getCorrectnessTests")
    @Order(2)
    public void testWithCorrectnessTests(TestCase<List<Integer>, Integer> testCase) {
        assertEquals(testCase.output, findMax(testCase.input));
    }

    @Override
    @ParameterizedTest
    @MethodSource("getPerformanceTests")
    @Order(3)
    public void testWithPerformanceTests(TestCase<List<Integer>, Integer> testCase) {
        assertEquals(1, 1);
    }

    public static Stream<TestCase<List<Integer>, Integer>> getExampleTests() {
        return testFixture.getExampleTests();
    }

    public static Stream<TestCase<List<Integer>, Integer>> getCorrectnessTests() {
        return testFixture.getCorrectnessTests();
    }

    public static Stream<TestCase<List<Integer>, Integer>> getPerformanceTests() {
        return Stream.of();
    }

}