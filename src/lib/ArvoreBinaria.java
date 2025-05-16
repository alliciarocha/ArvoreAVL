package lib;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import org.w3c.dom.Node;

public class ArvoreBinaria<T> implements IArvoreBinaria<T> {

    private No<T> raiz;
    private Comparator<T> comparador;

    public ArvoreBinaria(Comparator<T> comparador) {
        this.comparador = comparador;
    }

    /**
     * Método que adiciona um novo valor à árvore binária.
     * 
     * @param novoValor o valor que será adicionado à árvore binária
     */
    @Override
    public void adicionar(T novoValor) {
        No<T> novoNo = new No<>(novoValor);
        if (raiz == null) {
            raiz = novoNo;
            return;
        }
        No<T> atual = raiz;
        while (true) {
            int comparacao = comparador.compare(novoValor, atual.valor);
            if (comparacao < 0) {
                if (atual.esquerdo == null) {
                    atual.esquerdo = novoNo;
                    return;
                }
                atual = atual.esquerdo;
            } else if (comparacao > 0) {
                if (atual.direito == null) {
                    atual.direito = novoNo;
                    return;
                }
                atual = atual.direito;
            } else {
                // Se for igual... o que faremos??? Será admitido alguns valores iguais? Vai ser acreditado que não teremos que nos preocupar com isso?
                return;
            }
        }
    }

    /**
     * Método para pesquisar um valor na árvore, utilizando o comparador padrão
     * 
     * @param valor o valor a ser pesquisado na árvore
     * @return o valor do nó encontrado, ou null se o valor não for encontrado
     */
    @Override
    public T pesquisar(T valor) {
        No<T> atual = raiz;

        while (atual != null) {
            int comparacao = comparador.compare(valor, atual.valor);

            if (comparacao == 0) {
                return atual.valor;
            }
            else if (comparacao < 0) {
                atual = atual.esquerdo;
            }
            else {
                atual = atual.direito;
            }
        }
        return null;
    }

    /**
     * Método para pesquisar um valor na árvore, utilizando comparador personalizado
     * 
     * @param valor o valor a ser pesquisado na árvore
     * @return o valor do nó encontrado, ou null se o valor não for encontrado
     */
    @Override
    public T pesquisar(T valor, Comparator comparador) {
        if (!comparador.getClass().equals(this.comparador.getClass())) {
            return pesquisarLinear(valor, comparador);
        }

        No<T> atual = raiz;

        while (atual != null) {
            int comparacao = comparador.compare(valor, atual.getValor());

            if (comparacao == 0) {
                return atual.getValor();
            }
            else if (comparacao < 0) {
                atual = atual.getEsquerdo();
            }
            else {
                atual = atual.getDireito();
            }
        }
        return null;
    }
    
    /**
     * Método para realizar a pesquisa linear (quando o comparador é diferente do usado na árvore)
     * 
     * @param valor 
     * @param comparador
     * @return o valor do nó encontrado ou null caso não seja encontrado
     */
    private T pesquisarLinear(T valor, Comparator comparador) {
        return pesquisarPreOrdem(raiz, valor, comparador);
    }

    /**
     * Método recursivo para realizar a pesquisa usando travessia pré-ordem
     * A travessia pré-ordem percorre o nó atual, depois o filho esquerdo e depois o filho direito
     * 
     * @param no o nó atual sendo analisado
     * @param valor o valor a ser pesquisado
     * @param comparador o comparador utilizado para a comparação
     * @return o valor do nó encontrado ou null caso não encontre
     */
    private T pesquisarPreOrdem(No<T> no, T valor, Comparator comparador) {
        if (no == null) {
            return null;  
        }
    
        int comparacao = comparador.compare(valor, no.getValor());
        if (comparacao == 0) {
            return no.getValor();  
        }
    
        T resultadoEsquerdo = pesquisarPreOrdem(no.getEsquerdo(), valor, comparador);
        if (resultadoEsquerdo != null) {
            return resultadoEsquerdo; 
        }
    
        return pesquisarPreOrdem(no.getDireito(), valor, comparador); 
    }

