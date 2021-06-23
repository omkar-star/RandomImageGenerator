package com.example.randomimagegenerator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText search;
    ImageView imageView;
    Button chang, random;
    Bitmap myBitmap;
    String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Log.d("mk", "onCreate: Height" + height + " width :-  " + width);

        imageView = findViewById(R.id.image);
        chang = findViewById(R.id.changeImg);
        search = findViewById(R.id.search);
        random = findViewById(R.id.random);


        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgUrl = "https://source.unsplash.com/random/1000x1700";
                loadImage(imageView);
            }
        });
        chang.setOnClickListener(new View.OnClickListener() {
            private String searchImg;

            @Override
            public void onClick(View v) {
                searchImg = search.getText().toString();
                imgUrl = "https://source.unsplash.com/1000x1700/" + "?" + searchImg;
                loadImage(imageView);
            }
        });

        imgUrl = "https://source.unsplash.com/1000x1700/?love";
        loadImage(imageView);
    }

    private void loadImage(View view) {
        class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
            private String url;
            private ImageView imageView;

            ImageLoadTask(String url, ImageView imageView) {
                this.url = url;
                this.imageView = imageView;
            }

            @Override
            protected Bitmap doInBackground(Void... voids) {
                try {
                    URL connection = new URL(url);

                    InputStream input = connection.openStream();
                    myBitmap = BitmapFactory.decodeStream(input);
                    Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 1000, 1700, true);//1334x2100
                    return resized;

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                super.onPostExecute(result);
                imageView.setImageBitmap(result);
            }
        }

        ImageLoadTask obj = new ImageLoadTask(imgUrl, imageView);
        obj.execute();

    }

}