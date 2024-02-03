package io.plugin.pytorch.capacitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.util.Base64;
import androidx.activity.result.ActivityResult;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

@CapacitorPlugin(name = "CapTorch")
public class CapTorchPlugin extends Plugin {

    private CapTorch implementation = new CapTorch();

    // @Override
    // protected void load() {
    //     super.load();
    //     // Add any application logic here. This is called when the app
    //     // is loaded and the plugin starts up.
    // }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void loadImage(PluginCall call) {
        // implementation.loadImage();
        // call.resolve(ret);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        // // intent.EXTRA_ALLOW_MULTIPLE = false;
        // ResolveInfo info = resolveActivity(intent, "imagePickResult");
        startActivityForResult(call, intent, "imagePickResult");
    }

    private void imagePickFailed(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("name", "");
        ret.put("data", "");
        ret.put("mimeType", "");
        call.resolve(ret);
    }

    @ActivityCallback
    protected void imagePickResult(PluginCall call, ActivityResult result) {
        // check that all is good
        if (call == null || result.getResultCode() != Activity.RESULT_OK) {
            imagePickFailed(call);
        }

        // some prep
        Intent intent = result.getData();
        Uri url = intent.getData();
        Context content = getContext();

        // load ze image
        try {
            Bitmap bitmap = Images.Media.getBitmap(content.getContentResolver(), url);

            // convert image to webp
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 100, out);
            byte[] imageBytes = out.toByteArray();
            out.close();
            String imgString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            // send data to frontend
            JSObject ret = new JSObject();
            ret.put("name", url.getPath());
            ret.put("data", imgString);
            ret.put("mimeType", "image/webp");
            call.resolve(ret);
        } catch (FileNotFoundException e) {
            imagePickFailed(call);
        } catch (IOException e) {
            imagePickFailed(call);
        }
    }
}
