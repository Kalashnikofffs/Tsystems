package ru.kalashnikofffs.eCare.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
@NamedQueries(
        {
                @NamedQuery(name = "Client.getAllClients", query = "SELECT DISTINCT cl FROM Client cl "+
                        "LEFT JOIN cl.roles rols WHERE rols.title = 'ROLE_USER'"),
                @NamedQuery(name = "Client.findClientByLoginAndPassword", query = "SELECT cl FROM Client cl WHERE cl.email = :login AND cl.password = :password"),
                @NamedQuery(name = "Client.findClientByNumber", query = "SELECT cnt.client FROM Contract cnt WHERE cnt.number = :number"),
                @NamedQuery(name = "Client.findClientByEmail", query = "SELECT cl FROM Client cl WHERE cl.email = :email"),
        })
public class Client implements Serializable {

    private Long id;
    private String name;
    private String surname;
    private Date birthDate;
    private Long passport;
    private String address;
    private int balance = 0;
    private String email;
    private String password;

    private String fullName;
    private String confirmPassword;

    private Set<Role> roles = new HashSet<>();
    private Set<Contract> contracts = new HashSet<>();


    public Client() {
    }

    public Client(String name, String surname, Date birthDate, Long passport, String address, String email, String password, int balance) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.passport = passport;
        this.address = address;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Transient
    public String getFullName() {
        return name + " " + surname;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(name = "birthDate")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "passport")
    public Long getPassport() {
        return passport;
    }

    public void setPassport(Long passport) {
        this.passport = passport;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "balance")
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addAmountToBalance(int amount) {
        this.balance += amount;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @ManyToMany
    @JoinTable(name = "client_role",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(mappedBy = "client", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public void addContract(Contract contract) {
        this.contracts.add(contract);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", passport=" + passport +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
