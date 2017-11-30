package ru.javawebinar.topjava;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class TimeTestWatcher extends Stopwatch{

    private static void logInfo(Description description, long nanos) {
        String testName = description.getMethodName();
        String logInfo = String.format("Test %s finished, spent %d microseconds",
                testName, TimeUnit.NANOSECONDS.toMicros(nanos));
        System.out.println(logInfo);
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description,nanos);
    }
}