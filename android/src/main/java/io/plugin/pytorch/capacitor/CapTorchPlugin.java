package io.plugin.pytorch.capacitor;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "CapTorch")
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
        String imagePath = call.getString("imagePath");

        JSObject ret = new JSObject();
        ret.put("imagePath", implementation.loadImage(imagePath));
        call.resolve(ret);
    }
}
