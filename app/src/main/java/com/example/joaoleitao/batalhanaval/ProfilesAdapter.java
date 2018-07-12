package com.example.joaoleitao.batalhanaval;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joaoleitao.batalhanaval.BatalhaNaval.Perfil;

import java.util.List;

class ProfilesAdapter extends BaseAdapter{
    private List<Perfil> list;
    private Context context;

    public ProfilesAdapter(Context c, List<Perfil> list) {
        context = c;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Perfil perfil = list.get(i);
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.listitem_perfis, null);
        }
        TextView tvData = (TextView) view.findViewById(R.id.tvData);
        TextView tvName = (TextView) view.findViewById(R.id.tvNome);
        ImageView imageView = (ImageView) view.findViewById(R.id.imagePreview);

        tvName.setText(perfil.getStrNome());
        tvData.setText(context.getString(R.string.wins) + perfil.getWins()
                + context.getString(R.string.defeats) + perfil.getDefeats());
        utils.setPic(imageView, perfil.getImagemFundo(),context);


        return view;
    }

}
