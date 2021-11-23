/*
 *
 *   Creado por Universidad Politécnica de Tlaxcala en 11/10/21, 3:43 PM
 *   Copyright Ⓒ 2021. Todos los derechos reservados Ⓒ 2021 http://uptlax.edu.mx/
 *   Last modified: 11/10/21, 3:43 PM
 *
 *   Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 *   except in compliance with the License. You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *    either express or implied. See the License for the specific language governing permissions and
 *    limitations under the License.
 * /
 */

package com.example.proyectocarasuptx;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.List;

public class CameraOperation {
    private Camera cam = null;
    private Camera.Parameters parameters = null;
    private boolean isPreview = false;
    private final FrameCallback frameCallback = new FrameCallback();

    public synchronized void open(SurfaceHolder holder) throws IOException {
        int camId = 0;
        int width = 1920;
        int height = 1080;

        cam = Camera.open(camId);
        parameters = cam.getParameters();
        parameters.setPictureSize(width, height);
        parameters.setPictureFormat(ImageFormat.NV21);
        cam.setPreviewDisplay(holder);
        cam.setDisplayOrientation(90);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

        cam.setParameters(parameters);
    }

    public synchronized void close() {
        if (cam != null) {
            cam.release();
            cam = null;
        }
    }

    public synchronized void startPreview() {
        if (cam != null && !isPreview) {
            cam.startPreview();
            isPreview = true;
        }
    }

    public synchronized void stopPreview() {
        if (cam != null && isPreview) {
            cam.stopPreview();
            frameCallback.setProperties(null);
            isPreview = false;
        }
    }

    public synchronized void callbackFrame(Handler handler, double zoomValue) {
        if (cam != null && isPreview) {
            frameCallback.setProperties(handler);
            double defaultZoom = 1.0;
            if (cam.getParameters().isZoomSupported() && zoomValue != defaultZoom) {
                //Auto zoom.
                parameters.setZoom(convertZoomInt(zoomValue));
                cam.setParameters(parameters);
            }
            cam.setOneShotPreviewCallback(frameCallback);
        }
    }

    public int convertZoomInt(double zoomValue) {
        List<Integer> allZoomRatios = parameters.getZoomRatios();
        float maxZoom = Math.round(allZoomRatios.get(allZoomRatios.size() - 1) / 100f);
        if (zoomValue >= maxZoom) {
            return allZoomRatios.size() - 1;
        }
        for (int i = 1; i < allZoomRatios.size(); i++) {
            if (allZoomRatios.get(i) >= (zoomValue * 100) && allZoomRatios.get(i - 1) <= (zoomValue * 100)) {
                return i;
            }
        }
        return -1;
    }

    static class FrameCallback implements Camera.PreviewCallback {

        private Handler handler;

        public void setProperties(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onPreviewFrame(byte[] data, Camera cam) {
            if (handler != null) {
                Message message = handler.obtainMessage(0, cam.getParameters().getPreviewSize().width, cam.getParameters().getPreviewSize().height, data);
                message.sendToTarget();
                handler = null;
            }
        }
    }

}
