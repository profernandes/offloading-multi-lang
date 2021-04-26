package com.example.matrixgrpc;

//import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MatrixUtils {

    /*public static void writeToFile(String data, Context context, String filename) {
        //String path = Environment.getRootDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + File.separator + "yourFolder";
        //String path = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + File.separator + "yourFolder";
        //String path = Environment.getExternalStorageDirectory() + File.separator  + "yourFolder";
        String path = context.getExternalCacheDir().getAbsolutePath();

        System.out.println(path);

        // Create the folder.
        File folder = new File(path);
        folder.mkdirs();

        File file = new File(folder, filename);

        try {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.flush();
            fOut.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }*/

    public static void sendToServer(String msg, String ipServer, int portServer) {
        try {
            Socket socket = new Socket(ipServer, portServer);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(msg);
            socket.close();
        }
        catch (IOException e) {
            Log.d("DEBUG ERROR", e.toString());
        }
    }
}
