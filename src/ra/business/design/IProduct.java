package ra.business.design;

import ra.business.entity.Category;
import ra.business.entity.Product;

public interface IProduct extends IManageable<Category, Product>
{
    void sortByPrice();

    void searchByName();

    void searchInPriceRange();

    void productMenu();
}
