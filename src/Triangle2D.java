import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Описывает треугольник в 2D пространстве.
 */
public class Triangle2D implements Drawable, Scalable, AreaMeasurable, PerimeterMeasurable {
    public static final int TYPE_ID = 2; // Убедись, что этот ID уникален среди всех фигур

    static {
        DrawableRegistry.register(TYPE_ID, Triangle2D::readFromStream);
    }

    private final Vector2D p1;
    private final Vector2D p2;
    private final Vector2D p3;

    public Triangle2D(Vector2D p1, Vector2D p2, Vector2D p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    // --- Сериализация ---
    @Override
    public void writeToStream(DataOutputStream out) throws IOException {
        out.writeInt(TYPE_ID);
        out.writeDouble(p1.getX());
        out.writeDouble(p1.getY());
        out.writeDouble(p2.getX());
        out.writeDouble(p2.getY());
        out.writeDouble(p3.getX());
        out.writeDouble(p3.getY());
    }

    public static Triangle2D readFromStream(DataInputStream in) throws IOException {
        double x1 = in.readDouble();
        double y1 = in.readDouble();
        double x2 = in.readDouble();
        double y2 = in.readDouble();
        double x3 = in.readDouble();
        double y3 = in.readDouble();
        return new Triangle2D(
            new Vector2D(x1, y1),
            new Vector2D(x2, y2),
            new Vector2D(x3, y3)
        );
    }

    // --- Геттеры ---
    public Vector2D getP1() { return p1; }
    public Vector2D getP2() { return p2; }
    public Vector2D getP3() { return p3; }

    // --- equals & hashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Triangle2D)) return false;
        Triangle2D other = (Triangle2D) o;
        return Objects.equals(p1, other.p1)
            && Objects.equals(p2, other.p2)
            && Objects.equals(p3, other.p3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p1, p2, p3);
    }

    // --- toString ---
    @Override
    public String toString() {
        return "Triangle2D{p1=" + p1 + ", p2=" + p2 + ", p3=" + p3 + "}";
    }

    // --- Пример реализации интерфейсов (заготовки) ---

    @Override
    public double area() {
        // Формула площади через определитель
        return 0.5 * Math.abs(
            (p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) -
            (p3.getX() - p1.getX()) * (p2.getY() - p1.getY())
        );
    }

    @Override
    public double perimeter() {
        return p1.minus(p2).length() + p2.minus(p3).length() + p3.minus(p1).length();
    }

    @Override
    public Triangle2D scale(double factor) {
        Vector2D centroid = getCentroid();
        return new Triangle2D(
            scalePoint(p1, centroid, factor),
            scalePoint(p2, centroid, factor),
            scalePoint(p3, centroid, factor)
        );
    }

    private static Vector2D scalePoint(Vector2D point, Vector2D centroid, double factor) {
        double dx = point.getX() - centroid.getX();
        double dy = point.getY() - centroid.getY();
        return new Vector2D(centroid.getX() + dx * factor, centroid.getY() + dy * factor);
    }

    public Vector2D getCentroid() {
        return new Vector2D(
            (p1.getX() + p2.getX() + p3.getX()) / 3.0,
            (p1.getY() + p2.getY() + p3.getY()) / 3.0
        );
    }
}