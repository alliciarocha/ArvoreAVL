package appLibrary.model.entities;

/**
 * A classe Usuario representa um usuário dentro do sistema da biblioteca,
 * armazenando informações essenciais para o gerenciamento de empréstimos e acessos.
 */
public class Usuario {
    private String nome;
    private Livro livroAlugado;

    public Usuario(String nome) {
        this.nome = nome;
        this.livroAlugado = null;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public Livro getLivroAlugado() { return livroAlugado; }

    public void setLivroAlugado(Livro livroAlugado) { this.livroAlugado = livroAlugado; }

    public void alugarLivro(Livro livro) {
        if (livro.Disponibilidade()) {
            livroAlugado = livro;
            livro.alterarDisponibilidade(false);
            System.out.print(nome + "alugou o livro: " + livro.getTitulo());
        } else {
            System.out.print("O livro" + livro.getTitulo() + "não está disponível.");
        }
    }

    public void devolverLivro() {
        if (livroAlugado != null) {
            livroAlugado.alterarDisponibilidade(true);
            System.out.print(nome + "devolveu o livro: " + livroAlugado.getTitulo());
            livroAlugado = null;
        }
        else{
            System.out.print(nome + "não tem nenhum livro para devolver.");
        }
    }
}
