package br.com.nelsonssoares.springreview.domain.dtos.v1;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;


public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {

    private Long id;
    private String author;
    private LocalDateTime launchDate;
    private String title;
    private Double price;

    public BookDTO(Long id, String author, LocalDateTime launchDate, String title, Double price) {
        this.id = id;
        this.author = author;
        this.launchDate = launchDate;
        this.title = title;
        this.price = price;
    }

    public BookDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getPublicationDate() {
        return launchDate;
    }

    public void setPublicationDate(LocalDateTime launchDate) {
        this.launchDate = launchDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
