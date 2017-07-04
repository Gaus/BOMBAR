package com.gaustank.Player;

public enum Planes {
    B_17() {
        @Override
        public Player getPlane() {
            return new Player.Plane("B-17")
                    .animation(512, 0, 159, 44)
                    .mass(60000)
                    .lift(132)
                    .fuel(100000)
                    .maxSpeed(26)
                    .maxCountBombs(25)
                    .acceleration(0.01)
                    .build();
        }
    },
    HO_229() {
        @Override
        public Player getPlane() {
            return new Player.Plane("Ho.229")
                    .animation(512, 86, 143, 32)
                    .mass(28000)
                    .lift(54)
                    .fuel(50000)
                    .maxSpeed(50)
                    .maxCountBombs(15)
                    .acceleration(0.06)
                    .build();
        }
    },
    IL_2() {
        @Override
        public Player getPlane() {
            return new Player.Plane("Ил-2")
                    .animation(512, 44, 146, 39)
                    .mass(26700)
                    .lift(38)
                    .fuel(75000)
                    .maxSpeed(21)
                    .maxCountBombs(8)
                    .acceleration(0.05)
                    .build();
        }
    };

    public abstract Player getPlane();
}
