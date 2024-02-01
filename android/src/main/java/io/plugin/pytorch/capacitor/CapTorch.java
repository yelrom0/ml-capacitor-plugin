package io.plugin.pytorch.capacitor;

import { CapacitorHttp } from '@capacitor/core';
import { Filesystem, Directory, Encoding } from '@capacitor/filesystem';

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.pytorch.LiteModuleLoader;
import org.pytorch.Module;

public class CapTorch {

    Bitmap image = null;
    Module model = null;

    public String downloadModel() {
        
        // Download the model
        CapacitorHttp.request({
            method: 'GET',
            url: 'https://github.com/pytorch/android-demo-app/blob/master/HelloWorldApp/app/src/main/assets/model.pt'
        }).then((response) => {
            // Save the model to storage
            Filesystem.writeFile({
                path: "model.pt",
                data: response.data,
                directory: Directory.Data
            });

            // And this object
            this.model = LiteModuleLoader.load(response.data);

            return ""
        }).catch((err) => {
            // Handle error
            return err
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add any application logic here. This is called when the app
        // is loaded and the plugin starts up.

        // Check if the model is in storage, if not, download it
        Filesystem.readFile({
            path: "model.pt"
        }).then((file) => {
            // Load the model
            this.model = LiteModuleLoader.load(file);
        }).catch((err) => {
            // Download the model
            this.downloadModel();
        });
    }

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }

    public void loadImage(String imagePath) {
        const file = await Filesystem.readFile({
            path: imagePath
        });
        Bitmap image = BitmapFactory.decodeFile(file);
    }
}
