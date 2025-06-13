import java.util.Objects;

public final class Vector2D {
    private final double x;
    private final double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D multiply(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    public double angleWithXAxis() {
        return angleBetween(new Vector2D(1, 0));
    }

    public double angleWithYAxis() {
        return angleBetween(new Vector2D(0, 1));
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public double dotProduct(Vector2D other) {
        return x * other.x + y * other.y;
    }

    public double angleBetween(Vector2D other) {
        double dot = dotProduct(other);
        double lenProduct = length() * other.length();
        return Math.acos(dot / lenProduct);
    }

    public double triangleArea(Vector2D other) {
        return Math.abs(x * other.y - y * other.x) / 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return Double.compare(vector2D.x, x) == 0 && Double.compare(vector2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}