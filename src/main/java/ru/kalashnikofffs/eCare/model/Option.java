package ru.kalashnikofffs.eCare.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "options")
@NamedQueries(
        {
                @NamedQuery(name = "Option.getAllOptionsForTariff", query = "SELECT o FROM Option o WHERE o.tariff.id = :id"),
                @NamedQuery(name = "Option.deleteAllOptions", query = "DELETE FROM Option"),
                @NamedQuery(name = "Option.deleteAllOptionsForTariff", query = "DELETE FROM Option WHERE tariff.id = :id"),
        })
public class Option implements Serializable {

    private Long id;
    private String title;
    private Integer price;
    private Integer costOfConnection;
    private Tariff tariff;
    private Set<Option> dependentOptions = new HashSet<>();
    private Set<Option> incompatibleOptions = new HashSet<>();

    public Option() {
    }

    public Option(Tariff tariff, String title, Integer price, Integer costOfConnection) {
        this.tariff = tariff;
        this.title = title;
        this.price = price;
        this.costOfConnection = costOfConnection;
    }

    @Id
    @Column(name = "option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "option_title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "option_price")
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Column(name = "option_connection_price")
    public Integer getCostOfConnection() {
        return costOfConnection;
    }

    public void setCostOfConnection(Integer costOfConnection) {
        this.costOfConnection = costOfConnection;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "tariff_id")
    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable
            (
                    name = "dependent_options",
                    joinColumns = {@JoinColumn(name = "option_id", referencedColumnName = "option_id")},
                    inverseJoinColumns = {@JoinColumn(name = "dependent_option_id", referencedColumnName = "option_id")}
            )
    @JsonIgnore
    public Set<Option> getDependentOptions() {
        return dependentOptions;
    }

    public void setDependentOptions(Set<Option> dependentOptions) {
        this.dependentOptions = dependentOptions;
    }

    public void addDependentOption(Option option) {
        this.dependentOptions.add(option);
    }

    public void deleteDependentOption(Option option) {
        this.dependentOptions.remove(option);
    }

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable
            (
                    name = "incompatible_options",
                    joinColumns = {@JoinColumn(name = "option_id", referencedColumnName = "option_id")},
                    inverseJoinColumns = {@JoinColumn(name = "incompatible_option_id", referencedColumnName = "option_id")}
            )

    public Set<Option> getIncompatibleOptions() {
        return incompatibleOptions;
    }

    public void setIncompatibleOptions(Set<Option> incompatibleOptions) {
        this.incompatibleOptions = incompatibleOptions;
    }

    public void addIncompatibleOption(Option op) {
        this.incompatibleOptions.add(op);
    }

    public void deleteIncompatibleOption(Option op) {
        this.incompatibleOptions.remove(op);
    }

    @Override
    public String toString() {
        return "Option{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", costOfConnection=" + costOfConnection +
                '}';
    }
}