    /**
     * Remove um nó da árvore binária e retorna o valor do nó removido.
     * 
     * 
     * @param valor o valor do nó a ser removido
     * @return o valor do nó removido, ou null se o nó não for encontrado
     */
    @Override
    public T remover(T valor) {
        No<T> atual = raiz;
        No<T> pai = raiz;
        boolean filho_esquerdo = true;

        while (atual != null && comparador.compare(valor, atual.getValor()) != 0) {
            pai = atual;
            if (comparador.compare(valor, atual.getValor()) < 0)
            {
                filho_esquerdo = true;
                atual = atual.getEsquerdo();
            } else {
                filho_esquerdo = false;
                atual = atual.getDireito();
            }
        }

        if (atual == null) {return null;}

        // CASO 1 : *** NÓ SEM FILHOS (É UMA FOLHA) ***
        if (atual.getEsquerdo() == null && atual.getDireito() == null) {
            if (atual == raiz) raiz = null;
            else if (filho_esquerdo) pai.setEsquerdo(null);
            else pai.setDireito(null);
        }
        
        // CASO 2 : *** NÓ COM FILHOS APENAS NA ESQUERDA ***
        else if (atual.getDireito() == null) {
            if (atual == raiz) {
                raiz = atual.getEsquerdo();
            } else if (filho_esquerdo) {
                pai.setEsquerdo(atual.getEsquerdo());
            } else {
                pai.setDireito(atual.getEsquerdo());
            }
        }

        // CASO 3 : *** NÓ COM FILHOS APENAS NA DIREITA ***
        else if (atual.getEsquerdo() == null) {
            if (atual == raiz) {
                raiz = atual.getDireito();
            } else if (filho_esquerdo) {
                pai.setEsquerdo(atual.getDireito());
            } else {
                pai.setDireito(atual.getDireito());
            }
        }

        // CASO 4 : *** NÓ COM FILHOS EM AMBOS LADOS ***
        else {
            No<T> successor = noSucessor(atual);
            if (atual == raiz) raiz = successor;
            else if (filho_esquerdo) pai.setEsquerdo(successor);
            else pai.setDireito(successor);
            successor.setEsquerdo(atual.getEsquerdo());
        }

        return atual.getValor(); 
    }

    /**
     * Encontra o sucessor de um nó a ser apagado em uma árvore binária de busca.
     * 
     * @param apagar 
     * @return sucessor
     */
    private No<T> noSucessor(No<T> apagar) { 
        No<T> paiSucessor = apagar;
        No<T> sucessor = apagar;
        No<T> atual = apagar.getDireito();
    
        while (atual != null) {
            paiSucessor = sucessor;
            sucessor = atual;
            atual = atual.getEsquerdo();
        }
    
        if (sucessor != apagar.getDireito()) {
            paiSucessor.setEsquerdo(sucessor.getDireito());
            sucessor.setDireito(apagar.getDireito());
        }

        return sucessor;    
    }

    /**
     * Método que retorna a altura da árvore binária.
     * 
     * @return altura
     */
    @Override
    public int altura() {
        return altura(raiz) -1;
    }

    /**
     * Método auxiliar recursivo que calcula a altura de um nó da árvore binária.
     * 
     * @param no o nó a partir do qual a altura será calculada
     * @return a altura do nó na árvore
     */
    private int altura(No<T> no) {
        if (no == null) {
            return 0;
        }
        int alturaLadoEsquerdo = altura(no.esquerdo);
        int alturaLadoDireito = altura(no.direito);
        if (alturaLadoEsquerdo > alturaLadoDireito) {
            return alturaLadoEsquerdo +1 ;
        } else {
            return alturaLadoDireito + 1;
        }
    }

