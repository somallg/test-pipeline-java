package com.fa.assignments.model;

import java.util.List;

public class TestCategory<InputType, OutputType> {
    public String name;
    public boolean focus;
    public List<TestCase<InputType, OutputType>> testCases;
}
