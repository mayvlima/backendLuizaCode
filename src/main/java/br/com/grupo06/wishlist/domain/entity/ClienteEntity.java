package br.com.grupo06.wishlist.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "cliente")
public class ClienteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(length = 250, nullable = false)
    private String nome;

    @Column(length = 14, nullable = false)
    private String cpf;

    @Column(length = 14, nullable = false)
    private String telefone;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 250, nullable = false)
    private String logradouro;

    @Column(length = 15, nullable = false)
    private String numero;

    @Column(length = 100, nullable = false)
    private String bairro;

    @Column(length = 50)
    private String complemento;

    @Column(length = 100, nullable = false)
    private String cidade;

    @Column(length = 50, nullable = false)
    private String estado;

    @Column(length = 10, nullable = false)
    private String cep;

    @ManyToMany(cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "wishlist",
            joinColumns = {@JoinColumn(name = "cliente_id")},
            inverseJoinColumns = {@JoinColumn(name = "produto_id")})
    private List<ProdutoEntity> produtos;

    public ClienteEntity(){
    }

    public ClienteEntity(String nome, String cpf, String telefone, String email, String logradouro, String numero, String bairro, String complemento, String cidade, String estado, String cep) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @JsonIgnore
    public List<ProdutoEntity> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoEntity> produtos) {
        this.produtos = produtos;
    }
}
