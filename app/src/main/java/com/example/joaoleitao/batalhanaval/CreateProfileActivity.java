package com.example.joaoleitao.batalhanaval;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Perfil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class CreateProfileActivity extends Activity {

    //String imageFilePath = "/sdcard/temp.png";
    FrameLayout imagePreview;
    CameraPreview camaraPreview;
    Camera camera;
    byte[] photo;
    private Camera.PictureCallback takePhoto = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            photo = bytes;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }


        if (cameraExist()) {
            try {
                camera = Camera.open();
                camera.setDisplayOrientation(90);
                Camera.Parameters cp = camera.getParameters();
                cp.setRotation(90);
                camera.setParameters(cp);
            } catch (Exception e) {
                Toast.makeText(this, R.string.camara_em_utilizacao, Toast.LENGTH_SHORT).show();
            }
        }
        photo = null;

        imagePreview = (FrameLayout) findViewById(R.id.imagePreview);
        camaraPreview = new CameraPreview(this, camera);
        imagePreview.addView(camaraPreview);

    }

    // adicionar o botao "save" canto superior direito
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.menu_guardar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // quando se premite o botao "save" guarda perfil na lista de leaderboards
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int str = R.string.perfil_invalido;
        String strNome = ((EditText) findViewById(R.id.edNome)).getText().toString();
        String imageFilePath = getExternalFilesDir(null) + "/" + strNome + ".png";

        Perfil perfil = new Perfil(strNome, imageFilePath);

        boolean existe = false;
        for (Perfil i : BatalhaNavalApp.perfis)
            if (i.getStrNome().compareTo(perfil.getStrNome()) == 0) {
                existe = true;
                str = R.string.perfil_ja_exite;
                break;
            }
        if (perfil.isvalid() && photo != null && !existe) {

            File pictureFile = new File(perfil.getImagemFundo());
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(photo);
                fos.close();
            } catch (IOException e) {
            }
            photo = null;

            BatalhaNavalApp.addPerfil(perfil, getApplicationContext());
            str = R.string.perfil_adicionado;
        }


        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void onCapturarImagem(View v) {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        camera.takePicture(null, null, takePhoto);
    }


    public boolean cameraExist() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        else
            return false;
    }


}
