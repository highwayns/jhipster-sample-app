package io.swagger.graghql;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

@Component
public class BookResolver implements GraphQLQueryResolver {

    public Book bookById(String bookId) {
        // 実際は何らかのデータストアからデータを読み込み返却するケースがほとんどだが、ここではダミー値を返却
        Book book = new Book();
        book.setId(bookId);
        book.setName("bookName");
        book.setPageCount(900);
        Author author = new Author();
        author.setId("0001");
        author.setFirstName("fName");
        author.setLastName("lName");
        book.setAuthor(author);
        return book;
    }
}
