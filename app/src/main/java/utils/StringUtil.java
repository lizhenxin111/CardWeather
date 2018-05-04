package utils;

import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtil {
    public static String fromInputStream(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        try {
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getText(TextView tv) {
        return tv.getText().toString();
    }


    public static boolean hasNull(String... strs) {
        boolean result = false;
        for (String s : strs) result |= StringUtils.isEmpty(s);
        return result;
    }
}
