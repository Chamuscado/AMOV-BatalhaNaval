package com.example.joaoleitao.batalhanaval;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Perfil;

import java.io.File;

public class ProfilesActivity extends Activity {


    ListView lstView;
    ProfilesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1234);
        }

        lstView = (ListView) findViewById(R.id.lstPerfis);
        adapter = new ProfilesAdapter(getApplicationContext(), BatalhaNavalApp.perfis);
        lstView.setAdapter(adapter);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BatalhaNavalApp.perfilSelecionado = BatalhaNavalApp.perfis.get(i);
            }
        });
        lstView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder adb = new AlertDialog.Builder(ProfilesActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage(getString(R.string.msgToDeletePerfil) + "\""
                        + BatalhaNavalApp.perfis.get(i).getStrNome() + "\"");
                adb.setNegativeButton(R.string.cancel, null);
                adb.setPositiveButton(R.string.confirm, new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Perfil perfil = BatalhaNavalApp.perfis.remove(i);
                        BatalhaNavalApp.savePerfis(getApplicationContext());
                        File pictureFile = new File(perfil.getImagemFundo());
                        if (!pictureFile.delete())
                            Log.d("files", "Erro ao apagar imagem");
                        adapter.notifyDataSetChanged();
                    }
                });
                adb.show();
                return false;
            }
        });
    }


    // adicionar o botao "+" canto superior direito
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.menu_criar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // quando se premite o botao "+" vai para a activity de criacao de novo perfil
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, CreateProfileActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}
