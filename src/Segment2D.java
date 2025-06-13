import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Описывает отрезок в 2D пространстве.
 */
public class Segment2D implements Drawable, Scalable, PerimeterMeasurable {
    public static final int TYPE_ID = 8; // Уникальный идентификатор для Segment2D

    static {
        DrawableRegistry.register(TYPE_ID, Segment2D::readFromStream);
    }

    private final Vector2D start;
    private final Vector2D end;

    public Segment2D(Vector2D start, Vector2D end) {
        this.start = start;
        this.end = end;
    }

    // --- Сериализация ---
    @Override
    public void writeToStream(DataOutputStream out) throws IOException {
        out.writeInt(TYPE_ID);
        out.writeDouble(start.getX());
        out.writeDouble(start.getY());
        out.writeDouble(end.getX());
        out.writeDouble(end.getY());
    }

    public static Segment2D readFromStream(DataInputStream in) throws IOException {
        double x1 = in.readDouble();
        double y1 = in.readDouble();
        double x2 = in.readDouble();
        double y2 = in.readDouble();
        return new Segment2D(new Vector2D(x1, y1), new Vector2D(x2, y2));
    }

    // --- Геттеры ---
    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    // --- equals & hashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Segment2D)) return false;
        Segment2D other = (Segment2D) o;
        return Objects.equals(start, other.start)
            && Objects.equals(end, other.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    // --- toString ---
    @Override
    public String toString() {
        return "Segment2D{start=" + start + ", end=" + end + "}";
    }

    // --- Периметр (длина отрезка) ---
    @Override
    public double perimeter() {
        return start.minus(end).length();
    }

    // --- Масштабирование ---
    @Override
    public Segment2D scale(double factor) {
        Vector2D center = new Vector2D(
            (start.getX() + end.getX()) / 2.0,
            (start.getY() + end.getY()) / 2.0
        );
        Vector2D scaledStart = scalePoint(start, center, factor);
        Vector2D scaledEnd = scalePoint(end, center, factor);
        return new Segment2D(scaledStart, scaledEnd);
    }

    private static Vector2D scalePoint(Vector2D point, Vector2D center, double factor) {
        double dx = point.getX() - center.getX();
        double dy = point.getY() - center.getY();
        return new Vector2D(center.getX() + dx * factor, center.getY() + dy * factor);
    }
}