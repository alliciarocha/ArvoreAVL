package lib;

import java.util.Comparator;

public class ArvoreAVL<T> extends ArvoreBinaria<T> {

    public ArvoreAVL(Comparator<T> comparator) {
        super(comparator);
    }

    /**
     * Obtém a altura de um nó.
     *
     * @param n O nó.
     * @return A altura do nó (-1 se nulo).
     */
    private int alturaNo(No<T> n) {
        return n == null ? -1 : n.altura;
    }

    /**
     * Atualiza a altura de um nó.
     *
     * @param n O nó a ser atualizado.
     */
    private void atualizarAltura(No<T> n) {
        n.altura = 1 + Math.max(alturaNo(n.esquerdo), alturaNo(n.direito));
    }

    /**
     * Obtém o fator de balanceamento de um nó.
     *
     * @param n 
     * @return O fator de balanceamento
     */
    private int fatorBalanceamento(No<T> n) {
        return n == null ? 0 : alturaNo(n.esquerdo) - alturaNo(n.direito);
    }

    // etapa de rotação à esquerda
    private No<T> rotacaoEsquerda(No<T> r) {
        No<T> f = r.direito;
        r.direito = f.esquerdo;
        f.esquerdo = r;
        atualizarAltura(r);
        atualizarAltura(f);
        return f;
    }

    // etapa de rotação à direita
    private No<T> rotacaoDireita(No<T> r) {
        No<T> f = r.esquerdo;
        r.esquerdo = f.direito;
        f.direito = r;
        atualizarAltura(r);
        atualizarAltura(f);
        return f;
    }

    // etapa de rotação Esquerda-Direita
    private No<T> rotacaoEsquerdaDireita(No<T> r) {
        r.esquerdo = rotacaoEsquerda(r.esquerdo);
        return rotacaoDireita(r);
    }

    // etapa de rotação Direita-Esquerda
    private No<T> rotacaoDireitaEsquerda(No<T> r) {
        r.direito = rotacaoDireita(r.direito);
        return rotacaoEsquerda(r);
    }

    /**
     * Método auxiliar recursivo para adicionar um novo valor e balancear a árvore AVL.
     * Sobrescreve o método da classe pai.
     *
     * @param noAtual   O nó atual na recursão
     * @param novoValor O valor a ser inserido.
     * @return O nó raiz da subárvore
     */
    @Override
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

        // Atualizar a altura do nó atual
        atualizarAltura(noAtual);

        // Obter o fator de balanceamento para verificar o balanceamento
        int balanceamento = fatorBalanceamento(noAtual);

        // Casos de desbalanceamento
        // Esquerda-Esquerda
        if (balanceamento > 1 && comparador.compare(novoValor, noAtual.esquerdo.valor) < 0) {
            return rotacaoDireita(noAtual);
        }

        // Direita-Direita
        if (balanceamento < -1 && comparador.compare(novoValor, noAtual.direito.valor) > 0) {
            return rotacaoEsquerda(noAtual);
        }

        // Esquerda-Direita
        if (balanceamento > 1 && comparador.compare(novoValor, noAtual.esquerdo.valor) > 0) {
            return rotacaoEsquerdaDireita(noAtual);
        }

        // Direita-Esquerda
        if (balanceamento < -1 && comparador.compare(novoValor, noAtual.direito.valor) < 0) {
            return rotacaoDireitaEsquerda(noAtual);
        }

        return noAtual;
    }

    /**
     * Método auxiliar recursivo para remover um nó e balancear a árvore AVL.
     * Sobrescreve o método da classe pai.
     *
     * @param noAtual O nó atual na recursão.
     * @param valor   O valor a ser removido.
     * @return O nó raiz da subárvore (possivelmente alterado após balanceamento).
     */
    @Override
    protected No<T> removerRecursivo(No<T> noAtual, T valor) {
        if (noAtual == null) {
            return null;
        }

        int comparacao = comparador.compare(valor, noAtual.valor);

        if (comparacao < 0) {
            noAtual.esquerdo = removerRecursivo(noAtual.esquerdo, valor);
        } else if (comparacao > 0) {
            noAtual.direito = removerRecursivo(noAtual.direito, valor);
        } else {
            if ((noAtual.esquerdo == null) || (noAtual.direito == null)) {
                noAtual = (noAtual.esquerdo == null) ? noAtual.direito : noAtual.esquerdo;
            } else {
                No<T> temp = encontrarMinimo(noAtual.direito);
                noAtual.valor = temp.valor;
                noAtual.direito = removerRecursivo(noAtual.direito, temp.valor);
            }
        }

        if (noAtual == null) {
            return null;
        }

        // Atualizar a altura do nó atual
        atualizarAltura(noAtual);

        // Obter o fator de balanceamento para verificar o balanceamento
        int balanceamento = fatorBalanceamento(noAtual);

        // Casos de desbalanceamento
        // Esquerda-Esquerda
        if (balanceamento > 1 && fatorBalanceamento(noAtual.esquerdo) >= 0) {
            return rotacaoDireita(noAtual);
        }

        // Esquerda-Direita
        if (balanceamento > 1 && fatorBalanceamento(noAtual.esquerdo) < 0) {
            return rotacaoEsquerdaDireita(noAtual);
        }

        // Direita-Direita
        if (balanceamento < -1 && fatorBalanceamento(noAtual.direito) <= 0) {
            return rotacaoEsquerda(noAtual);
        }

        // Direita-Esquerda
        if (balanceamento < -1 && fatorBalanceamento(noAtual.direito) > 0) {
            return rotacaoDireitaEsquerda(noAtual);
        }

        return noAtual;
    }

    /**
     * Encontra o nó com o menor valor em uma subárvore.
     *
     * @param no O nó raiz da subárvore.
     * @return O nó com o menor valor.
     */
    private No<T> encontrarMinimo(No<T> no) {
        No<T> atual = no;
        while (atual.esquerdo != null) {
            atual = atual.esquerdo;
        }
        return atual;
    }

    @Override
    protected int altura(No<T> no) {
        return alturaNo(no) + 1; // Ajuste para a definição de altura da ArvoreBinaria
    }
}
