package com.rubynaxela.kyanite.audio.synth;

final class Note {

    public static int pause = Integer.MIN_VALUE;
    public static int A0 = -48;
    public static int ASHARP0 = -47;
    public static int B0 = -46;
    public static int C1 = -45;
    public static int CSHARP1 = -44;
    public static int D1 = -43;
    public static int DSHARP1 = -42;
    public static int E1 = -41;
    public static int F1 = -40;
    public static int FSHARP1 = -39;
    public static int G1 = -38;
    public static int GSHARP1 = -37;
    public static int A1 = -36;
    public static int ASHARP1 = -35;
    public static int B1 = -34;
    public static int C2 = -33;
    public static int CSHARP2 = -32;
    public static int D2 = -31;
    public static int DSHARP2 = -30;
    public static int E2 = -29;
    public static int F2 = -28;
    public static int FSHARP2 = -27;
    public static int G2 = -26;
    public static int GSHARP2 = -25;
    public static int A2 = -24;
    public static int ASHARP2 = -23;
    public static int B2 = -22;
    public static int C3 = -21;
    public static int CSHARP3 = -20;
    public static int D3 = -19;
    public static int DSHARP3 = -18;
    public static int E3 = -17;
    public static int F3 = -16;
    public static int FSHARP3 = -15;
    public static int G3 = -14;
    public static int GSHARP3 = -13;
    public static int A3 = -12;
    public static int ASHARP3 = -11;
    public static int B3 = -10;
    public static int C4 = -9;
    public static int CSHARP4 = -8;
    public static int D4 = -7;
    public static int DSHARP4 = -6;
    public static int E4 = -5;
    public static int F4 = -4;
    public static int FSHARP4 = -3;
    public static int G4 = -2;
    public static int GSHARP4 = -1;
    public static int A4 = 0;
    public static int ASHARP4 = 1;
    public static int B4 = 2;
    public static int C5 = 3;
    public static int CSHARP5 = 4;
    public static int D5 = 5;
    public static int DSHARP5 = 6;
    public static int E5 = 7;
    public static int F5 = 8;
    public static int FSHARP5 = 9;
    public static int G5 = 10;
    public static int GSHARP5 = 11;
    public static int A5 = 12;
    public static int ASHARP5 = 14;
    public static int B5 = 14;
    public static int C6 = 15;
    public int id;
    public float duration;

    Note(int id, float duration) {
        this.id = id;
        this.duration = duration;
    }
}
