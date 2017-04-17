package com.example.yah.androidfinal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.System.in;


public class Weather extends TestToolbar {


    final String ACTIVITY_NAME = "Weather";
    private ProgressBar progressBar;
    private String url = "http://api.openweathermap.org/data/2.5/weather?q=ottawa," +
            "ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    private static String current; // Current temp

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Make Progress Bar Visible (View.Visible)
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Instantiate and execute forecast query
        ForecastQuery forecastQuery = new ForecastQuery();
        forecastQuery.execute();
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String min; // Min temp
        private String max; // Max temp
        private String iconName; // Icon name
        private Bitmap bitmap; // Bitmap to hold temperature image

        @Override
        protected String doInBackground(String... args) {
            try {
                XmlPullParser pullParser = Xml.newPullParser();
                try {
                    // Use an XML Pull Parser
                    pullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    pullParser.setInput(downloadURL(url), null);
                    pullParser.nextTag();

                    // Iterate through XML tags using next() function
                    while (pullParser.next() != XmlPullParser.END_DOCUMENT) {
                        if (pullParser.getEventType() != XmlPullParser.START_TAG) {
                            continue;
                        }
                        String name = pullParser.getName();
                        if (name.equals("temperature")) {
                            // Get current temperature value
                            current = pullParser.getAttributeValue(null, "value");
                            publishProgress(25);
                            // Get minimum temperature value
                            min = pullParser.getAttributeValue(null, "min");
                            publishProgress(50);
                            // Get maximum temperature value
                            max = pullParser.getAttributeValue(null, "max");
                            publishProgress(75);
                        } else if (name.equals("weather")) {
                            iconName = pullParser.getAttributeValue(null, "icon");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            } finally {
                try {
                    in.close(); // Close System.in
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

            if(fileExistance(iconName + ".png")) {
                // If image exists, don't re-download just read it from disk
                FileInputStream fis = null;
                try {
                    File file = new File(getFilesDir(), iconName + ".png");
                    fis = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap = BitmapFactory.decodeStream(fis);
                Log.i(ACTIVITY_NAME, "Found " + iconName + ".png locally");
            } else {
                // The OpenWeatherMap site has icons for cloudy, sunny, etc. @
                String bitmapURL = "http://openweathermap.org/img/w/" + iconName + ".png";
                bitmap = HTTPUtils.getImage(bitmapURL);
                FileOutputStream outputStream = null;
                try {
                    outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                Log.i(ACTIVITY_NAME, "Downloading " + iconName + ".png");
                new File(getFilesDir(), iconName + ".png");
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Call publishProgress(100) to show that progress is complete after building bitmap object
            publishProgress(100);

            String weatherForecast = current + " " + min + " " + max + " " + iconName;

            return weatherForecast;
        }

        private InputStream downloadURL(String url) throws IOException {
            URL uRL = new URL(url); // Create real url object from string url
            HttpURLConnection conn = (HttpURLConnection) uRL.openConnection();
            conn.setReadTimeout(10000); // In milliseconds
            conn.setConnectTimeout(15000); // In milliseconds
            conn.setRequestMethod("GET"); // Set type to GET
            conn.setDoInput(true); // Start the query
            conn.connect();
            return conn.getInputStream();
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            // Set visibilty of progressBar to visible, set to variable value[0] being passed in
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        public boolean fileExistance(String fname) {
            // Check to see if your sunny, cloudy, rainy images are already present in local storage directory
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        protected void onPostExecute(String result) {
            // Update GUI components with min, max, and current temperature that you have read from XML
            TextView minText = (TextView) findViewById(R.id.minimumTemperature);
            TextView maxText = (TextView) findViewById(R.id.maximumTemperature);
            TextView currentText = (TextView) findViewById(R.id.currentWeather);

            // Also update imageView with the bitmap you downloaded
            ImageView weatherImage = (ImageView) findViewById(R.id.currentWeatherImage);
            weatherImage.setImageBitmap(bitmap);

            minText.setText(getString(R.string.min) + min);
            maxText.setText(getString(R.string.max) + max);
            currentText.setText(getString(R.string.cTemp) + current);

            // Alert Dialog
            AlertDialog alertDialog = new AlertDialog.Builder(Weather.this).create();
            alertDialog.setTitle(getString(R.string.weatherLulz));

            alertDialog.setMessage(getString(R.string.theCurrentW) + current + "°C");
            alertDialog.setIcon(R.drawable.cloud);

            final double temp = Double.parseDouble(current);

            alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (temp >= 20) {
                        Toast.makeText(getApplicationContext(), getString(R.string.shorts), Toast.LENGTH_SHORT).show();
                    } else if (temp < 20 && temp >= 10) {
                        Toast.makeText(getApplicationContext(), getString(R.string.sweater), Toast.LENGTH_SHORT).show();
                    } else if (temp < 10 && temp >= 0) {
                        Toast.makeText(getApplicationContext(), getString(R.string.jacket), Toast.LENGTH_SHORT).show();
                    } else if (temp < 0) {
                        Toast.makeText(getApplicationContext(), getString(R.string.parka), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertDialog.show();
        }
    }

    private static class HTTPUtils {
        // Code provided by http://www.java2s.com/Code/Android/2D-Graphics/GetBitmapfromUrlwithHttpURLConnection.htm
        public static Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        public static Bitmap getImage(String urlString) {
            // Code provided by http://www.java2s.com/Code/Android/2D-Graphics/GetBitmapfromUrlwithHttpURLConnection.htm
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
} // End of Weather.java