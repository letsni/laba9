import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Описывает точку в 2D пространстве.
 */
public class Point2D implements Drawable, Scalable {
    public static final int TYPE_ID = 7; // Уникальный идентификатор для Point2D

    static {
        DrawableRegistry.register(TYPE_ID, Point2D::readFromStream);
    }

    private final Vector2D position;

    public Point2D(Vector2D position) {
        this.position = position;
    }

    // --- Сериализация ---
    @Override
    public void writeToStream(DataOutputStream out) throws IOException {
        out.writeInt(TYPE_ID);
        out.writeDouble(position.getX());
        out.writeDouble(position.getY());
    }

    public static Point2D readFromStream(DataInputStream in) throws IOException {
        double x = in.readDouble();
        double y = in.readDouble();
        return new Point2D(new Vector2D(x, y));
    }

    // --- Геттеры ---
    public Vector2D getPosition() {
        return position;
    }

    // --- equals & hashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point2D)) return false;
        Point2D other = (Point2D) o;
        return Objects.equals(position, other.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    // --- toString ---
    @Override
    public String toString() {
        return "Point2D{position=" + position + "}";
    }

    // --- Масштабирование ---
    @Override
    public Point2D scale(double factor) {
        return new Point2D(position.scale(factor));
    }
}