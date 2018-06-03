package com.example.iceka.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private BooksAdapter mAdapter;
    private static final String BOOK_API_URL = "https://www.googleapis.com/books/v1/volumes?q=love";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView booksList = (ListView)findViewById(R.id.list);
        mAdapter = new BooksAdapter(this, new ArrayList<Books>());
        booksList.setAdapter(mAdapter);

        BookAsyncTask task = new BookAsyncTask();
        task.execute(BOOK_API_URL);

    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Books>> {

        @Override
        protected List<Books> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Books> booksList = QueryUtils.fetchBooksData(urls[0]);
            return booksList;
        }

        @Override
        protected void onPostExecute(List<Books> books) {
            mAdapter.clear();

            if (books != null && !books.isEmpty()) {
                mAdapter.addAll(books);
            }
        }
    }

}
