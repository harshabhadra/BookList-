package com.example.android.booklist;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Booklist>> {

    String url;

    public BookLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public List<Booklist> loadInBackground() {


        if (url ==  null){
            return null;
        }
        List<Booklist> result = QueryUtils.fetchBookData(url);
        return result;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
