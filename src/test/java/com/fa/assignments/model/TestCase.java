package com.fa.assignments.model;

public class TestCase<InputType, OutputType> {
    public String name;
    public InputType input;
    public OutputType output;
    public int inputSize;
    public int timeLimit;

    @Override
    public String toString() {
        return String.format("[%s] should return %s for %s", name, output, input);
    }
}
