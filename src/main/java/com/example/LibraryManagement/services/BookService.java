package com.example.LibraryManagement.services;

import com.example.LibraryManagement.dtos.BookResponseDTO;
import com.example.LibraryManagement.entities.Book;
import com.example.LibraryManagement.repositories.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepo bookrepo;

    public void addBook(Book book){
        bookrepo.save(book);
    }

    public List<BookResponseDTO> findBookByKeyword(String author){
        List<Book> books = bookrepo.findByKeyword(author);

        return books.stream().map((book) -> new BookResponseDTO(
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getTotalCopies(),
                book.getAvailableCopies()
        )).collect(Collectors.toList());
    }

    public void addMoreCopies(String name,int copies){
        if(copies < 1){
            throw new RuntimeException("Invalid copies size");
        }
        Book book = bookrepo.searchByKeyword(name).orElseThrow(() -> new RuntimeException("Book of this title not found"));

        book.setTotalCopies(book.getTotalCopies() + copies);
        book.setAvailableCopies(book.getAvailableCopies() + copies);

        bookrepo.save(book);
    }

    public void reportDamagedBook(String title,int copies){
        if(copies < 1){
            throw new RuntimeException("Invalid copies size");
        }

        Book book = bookrepo.searchByKeyword(title).orElseThrow(() -> new RuntimeException("Book of this title not found"));

        if(book.getAvailableCopies() - copies < 0){
            throw new RuntimeException("Invalid number of copies");
        }

        book.setAvailableCopies(book.getAvailableCopies()-copies);
        book.setTotalCopies(book.getTotalCopies()-copies);
        bookrepo.save(book);
    }
}
