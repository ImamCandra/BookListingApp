package com.example.iceka.booklistingapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {

    }

    public static List<Books> fetchBooksData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request. ", e);
        }

        List<Books> booksList = extractFeatureFromJson(jsonResponse);
        return booksList;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building url, ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code = " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the books json result. ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return builder.toString();
    }

    private static List<Books> extractFeatureFromJson(String earthquakeJSON) {
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        List<Books> booksList = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray booksArray = baseJsonResponse.getJSONArray("items");
            for (int i = 0;i < booksArray.length();i++) {
                JSONObject currentBooks = booksArray.getJSONObject(i);
                JSONObject volumeInfo = currentBooks.getJSONObject("volumeInfo");
                JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

                String subtitle = null;
                String title = volumeInfo.getString("title");

                if (volumeInfo.has("subtitle")) {
                    subtitle = (String) volumeInfo.getString("subtitle");
                    title.concat(":" + subtitle);
                } else {
                    subtitle = null;
                }

                String author = authorsArray.getString(0);
                String published = volumeInfo.getString("publishedDate");
                String thumbnails = imageLinks.getString("smallThumbnail");

                Books books = new Books(title, author, published, thumbnails, subtitle);
                booksList.add(books);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the books JSON results", e);
        }
        return booksList;
    }
}
