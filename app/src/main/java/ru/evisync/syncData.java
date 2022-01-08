package ru.evisync;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;

public class syncData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync_data);

        File storageDirectory = Environment.getExternalStorageDirectory();

        Collection<File> files = FileUtils.listFiles(storageDirectory, new String[]{"mp4"}, true);

        for (File file : files) {
            // todo: make insert founded file in archive
        }

    }

}