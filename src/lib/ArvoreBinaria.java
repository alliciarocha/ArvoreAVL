package lib;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class ArvoreBinaria<T> implements IArvoreBinaria<T> {

    protected static class No<T> {
        T valor;
        No<T> esquerdo;
        No<T> direito;
        int altura; // Adicionado para AVL

        public No(T valor) {
            this.valor = valor;
            this.esquerdo = null;
            this.direito = null;
            this.altura = 0; // Altura inicial de um novo nó
        }

        public T getValor() {
            return valor;
        }

        public No<T> getEsquerdo() {
            return esquerdo;
        }

        public void setEsquerdo(No<T> esquerdo) {
            this.esquerdo = esquerdo;
        }

        public No<T> getDireito() {
            return direito;
        }

        public void setDireito(No<T> direito) {
            this.direito = direito;
        }

        public int getAltura() {
            return altura;
        }

        public void setAltura(int altura) {
            this.altura = altura;
        }
    }

    private No<T> raiz;
    protected Comparator<T> comparador; // Tornando protected para ArvoreAVL acessar

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
        raiz = adicionarRecursivo(raiz, novoValor);
    }

    /**
     * Método auxiliar recursivo para adicionar um novo valor à árvore.
     *
     * @param noAtual   O nó atual na recursão.
     * @param novoValor O valor a ser inserido.
     * @return O nó raiz da subárvore (possivelmente alterado após inserção).
     */
    protected No<T> adicionarRecursivo(No<T> noAtual, T novoValor) {
        if (noAtual == null) {
            return new No<>(novoValor);
        }

        int comparacao = comparador.compare(novoValor, noAtual.valor);

        if (comparacao < 0) {
            noAtual.esquerdo = adicionarRecursivo(noAtual.esquerdo, novoValor);
        } else if (comparacao > 0) {
            noAtual.direito = adicionarRecursivo(noAtual.direito, novoValor);
        } else {
            // Valor duplicado, não fazemos nada
            return noAtual;
        }
        return noAtual; // Retorna o nó atualizado (para as chamadas recursivas)
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
            } else if (comparacao < 0) {
                atual = atual.esquerdo;
            } else {
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
            } else if (comparacao < 0) {
                atual = atual.getEsquerdo();
            } else {
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
     * @param no         o nó atual sendo analisado
     * @param valor      o valor a ser pesquisado
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
     * @param valor o valor do nó a ser removido
     * @return o valor do nó removido, ou null se o nó não for encontrado
     */
    @Override
    public T remover(T valor) {
        raiz = removerRecursivo(raiz, valor);
        return (raiz != null) ? pesquisar(valor) : null;
    }

    /**
     * Método auxiliar recursivo para remover um nó da árvore binária.
     *
     * @param noAtual O nó atual na recursão.
     * @param valor   O valor a ser removido.
     * @return O nó raiz da subárvore (possivelmente alterado após a remoção).
     */
    protected No<T> removerRecursivo(No<T> noAtual, T valor) {
        if (noAtual == null) {
            return null; // Valor não encontrado
        }

        int comparacao = comparador.compare(valor, noAtual.valor);

        if (comparacao < 0) {
            noAtual.esquerdo = removerRecursivo(noAtual.esquerdo, valor);
        } else if (comparacao > 0) {
            noAtual.direito = removerRecursivo(noAtual.direito, valor);
        } else { // Encontrou o nó a ser removido
            if (noAtual.esquerdo == null) {
                return noAtual.direito;
            } else if (noAtual.direito == null) {
                return noAtual.esquerdo;
            }

            // Nó com dois filhos: encontrar o sucessor (menor nó na subárvore direita)
            No<T> sucessor = encontrarMinimo(noAtual.direito);
            noAtual.valor = sucessor.valor;
            noAtual.direito = removerRecursivo(noAtual.direito, sucessor.valor);
        }
        return noAtual; // Retorna o nó atualizado (para as chamadas recursivas)
    }

    private No<T> encontrarMinimo(No<T> no) {
        while (no.esquerdo != null) {
            no = no.esquerdo;
        }
        return no;
    }

    /**
     * Método que retorna a altura da árvore binária.
     *
     * @return altura
     */
    @Override
    public int altura() {
        return altura(raiz) - 1;
    }

    /**
     * Método auxiliar recursivo que calcula a altura de um nó da árvore binária.
     *
     * @param no o nó a partir do qual a altura será calculada
     * @return a altura do nó na árvore
     */
    protected int altura(No<T> no) { // Tornando protected para ArvoreAVL acessar
        if (no == null) {
            return 0;
        }
        int alturaLadoEsquerdo = altura(no.esquerdo);
        int alturaLadoDireito = altura(no.direito);
        if (alturaLadoEsquerdo > alturaLadoDireito) {
            return alturaLadoEsquerdo + 1;
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
     * @param sb   o StringBuilder usado para acumular os valores
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
    public String caminharPosOrdem() {
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
}
