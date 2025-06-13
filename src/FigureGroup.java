import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * FigureGroup — класс для объединения фигур (Drawable) в одну группу.
 * Реализует все функции: геометрия, масштабирование, сериализация, структурное сравнение.
 */
public class FigureGroup implements Drawable, Scalable, AreaMeasurable, PerimeterMeasurable {
    public static final int TYPE_ID = 100; // Уникальный идентификатор для группы фигур

    static {
        DrawableRegistry.register(TYPE_ID, FigureGroup::readFromStream);
    }

    private final List<Drawable> figures;

    public FigureGroup() {
        this.figures = new ArrayList<>();
    }

    public FigureGroup(List<Drawable> figures) {
        this.figures = new ArrayList<>(figures);
    }

    // Добавление и получение фигур
    public void addFigure(Drawable figure) {
        figures.add(figure);
    }

    public boolean removeFigure(Drawable figure) {
        return figures.remove(figure);
    }

    public Drawable removeFigure(int index) {
        return figures.remove(index);
    }

    public Drawable getFigure(int index) {
        return figures.get(index);
    }

    public List<Drawable> getFigures() {
        return Collections.unmodifiableList(figures);
    }

    // --- Сериализация ---
    @Override
    public void writeToStream(DataOutputStream out) throws IOException {
        out.writeInt(TYPE_ID);
        out.writeInt(figures.size());
        for (Drawable d : figures) {
            d.writeToStream(out);
        }
    }

    public static FigureGroup readFromStream(DataInputStream in) throws IOException {
        int count = in.readInt();
        List<Drawable> figures = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            figures.add(DrawableRegistry.readFromStream(in));
        }
        return new FigureGroup(figures);
    }

    // --- Масштабирование и перенос ---
    @Override
    public FigureGroup scale(double factor) {
        List<Drawable> scaled = new ArrayList<>();
        for (Drawable figure : figures) {
            if (figure instanceof Scalable) {
                scaled.add(((Scalable) figure).scale(factor));
            } else {
                scaled.add(figure); // или выбросить исключение
            }
        }
        return new FigureGroup(scaled);
    }

    @Override
    public FigureGroup translate(Vector2D delta) {
        List<Drawable> moved = new ArrayList<>();
        for (Drawable figure : figures) {
            moved.add(figure.translate(delta));
        }
        return new FigureGroup(moved);
    }

    // --- Геометрия ---
    @Override
    public double getArea() {
        double sum = 0;
        for (Drawable figure : figures) {
            if (figure instanceof AreaMeasurable) {
                sum += ((AreaMeasurable) figure).getArea();
            }
        }
        return sum;
    }

    @Override
    public double getPerimeter() {
        double sum = 0;
        for (Drawable figure : figures) {
            if (figure instanceof PerimeterMeasurable) {
                sum += ((PerimeterMeasurable) figure).getPerimeter();
            }
        }
        return sum;
    }

    // --- Рисование ---
    @Override
    public void draw() {
        for (Drawable figure : figures) {
            figure.draw();
        }
    }

    // --- equals, hashCode, toString ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FigureGroup that = (FigureGroup) o;
        return figures.equals(that.figures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(figures);
    }

    @Override
    public String toString() {
        return "FigureGroup{" + "figures=" + figures + '}';
    }

    /**
     * Сравнивает содержимое групп на структурную идентичность.
     * Корректно сравнивает вложенные группы и обычные фигуры.
     */
    public boolean contentEquals(FigureGroup other) {
        if (this == other) return true;
        if (other == null) return false;
        if (this.figures.size() != other.figures.size()) return false;
        for (int i = 0; i < this.figures.size(); i++) {
            Drawable a = this.figures.get(i);
            Drawable b = other.figures.get(i);
            if (a instanceof FigureGroup && b instanceof FigureGroup) {
                if (!((FigureGroup) a).contentEquals((FigureGroup) b)) return false;
            } else if (!a.equals(b)) {
                return false;
            }
        }
        return true;
    }
}