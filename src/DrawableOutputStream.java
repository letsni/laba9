import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DrawableOutputStream implements AutoCloseable{
    private final DataOutputStream out;

    public DrawableOutputStream(OutputStream outputStream) {
        this.out = new DataOutputStream(outputStream);
    }

    public void writeDrawable(Drawable drawable) throws IOException {
        drawable.writeToStream(out);
    }

    public void writeDrawables(List<Drawable> drawables) throws IOException {
        out.writeInt(drawables.size());
        for (Drawable d : drawables) {
            writeDrawable(d);
        }
    }

    public void close() throws IOException {
        out.close();
    }


}