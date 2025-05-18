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

    // adicionar
    

    // Remover

    // Implementar métodos para fazer o balanceamento

}
