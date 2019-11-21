package com.example.kanikanavbar.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.kanikanavbar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadActivity extends AppCompatActivity {

    Button chF, upF, postB;
    ImageView img;
    EditText comp, phone;
    StorageReference mStorageRef;
    public Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mStorageRef= FirebaseStorage.getInstance().getReference("Images");

        chF= (Button) findViewById(R.id.chooseF);
        upF= (Button) findViewById(R.id.uploadImg);
        postB= (Button) findViewById(R.id.postbtn);

        img= (ImageView) findViewById(R.id.sparePartImg);
        comp= (EditText) findViewById(R.id.compName);
        phone= (EditText) findViewById(R.id.phoneNumber);

        chF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FileChooser();
            }
        });

        upF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fileuploader();
            }
        });
    }

    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Fileuploader(){

        StorageReference Ref= mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imgUri));

        Ref.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(UploadActivity.this, "Image uploaded successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }


    private void FileChooser(){
        Intent intent = new Intent();
        intent.setType("image/'");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode== RESULT_OK && data != null && data.getData() != null){
            imgUri= data.getData();
            img.setImageURI(imgUri);
        }
    }

}
