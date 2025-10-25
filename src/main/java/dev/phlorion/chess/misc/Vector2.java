package dev.phlorion.chess.misc;

import java.util.Objects;

public class Vector2 {
    public int x;
    public int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 other) {
        x = other.x;
        y = other.y;
    }

    public static Vector2 add(Vector2 v1, Vector2 v2) {
        return new Vector2(v1.x + v2.x, v1.y + v2.y);
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return Objects.equals(x, vector2.x) && Objects.equals(y, vector2.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}