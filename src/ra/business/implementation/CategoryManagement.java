package ra.business.implementation;

import ra.business.config.CONSTANT;
import ra.business.config.InputMethods;
import ra.business.design.ICategory;
import ra.business.entity.Category;
import ra.business.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ra.business.implementation.ProductManagement.productList;

public class CategoryManagement implements ICategory
{
    final static List<Category> categoryList = new ArrayList<>();

    @Override
    public void categoryMenu()
    {
        while (true)
        {
            System.out.println("********************CATEGORIES MENU***********\n" +
                    "1. Nhập thông tin các danh mục\n" +
                    "2. Hiển thị thông tin các danh mục\n" +
                    "3. Cập nhật thông tin danh mục\n" +
                    "4. Xóa danh mục\n" +
                    "5. Cập nhật trạng thái danh mục\n" +
                    "6. Quay lại\n");
            System.out.println("Vui lòng nhập lựa chon theo các mục trên");
            int choice = InputMethods.getInteger();
            switch (choice)
            {
                case 1:
                    addItem(categoryList, productList);
                    break;
                case 2:
                    displayAll();
                    break;
                case 3:
                    updateItem(categoryList, productList);
                    break;
                case 4:
                    deleteItem(productList);
                    break;
                case 5:
                    changeCategoryStatus();
                    break;
                case 6:
                    return;
                default:
                    System.out.println(CONSTANT.CHOICE_NOT_AVAI);
                    break;
            }
        }
    }

    @Override
    public void addItem(List<Category> categoryList, List<Product> productList)
    {
        System.out.println("Nhập số lượng danh mục muốn thêm mới");
        int n = InputMethods.getInteger();
        for (int i = 0; i < n; i++)
        {
            System.out.println("Nhập thông tin cho danh mục thứ " + (i + 1));
            Category newCategory = new Category();
            newCategory.inputData(categoryList, true);
            categoryList.add(newCategory);
            System.out.println("Thêm danh mục thành công");
        }
    }

    @Override
    public void displayAll()
    {
        if (categoryList.isEmpty())
        {
            System.out.println("Hiện không có danh mục nào");
            return;
        }
        categoryList.forEach(c -> c.displayData());
    }

    @Override
    public void updateItem(List<Category> categoryList, List<Product> productList)
    {
        if (categoryList.isEmpty())
        {
            System.out.println("Hiện không có danh mục nào");
            return;
        }
        System.out.println("Danh sách các danh mục hiện có:");
        categoryList.forEach(System.out::println);
        System.out.println("Nhập mã danh mục muốn cập nhật thông tin");
        int updateIndex = getIndexByID();
        if (updateIndex == -1)
        {
            System.out.println("Không tìm thấy danh mục cần cập nhật");
            return;
        }
        while (true)
        {
            System.out.println("Lựa chọn cách cập nhật:");
            System.out.println("1. Cập nhật tên danh mục");
            System.out.println("2. Cập nhật mô tả");
            System.out.println("3. Cập nhật trạng thái");
            System.out.println("4. Cập nhật tất cả");
            System.out.println("0. Thoát");
            int updateChoice = InputMethods.getInteger();
            Category updatedCategory = categoryList.get(updateIndex);
            switch (updateChoice)
            {
                case 1:
                    updatedCategory.getUpdateName(categoryList);
                    System.out.println("Đã cập nhật");
                    break;
                case 2:
                    System.out.println("Nhập mô tả mới");
                    updatedCategory.setDescription(InputMethods.getString());
                    System.out.println("Đã cập nhật");
                    break;
                case 3:
                    //Set về trạng thái ngược lại
                    updatedCategory.setCatalogStatus(!updatedCategory.getCatalogStatus());
                    System.out.println("Đã cập nhật");
                    break;
                case 4:
                    updatedCategory.inputData(categoryList, false);
                    System.out.println("Đã cập nhật");
                    break;
                case 0:
                    return;
                default:
                    System.out.println(CONSTANT.CHOICE_NOT_AVAI);
                    break;
            }
        }
    }

    @Override
    public void deleteItem(List<Product> productList)
    {
        if (categoryList.isEmpty())
        {
            System.out.println("Hiện không có danh mục nào");
            return;
        }
        System.out.println("Danh sách các danh mục hiện có:");
        categoryList.forEach(System.out::println);
        System.out.println("Nhập mã danh mục muốn xóa");
        int deleteIndex = getIndexByID();
        if (deleteIndex == -1)
        {
            System.out.println("Mã danh mục không tồn tại");
            return;
        }
        //Kiểm tra xem có sản phẩm nào có catalogId trùng với danh mục này không
        if (productList.stream().noneMatch(p -> p.getCatalogId() == categoryList.get(deleteIndex).getCatalogId()))
        {   //noneMatch = true => Không có sản phẩm
            categoryList.remove(deleteIndex);
            System.out.println("Đã xóa danh mục");
        } else
        {
            System.out.println("Danh mục này vẫn còn sản phẩm nên không thể xóa");
        }
    }

    @Override
    public void changeCategoryStatus()
    {
        if (categoryList.isEmpty())
        {
            System.out.println("Hiện không có danh mục nào");
            return;
        }
        System.out.println("Danh sách các danh mục hiện có:");
        categoryList.forEach(System.out::println);
        System.out.println("Nhập mã danh mục muốn cập nhật trạng thái");
        int changeStatusIndex = getIndexByID();
        if (changeStatusIndex == -1)
        {
            System.out.println("Không tìm thấy danh mục");
            return;
        }
        //Đảo ngược trạng thái hiện tại
        categoryList.get(changeStatusIndex).setCatalogStatus(!categoryList.get(changeStatusIndex).getCatalogStatus());
        System.out.println("Cập nhật thành công");
    }

    @Override
    public int getIndexByID()
    {
        int searchId = InputMethods.getInteger();
        if (categoryList.isEmpty())
            return -1;
        for (int i = 0; i < categoryList.size(); i++)
        {
            if (searchId == categoryList.get(i).getCatalogId())
                return i;
        }
        return -1;
    }
}
