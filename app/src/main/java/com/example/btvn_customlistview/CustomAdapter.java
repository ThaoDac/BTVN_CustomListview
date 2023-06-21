package com.example.btvn_customlistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Account> implements Serializable {
    private Context context;
    private int resource;
    private ArrayList<Account> arrAccount;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Account> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.arrAccount=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.listitem_account,parent,false);

        ImageView img=convertView.findViewById(R.id.imgAva);
        TextView tvName=convertView.findViewById(R.id.tvName);
        TextView tvPhone=convertView.findViewById(R.id.tvPhone);
        TextView tvEmail=convertView.findViewById(R.id.tvEmail);

        Account acc=arrAccount.get(position);
        img.setImageURI(acc.getPathImg());
        tvName.setText(acc.getsName());
        tvPhone.setText(acc.getsPhone());
        tvEmail.setText(acc.getsEmail());

        return convertView;
    }
}
