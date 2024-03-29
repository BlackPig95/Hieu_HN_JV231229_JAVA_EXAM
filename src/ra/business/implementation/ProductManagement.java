package ra.business.implementation;

import ra.business.config.CONSTANT;
import ra.business.config.InputMethods;
import ra.business.design.IProduct;
import ra.business.entity.Category;
import ra.business.entity.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ra.business.implementation.CategoryManagement.categoryList;

public class ProductManagement implements IProduct
{
    static final List<Product> productList = new ArrayList<>();

    @Override
    public void productMenu()
    {
        while (true)
        {
            System.out.println("*******************PRODUCT MANAGEMENT*****************\n" +
                    "1. Nhập thông tin các sản phẩm\n" +
                    "2. Hiển thị thông tin các sản phẩm\n" +
                    "3. Sắp xếp các sản phẩm theo giá\n" +
                    "4. Cập nhật thông tin sản phẩm theo mã sản phẩm\n" +
                    "5. Xóa sản phẩm theo mã sản phẩm\n" +
                    "6. Tìm kiếm các sản phẩm theo tên sản phẩm\n" +
                    "7. Tìm kiếm sản phẩm trong khoảng giá a – b (a,b nhập từ bàn\n" +
                    "phím)\n" +
                    "8. Quay lại\n");
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
                    sortByPrice();
                    break;
                case 4:
                    updateItem(categoryList, productList);
                    break;
                case 5:
                    deleteItem(productList);
                    break;
                case 6:
                    searchByName();
                    break;
                case 7:
                    searchInPriceRange();
                    break;
                case 8:
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
        if (categoryList.isEmpty())
        {
            System.out.println("Hiện không có danh mục nào. Vui lòng thêm danh mục trước khi thêm sản phẩm");
            return;
        }
        System.out.println("Nhập số lượng sản phẩm muốn thêm mới");
        int n = InputMethods.getInteger();
        for (int i = 0; i < n; i++)
        {
            System.out.println("Nhập thông tin cho sản phẩm thứ " + (i + 1));
            Product newProduct = new Product();
            newProduct.inputData(categoryList, productList, true);
            productList.add(newProduct);
            System.out.println("Thêm sản phẩm thành công");
        }
    }

    @Override
    public void displayAll()
    {
        if (productList.isEmpty())
        {
            System.out.println("Hiện không có sản phẩm nào để hiển thị");
            return;
        }
        productList.forEach(p -> p.displayData(categoryList));
    }

    @Override
    public void updateItem(List<Category> categoryList, List<Product> productList)
    {
        if (productList.isEmpty())
        {
            System.out.println("Hiện không có sản phẩm nào");
            return;
        }
        System.out.println("Danh sách các sản phẩm hiện có:");
        productList.forEach(System.out::println);
        System.out.println("Nhập mã sản phẩm muốn cập nhật thông tin");
        int updateIndex = getIndexByID();
        if (updateIndex == -1)
        {
            System.out.println("Không tìm thấy sản phẩm cần cập nhật");
            return;
        }
        while (true)
        {
            System.out.println("Lựa chọn cách cập nhật:");
            System.out.println("1. Cập nhật tên sản phẩm");
            System.out.println("2. Cập nhật mã sản phẩm");
            System.out.println("3. Cập nhật trạng thái");
            System.out.println("4. Cập nhật giá sản phẩm");
            System.out.println("5. Cập nhật ngày sản phẩm");
            System.out.println("6. Cập nhật danh mục của sản phẩm");
            System.out.println("7. Cập nhật tất cả");
            System.out.println("0. Thoát");
            int updateChoice = InputMethods.getInteger();
            Product updatedProduct = productList.get(updateIndex);
            switch (updateChoice)
            {
                case 1:
                    updatedProduct.getUpdateName(productList);
                    System.out.println("Cập nhật thành công");
                    break;
                case 2:
                    updatedProduct.getUpdateId(productList);
                    System.out.println("Cập nhật thành công");
                    break;
                case 3:
                    updatedProduct.setProductStatus(updatedProduct.getEnumStatus());
                    break;
                case 4:
                    System.out.println("Nhập giá mới");
                    float newPrice = InputMethods.getFloat();
                    if (newPrice > 0)
                    {
                        updatedProduct.setPrice(newPrice);
                        System.out.println("Cập nhật thành công");
                    } else
                    {
                        System.out.println("Giá sản phẩm phải lớn hơn 0");
                    }
                    break;
                case 5:
                    updatedProduct.setCreated(updatedProduct.getInputDate());
                    break;
                case 6:
                    updatedProduct.setCatalogId(updatedProduct.getCatalogIdInput(categoryList));
                    break;
                case 7:
                    productList.get(updateIndex).inputData(categoryList, productList, false);
                    System.out.println("Cập nhật thành công");
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
        if (productList.isEmpty())
        {
            System.out.println("Hiện không có sản phẩm nào");
            return;
        }
        System.out.println("Danh sách các sản phẩm hiện có:");
        productList.forEach(System.out::println);
        System.out.println("Nhập mã sản phẩm muốn xóa");
        int deleteIndex = getIndexByID();
        if (deleteIndex == -1)
        {
            System.out.println("Mã sản phẩm không tồn tại");
            return;
        }
        productList.remove(deleteIndex);
        System.out.println("Đã xóa sản phẩm");
    }

    @Override
    public int getIndexByID()
    {
        String searchId = InputMethods.getString();
        if (productList.isEmpty())
            return -1;
        for (int i = 0; i < productList.size(); i++)
        {
            if (searchId.equals(productList.get(i).getProductId()))
                return i;
        }
        return -1;
    }

    @Override
    public void sortByPrice()
    {
        if (productList.isEmpty())
        {
            System.out.println("Hiện không có sản phẩm nào");
            return;
        }
        while (true)
        {
            System.out.println("Nhập 1 nếu muốn sắp xếp theo chiều tăng dần, 2 để sắp xếp giảm dần, 0 để thoát");
            int choice = InputMethods.getInteger();
            switch (choice)
            {
                case 1:
                    productList.sort((p1, p2) -> Float.compare(p1.getPrice(), p2.getPrice()));
                    System.out.println("Đã sắp xếp:");
                    productList.forEach(System.out::println);
                    break;
                case 2:
                    productList.sort((p1, p2) -> Float.compare(p2.getPrice(), p1.getPrice()));
                    System.out.println("Đã sắp xếp:");
                    productList.forEach(p -> System.out.println(p));
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
    public void searchByName()
    {
        if (productList.isEmpty())
        {
            System.out.println("Hiện không có sản phẩm nào");
            return;
        }
        System.out.println("Nhập tên sản phẩm cần tìm");
        String searchName = InputMethods.getString();
        System.out.println("Danh sách các sản phẩm có tên giống với mô tả:");
        productList.stream().filter(p -> p.getProductName().
                contains(searchName)).forEach(p -> System.out.println(p.getProductName()));
        System.out.println("-------------------------------------------------------------------------------------");
    }

    @Override
    public void searchInPriceRange()
    {
        if (productList.isEmpty())
        {
            System.out.println("Hiện không có sản phẩm nào");
            return;
        }
        System.out.println("Nhập giới hạn dưới của giá sản phẩm muốn tìm");
        float lowerBound = InputMethods.getFloat();
        System.out.println("Nhập giới hạn trên của giá sản phẩm muốn tìm");
        float upperBound = InputMethods.getFloat();
        System.out.println("Danh sách các sản phẩm trong khoảng giá này:");
        productList.stream().filter(p -> p.getPrice() >= lowerBound && p.getPrice() <= upperBound)
                .forEach(p -> System.out.println(p.getProductName()));
        System.out.println("-------------------------------------------------------------------------------------");
    }
}
