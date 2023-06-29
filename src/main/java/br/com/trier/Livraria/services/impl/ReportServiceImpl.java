package br.com.trier.Livraria.services.impl;

import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.BookAuthor;
import br.com.trier.Livraria.domain.BookGenres;
import br.com.trier.Livraria.domain.dto.ReportBookAuthorDTO;
import br.com.trier.Livraria.services.ReportService;

import java.util.ArrayList;
import java.util.List;

public class ReportServiceImpl implements ReportService {

    private List<Book> books;

    public ReportServiceImpl(List<Book> books) {
        this.books = books;
    }

    @Override
    public List<ReportBookAuthorDTO> getBooksByAuthor(Author author) {
        List<ReportBookAuthorDTO> result = new ArrayList<>();
        for (BookAuthor bookAuthors : bookAuthor) {
            if (bookAuthor.getAuthor().equals(author)) {
                Book book = bookAuthor.getBook();
                result.add(new ReportBookAuthorDTO(author.getId(), book, author));
            }
        }
        return result;
    }



    @Override
    public List<BookGenres> getBooksByGenre(String genre) {
        List<BookGenres> result = new ArrayList<>();
        for (Book book : books) {
            if (book.equals(genre)) {
                result.add(book);
            }
        }
        return result;
    }
}
