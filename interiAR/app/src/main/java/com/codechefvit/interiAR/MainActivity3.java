package com.codechefvit.interiAR;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity3 extends AppCompatActivity {

    ArFragment arFragment;
    AnchorNode anchorNode;
    File videoDirectory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        String fil;
        String filename;
        fil = getIntent().getStringExtra("selected_item");
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

        findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity3.super.onBackPressed();
            }
        });
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
    private void takePhoto() {
        Toast.makeText(this,"Screenshot Saved",Toast.LENGTH_LONG).show();
        ArSceneView view = arFragment.getArSceneView();

        // Create a bitmap the size of the scene view.
        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);

        // Create a handler thread to offload the processing of the image.
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        // Make the request to copy.
        PixelCopy.request(view, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                Log.e("save","save");
                try {
                    saveBitmapToDisk(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }



            } else {

                Toast.makeText(MainActivity3.this, "screenshot cannot saved",
                        Toast.LENGTH_LONG).show();

            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }


    public void saveBitmapToDisk(Bitmap bitmap) throws IOException {

        if (videoDirectory == null) {
            videoDirectory =
                    new File(String.valueOf(new File(

                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                    + "/Screenshots")));
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        String formattedDate = df.format(c.getTime());

        File mediaFile = new File(videoDirectory, "RoomViz"+formattedDate+".jpeg");

        FileOutputStream fileOutputStream = new FileOutputStream(mediaFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private void handleUpload(Bitmap bitmap){
        String user= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        String ssfile=user.replace('.','_');
        StorageReference reference=FirebaseStorage.getInstance().getReference()
                .child(ssfile)
                .child(Calendar.getInstance().getTime().toString()+".jpg");
        reference.putBytes(baos.toByteArray());
        Toast.makeText(getApplicationContext(),"Screenshot uploaded successfully",Toast.LENGTH_SHORT).show();
    }

    private void removeAnchorNode(AnchorNode nodeToremove) {
        //Remove an anchor node
        if (nodeToremove != null) {
            arFragment.getArSceneView().getScene().removeChild(nodeToremove);
            nodeToremove.getAnchor().detach();
            nodeToremove.setParent(null);
            nodeToremove = null;
            Toast.makeText(getApplicationContext(), "The Object Was Successfully Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Test Delete - markAnchorNode was null", Toast.LENGTH_SHORT).show();
        }
    }

}
