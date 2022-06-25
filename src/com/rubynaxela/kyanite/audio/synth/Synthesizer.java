package com.rubynaxela.kyanite.audio.synth;

import com.rubynaxela.kyanite.audio.SoundBuffer;
import com.rubynaxela.kyanite.util.Unit;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public final class Synthesizer {

    private static final int SAMPLE_RATE = 24000;
    private static final int SMOOTHING = 100;

    private Synthesizer() {
    }

    public static SoundBuffer createBuffer(@NotNull Song.Track track) {

        final List<Note> notes = track.getNotes();
        final Oscillator oscillator = track.getOscillator();
        final Function<Float, Float> function = oscillator.getFunction();
        final float attackDuration = oscillator.getAttackDuration();
        final float attackFactor = oscillator.getAttackFactor();

        final float duration = notes.stream().reduce(0f, (s, n) -> s += n.duration, (s, n) -> s += n);
        final int attackSamples = (int) (attackDuration * SAMPLE_RATE / 1000);
        final int samplesCount = (int) (SAMPLE_RATE * duration / 1000);
        final short[] samples = new short[samplesCount];
        int currentNoteIndex = 0;
        float scannedDuration = 0;
        Note previousNote = null, currentNote = notes.get(currentNoteIndex);
        int edgePoint = 0;
        for (int t = 0; t < samplesCount; t++) {
            if (t / (SAMPLE_RATE / 1000f) > scannedDuration + currentNote.duration && currentNoteIndex < notes.size() - 1) {
                scannedDuration += currentNote.duration;
                previousNote = notes.get(currentNoteIndex);
                currentNote = notes.get(++currentNoteIndex);
                edgePoint = t;
            }

            if (currentNote.id != Note.pause) {

                if (t <= SMOOTHING) {
                    final float factor = t / (float) SMOOTHING;
                    samples[t] = sample(currentNote.id, factor * Short.MAX_VALUE, function, t);
                } else if (previousNote != null && t <= edgePoint + SMOOTHING) {
                    final float factor = (t - edgePoint) / (float) SMOOTHING;
                    samples[t] = (short) ((1 - factor)
                                          * sample(previousNote.id, Short.MAX_VALUE, function, t)
                                          + factor * sample(currentNote.id, Short.MAX_VALUE, function, t));
                } else if (t < samplesCount - SMOOTHING - 1)
                    samples[t] = sample(currentNote.id, Short.MAX_VALUE, function, t);
                else {
                    final float factor = (samplesCount - t - 1) / (float) SMOOTHING;
                    samples[t] = sample(currentNote.id, factor * Short.MAX_VALUE, function, t);
                }

                if (t > edgePoint + attackSamples - 1) samples[t] *= 1 - attackFactor;
                else samples[t] *= attackEnvelope(t - edgePoint, attackSamples, attackFactor);
            } else samples[t] = 0;
        }

        final SoundBuffer buffer = new SoundBuffer();
        buffer.loadFromSamples(samples, 1, SAMPLE_RATE);
        return buffer;
    }

    private static float attackEnvelope(float t, int duration, float factor) {
        final float x = 2 * t / (float) (duration - 1) - 1;
        return (float) ((1 - factor) + Math.exp(-7.5 * x * x) * factor);
    }

    private static float frequency(int noteId) {
        return (float) (440 * Math.pow(2, noteId / 12f));
    }

    private static short sample(int noteId, float amplitude, @NotNull Function<Float, Float> function, int t) {
        return (short) (function.apply((float) (t * frequency(noteId) * 2 * Math.PI / SAMPLE_RATE)) * amplitude);
    }
}
