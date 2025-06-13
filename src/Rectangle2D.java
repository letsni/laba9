import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Описывает прямоугольник в 2D пространстве.
 * Прямоугольник задаётся центром, шириной, высотой и (необязательно) углом поворота относительно Ox.
 */
public class Rectangle2D implements Drawable, Scalable, AreaMeasurable, PerimeterMeasurable {
    public static final int TYPE_ID = 3; // Убедись, что этот ID уникален среди всех фигур

    static {
        DrawableRegistry.register(TYPE_ID, Rectangle2D::readFromStream);
    }

    private final Vector2D center;
    private final double width;
    private final double height;
    private final double angle; // угол поворота в радианах относительно оси Ox

    public Rectangle2D(Vector2D center, double width, double height, double angle) {
        this.center = center;
        this.width = width;
        this.height = height;
        this.angle = angle;
    }

    // --- Сериализация ---
    @Override
    public void writeToStream(DataOutputStream out) throws IOException {
        out.writeInt(TYPE_ID);
        out.writeDouble(center.getX());
        out.writeDouble(center.getY());
        out.writeDouble(width);
        out.writeDouble(height);
        out.writeDouble(angle);
    }

    public static Rectangle2D readFromStream(DataInputStream in) throws IOException {
        double x = in.readDouble();
        double y = in.readDouble();
        double w = in.readDouble();
        double h = in.readDouble();
        double a = in.readDouble();
        return new Rectangle2D(new Vector2D(x, y), w, h, a);
    }

    // --- Геттеры ---
    public Vector2D getCenter() { return center; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getAngle() { return angle; }

    // --- equals & hashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rectangle2D)) return false;
        Rectangle2D other = (Rectangle2D) o;
        return Double.compare(width, other.width) == 0
            && Double.compare(height, other.height) == 0
            && Double.compare(angle, other.angle) == 0
            && Objects.equals(center, other.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, width, height, angle);
    }

    // --- toString ---
    @Override
    public String toString() {
        return "Rectangle2D{center=" + center + ", width=" + width + ", height=" + height + ", angle=" + angle + "}";
    }

    // --- Пример реализации интерфейсов (заготовки) ---

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public Rectangle2D scale(double factor) {
        return new Rectangle2D(center, width * factor, height * factor, angle);
    }
}