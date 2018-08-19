package com.example.android.booklist;

public class Booklist {
    private String bookName;

    private String authorName;

    private String price;

    private String curency;

    private String url;

    private String writerName;

    private String imageUrl;


    public Booklist(String bookName, String authorName, String price,String url, String curency, String writerName, String imageUrl) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.price = price;
        this.url = url;
        this.curency= curency;
        this.writerName= writerName;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }
}
