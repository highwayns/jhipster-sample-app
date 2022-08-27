package io.swagger.graghql;


public class Book {

    private String id;
    private String name;
    private int pageCount;
    private Author author;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
