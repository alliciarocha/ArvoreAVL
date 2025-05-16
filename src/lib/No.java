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


    public int obterAltura(){
        return obterAltura(this);
    }

    public int obterAltura(No<T> r){
        if (r==null)
            return -1;
        
        else{
            int hd = obterAltura(r.getDireito());
            int he = obterAltura(r.getEsquerdo());
            if (hd > he){
                return hd + 1;
            }
            else{
                return he + 1;
            }
        }
    }

    public int fatorBalanceamento(){
        return obterAltura(this.direito) - obterAltura(this.esquerdo);
    }
}
