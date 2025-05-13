package lib;

public class No<T> {
    T valor;
    No<T> esquerdo;
    No<T> direito;

    public No(T valor) {
        this.valor = valor;
        this.esquerdo = null;
        this.direito = null;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
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
}
