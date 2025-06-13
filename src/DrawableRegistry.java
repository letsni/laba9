import java.util.HashMap;
import java.util.Map;

public class DrawableRegistry {
    private static final Map<Integer, DrawableReader> readers = new HashMap<>();

    public static void register(int typeId, DrawableReader reader) {
        readers.put(typeId, reader);
    }

    public static DrawableReader getReader(int typeId) {
        return readers.get(typeId);
    }
}