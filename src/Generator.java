import java.util.*;

public class Generator {

    public static Drawable circle() {
        return new Circle2D(new Vector2D(1, 2), 3.0);
    }

    public static Drawable rectangle() {
        return new Rectangle2D(new Vector2D(0, 0), 4.0, 2.0, 0.0);
    }

    public static Drawable triangle() {
        return new Triangle2D(
                new Vector2D(0, 0),
                new Vector2D(1, 0),
                new Vector2D(0, 1)
        );
    }

    public static Drawable ellipse() {
        return new Ellipse2D(new Vector2D(5, 5), 3.0, 1.5);
    }

    public static Drawable convexPolygon() {
        List<Vector2D> vertices = Arrays.asList(
                new Vector2D(0, 0),
                new Vector2D(2, 0),
                new Vector2D(2, 2),
                new Vector2D(0, 2)
        );
        return new ConvexPolygon2D(vertices);
    }

    public static Drawable point() {
        return new Point2D(new Vector2D(10, 10));
    }

    public static Drawable segment() {
        return new Segment2D(new Vector2D(1, 1), new Vector2D(2, 3));
    }

    public static List<Drawable> drawableList1() {
        return Arrays.asList(circle(), rectangle(), triangle(), point());
    }

    public static List<Drawable> drawableList2() {
        return Arrays.asList(ellipse(), convexPolygon(), segment());
    }

    public static FigureGroup flatGroup() {
        FigureGroup group = new FigureGroup();
        for (Drawable d : drawableList1()) {
            group.addFigure(d);
        }
        return group;
    }

    public static FigureGroup nestedGroup() {
        FigureGroup outer = new FigureGroup();
        for (Drawable d : drawableList1()) {
            outer.addFigure(d);
        }
        FigureGroup inner = new FigureGroup();
        for (Drawable d : drawableList2()) {
            inner.addFigure(d);
        }
        outer.addFigure(inner);
        return outer;
    }
}