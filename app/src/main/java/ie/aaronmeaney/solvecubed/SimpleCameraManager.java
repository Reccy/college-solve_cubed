package ie.aaronmeaney.solvecubed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.net.Uri;
import android.provider.Settings;
import android.view.Surface;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Wrapper class to return objects related to the camera.
 * Uses camera2 api.
 */
class SimpleCameraManager {

    private Context context;

    private AlertDialog securityAlertDialog;

    SimpleCameraManager(Context context) {
        this.context = context;
    }

    /**
     * Returns the ID of the back camera of the device
     * @return String of the camera ID. "-1" if the camera was not found.
     */
    String getBackCameraId()
    {
        // Get instance of the device camera manager
        CameraManager cameraManager = context.getSystemService(CameraManager.class);

        // Get list of cameras on the device
        String[] cameraIdList;
        try {
            cameraIdList = cameraManager.getCameraIdList();

            // Iterate over cameras until a back facing camera is found (LENS_FACING_BACK = 1)
            for (String cameraId : cameraIdList) {
                if (cameraManager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK)
                {
                    return cameraId;
                }
            }
        } catch (android.hardware.camera2.CameraAccessException | java.lang.NullPointerException e) {
            System.out.println("Exception: " + e);
        }

        // Return -1 if the camera couldn't be found
        return "-1";
    }

    /**
     * Streams the output of the camera to the texture
     * @param cameraId The ID of the camera to capture from
     * @param surface The surface to render the camera output to
     */
    void streamCameraToTexture(final String cameraId, final Surface surface) {
        final CameraManager cameraManager = context.getSystemService(CameraManager.class);

        // Setup camera behaviour
        CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(final CameraDevice cameraDevice) {
                try {
                    // When camera is opened, create a new session
                    cameraDevice.createCaptureSession(new ArrayList<>(Arrays.asList(surface)), new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                            // When the session is configured, begin capturing the camera output
                            try {
                                CaptureRequest.Builder reqBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                                reqBuilder.addTarget(surface);
                                CaptureRequest request = reqBuilder.build();

                                cameraCaptureSession.setRepeatingRequest(request, new CameraCaptureSession.CaptureCallback() {
                                    @Override
                                    public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                                        super.onCaptureCompleted(session, request, result);

                                        System.out.println(result.toString());
                                    }
                                }, null);
                            } catch (Exception e) {
                                System.out.println("Exception: " + e);
                            }
                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                            System.out.println("Configuration failed: " + cameraCaptureSession);
                        }
                    }, null);
                } catch (CameraAccessException e) {
                    System.out.println("Exception: " + e);
                }
            }

            @Override
            public void onDisconnected(final CameraDevice cameraDevice) {
                System.out.println("Camera Disconnected");
            }

            @Override
            public void onError(CameraDevice cameraDevice, int i) {
                System.out.println("Camera Error: " + i);
            }
        };

        // Open camera stream
        try {
            cameraManager.openCamera(cameraId, stateCallback, null);
        }
        catch (SecurityException e) {
            // On Security Exception, create alert notifying the user of app permissions error
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("Exception: " + e);
            if (securityAlertDialog == null)
            {
                securityAlertDialog = new AlertDialog.Builder(context).setTitle(R.string._err_security_permissions_title)
                        .setMessage(R.string._err_security_permissions_desc)
                        .setCancelable(false)
                        .setPositiveButton(R.string._btn_security_permissions, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                securityAlertDialog = null;
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", "ie.aaronmeaney.solvecubed", null);
                                intent.setData(uri);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string._btn_return, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                securityAlertDialog = null;
                                Intent intent = new Intent(context.getApplicationContext(), SplashActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                            }
                        })
                        .create();
                securityAlertDialog.show();
            }
        }
        catch (java.lang.NullPointerException|CameraAccessException e) {
            System.out.println("Exception: " + e);
        }
    }
}
