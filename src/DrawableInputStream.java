import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Обёртка над InputStream для чтения Drawable-объектов в бинарном виде.
 * Использует DrawableRegistry для расширяемого создания экземпляров фигур по их typeId.
 */
public class DrawableInputStream implements AutoCloseable{
    private final DataInputStream in;

    public DrawableInputStream(InputStream inputStream) {
        this.in = new DataInputStream(inputStream);
    }

    /**
     * Прочитать одну фигуру из потока.
     */
    public Drawable readDrawable() throws IOException {
        int typeId = in.readInt();
        DrawableReader reader = DrawableRegistry.getReader(typeId);
        if (reader == null) {
            throw new IOException("Unknown Drawable typeId: " + typeId);
        }
        return reader.read(in);
    }

    /**
     * Прочитать список фигур (сначала размер, затем сами фигуры).
     */
    public List<Drawable> readDrawables() throws IOException {
        int size = in.readInt();
        List<Drawable> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(readDrawable());
        }
        return result;
    }

    /**
     * Закрыть поток.
     */
    public void close() throws IOException {
        in.close();
    }

    /**
     * Статический помощник для чтения Drawable из любого DataInputStream.
     * Удобно использовать внутри статических методов чтения фигур (например, в группах).
     */
    public static Drawable readDrawableStatic(DataInputStream in) throws IOException {
        int typeId = in.readInt();
        DrawableReader reader = DrawableRegistry.getReader(typeId);
        if (reader == null) {
            throw new IOException("Unknown Drawable typeId: " + typeId);
        }
        return reader.read(in);
    }
}