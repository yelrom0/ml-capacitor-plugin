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
    public String loadImage(PluginCall call) {
        implementation.loadImage();
        // call.resolve(ret);
    }

    @ActivityCallback
    protected void imagePickResult(ActivityResult result) {
        if (call == null) {
            return;
        }

        if (result.getResultCode() == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();

            // Load the image
            this.image = BitmapFactory.decodeFile(imagePath);

            // Write the image to a parcel
            Parcel parcel = Parcel.obtain();
            this.image.writeToParcel(parcel, 0);
            Byte[] bArr = parcel.createByteArray();
            String imageString = Base64.encodeToString(bArr, Base64.DEFAULT);

            // Return the image
            JSObject ret = new JSObject();
            ret.put("image", imageString);
            notifyListeners("imagePickResult", ret);
        }
        // Return a blank string
        JSObject ret = new JSObject();
        ret.put("image", "");
        notifyListeners("imagePickResult", ret);
    }
}
