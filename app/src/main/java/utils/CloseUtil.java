package utils;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil {
    public static void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
