package com.example.interiordesign;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.PixelCopy;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity3 extends AppCompatActivity {

    ArFragment arFragment;
    AnchorNode anchorNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        String fil;
        String filename;
        int itemno;
        itemno = getIntent().getIntExtra("selected_item",0);
        switch(itemno){
            case 0:
                fil="artistschool";
                break;
            case 1:
                fil="bedroom";
                break;
            case 2:
                fil="besidetable";
                break;
            case 3:
                fil="chairsingleton";
                break;
            case 4:
                fil="chairsingle";
                break;
            case 5:
                fil="comfycouch";
                break;
            case 6:
                fil="dressingtable";
                break;
            case 7:
                fil="hearth";
                break;
            case 8:
                fil="kitchenTable";
                break;
            case 9:
                fil="sofasingle";
                break;
            case 10:
                fil="tv";
                break;
            case 11:
                fil="sleeepingtable";
                break;
            case 12:
                fil="fireplace";
                break;
            case 13:
                fil="desk";
                break;
            case 14:
                fil="sofafbxabitgood";
                break;
            case 15:
                fil="jummer";
                break;
            case 16:
                fil="dressingtableparttow";
                break;
            case 17:
                fil="HUG ARMCHAIR";
                break;
            case 18:
                fil="sofabitgood";
                break;
            case 19:
                fil="shellabitlow";
                break;
            case 20:
                fil="dinningtablemodel";
                break;
            case 21:
                fil="lowspeeddinningtable";
                break;
            case 22:
                fil="sideboard";
                break;
            case 23:
                fil="jocodinningtable";
                break;
            case 24:
                fil="abcd";
                break;
            case 25:
                fil="NewTod";
                break;
            case 26:
                fil="fileag";
                break;
            case 27:
                fil="out";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + itemno);
        }
        filename=fil+".glb";
        findViewById(R.id.savebtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        FirebaseApp.initializeApp(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference modelRef = storage.getReference().child(filename);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment1);
        findViewById(R.id.downloadBtn1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            File file = File.createTempFile(fil, "glb");

                            modelRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    buildModel(file);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        findViewById(R.id.removeBtn)
                .setOnClickListener( v -> {
                    try {
                        File file = File.createTempFile(fil, "glb");

                        modelRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                removeAnchorNode(anchorNode);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {

            anchorNode = new AnchorNode(hitResult.createAnchor());
            TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
            transformableNode.setParent(anchorNode);
            transformableNode.setRenderable(renderable);
            //anchorNode.setRenderable(renderable);
            arFragment.getArSceneView().getScene().addChild(anchorNode);
            transformableNode.select();

        }));
    }
    private ModelRenderable renderable ;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buildModel(File file) {
        RenderableSource renderableSource = RenderableSource
                .builder()
                .setSource(this, Uri.parse(file.getPath()), RenderableSource.SourceType.GLB)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build();

        ModelRenderable
                .builder()
                .setSource(this, renderableSource)
                .setRegistryId(file.getPath())
                .build()
                .thenAccept(modelRenderable -> {
                    Toast.makeText(this, "Model Built", Toast.LENGTH_SHORT).show();
                    renderable = modelRenderable;
                });
    }
    private void takePhoto(){
        ArSceneView view = arFragment.getArSceneView();

        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),
                Bitmap.Config.ARGB_8888);

        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            PixelCopy.request(view, bitmap, (copyResult) -> {
                if (copyResult == PixelCopy.SUCCESS) {
                    handleUpload(bitmap);
                } else {
                    Toast toast = Toast.makeText(MainActivity3.this, "Failed to save screenshot !" + copyResult, Toast.LENGTH_LONG);
                    toast.show();
                }
                handlerThread.quitSafely();
            }, new Handler(handlerThread.getLooper()));
        }
    }
    private void handleUpload(Bitmap bitmap){
        String user= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        StorageReference reference=FirebaseStorage.getInstance().getReference()
                .child(user)
                .child(Calendar.getInstance().getTime().toString()+".jpg");
        reference.putBytes(baos.toByteArray());
        Toast.makeText(this,"Screenshot uploaded successfully",Toast.LENGTH_SHORT).show();
    }

    private void removeAnchorNode(AnchorNode nodeToremove) {
        //Remove an anchor node
        if (nodeToremove != null) {
            arFragment.getArSceneView().getScene().removeChild(nodeToremove);
            nodeToremove.getAnchor().detach();
            nodeToremove.setParent(null);
            nodeToremove = null;
            Toast.makeText(this, "The Object Was Successfully Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Test Delete - markAnchorNode was null", Toast.LENGTH_SHORT).show();
        }
    }
}
