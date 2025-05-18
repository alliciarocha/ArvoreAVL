package lib;

import java.util.Comparator;

public class ArvoreAVL <T> extends ArvoreBinaria<T>{

    public ArvoreAVL(Comparator<T> comparator) {
        super(comparator);
    }


    // etapa de rotação à esquerda
    private No<T> rotacaoEsquerda(No<T> r){
        No<T> f = r.getDireito();
        r.setDireito(f.getEsquerdo());
        f.setEsquerdo(r);

        return f;
    }

    // etapa de rotação à direita
    private No<T> rotacaoDireita(No<T> r){
        No<T> f = r.getEsquerdo();
        r.setEsquerdo(f.getDireito());
        f.setDireito(r);
        
        return f;
    }

    // etapa de rotação Esquerda-Direita
    private No<T> rotacaoEsquerdaDireita (No<T> r){
        r.setEsquerdo(rotacaoEsquerda(r.getEsquerdo()));
        return rotacaoDireita(r);
    }

    // etapa de rotação Direita-Esquerda
    private No<T> rotacaoDireitaEsquerda (No<T> r){
        r.setDireito(rotacaoDireita(r.getDireito()));
        return rotacaoEsquerda(r);
    }

    // Adicionar
    public void adicionar(T novoValor){
        No<T> raiz = super.adicionarRecursivo(novoValor);

        if (raiz.fatorBalanceamento() > 1){
            if (raiz.getDireito().fatorBalanceamento() > 0)
                raiz = this.rotacaoEsquerda(raiz);
            else
                raiz = this.rotacaoDireitaEsquerda(raiz);
        }
        else if (raiz.fatorBalanceamento() < -1){
            if (raiz.getEsquerdo().fatorBalanceamento() < 0)
                raiz = this.rotacaoDireita(raiz);
            else
                raiz = this.rotacaoEsquerdaDireita(raiz);
        }
        return;
    }

    // Remover

    // Implementar métodos para fazer o balanceamento

}
