package com.example.android.booklist;

public class Booklist {
    private String bookName;

    private String authorName;

    private String date;

    private String price;

    private String curency;

    private String url;

    private String writerName;


    public Booklist(String bookName, String authorName, String date, String price,String url, String curency, String writerName) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.date = date;
        this.price = price;
        this.url = url;
        this.curency= curency;
        this.writerName= writerName;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getUrl() {
        return url;
    }

    public String getCurency() {
        return curency;
    }

    public String getWriterName() {
        return writerName;
    }

}
