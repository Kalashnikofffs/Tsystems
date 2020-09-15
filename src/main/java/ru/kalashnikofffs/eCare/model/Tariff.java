package ru.kalashnikofffs.eCare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonPOJOBuilder
@Table(name = "tariffs")
@NamedQueries(
        {
                @NamedQuery(name = "Tariff.getAllTariffs", query = "SELECT t FROM Tariff t"),
                @NamedQuery(name = "Tariff.deleteAllTariffs", query = "DELETE FROM Tariff"),
        })
public class Tariff  implements Serializable {

    private Long id;
    private String title;
    private Integer price;
    private Set<Option> options  = new HashSet<>();

    public Tariff() {
    }

    public Tariff(String title, int price) {
        this.title = title;
        this.price = price;
    }

    @Id
    @Column(name = "tariff_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "tariff_title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "tariff_price")
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @JsonManagedReference
    @OneToMany( mappedBy = "tariff", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public void addOption(Option option) {
        this.options.add(option);
    }

    @Override
    public String toString() {
        return "Tariff [title=" + title + ", price=" + price + "]";
    }
}
