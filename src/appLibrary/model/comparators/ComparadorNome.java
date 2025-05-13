package appLibrary.model.comparators;

import appLibrary.model.entities.Livro;

import java.util.Comparator;

public class ComparadorNome implements Comparator<Livro> {

    @Override
    public int compare(Livro l1, Livro l2) {
        return l1.getTitulo().compareToIgnoreCase(l2.getTitulo());
    }
}


