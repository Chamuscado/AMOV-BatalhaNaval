package com.example.joaoleitao.batalhanaval;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Game;

public class PrepareGameActivity extends Activity implements BoardFragment.OnFragmentInteractionListener {

    BoardFragment boardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_game);
        BatalhaNavalApp.game = new Game(this);
        startFragment();
    }


    private void startFragment() {
        boardFragment = BoardFragment.newInstance(BatalhaNavalApp.game.getShipsBoard());
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.BoardContainer, boardFragment);
        fragmentTransaction.commit();

        //ImageView fotoPlayer1 = (ImageView) findViewById(R.id.fotoPlayer1);
        //ImageView fotoPlayer2 = (ImageView) findViewById(R.id.fotoPlayer2);
        //TextView player1 = (TextView) findViewById(R.id.player1);
        //TextView player2 = (TextView) findViewById(R.id.player2);

        //Player[] players = BatalhaNavalApp.game.getplayeres();
        //player1.setText(players[0].getPerfil().getStrNome());
        //player2.setText(players[1].getPerfil().getStrNome());

        //utils.setPic(fotoPlayer1, players[0].getPerfil().getImagemFundo(), getApplicationContext());
        //utils.setPic(fotoPlayer2, players[1].getPerfil().getImagemFundo(), getApplicationContext());

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(getApplicationContext(), "Not Done", Toast.LENGTH_SHORT).show();
    }

    public void onStartGame(View view) {
        BatalhaNavalApp.game.getShipsBoard().setReady();
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

    }
}
