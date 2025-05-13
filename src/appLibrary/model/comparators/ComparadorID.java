package appLibrary.model.comparators;
import appLibrary.model.entities.Livro;
import java.util.Comparator;

public class ComparadorID implements Comparator<Livro> {

    @Override
    public int compare(Livro l1, Livro l2) {
        return Integer.compare(l1.getId(), l2.getId());
    }
}

