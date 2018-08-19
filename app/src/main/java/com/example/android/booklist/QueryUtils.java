package com.example.android.booklist;

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
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private QueryUtils() {
    }

    public static List<Booklist> fetchBookData(String requestUrl){
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //Create URL objects
        URL url = createUrl(requestUrl);

        //Perform HTTP request to the URL and receive a JSON response
        String jsonresponse = null;
        try {
            jsonresponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e("Tag", "Error closing input stream", e);
        }
        List<Booklist> list = extractBookJsonData(jsonresponse);
        return list;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("Tsg", "Error with creating URL ", e);
        }
        return url;
    }

    /*
     * Make HTTP Request to the given Url and return Json response.
     * /
     *
     */

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            }
        }catch (IOException e) {

        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    /**
     * convert the {@link java.io.InputStream} into a string
     * which contains the whole json responsse from server
     */
    private static String readInputStream(InputStream inputStream)throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Booklist> extractBookJsonData(String bookJson){

        //If JDON string is empty or null, then return early
        if (TextUtils.isEmpty(bookJson)){
            return  null ;
        }

        //Create an empty ArrayList that we can start adding books to
        List<Booklist> bookLists = new ArrayList<>();


        try {

            //Create a JSONObject from the SAMPLE_JSON_RESPONSE
            JSONObject jsonObject = new JSONObject(bookJson);

            JSONArray bookarray = jsonObject.getJSONArray("items");

            for(int i = 0; i<bookarray.length();i++){
                JSONObject currentBook = bookarray.getJSONObject(i);

                JSONObject volume = currentBook.getJSONObject("volumeInfo");

                String title = volume.getString("title");

                String author ;

                if (volume.has("authors")){
                    JSONArray writer = volume.getJSONArray("authors");
                    author = (String) writer.get(0);
                }else {
                    author = "missing info ";
                }

                String publisher = volume.getString("publisher");


                JSONObject saleInfo = currentBook.getJSONObject("saleInfo");
                JSONObject price = saleInfo.getJSONObject("retailPrice");

                String amount = price.getString("amount");

                String currency = price.getString("currencyCode");

                String url = saleInfo.getString("buyLink");

                JSONObject imageLink = volume.getJSONObject("imageLinks");

                String coverImageUrl = imageLink.getString("smallThumbnail");

                Booklist booklist = new Booklist(title,publisher,amount,url,currency,author,coverImageUrl);

                bookLists.add(booklist);
            }
        }catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return bookLists;
    }

}
