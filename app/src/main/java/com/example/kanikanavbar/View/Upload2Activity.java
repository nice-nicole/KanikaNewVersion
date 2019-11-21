//package com.example.kanikaapp.View;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.webkit.MimeTypeMap;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.example.kanikaapp.Model.SparePart;
//import com.example.kanikaapp.R;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.OnProgressListener;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.StorageTask;
//import com.google.firebase.storage.UploadTask;
//import com.squareup.picasso.Picasso;
//
//public class Upload2Activity extends AppCompatActivity {
//
//    private static final int PICK_IMAGE_REQUEST = 1;
//
//    private Button chooseImageBtn;
//    private Button uploadBtn;
//    private EditText nameEditText;
//    private EditText descriptionEditText;
//    private ImageView chosenImageView;
//    private ProgressBar uploadProgressBar;
//
//    private Uri mImageUri;
//
//    private StorageReference mStorageRef;
//    private DatabaseReference mDataBaseRef;
//
//    private StorageTask mUploadTask;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upload2);
//
//        chooseImageBtn = findViewById(R.id.button_choose_image);
//        uploadBtn = findViewById(R.id.uploadBtn);
//        nameEditText = findViewById(R.id.nameEditText);
//        descriptionEditText = findViewById(R.id.descriptionEditText);
//        chosenImageView = findViewById(R.id.chosenImageView);
//        uploadProgressBar = findViewById(R.id.progress_bar);
//
//        mStorageRef= FirebaseStorage.getInstance().getReference("spareParts_uploads");
//        mDataBaseRef = FirebaseDatabase.getInstance().getReference("spareParts_uploads");
//
//        chooseImageBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                openFileChooser();
//            }
//        });
//        uploadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mUploadTask != null && mUploadTask.isInProgress()){
//                    Toast.makeText(Upload2Activity.this, "An Upload is still in progress", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    uploadFile();
//                }
//            }
//        });
//
//
//    }
//    private void openFileChooser(){
//        Intent intent= new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode== RESULT_OK && data != null && data.getData()!= null){
//            mImageUri = data.getData();
//
//            Picasso.with(this).load(mImageUri).into(chosenImageView);
//        }
//    }
//
//    private String getFileExtension(Uri uri){
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap mime =  MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(uri));
//    }
//
//    private void uploadFile(){
//        if(mImageUri != null){
//            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+ getFileExtension(mImageUri));
//
//            uploadProgressBar.setVisibility(View.VISIBLE);
//            uploadProgressBar.setIndeterminate(true);
//
//            mUploadTask = fileReference.putFile(mImageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    uploadProgressBar.setVisibility(View.VISIBLE);
//                                    uploadProgressBar.setIndeterminate(false);
//                                    uploadProgressBar.setProgress(0);
//                                }
//                            }, 500);
//
//                            Toast.makeText(Upload2Activity.this, "Spare part uploaded successfuly", Toast.LENGTH_SHORT).show();
//                            Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
//                            SparePart upload = new SparePart(nameEditText.getText().toString().trim(),
//                                    uri.toString());
//
//                            String uploadId = mDataBaseRef.push().getKey();
//                            mDataBaseRef.child(uploadId).setValue(upload);
//
//                            uploadProgressBar.setVisibility(View.INVISIBLE);
//                            openImagesActivity();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            uploadProgressBar.setVisibility(View.INVISIBLE);
//                            Toast.makeText(Upload2Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred());
//                            uploadProgressBar.setProgress((int) progress);
//                        }
//                    });
//        }else {
//            Toast.makeText(this, "you haven't selected any file", Toast.LENGTH_SHORT).show();
//
//        }
//
//
//
//    }
//    private void openImagesActivity(){
//        Intent intent = new Intent(this, shareholderActivity.class);
//        startActivity(intent);
//    }
//}
