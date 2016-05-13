package se.chalmers.phrasebook.backend;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import se.chalmers.phrasebook.R;

/**
 * Created by David on 2016-05-02.
 */
public class FileWriter {

    public static boolean saveToFile(Context context, PhraseBookHolder books) {
        boolean status = false;
        if(books==null)
            return false;

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(context.getString(R.string.save_file), Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(books);
            fileOutputStream.close();
            objectOutputStream.close();
            status = true;
        } catch (IOException e) {
            e.printStackTrace();
            status = false;
        }
        return status;
    }

    public static PhraseBookHolder readFromFile(Context context) {
        try {
            FileInputStream fileInputStream = context.openFileInput(context.getString(R.string.save_file));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object object = objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
            if ((object != null) && (object instanceof PhraseBookHolder)) {
                return (PhraseBookHolder) object;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


}
