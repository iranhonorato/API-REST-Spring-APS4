package com.aps4.APS4.autenticacao;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(unique = true, nullable = false, length = 180)
    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 60)
    private String password;


    public Usuario() {}


    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public Integer getId() {return this.id;}
    public String getEmail() {return this.email;}
    public String getPassword() {return this.password;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}

}