    /**
     * Método que retorna a quantidade de nós da árvore binária.
     * 
     * @return quantidadedeNos
     */
    @Override
    public int quantidadeNos() {
        return quantidadeNos(raiz);
    }
    /**
     * Método auxiliar recursivo que calcula a quantidade de um nós da árvore binária.
     * 
     * @param no 
     * @return quantidadeNos
     */
    private int quantidadeNos(No<T> no) {
        if (no == null) {
            return 0;
        }
        return 1 + quantidadeNos(no.esquerdo) + quantidadeNos(no.direito);
    }

    /**
     * Retorna uma String contendo os elementos da árvore em nível (Raiz -> Filhos -> Níveis subsequentes).
     *
     * @return uma String com os elementos da árvore em nível
     */
    @Override
    public String caminharEmNivel() {
        StringBuilder sb = new StringBuilder();
        if (raiz != null) {
            Queue<No<T>> fila = new LinkedList<>();
            fila.add(raiz);  
            caminharEmNivel(fila, sb); 
        }
        return sb.toString().trim();
    }
    /**
     * Método auxiliar recursivo que realiza o percurso em nível na árvore binária,
     * adicionando os valores visitados ao StringBuilder fornecido.
     * 
     * @param fila a fila que contém os nós da árvore a serem visitados
     * @param sb o StringBuilder usado para acumular os valores
     */
    private void caminharEmNivel(Queue<No<T>> fila, StringBuilder sb) {
        if (fila.isEmpty()) return;

        No<T> atual = fila.poll();  
        sb.append(atual.getValor()).append(" "); 
    
        if (atual.getEsquerdo() != null) fila.add(atual.getEsquerdo());
        if (atual.getDireito() != null) fila.add(atual.getDireito());

        caminharEmNivel(fila, sb);
    }
    
    /**
     * Retorna uma String contendo os elementos da árvore em ordem (Esquerda -> Raiz -> Direita).
     *
     * @return uma String com os elementos da árvore em ordem
     */
    @Override
    public String caminharEmOrdem() {
        StringBuilder sb = new StringBuilder();
        caminharEmOrdem(raiz, sb);
        return sb.toString().trim();
    }
    /**
     * Método auxiliar recursivo que realiza o percurso em ordem na árvore binária,
     * adicionando os valores visitados ao StringBuilder fornecido.
     *
     * @param no o nó atual da árvore sendo visitado
     * @param sb o StringBuilder usado para acumular os valores
     */
    private void caminharEmOrdem(No<T> no, StringBuilder sb) {
        if (no == null) return;

        caminharEmOrdem(no.getEsquerdo(), sb);
        sb.append(no.getValor()).append(" ");
        caminharEmOrdem(no.getDireito(), sb);
    }

    /**
     * Método auxiliar recursivo que realiza o percurso em pós-ordem na árvore binária,
     * adicionando os valores visitados ao StringBuilder fornecido.
     *
     * @param no o nó atual da árvore sendo visitado.
     * @param sb o StringBuilder usado para acumular os valores.
     */
    public String caminharPosOrdem(){
        StringBuilder sb = new StringBuilder();
        caminharPosOrdem(raiz, sb);
        return sb.toString().trim();
    }

    /**
     * Método auxiliar recursivo que realiza o percurso em pós-ordem na árvore binária,
     * adicionando os valores visitados ao StringBuilder fornecido.
     *
     * @param no o nó atual da árvore sendo visitado.
     * @param sb o StringBuilder usado para acumular os valores.
     */
    private void caminharPosOrdem(No<T> no, StringBuilder sb) {
        if (no == null) return;

        caminharPosOrdem(no.getEsquerdo(), sb);
        caminharPosOrdem(no.getDireito(), sb);
        sb.append(no.getValor()).append(" ");
    }

    public No<T> adcionar(No<T> raiz2, No<T> novo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'adcionar'");
    }

}
