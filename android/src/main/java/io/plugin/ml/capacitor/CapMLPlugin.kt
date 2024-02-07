package io.plugin.ml.capacitor

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.activity.result.ActivityResult
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.ActivityCallback
import com.getcapacitor.annotation.CapacitorPlugin
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException

@CapacitorPlugin(name = "CapML")
class CapMLPlugin : Plugin() {
    private val implementation = CapML()

    // @Override
    // protected void load() {
    //     super.load();
    //     // Add any application logic here. This is called when the app
    //     // is loaded and the plugin starts up.
    // }
    @PluginMethod
    fun echo(call: PluginCall) {
        val value = call.getString("value")
        val ret = JSObject()
        ret.put("value", implementation.echo(value!!))
        call.resolve(ret)
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    fun loadImage(call: PluginCall) {
        val intent = Intent(Intent.ACTION_GET_CONTENT, null)
        intent.setType("image/*")
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)

        // store the call to be reused
        call.setKeepAlive(true)

        // activate the picker which should activate the callback
        startActivityForResult(call, intent, "imagePickResult")
    }

    @ActivityCallback
    protected fun imagePickResult(call: PluginCall?, result: ActivityResult) {
        Log.i("Image Loading", "In Callback")
        // check that all is good
        if (call == null) {
            Log.e("ImageLoading", "PluginCall object is null")
            return
        } else if (result.resultCode != Activity.RESULT_OK) {
            implementation.imageProcessingFailed(call, "ImageLoading", "result was not okay")
            return
        }

        // some prep
        val intent = result.data
        val url = intent!!.data
        val content = context

        // load ze image
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(content.contentResolver, url)

            // convert image to webp
            val out = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 100, out)
            val imageBytes = out.toByteArray()
            val imgString = Base64.encodeToString(imageBytes, Base64.DEFAULT)

            // send data to frontend
            val imgPath = url!!.path
            val data = JSObject()
            data.put("name", imgPath)
            data.put("data", imgString)
            data.put("mimeType", "image/webp")
            val ret = JSObject()
            ret.put("id", imgPath)
            ret.put("type", "image")
            ret.put("data", data)
            Log.i("ImageLoading", "About to return from callback")
            Log.i("ImageLoading", "name: $imgPath")
            // Log.i("ImageLoading", "data: " + imgString); this is commented out as the data is too large
            call.resolve(ret)
            Log.i("ImageLoading", "Calling AI model")
            implementation.runTorchBitmapClassify(call, bitmap, content)
        } catch (e: FileNotFoundException) {
            implementation.imageProcessingFailed(call, "ImageLoading", "Image loading error: $e")
        } catch (e: NullPointerException) {
            implementation.imageProcessingFailed(call, "ImageLoading", "No url received for image$e")
        } catch (e: IOException) {
            implementation.imageProcessingFailed(call, "ImageLoading", "Image loading error: $e")
        }
    }
}
