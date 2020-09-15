package ru.kalashnikofffs.eCare.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contracts")
@NamedQueries(
        {
                @NamedQuery(name = "Contract.getAllContracts", query = "SELECT cnt FROM Contract cnt"),
                @NamedQuery(name = "Contract.findContractByNumber", query = "SELECT cnt FROM Contract cnt WHERE cnt.number = :number"),
                @NamedQuery(name = "Contract.getAllContractsForClient", query = "SELECT cnt FROM Contract cnt WHERE cnt.client.id = :clientId"),
        })
public class Contract implements Serializable {

    private Long id;
    private Long number;
    private Boolean blockedByClient = false;
    private Boolean blockedByOperator = false;
    private Tariff tariff;
    private Client client;
    private Set<Option> options = new HashSet<>();

    public Contract() {
    }

    public Contract(Long number, Tariff tariff, Client client, boolean isBlockedByClient, boolean isBlockedByOperator) {
        this.number = number;
        this.tariff = tariff;
        this.client = client;
        this.blockedByClient = isBlockedByClient;
        this.blockedByOperator = isBlockedByOperator;
    }

    @Id
    @Column(name = "contract_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "number")
    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    @ManyToOne
    @JoinColumn(name = "client_id")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable
            (
                    name="connected_options",
                    joinColumns={ @JoinColumn(name="contract_id", referencedColumnName="contract_id") },
                    inverseJoinColumns={ @JoinColumn(name="option_id", referencedColumnName="option_id") }
            )
    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public void addOption(Option option) {
        this.options.add(option);
    }

    public void deleteOption(Option option) {
        this.options.remove(option);
    }

    @Column(name = "blocked_by_client")
    public Boolean isBlockedByClient() {
        return blockedByClient;
    }

    public void setBlockedByClient(Boolean blockedByClient) {
        this.blockedByClient = blockedByClient;
    }

    @Column(name = "blocked_by_operator")
    public Boolean isBlockedByOperator() {
        return blockedByOperator;
    }

    public void setBlockedByOperator(Boolean blockedByOperator) {
        this.blockedByOperator = blockedByOperator;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "number=" + number +
                ", tariff=" + tariff +
                ", isBlockedByClient=" + blockedByClient +
                ", isBlockedByOperator=" + blockedByOperator +
                ", client=" + client +
                '}';
    }

}
