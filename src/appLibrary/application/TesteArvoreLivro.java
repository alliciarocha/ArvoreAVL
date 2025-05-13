package appLibrary.application;

import appLibrary.model.entities.Livro;
import appLibrary.model.comparators.ComparadorID;
import appLibrary.model.comparators.ComparadorNome;
import lib.ArvoreBinaria;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

// Adicionando Generics à classe Livro e mantendo o ID como Integer

public class TesteArvoreLivro {
    public static void main(String[] args) {
        ArvoreBinaria<Livro> arvore = new ArvoreBinaria<>(new ComparadorID());
        Scanner scanner = new Scanner(System.in);
        int opcao;

        // Pré-popula a árvore com alguns livros para facilitar os testes
        Livro l1 = new Livro(3,  "livro 3",  "autor 3",  "c3");
        Livro l2 = new Livro(7,  "livro 7",  "autor 7",  "c7");
        Livro l3 = new Livro(5,  "livro 5",  "autor 5",  "c5");
        Livro l4 = new Livro(4,  "livro 4",  "autor 4",  "c4");
        Livro l5 = new Livro(8,  "livro 8",  "autor 8",  "c8");
        Livro l6 = new Livro(13, "livro 13", "autor 13", "c13");
        Livro l7 = new Livro(11, "livro 11", "autor 11", "c11");
        Livro l8 = new Livro(12, "livro 12", "autor 12", "c12");
        Livro l9 = new Livro(6, "livro 6", "autor 6", "c6");

        arvore.adicionar(l1);
        arvore.adicionar(l2);
        arvore.adicionar(l3);
        arvore.adicionar(l4);
        arvore.adicionar(l5);
        arvore.adicionar(l6);
        arvore.adicionar(l7);
        arvore.adicionar(l8);
        arvore.adicionar(l9);


        do {
            System.out.println("\n--- Menu de Teste da Árvore de Livros ---");
            System.out.println("1.  Imprimir em Ordem");
            System.out.println("2.  Imprimir em Nível");
            System.out.println("3.  Imprimir em Pós-Ordem");
            System.out.println("4.  Obter Altura da Árvore");
            System.out.println("5.  Pesquisar Livro por ID");
            System.out.println("6.  Pesquisar Livro por Nome");
            System.out.println("7.  Remover Livro por ID");
            System.out.println("8.  Adicionar Novo Livro");
            
            System.out.println("0.  Sair");
            System.out.print("Escolha a opção: ");

            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Por favor, digite um número inteiro.");
                scanner.next(); // Limpa o buffer do scanner
                opcao = -1; // Garante que o loop continue
                continue;
            }
            scanner.nextLine(); // Consome a nova linha após ler o número.

            switch (opcao) {
                case 1:
                    System.out.println("Livros em ordem: " + arvore.caminharEmOrdem());
                    break;
                case 2:
                    System.out.println("Livros em nível: " + arvore.caminharEmNivel());
                    break;
                case 3:
                    System.out.println("Livros em pós-ordem: " + arvore.caminharPosOrdem());
                    break;
                case 4:
                    System.out.println("Altura da árvore: " + arvore.altura());
                    break;
                case 5:
                    System.out.print("Digite o ID do livro a pesquisar: ");
                    int idPesquisa = scanner.nextInt();
                    scanner.nextLine(); // Consome a nova linha
                    Livro livroPesquisa = new Livro(idPesquisa, "", "", "");
                    Livro resultadoPesquisa = arvore.pesquisar(livroPesquisa);
                    if (resultadoPesquisa != null) {
                        System.out.println("Livro encontrado: " + resultadoPesquisa);
                    } else {
                        System.out.println("Livro não encontrado.");
                    }
                    break;

                case 6:
                    System.out.print("Digite o NOME do livro a pesquisar: ");
                    ComparadorNome comparadorNome = new ComparadorNome();

                    String nomePesquisa = scanner.nextLine();
                    scanner.nextLine(); // Consome a nova linha
                    Livro livroNomePesquisa = new Livro(null, nomePesquisa, "", "");
                    Livro resultadoNomePesquisa = arvore.pesquisar(livroNomePesquisa, comparadorNome);
                    if (resultadoNomePesquisa != null) {
                        System.out.println("Livro encontrado: " + resultadoNomePesquisa);
                    } else {
                        System.out.println("Livro não encontrado.");
                    }
                    break;
                    
                case 7:
                    System.out.print("Digite o ID do livro a remover: ");
                    int idRemocao = scanner.nextInt();
                    scanner.nextLine(); // Consome a nova linha
                    Livro livroRemocao = new Livro(idRemocao, "", "", "");
                    Livro removido = arvore.remover(livroRemocao);
                    if (removido != null) {
                        System.out.println("Livro removido com sucesso: " + removido);
                    } else {
                        System.out.println("Livro não encontrado para remoção.");
                    }
                    break;
                case 8:
                    System.out.print("Digite o ID do novo livro: ");
                    int novoId = scanner.nextInt();
                    scanner.nextLine(); // Consome a nova linha
                    System.out.print("Digite o título do novo livro: ");
                    String novoTitulo = scanner.nextLine();
                    System.out.print("Digite o autor do novo livro: ");
                    String novoAutor = scanner.nextLine();
                    System.out.print("Digite o gênero do novo livro: ");
                    String novoGenero = scanner.nextLine();
                    Livro novoLivro = new Livro(novoId, novoTitulo, novoAutor, novoGenero);
                    arvore.adicionar(novoLivro);
                    System.out.println("Livro adicionado com sucesso.");
                    break;
                case 0:
                    System.out.println("Saindo do programa.");
                    break;
                default:
                    if (opcao != -1)
                        System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}




