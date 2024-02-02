package io.plugin.pytorch.capacitor;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;


@CapacitorPlugin(name = "CapTorch", permissions = { @Permission(alias = "storage"), strings = { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE } }
public class CapTorchPlugin extends Plugin {

    private CapTorch implementation = new CapTorch();


    @Override
    protected void load() {
        super.load();
        // Add any application logic here. This is called when the app
        // is loaded and the plugin starts up.
    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void loadImage(PluginCall call) {
        implementation.loadImage();
        // call.resolve(ret);
    }

    private void imagePickFailed() {
        JSObject ret = new JSObject();
        ret.put("name", "");
        ret.put("data", "");
        ret.put("mimeType", "");
        notifyListeners("imagePickResult", ret);
    }

    @ActivityCallback
    protected void imagePickResult(ActivityResult result) {
        if (call == null) {
            imagePickFailed();
            return;
        }

        if (result.getResultCode() == RESULT_OK) {
            // create the return object
            JSObject ret = new JSObject();

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();

            // Load the image
            implementation.image = BitmapFactory.decodeFile(imagePath);
            JSObject ret = new JSObject();

            // convert to webp
            OutputStream baos = new ByteArrayOutputStream(imagePath);
            boolean succeeded = implementation.image.compress(Bitmap.CompressFormat.WEBP_LOSSY, 100, baos);
            if (!succeeded) {
                Log.e("CapTorch", "Failed to convert image to webp");
                imagePickFailed();
                return;
            }

            // convert the image to a base64 string
            ByteArray imageByteArray = baos.toByteArray();
            String imageString = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

            // Return the image
            ret.put("name", imagePath);
            ret.put("data", imageString);
            ret.put("mimeType", "image/webp");
            notifyListeners("imagePickResult", ret);
        }
        // Return a blank string
        imagePickFailed();
    }
}
