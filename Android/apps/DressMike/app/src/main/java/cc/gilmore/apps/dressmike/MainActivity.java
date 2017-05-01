package cc.gilmore.apps.dressmike;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    ImageView shirtImageView;
    ImageView pantsImageView;
    View layout;

    Button nextButton;
    Button chooseButton;

    String currentShirt;
    String currentPants;
    OutfitDB outfitDB;

    static final int CAM_REQUEST = 1;
    static final String appFolder = "sdcard/camera_app/";
    static final String SHIRT = "shirt";
    static final String PANTS = "pants";

    Random rand = new Random();

    private GestureDetector gesturedetector = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shirtImageView = (ImageView)findViewById(R.id.shirtImage);
        pantsImageView = (ImageView)findViewById(R.id.pantsImage);
        nextButton = (Button)findViewById(R.id.nextButton);
        chooseButton = (Button)findViewById(R.id.chooseButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRandomPics();
            }
        });
        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOutfit();
            }
        });

        shirtImageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                loadFile(SHIRT, getRandomPic(SHIRT));
            }
        });
        pantsImageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                loadFile(PANTS, getRandomPic(PANTS));
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        loadRandomPics();
        loadOutfitDB();


        gesturedetector = new GestureDetector(new SwipeListener());

        layout = (LinearLayout)findViewById(R.id.);
        layout.setOnTouchListener(new View.OnTouchListener() {

            @Override

            public boolean onTouch(View v, MotionEvent event) {

                gesturedetector.onTouchEvent(event);

                return true;

            }

        });
    }

    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return gesturedetector.onTouchEvent(ev);
    }

    private void getNew(String item) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile(item);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(cameraIntent, CAM_REQUEST);
    }

    private void loadOutfitDB() {
        outfitDB = new OutfitDB(appFolder + "outfitDB");

    }

    private void saveOutfit() {
        if(outfitDB == null) {
            Logger.log("Failed to save outfit. DB object is null.", Logger.LogLevel.ERROR);
            return;
        }

        outfitDB.saveTodaysOutfit(currentShirt, currentPants);
    }

    /*
    private void setCounts() {
        File shirtsFolder = new File(appFolder + SHIRT);
        Integer numShirts = 0;
        if(shirtsFolder.exists()) {
            numShirts = shirtsFolder.listFiles().length;
        }
        shirtCountText.setText(numShirts.toString());

        File pantsFolder = new File(appFolder + PANTS);
        Integer numPants = 0;
        if(pantsFolder.exists()) {
            numPants = pantsFolder.listFiles().length;
        }
        pantsCountText.setText(numPants.toString());
    }
    */

    private void loadRandomPics() {
        loadFile(SHIRT, getRandomPic(SHIRT));
        loadFile(PANTS, getRandomPic(PANTS));
    }

    private String getRandomPic(String cat) {
        File folder = new File(appFolder + cat);
        if(folder.exists()) {
            File[] fileList = folder.listFiles();
            if(fileList != null) {
                int chosen = randInt(1, fileList.length);
                return fileList[chosen-1].getAbsolutePath();
            }
        }
        return null;
    }

    private int randInt(int min, int max) {
        //nextInt excludes top value, +1 for inclusive
        return rand.nextInt((max - min) + 1) + min;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_new_shirt) {
            getNew(SHIRT);
        }
        else if (id == R.id.action_new_pants) {
            getNew(PANTS);
        }
        else if (id == R.id.action_next_outfit) {
            loadRandomPics();
        }
        else if (id == R.id.action_history) {
            Intent myIntent = new Intent(MainActivity.this, HistoryActivity.class);
            //myIntent.putExtra("key", value); //Optional parameters
            MainActivity.this.startActivity(myIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private File getFile(String cat) {
        File folder = new File(appFolder + cat);
        if(!folder.exists()) {
            folder.mkdir();
        }

        File newFile = new File(folder, Util.getDateTime() + "_" + cat + ".jpg");
        return newFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    private void loadFile(String cat, String path) {
        if(path == null || path.length() == 0) {
            Logger.log("Calling loadFile() with empty file path", Logger.LogLevel.WARNING);
            return;
        }

        File file = new File(path);
        if(!file.exists()) {
            Logger.log("Calling loadFile() with file path that doesn't exist (" + path + ")", Logger.LogLevel.ERROR);
            return;
        }

        Bitmap bitmap = BitmapFactory.decodeFile(path);

        if(SHIRT.equals(cat)) {
            currentShirt = path;
        }
        else {
            currentPants = path;
        }

        if (bitmap.getHeight() > GL10.GL_MAX_TEXTURE_SIZE) {
            float widthScaling = 0.75f;
            float heightScaling = 0.3f;
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
                    (int)(bitmap.getWidth()*widthScaling),
                    (int)(bitmap.getHeight()*heightScaling),
                    true);

            if(SHIRT.equals(cat)) {
                shirtImageView.setImageBitmap(scaledBitmap);
            }
            else {
                pantsImageView.setImageBitmap(scaledBitmap);
            }
        }
        else{
            // for bitmaps with dimensions that lie within the limits, load the image normally
            if (Build.VERSION.SDK_INT >= 16) {
                BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                if(SHIRT.equals(cat)) {
                    shirtImageView.setBackground(ob);
                }
                else {
                    pantsImageView.setBackground(ob);
                }
            } else {
                if(SHIRT.equals(cat)) {
                    shirtImageView.setImageBitmap(bitmap);
                }
                else {
                    pantsImageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
