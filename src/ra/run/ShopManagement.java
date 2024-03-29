package ra.run;

import ra.business.config.CONSTANT;
import ra.business.config.InputMethods;
import ra.business.design.ICategory;
import ra.business.design.IProduct;
import ra.business.implementation.CategoryManagement;
import ra.business.implementation.ProductManagement;

public class ShopManagement
{
    public static void main(String[] args)
    {
        ICategory categoryManagement = new CategoryManagement();
        IProduct productManagement = new ProductManagement();
        while (true)
        {
            System.out.println("******************SHOP MENU*******************\n" +
                    "1. Quản lý danh mục sản phẩm\n" +
                    "2. Quản lý sản phẩm\n" +
                    "3. Thoát\n");
            System.out.println("Nhập lựa chọn theo danh sách trên");
            int choice = InputMethods.getInteger();
            switch (choice)
            {
                case 1:
                    categoryManagement.categoryMenu();
                    break;
                case 2:
                    productManagement.productMenu();
                    break;
                case 3:
                    return;
                default:
                    System.out.println(CONSTANT.CHOICE_NOT_AVAI);
                    break;
            }
        }
    }
}
