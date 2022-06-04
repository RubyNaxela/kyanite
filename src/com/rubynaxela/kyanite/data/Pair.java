package com.rubynaxela.kyanite.data;

/**
 * A simple two-value tuple. The values may be objects of different classes.
 *
 * @param <T1> the first value class
 * @param <T2> the second value class
 */
public record Pair<T1, T2>(T1 value1, T2 value2) {

}
