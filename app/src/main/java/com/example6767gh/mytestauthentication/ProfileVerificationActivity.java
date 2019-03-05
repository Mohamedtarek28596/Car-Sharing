package com.example6767gh.mytestauthentication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example6767gh.mytestauthentication.mina.CloudTextGraphic;
import com.example6767gh.mytestauthentication.mina.GraphicOverlay;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileVerificationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CHOOSE_IMAGE = 101;

    ImageView imageView;

    Uri uriProfileImage;        //uniform resources identifier image storage

    Button Btn;
    private GraphicOverlay mGraphicOverlay;
    private Bitmap mSelectedImage;
    private String sid, nameEng;
    private String[] date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_verification);
        mGraphicOverlay = findViewById(R.id.graphic_overlay);
        imageView = (ImageView) findViewById(R.id.imageView);


        //   textView = (TextView) findViewById(R.id.textViewVerified);
        findViewById(R.id.ButtonNext).setOnClickListener(this);
        Btn = (Button)findViewById(R.id.ButtonNext);

        // on click lilo :heart: , save and display
        imageView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                showImageChooser();           // method permit user to select image
            }
        });
    }

    // display the image after the user selected it

    Integer mImageMaxWidth;
    private Integer getImageMaxWidth() {
        if (mImageMaxWidth == null) {
            // Calculate the max width in portrait mode. This is done lazily since we need to
            // wait for
            // a UI layout pass to get the right values. So delay it to first time image
            // rendering time.
            mImageMaxWidth = imageView.getWidth();
        }

        return mImageMaxWidth;
    }

    Integer mImageMaxHeight;
    // Returns max image height, always for portrait mode. Caller needs to swap width / height for
    // landscape mode.
    private Integer getImageMaxHeight() {
        if (mImageMaxHeight == null) {
            // Calculate the max width in portrait mode. This is done lazily since we need to
            // wait for
            // a UI layout pass to get the right values. So delay it to first time image
            // rendering time.
            mImageMaxHeight =
                    imageView.getHeight();
        }

        return mImageMaxHeight;
    }


    private Pair<Integer, Integer> getTargetedWidthHeight() {
        int targetWidth;
        int targetHeight;
        int maxWidthForPortraitMode = getImageMaxWidth();
        int maxHeightForPortraitMode = getImageMaxHeight();
        targetWidth = maxWidthForPortraitMode;
        targetHeight = maxHeightForPortraitMode;
        return new Pair<>(targetWidth, targetHeight);
    }
    /* */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check for the image selected
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                imageView.setImageBitmap(bitmap);

                //TODO: Start here
                mGraphicOverlay.clear();

                mSelectedImage = bitmap;
                if (mSelectedImage != null) {
                    // Get the dimensions of the View
                    Pair<Integer, Integer> targetedSize = getTargetedWidthHeight();

                    int targetWidth = targetedSize.first;
                    int maxHeight = targetedSize.second;

                    // Determine how much to scale down the image
                    float scaleFactor =
                            Math.max(
                                    (float) mSelectedImage.getWidth() / (float) targetWidth,
                                    (float) mSelectedImage.getHeight() / (float) maxHeight);

                    Bitmap resizedBitmap =
                            Bitmap.createScaledBitmap(
                                    mSelectedImage,
                                    (int) (mSelectedImage.getWidth() / scaleFactor),
                                    (int) (mSelectedImage.getHeight() / scaleFactor),
                                    true);
                    mSelectedImage = resizedBitmap;
                }
                runCloudTextRecognition();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void runCloudTextRecognition() {

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(mSelectedImage);
        FirebaseVisionDocumentTextRecognizer recognizer = FirebaseVision.getInstance()
                .getCloudDocumentTextRecognizer();
        recognizer.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionDocumentText>() {
                            @Override
                            public void onSuccess(FirebaseVisionDocumentText texts) {

                                processCloudTextRecognitionResult(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                // Task failed with an exception
                                e.printStackTrace();
                            }
                        });

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void processCloudTextRecognitionResult(FirebaseVisionDocumentText text) {
        // Task completed successfully

        if (text == null) {
            showToast("No text found");
            return;
        }
        mGraphicOverlay.clear();
        List<FirebaseVisionDocumentText.Block> blocks = text.getBlocks();
        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionDocumentText.Paragraph> paragraphs = blocks.get(i).getParagraphs();
            for (int j = 0; j < paragraphs.size(); j++) {
                //Log.i("elec", paragraphs.get(0).getText());
                    if (i == 2)
                        sid = paragraphs.get(0).getText();
                    else if(i == 3)
                        nameEng = paragraphs.get(0).getText();
                    else if (i == 8)
                        date = arabicToDecimal(paragraphs.get(0).getText().replace('۶','٤').replaceAll("[\n]", "").split("/"));
                List<FirebaseVisionDocumentText.Word> words = paragraphs.get(j).getWords();
                for (int l = 0; l < words.size(); l++) {
                    CloudTextGraphic cloudDocumentTextGraphic = new CloudTextGraphic(mGraphicOverlay,
                            words.get(l));
                    mGraphicOverlay.add(cloudDocumentTextGraphic);

                }
 /*               ArrayList<String> ar = new ArrayList<String>();
                ar.add(wordsRead.toString());
                showToast(ar.get(0));*/

//                for (String x : wordsRead)
//                    showToast(x);


            }
        }
        Log.i("elec", sid + " " + nameEng + " " + date[0] + "/"+date[1]+"/"+date[2]);
        //Log.i("elec",wordsRead.get(14));
        //Log.i("elec",wordsRead.get(15) + " " + wordsRead.get(16) + " " + wordsRead.get(17) + " "+ wordsRead.get(18));
        //Log.i("elec",wordsRead.get(37) + " " + wordsRead.get(38) + " " + wordsRead.get(39) + " "+ wordsRead.get(40) + " " + wordsRead.get(41));
    }

    private String[] arabicToDecimal(String[] numbers) {
        String[] res = new String[numbers.length];
        for (int j = 0; j < numbers.length; j++) {
            char[] chars = new char[numbers[j].length()];
            for (int i = 0; i < numbers[j].length(); i++) {
                char ch = numbers[j].charAt(i);
                if (ch >= 0x0660 && ch <= 0x0669)
                    ch -= 0x0660 - '0';
                else if (ch >= 0x06f0 && ch <= 0x06F9)
                    ch -= 0x06f0 - '0';
                chars[i] = ch;
            }
            res[j] = new String(chars);
        }

        return res;
    }


    //  selects image of the user

    /* */
    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT); // get the image
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ButtonNext:
                startActivity(new Intent(this, ProfileData.class));
                finish();
                break;
        }
    }
}