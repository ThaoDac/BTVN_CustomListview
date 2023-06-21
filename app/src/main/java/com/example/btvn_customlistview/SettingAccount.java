package com.example.btvn_customlistview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;

public class SettingAccount extends AppCompatActivity implements Serializable {
    private ImageView imgAva;
    private EditText edName;
    private EditText edPhone;
    private EditText edMail;
    private Button btnUpdate;
    private final int PICK_IMAGE=112;
    private String path_image;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_account);
        getID();

        Intent intent=getIntent();

        String name=intent.getStringExtra("name");
        String phone=intent.getStringExtra("phone");
        String email=intent.getStringExtra("email");
        int position=intent.getIntExtra("position",0);


        edName.setText(name);
        edPhone.setText(phone);
        edMail.setText(email);
        imgAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edName.getText().toString();
                String phone=edPhone.getText().toString();
                String mail=edMail.getText().toString();
                String uriStr = uri.toString();

                Intent iBack=new Intent();
                iBack.putExtra("new_image",uriStr);
                iBack.putExtra("new_name",name);
                iBack.putExtra("new_phone",phone);
                iBack.putExtra("new_mail",mail);
                iBack.putExtra("position",position);
                setResult(RESULT_OK,iBack);
                finish();
//                startActivity(iBack);
            }
        });

    }
    public void getID(){
        imgAva=findViewById(R.id.imgUptAva);
        edName=findViewById(R.id.edName);
        edPhone=findViewById(R.id.edPhone);
        edMail=findViewById(R.id.edEmail);
        btnUpdate=findViewById(R.id.btnUpt);
    }
    public void openGallery(){
//        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, 123);

        if (Build.VERSION.SDK_INT <19){
            Intent intent = new Intent();
            intent.setType("*/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(intent, PICK_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==PICK_IMAGE && requestCode!=RESULT_CANCELED && data!=null){
//            Uri uri=data.getData();
//            path_image=getPathFromUri(uri);
//            Bitmap bImage= BitmapFactory.decodeFile(path_image);
//            imgAva.setImageBitmap(bImage);
//        }
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null){
            uri = data.getData();
            imgAva.setImageURI(uri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getApplicationContext().getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }
    }
    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }

        return uri.getPath();
    }
}