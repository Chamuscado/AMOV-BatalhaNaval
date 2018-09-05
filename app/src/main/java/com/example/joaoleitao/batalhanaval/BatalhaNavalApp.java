package com.example.joaoleitao.batalhanaval;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Game;
import com.example.joaoleitao.batalhanaval.BatalhaNaval.Perfil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BatalhaNavalApp extends Application{
    private static final String PERFIS = "perfis.dat";
    public static Game game;
    public static Perfil perfilSelecionado;
    public static List<Perfil> perfis;
    public static ObjectOutputStream out;
    private static Socket gameSocket;
    private static Context context;

    public static Socket getGameSocket() {
        return gameSocket;
    }

    public static void setGameSocket(Socket gameSocket) {
        BatalhaNavalApp.gameSocket = gameSocket;

        try {
            out = new ObjectOutputStream(BatalhaNavalApp.gameSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        BatalhaNavalApp.context = context;
    }

    public static Perfil getPerfilSelecionado() {
        return perfilSelecionado;
    }

    public static void setPerfilSelecionado(Perfil perfilSelecionado) {
        BatalhaNavalApp.perfilSelecionado = perfilSelecionado;
    }

    public static void savePerfis(Context context) {
        if (BatalhaNavalApp.context == null)
            BatalhaNavalApp.context = context;
        try {
            FileOutputStream fos = new FileOutputStream(new File(
                    context.getExternalFilesDir(null) + "/" + PERFIS));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(perfis);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void lerPerfis(Context context) {
        if (BatalhaNavalApp.context == null)
            BatalhaNavalApp.context = context;
        try {
            FileInputStream fis = new FileInputStream(new File(
                    context.getExternalFilesDir(null) + "/" + PERFIS));
            ObjectInputStream ois = new ObjectInputStream(fis);
            perfis = (ArrayList<Perfil>) ois.readObject();
            fis.close();
            if (perfis == null)
                perfis = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            perfis = new ArrayList<>();
        }

    }

    @Deprecated
    public static Bitmap getImage(Perfil perfil) {
        Bitmap bitmap = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                    new File(context.getExternalFilesDir(null) + "/"
                            + perfil.getImagemFundo())));
            bitmap = (Bitmap) in.readObject();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static void addPerfil(Perfil perfil, Context context) {
        perfis.add(perfil);
        savePerfis(context);
    }

    public static void endSocket() {
        try {
            gameSocket.close();
            out.close();
          //  game.removeRemote();
        } catch (IOException e) {
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        lerPerfis(getApplicationContext());
    }
}
