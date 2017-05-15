package cc.gilmore.dressmike;

//Add photo to remote server via PHP
//http://www.codepool.biz/take-a-photo-from-android-camera-and-upload-it-to-a-remote-php-server.html

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {
	Button btnShirt;
	Button btnPants;
	int REQUEST_CODE = 1;
	ImageView imgShirt;
	ImageView imgPants;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		btnShirt = (Button)findViewById(R.id.btnShirt);
		btnPants = (Button)findViewById(R.id.btnPants);
		imgShirt = (ImageView)findViewById(R.id.imgShirt);
		imgPants = (ImageView)findViewById(R.id.imgPants);
		
		btnShirt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				if(i.resolveActivity(getPackageManager()) != null) {
					startActivityForResult(i, REQUEST_CODE);
				}
			}
		});
		btnPants.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				if(i.resolveActivity(getPackageManager()) != null) {
					startActivityForResult(i, REQUEST_CODE);
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE) {
			if(resultCode == RESULT_OK) {
				//we get a thumbnail image and a full size image
				
				Bundle bundle = new Bundle();
				bundle = data.getExtras();
				Bitmap bmp = (Bitmap)bundle.get("data");
				imgShirt.setImageBitmap(bmp);
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
