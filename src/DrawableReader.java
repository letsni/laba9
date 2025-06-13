import java.io.DataInputStream;
import java.io.IOException;

@FunctionalInterface
public interface DrawableReader {
    Drawable read(DataInputStream in) throws IOException;
}