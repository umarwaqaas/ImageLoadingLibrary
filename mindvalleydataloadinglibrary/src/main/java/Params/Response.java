package Params;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Umer Waqas on 12/08/2017.
 */
public class Response {
    private int code;
    private InputStream data;

    public int getCode() {
        return code;
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    public InputStream getData() {
        return data;
    }

    public Response setData(InputStream data) {
        this.data = data;
        return this;
    }

    /**
     * Reads an InputStream and make it String.
     * @return String
     * @throws IOException Exception
     */
    public String getDataAsString() throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(data, "UTF-8");
      //  BufferedReader r = new BufferedReader(new InputStreamReader(data));
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
//        for (String line; (line = r.readLine()) != null; ) {
//            out.append(line).append('\n');
//        }
        if (data != null) {
            data.close();
        }
        return out.toString();

    }

    /**
     * Converts input Stream to bitmap
     * @return Bitmap
     */
    public Bitmap getAsBitmap() {
        Bitmap bitmap = BitmapFactory.decodeStream(this.data);
        if (data != null) {
            try {
                data.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
