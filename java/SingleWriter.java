import java.io.IOException;
import java.lang.String;
import java.io.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.lang.System;

public class SingleWriter {
    private static SingleWriter instance = null;
    private static PrintWriter out;
    private static boolean flag = false;

    private SingleWriter() {
    }

    public void Close() {
        out.close();
    }

    public static SingleWriter getInstance(String fileName) throws IOException {
        if (instance == null) {
            instance = new SingleWriter();
        }
        out = new PrintWriter(fileName, "UTF-8");
        return instance;
    }

    public void Show(String buf) throws IOException {
        if (flag)
            out.print(buf + " ");
    }

    public void Show(int buf) throws IOException {
        if (flag) {
            out.print(buf);
            out.print(" ");
        }
    }

    public void NewLine() throws IOException {
        if (flag)
            out.println();
    }


}