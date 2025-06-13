import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Описывает выпуклый многоугольник в 2D пространстве.
 */
public class ConvexPolygon2D implements Drawable, Scalable, AreaMeasurable, PerimeterMeasurable {
    public static final int TYPE_ID = 6; // Убедись, что этот ID уникален среди всех фигур

    static {
        DrawableRegistry.register(TYPE_ID, ConvexPolygon2D::readFromStream);
    }

    private final List<Vector2D> vertices;

    public ConvexPolygon2D(List<Vector2D> vertices) {
        this.vertices = new ArrayList<>(vertices);
    }

    // --- Сериализация ---
    @Override
    public void writeToStream(DataOutputStream out) throws IOException {
        out.writeInt(TYPE_ID);
        out.writeInt(vertices.size());
        for (Vector2D v : vertices) {
            out.writeDouble(v.getX());
            out.writeDouble(v.getY());
        }
    }

    public static ConvexPolygon2D readFromStream(DataInputStream in) throws IOException {
        int n = in.readInt();
        List<Vector2D> vertices = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            vertices.add(new Vector2D(x, y));
        }
        return new ConvexPolygon2D(vertices);
    }

    // --- Геттеры ---
    public List<Vector2D> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    // --- equals & hashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConvexPolygon2D)) return false;
        ConvexPolygon2D other = (ConvexPolygon2D) o;
        return Objects.equals(vertices, other.vertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices);
    }

    // --- toString ---
    @Override
    public String toString() {
        return "ConvexPolygon2D{vertices=" + vertices + "}";
    }

    // --- Пример реализации интерфейсов (заготовки) ---

    @Override
    public double area() {
        // Пример: площадь вычисляется по формуле Гаусса для полигона
        double sum = 0.0;
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Vector2D a = vertices.get(i);
            Vector2D b = vertices.get((i + 1) % n);
            sum += a.getX() * b.getY() - a.getY() * b.getX();
        }
        return Math.abs(sum) * 0.5;
    }

    @Override
    public double perimeter() {
        double sum = 0.0;
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Vector2D a = vertices.get(i);
            Vector2D b = vertices.get((i + 1) % n);
            sum += a.minus(b).length();
        }
        return sum;
    }

    @Override
    public ConvexPolygon2D scale(double factor) {
        List<Vector2D> scaled = new ArrayList<>(vertices.size());
        Vector2D centroid = getCentroid();
        for (Vector2D v : vertices) {
            double dx = v.getX() - centroid.getX();
            double dy = v.getY() - centroid.getY();
            scaled.add(new Vector2D(centroid.getX() + dx * factor, centroid.getY() + dy * factor));
        }
        return new ConvexPolygon2D(scaled);
    }

    /**
     * Вычисление центра масс (центроида) полигона.
     */
    public Vector2D getCentroid() {
        double cx = 0, cy = 0;
        int n = vertices.size();
        for (Vector2D v : vertices) {
            cx += v.getX();
            cy += v.getY();
        }
        return new Vector2D(cx / n, cy / n);
    }
}