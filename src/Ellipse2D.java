import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Описывает эллипс в 2D пространстве.
 */
public class Ellipse2D implements Drawable, Scalable, AreaMeasurable, PerimeterMeasurable {
    public static final int TYPE_ID = 4; // Убедись, что этот ID уникален среди всех фигур

    static {
        DrawableRegistry.register(TYPE_ID, Ellipse2D::readFromStream);
    }

    private final Vector2D center;
    private final double radiusX;
    private final double radiusY;

    public Ellipse2D(Vector2D center, double radiusX, double radiusY) {
        this.center = center;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    // --- Сериализация ---
    @Override
    public void writeToStream(DataOutputStream out) throws IOException {
        out.writeInt(TYPE_ID);
        out.writeDouble(center.getX());
        out.writeDouble(center.getY());
        out.writeDouble(radiusX);
        out.writeDouble(radiusY);
    }

    public static Ellipse2D readFromStream(DataInputStream in) throws IOException {
        double x = in.readDouble();
        double y = in.readDouble();
        double rx = in.readDouble();
        double ry = in.readDouble();
        return new Ellipse2D(new Vector2D(x, y), rx, ry);
    }

    // --- Геттеры ---
    public Vector2D getCenter() {
        return center;
    }

    public double getRadiusX() {
        return radiusX;
    }

    public double getRadiusY() {
        return radiusY;
    }

    // --- equals & hashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ellipse2D)) return false;
        Ellipse2D other = (Ellipse2D) o;
        return Double.compare(radiusX, other.radiusX) == 0
            && Double.compare(radiusY, other.radiusY) == 0
            && Objects.equals(center, other.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radiusX, radiusY);
    }

    // --- toString ---
    @Override
    public String toString() {
        return "Ellipse2D{center=" + center + ", radiusX=" + radiusX + ", radiusY=" + radiusY + "}";
    }

    // --- Пример реализации интерфейсов (заготовки) ---

    @Override
    public double area() {
        return Math.PI * radiusX * radiusY;
    }

    @Override
    public double perimeter() {
        // Приближённая формула Раманауджана для длины эллипса
        double h = Math.pow(radiusX - radiusY, 2) / Math.pow(radiusX + radiusY, 2);
        return Math.PI * (radiusX + radiusY) * (1 + (3 * h) / (10 + Math.sqrt(4 - 3 * h)));
    }

    @Override
    public Ellipse2D scale(double factor) {
        return new Ellipse2D(center, radiusX * factor, radiusY * factor);
    }
}