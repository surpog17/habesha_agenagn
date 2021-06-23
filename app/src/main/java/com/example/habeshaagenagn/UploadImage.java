package com.example.habeshaagenagn;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class UploadImage extends AppCompatActivity implements View.OnClickListener {

    //Declaring views
    /**
     *
     */
    private Button buttonChoose;
    private Button buttonUpload;
    private ImageView imageView;
    private EditText editText;
    private EditText firstname, lastname, work, experience, email, phone, description;
    Spinner dwork;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        //Requesting storage permission
        requestStoragePermission();

        //Initializing views
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editTextName);

        //Setting clicklistener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        dwork = (Spinner) findViewById (R.id.dwork);
        //loading = findViewById (R.id.loading);
        firstname = findViewById (R.id.firstnamediver);
        lastname = findViewById (R.id.lastnamedirver);
        experience=findViewById (R.id.experiencedirver);
        dwork = findViewById (R.id.dwork);
        description = findViewById (R.id.descriptiondirver);
        email = findViewById (R.id.emaildirver);
        phone = findViewById (R.id.phonedirver);
        // dregister = findViewById (R.id.dregister);
        String[] dirversworks = new String[]{"personal driver", "school driver ", "taxi Driver",};
        ArrayAdapter<String> works = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, dirversworks);
        dwork.setAdapter (works);
        buttonUpload.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String emailinput = email.getEditableText ().toString ();
                int i = 0;
                int j = 0;
                if (firstname.getText ().toString ().length () == 0) {
                    firstname.setError ("Please enter Your firstname");
                    i++;
                }
                if (lastname.getText ().toString ().length () == 0) {
                    lastname.setError ("Please enter Your lastname");
                    i++;
                }
                if (description.getText ().toString ().length () == 0) {
                    description.setError ("Please enter Your description");
                    i++;
                }
                if (phone.getText ().toString ().length () == 0) {
                    phone.setError ("Please enter Your phone number");
                    i++;
                }
                if (dwork.getSelectedItem ().toString ().length () == 0) {
                    // Ework.setError ("Please enter Your work type");
                    i++;
                }
                if (experience.getText ().toString ().length () == 0) {
                    experience.setError ("Please enter your experience");
                    i++;
                }
                if (emailinput.isEmpty ()) {
                    email.setError ("Please enter Your email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher (emailinput).matches ()) {
                    email.setError ("please enter  a valid email address");
                } else {
                    uploadMultipart ();
                }

            }
        });
    }





    /*
     * This is the method responsible for image upload
     * We need the full image path and the name for the image in this method
     * */
    public void uploadMultipart() {
        //getting name for the image
        String firstname=this.firstname.getText().toString().trim();
        String lastname=this.lastname.getText().toString().trim();
        String work=this.dwork.getSelectedItem ().toString ();
        String experience=this.experience.getText().toString().trim();
        String email=this.email.getText().toString().trim();
        String phone=this.phone.getText().toString().trim();
        String description=this.description.getText().toString().trim();
        String name = editText.getText().toString().trim();
        //getting the actual path of the image
        String path = getPath(filePath);
        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();
            //Creating a multi part request
            new MultipartUploadRequest (this, uploadId, Constants.UPLOAD_URL)
                    .addParameter ("firstname",firstname)//Adding text parameter to the request
                    .addParameter ("lastname",lastname)//Adding text parameter to the request
                    .addParameter ("experience",experience)//Adding text parameter to the request
                    .addParameter ("work",work)//Adding text parameter to the request
                    .addParameter ("email",email)//Adding text parameter to the request
                    .addParameter ("phone",phone)//Adding text parameter to the request
                    .addParameter ("Description",description)//Adding text parameter to the request
                    .addParameter("name", name)
                    .addFileToUpload(path, "image") //Adding file
                    .setNotificationConfig(new UploadNotificationConfig ())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent ();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block

            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            uploadMultipart();
        }
    }


}