package ra.business.design;

import java.util.List;

public interface IManageable<T, E>
{
    void addItem(List<T> tList, List<E> eList);

    void displayAll();

    void updateItem(List<T> tList, List<E> eList);

    void deleteItem(List<E> eList);

    int getIndexByID();
}
