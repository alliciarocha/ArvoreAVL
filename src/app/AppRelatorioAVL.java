/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;
//Lembre-se de ajustar os imports!!!!!
import lib.ArvoreAVL;
import lib.ArvoreBinariaExemplo;
import lib.IArvoreBinaria;
import lib.ArvoreBinaria;

/**
 *
 * @author victoriocarvalho
 * 
 * Classe principal do aplicativo a ser utilizado para fazer o relatório do trabalho 
 * de árvore AVL
 */
public class AppRelatorioAVL {
    public static void main(String[] args) {

        GeradorDeArvores gerador = new GeradorDeArvores();
        ComparadorAlunoPorMatricula comparador = new ComparadorAlunoPorMatricula();
        IArvoreBinaria<Aluno> arv;

        System.out.println("\n");
        arv = new ArvoreAVL(comparador);
        gerador.geraArvoreDegenerada(100, arv);
        System.out.println("Árvore AVL Degenerada");
        System.out.println("Quantidade de Nós: " + arv.quantidadeNos()+ " Altura: " + arv.altura());
        System.out.println("\n");

        arv = new ArvoreBinaria(comparador);
        gerador.geraArvoreDegenerada(100, arv);
        System.out.println("Árvore Binária Degenerada");
        System.out.println("Quantidade de Nós: " + arv.quantidadeNos()+ " Altura: " + arv.altura());
        System.out.println("\n");

        // arv = new ArvoreBinariaExemplo(comparador);
        // gerador.geraArvoreDegenerada(100, arv);
        // System.out.println("Árvore Degenerada Criada");
        // System.out.println("Quantidade de Nós: " + arv.quantidadeNos()+ " Altura: " + arv.altura());

        arv = new ArvoreAVL(comparador);
        gerador.geraArvoreDegenerada(1000, arv);
        System.out.println("Árvore AVL Criada");
        System.out.println("Quantidade de Nós: " + arv.quantidadeNos()+ " Altura: " + arv.altura());
        System.out.println("\n");

        arv = new ArvoreBinaria(comparador);
        gerador.geraArvoreDegenerada(1000, arv);
        System.out.println("Árvore Binária Degenerada");
        System.out.println("Quantidade de Nós: " + arv.quantidadeNos()+ " Altura: " + arv.altura());
        System.out.println("\n");

        // arv = new ArvoreBinariaExemplo(comparador);
        // gerador.geraArvoreDegenerada(1000, arv);
        // System.out.println("Árvore Degenerada Criada");
        // System.out.println("Quantidade de Nós: " + arv.quantidadeNos()+ " Altura: " + arv.altura());
        
        arv = new ArvoreAVL(comparador);
        gerador.geraArvoreDegenerada(10000, arv);
        System.out.println("Árvore AVL Degenerada");
        System.out.println("Quantidade de Nós: " + arv.quantidadeNos()+ " Altura: " + arv.altura());
        System.out.println("\n");

        arv = new ArvoreBinaria(comparador);
        gerador.geraArvoreDegenerada(10000, arv);
        System.out.println("Árvore Binária Degenerada");
        System.out.println("Quantidade de Nós: " + arv.quantidadeNos()+ " Altura: " + arv.altura());

        // arv = new ArvoreBinariaExemplo(comparador);
        // gerador.geraArvoreDegenerada(10000, arv);
        // System.out.println("Árvore Degenerada Criada");
        // System.out.println("Quantidade de Nós: " + arv.quantidadeNos()+ " Altura: " + arv.altura());
    }
}
