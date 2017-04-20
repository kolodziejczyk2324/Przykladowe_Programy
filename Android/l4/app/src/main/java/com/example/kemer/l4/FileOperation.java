package com.example.kemer.l4;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Kemer on 2017-04-14.
 */

public class FileOperation {


    private static String loadJSON() throws IOException {
        InputStream is = null;
        String json = null;
        is = new FileInputStream("data/data/com.example.kemer.l4/tasks.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");
        return json;
    }

    public static ArrayList<TaskData> getTasksFromFile() {
        ArrayList<TaskData> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(loadJSON());
            for(int i = 0; i<jsonArray.length() ; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                list.add(new TaskData(  jsonObject.getString("title"),
                                        jsonObject.getString("text"),
                                        new MyDate( jsonObject.getInt("day"),
                                                    jsonObject.getInt("month"),
                                                    jsonObject.getInt("year"),
                                                    jsonObject.getInt("hour"),
                                                    jsonObject.getInt("minute")),
                                        Priority.values()[jsonObject.getInt("priority")]));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void writeJsonStream(ArrayList<TaskData> messages){
        File yourFile = new File("data/data/com.example.kemer.l4/tasks.json");
        try {
            yourFile.createNewFile(); // if file already exists will do nothing
            FileOutputStream out = new FileOutputStream(yourFile, false);
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.setIndent("  ");
            writeMessagesArray(writer, messages);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeMessagesArray(JsonWriter writer, ArrayList<TaskData> messages) throws IOException {
        writer.beginArray();
        for (TaskData message : messages) {
            writeMessage(writer, message);
        }
        writer.endArray();
    }

    private static void writeMessage(JsonWriter writer, TaskData message) throws IOException {
        writer.beginObject();
        writer.name("title").value(message.getTitle());
        writer.name("text").value(message.getText());
        writer.name("day").value(message.getDate().getDay());
        writer.name("month").value(message.getDate().getMonthInt());
        writer.name("year").value(message.getDate().getYear());
        writer.name("hour").value(message.getDate().getHour());
        writer.name("minute").value(message.getDate().getMinute());
        writer.name("priority").value(message.getPriority().ordinal());
        writer.endObject();
    }

}
