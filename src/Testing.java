import java.io.IOException;

public class Testing {
    public static void testFigures() throws IOException {
        System.out.println("=== ТЕСТ ОТДЕЛЬНЫХ ФИГУР ===");
        Drawable[] figures = {
                Generator.circle(),
                Generator.rectangle(),
                Generator.triangle(),
                Generator.ellipse(),
                Generator.convexPolygon(),
                Generator.point(),
                Generator.segment()
        };
        for (int i = 0; i < figures.length; i++) {
            Drawable orig = figures[i];
            String fname = "figure" + i + ".bin";
            // Запись
            try (DrawableOutputStream out = new DrawableOutputStream(new java.io.FileOutputStream(fname))) {
                out.writeDrawable(orig);
            }
            // Чтение
            Drawable restored;
            try (DrawableInputStream in = new DrawableInputStream(new java.io.FileInputStream(fname))) {
                restored = in.readDrawable();
            }
            System.out.printf("Фигура %d: equals = %s\n", i, orig.equals(restored));
        }
    }

    public static void testLists() throws IOException {
        System.out.println("=== ТЕСТ СПИСКОВ ФИГУР ===");
        java.util.List<Drawable>[] lists = new java.util.List[] {
                Generator.drawableList1(),
                Generator.drawableList2()
        };
        for (int i = 0; i < lists.length; i++) {
            java.util.List<Drawable> orig = lists[i];
            String fname = "list" + i + ".bin";
            // Запись
            try (DrawableOutputStream out = new DrawableOutputStream(new java.io.FileOutputStream(fname))) {
                out.writeDrawables(orig);
            }
            // Чтение
            java.util.List<Drawable> restored;
            try (DrawableInputStream in = new DrawableInputStream(new java.io.FileInputStream(fname))) {
                restored = in.readDrawables();
            }
            boolean ok = orig.equals(restored);
            System.out.printf("Список %d: equals = %s\n", i, ok);
        }
    }

    public static void testGroups() throws IOException {
        System.out.println("=== ТЕСТ ГРУПП ===");
        FigureGroup[] groups = { Generator.flatGroup(), Generator.nestedGroup() };
        for (int i = 0; i < groups.length; i++) {
            FigureGroup orig = groups[i];
            String fname = "group" + i + ".bin";
            // Запись
            try (DrawableOutputStream out = new DrawableOutputStream(new java.io.FileOutputStream(fname))) {
                out.writeDrawable(orig);
            }
            // Чтение
            FigureGroup restored;
            try (DrawableInputStream in = new DrawableInputStream(new java.io.FileInputStream(fname))) {
                restored = (FigureGroup) in.readDrawable();
            }
            boolean ok = orig.contentEquals(restored);
            System.out.printf("Группа %d: contentEquals = %s\n", i, ok);
        }
    }

    public static void main(String[] args) throws IOException {
        testFigures();
        testLists();
        testGroups();
        System.out.println("=== ВСЕ ТЕСТЫ ЗАВЕРШЕНЫ ===");
    }
}