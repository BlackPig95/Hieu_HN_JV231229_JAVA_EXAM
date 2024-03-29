package ra.business.design;

import ra.business.entity.Category;
import ra.business.entity.Product;

public interface ICategory extends IManageable<Category, Product>
{
    void changeCategoryStatus();

    void categoryMenu();
}
