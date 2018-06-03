package com.example.iceka.booklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BooksAdapter extends ArrayAdapter<Books> {
    public BooksAdapter(@NonNull Context context, @NonNull List<Books> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Books currentBooks = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.tvJudul);
        TextView author = (TextView) listItemView.findViewById(R.id.tvPengarang);
        TextView published = (TextView) listItemView.findViewById(R.id.tvTglPublish);
        ImageView thumbnail = (ImageView) listItemView.findViewById(R.id.imgThumbnail);

        String tes = currentBooks.getmTitle();
        String tes2 = currentBooks.getmSubTitle();

        if (tes2 != null) {
            title.setText(tes + ": " + tes2);
        } else {
            title.setText(tes);
        }

        author.setText(currentBooks.getmAuthor());
        published.setText(currentBooks.getmPublishedDate());
        Picasso.with(getContext()).load(currentBooks.getmImage()).into(thumbnail);

        return listItemView;
    }
}
