package appLibrary.model.entities;

/**
 * A classe Livro representa um livro, contendo informações como título e autor.
 * Ela oferece métodos para acessar e modificar esses atributos, permitindo o armazenamento
 * e a manipulação de dados relacionados ao livro.
 */
public class Livro {
    private Integer id;
    private String titulo;
    private String autor;
    private String categoria;
    private boolean disponivel;

    public Livro(Integer id, String titulo, String autor, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.id = id;
        this.categoria = categoria;
        this.disponivel = true;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getAutor() {
        return autor;
    }
    public Integer getId() {
        return id;
    }
    public String getCategoria() {
        return categoria;
    }
    public boolean Disponibilidade() {
        return disponivel;
    }
    public void alterarDisponibilidade(boolean disponivel) { this.disponivel = disponivel;}

    @Override
    public String toString() {
        return ("id=" + id +
                ", nome=" + titulo +
                ", autor=" + autor +
                ", categoria=" + categoria
        );
    }
}

