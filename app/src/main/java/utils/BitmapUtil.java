package utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapUtil {
    public static Bitmap fromRes(String name) {
        int id = ResourceUtil.getId(name, "drawable");
        return BitmapFactory.decodeResource(AppContext.getAppContext().getResources(), id);
    }

    public static Bitmap fromRes(String name, float xScale, float yScale) {
        Bitmap bmp = fromRes(name);
        Matrix matrix = new Matrix();
        matrix.postScale(xScale, yScale);
        return Bitmap.createBitmap(bmp,
                0, 0, bmp.getWidth(), bmp.getHeight(),
                matrix,
                true);
    }
}
