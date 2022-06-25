package com.rubynaxela.kyanite.audio.synth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public final class Song {

    @JsonProperty(value = "tempo", required = true)
    private float tempo;
    @JsonProperty(value = "tracks", required = true)
    private List<Track> tracks;

    private Song() {
    }

    public float getTempo() {
        return tempo;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public static final class Track {

        @JsonProperty(value = "oscillator", required = true)
        private Oscillator oscillator;
        @JsonProperty(value = "notes", required = true)
        private String notes;

        private Track() {
        }

        public Oscillator getOscillator() {
            return oscillator;
        }

        public List<Note> getNotes() {
            final List<Note> parsedNotes = new ArrayList<>();
            try {
                final String[] data = notes.split("\\s+");
                for (int i = 0; i < data.length; i += 2)
                    parsedNotes.add(new Note(Note.class.getDeclaredField(data[i].replace("#", "SHARP"))
                                                       .getInt(null), Float.parseFloat(data[i + 1])));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            return parsedNotes;
        }
    }
}
