package com.rubynaxela.kyanite.audio.synth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rubynaxela.kyanite.util.Unit;

import java.util.function.Function;

final class Oscillator {

    @JsonProperty(value = "function", required = true)
    private String function;
    @JsonProperty(value = "attack_factor", required = true)
    private float attackFactor;
    @JsonProperty(value = "attack_duration", required = true)
    private float attackDuration;

    private Oscillator() {
    }

    public Function<Float, Float> getFunction() {
        return switch (function) {
            case "sin" -> arg -> (float) Math.sin(arg);
            case "square" -> arg -> (float) Math.signum(Math.sin(arg)) * 0.5f;
            default -> arg -> 0f;
        };
    }

    public float getAttackFactor() {
        return attackFactor;
    }

    public float getAttackDuration() {
        return attackDuration;
    }
}
