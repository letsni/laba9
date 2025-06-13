import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Описывает окружность в 2D пространстве.
 */
public final class Circle2D implements Drawable, Scalable, AreaMeasurable, PerimeterMeasurable {
    public static final int TYPE_ID = 1;

    static {
        DrawableRegistry.register(TYPE_ID, Circle2D::readFromStream);
    }

    private final Vector2D center;
    private final double radius;

    public Circle2D(Vector2D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    // --- Сериализация ---
    @Override
    public void writeToStream(DataOutputStream out) throws IOException {
        out.writeInt(TYPE_ID);
        out.writeDouble(center.getX());
        out.writeDouble(center.getY());
        out.writeDouble(radius);
    }

    public static Circle2D readFromStream(DataInputStream in) throws IOException {
        double x = in.readDouble();
        double y = in.readDouble();
        double r = in.readDouble();
        return new Circle2D(new Vector2D(x, y), r);
    }

    // --- Геттеры ---
    public Vector2D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    // --- equals & hashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circle2D)) return false;
        Circle2D other = (Circle2D) o;
        return Double.compare(radius, other.radius) == 0
            && Objects.equals(center, other.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }

    // --- toString ---
    @Override
    public String toString() {
        return "Circle2D{center=" + center + ", radius=" + radius + "}";
    }

    // --- Пример реализации интерфейсов (заготовки) ---

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public Circle2D scale(double factor) {
        return new Circle2D(center, radius * factor);
    }
}