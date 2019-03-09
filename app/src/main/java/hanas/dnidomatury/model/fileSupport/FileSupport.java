package hanas.dnidomatury.model.fileSupport;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import hanas.dnidomatury.model.matura.Exam;

public interface FileSupport <T>{

    static boolean fileExists(final Context context, String fileTitle) {
        File file = context.getFileStreamPath(fileTitle);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    static String getFileTitle(Exam exam, String file_suffix) {
        if (exam != null) return exam.getName()+"_"+exam.getLevel()+"_"+exam.getType()+file_suffix;
        else return file_suffix;
    }

    class FileReader<T> implements Runnable {
        private volatile T listFromFile;
        private final Context context;
        private final String fileTitle;

        public FileReader(Context context, String fileTitle) {
            this.context = context;
            this.fileTitle = fileTitle;
        }

        @Override
        public void run() {
            try {
                if (fileExists(context, fileTitle)) {
                    FileInputStream fileIn = context.openFileInput(fileTitle);
                    ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                    listFromFile = (T) objectIn.readObject();
                    objectIn.close();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public T getValue() {
            return listFromFile;
        }
    }

    static <T> T fromFile(final Context context, String fileTitle) {

        FileReader<T> fileReader = new FileReader<>(context, fileTitle);
        Thread thread = new Thread(fileReader);
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return fileReader.getValue();
    }

    default void toFile(final Context context, final String fileTitle) {
        new Thread(() -> {
            try {
                FileOutputStream fileOut = context.openFileOutput(fileTitle, Context.MODE_PRIVATE);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(this);
                objectOut.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).run();
    }

}