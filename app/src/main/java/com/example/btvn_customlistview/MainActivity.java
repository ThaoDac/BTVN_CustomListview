package com.example.btvn_customlistview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.security.AccessControlContext;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {
    private ListView lvAccount;
    private final int UPDATE_INFO_CODE = 111;
    private ArrayList<Account> arrAccount;
    private CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getID();
        getListAccount();
        lvAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = lvAccount.getItemAtPosition(position).toString();
                Intent intent = new Intent(MainActivity.this, SettingAccount.class);
                intent.putExtra("name",arrAccount.get(position).getsName());
                intent.putExtra("phone",arrAccount.get(position).getsPhone());
                intent.putExtra("email",arrAccount.get(position).getsEmail());
                intent.putExtra("position", position);
                startActivityForResult(intent, UPDATE_INFO_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_INFO_CODE && resultCode !=RESULT_CANCELED && data != null) {
            String name=data.getStringExtra("new_name");
            String phone=data.getStringExtra("new_phone");
            String mail=data.getStringExtra("new_mail");
            String image=data.getStringExtra("new_image");
            int position=data.getIntExtra("position",0);

            arrAccount.get(position).setPathImg(Uri.parse(image));
            arrAccount.get(position).setsName(name);
            arrAccount.get(position).setsPhone(phone);
            arrAccount.get(position).setsEmail(mail);

            adapter.notifyDataSetChanged();
        }
    }

    public void getID() {
        lvAccount = findViewById(R.id.lvAccount);
    }

    public void getListAccount() {
        arrAccount = new ArrayList<Account>();
        Account acc1 = new Account( "Nguyễn Văn A", "094934838434", "abcxxxx@gmail.com");
        Account acc2 = new Account("Nguyễn Văn B", "034939483854", "dvbdxxx@gmail.com");
        Account acc3 = new Account("Nguyễn Văn C", "093483847723", "dqwezxxx@gmail.com");
        Account acc4 = new Account("Nguyễn Văn D", "039238284824", "drvewxxx@gmail.com");

        arrAccount.add(acc1);
        arrAccount.add(acc2);
        arrAccount.add(acc3);
        arrAccount.add(acc4);

        adapter = new CustomAdapter(this, R.layout.listitem_account, arrAccount);
        lvAccount.setAdapter(adapter);
    }
}