/*
 *
 *   Creado por Universidad Politécnica de Tlaxcala en 11/10/21, 4:04 PM
 *   Copyright Ⓒ 2021. Todos los derechos reservados Ⓒ 2021 http://uptlax.edu.mx/
 *   Last modified: 11/10/21, 4:04 PM
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

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

import java.io.ByteArrayOutputStream;

import android.os.Handler;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

public class ScanHandler extends Handler {

    private static final double DEFAULT_ZOOM = 1.0;
    private final CameraOperation cameraOperation;
    private final HandlerThread decodeThread;
    private final android.os.Handler decodeHandle;
    private final Activity activity;
    public HmsScan[] rawFinalResult;

    public TextView titleResult;
    public CardView titleBg;
    public ImageView scanSkin;

    public ScanHandler(final Activity activity, CameraOperation cameraOperation) {
        this.cameraOperation = cameraOperation;
        this.activity = activity;

        decodeThread = new HandlerThread("DecodeThread");
        decodeThread.start();
        decodeHandle = new android.os.Handler(decodeThread.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                if (msg == null) return;
                HmsScan[] result = decodeSyn(msg.arg1, msg.arg2, (byte[]) msg.obj, activity, HmsScan.QRCODE_SCAN_TYPE);

                if (result == null || result.length == 0) {
                    ScanActivity currScanActivity = (ScanActivity) activity;
                    clearScanningStatus(currScanActivity);
                    restart(DEFAULT_ZOOM);
                } else if (TextUtils.isEmpty(result[0].getOriginalValue()) && result[0].getZoomValue() != 1.0) {
                    restart(result[0].getZoomValue());
                } else if (!TextUtils.isEmpty(result[0].getOriginalValue())) {
                    Message message = new Message();
                    message.what = msg.what;
                    message.obj = result;
                    ScanHandler.this.sendMessage(message);
                    restart(DEFAULT_ZOOM);
                } else {
                    restart(DEFAULT_ZOOM);
                }
            }
        };
        cameraOperation.startPreview();
        restart(DEFAULT_ZOOM);
    }

    /**
     * Call the MultiProcessor API in synchronous mode.
     */
    private HmsScan[] decodeSyn(int width, int height, byte[] data, final Activity activity, int type) {
        Bitmap bitmap = convertToBitmap(width, height, data);
        HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(type).setPhotoMode(false).create();
        return ScanUtil.decodeWithBitmap(activity, bitmap, options);
    }

    private Bitmap convertToBitmap(int width, int height, byte[] data) {
        YuvImage yuv = new YuvImage(data, ImageFormat.NV21, width, height, null);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        yuv.compressToJpeg(new Rect(0, 0, width, height), 100, stream);
        return BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().length);
    }

    @Override
    public void handleMessage(Message message) {
        removeMessages(1);
        if (message.what == 0) {
            ScanActivity currScanActivity = (ScanActivity) activity;
            rawFinalResult = (HmsScan[]) message.obj; // Resultado del código obtenido
            updateScanningStatus(rawFinalResult[0].getOriginalValue(), currScanActivity);
        }
    }

    // Actualizar el estado del escáner
    public void updateScanningStatus(String result, ScanActivity scanActivityResult) {
        titleResult = scanActivityResult.findViewById(R.id.scan_status);
        titleBg = scanActivityResult.findViewById(R.id.scan_status_bg);
        scanSkin = scanActivityResult.findViewById(R.id.scan_area);

        System.out.println("Result switch: " + validateStatus(result));
        switch (validateStatus(result)){
            case 1: // Caso válido
                titleResult.setText("Puede pasar");
                titleResult.setTextColor(scanActivityResult.getResources().getColor(R.color.white_s));
                titleBg.setCardBackgroundColor(scanActivityResult.getResources().getColor(R.color.green_dark_s));
                scanSkin.setBackground(scanActivityResult.getResources().getDrawable(R.drawable.cloors_green));
                break;
            case 2: // Caso verificar
                titleResult.setText("Verifica la identidad");
                titleResult.setTextColor(scanActivityResult.getResources().getColor(R.color.white_s));
                titleBg.setCardBackgroundColor(scanActivityResult.getResources().getColor(R.color.gold_light_s));
                scanSkin.setBackground(scanActivityResult.getResources().getDrawable(R.drawable.cloors_yellow));
                break;
            case 3: // Caso no válido
                titleResult.setText("Denegado");
                titleResult.setTextColor(scanActivityResult.getResources().getColor(R.color.white_s));
                titleBg.setCardBackgroundColor(scanActivityResult.getResources().getColor(R.color.red_light_s));
                scanSkin.setBackground(scanActivityResult.getResources().getDrawable(R.drawable.cloors_red));
                break;
            default:
                Toast.makeText(scanActivityResult, "Error de escáner", Toast.LENGTH_SHORT).show();
        }
    }

    // Limpiar el estado del escáner
    public void clearScanningStatus(ScanActivity scanActivityResult) {
        titleResult = scanActivityResult.findViewById(R.id.scan_status);
        titleBg = scanActivityResult.findViewById(R.id.scan_status_bg);
        scanSkin = scanActivityResult.findViewById(R.id.scan_area);

        titleResult.setText("Esperando QR...");
        titleResult.setTextColor(scanActivityResult.getResources().getColor(R.color.dark_s));
        titleBg.setCardBackgroundColor(scanActivityResult.getResources().getColor(R.color.white_s));
        scanSkin.setBackground(scanActivityResult.getResources().getDrawable(R.drawable.cloors_white));
    }

    // Verificar si el QR es válido, necesita verificación o no es válido
    public int validateStatus(String result){
        System.out.println("Result: " + result);
        if(result.equals("valido")) return 1;
        if(result.equals("verificar")) return 2;
        else return 3;
    }

    public void quit() {
        try {
            cameraOperation.stopPreview();
            decodeHandle.getLooper().quit();
            decodeThread.join(500);
        } catch (InterruptedException ignored) {

        }
    }

    public void restart(double zoomValue) {
        cameraOperation.callbackFrame(decodeHandle, zoomValue);
    }

}
