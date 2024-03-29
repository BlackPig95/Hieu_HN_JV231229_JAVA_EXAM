package ra.business.entity;

import ra.business.config.CONSTANT;
import ra.business.config.InputMethods;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Product
{
    enum STATUS
    {
        ACTIVE,
        BLOCK,
        INACTIVE;
    }

    private String productId;
    private String productName;
    private float price;
    private String description;
    private Date created;
    private int catalogId;
    private Enum<STATUS> productStatus;

    public Product()
    {
    }

    public Product(String productId, String productName, float price, String description, Date created, int catalogId, Enum<STATUS> productStatus)
    {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.created = created;
        this.catalogId = catalogId;
        this.productStatus = productStatus;
    }

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public int getCatalogId()
    {
        return catalogId;
    }

    public void setCatalogId(int catalogId)
    {
        this.catalogId = catalogId;
    }

    public Enum<STATUS> getProductStatus()
    {
        return productStatus;
    }

    public void setProductStatus(Enum<STATUS> productStatus)
    {
        this.productStatus = productStatus;
    }

    public void displayData(List<Category> categoryList)
    {
        if (categoryList.isEmpty())
        {
            System.out.println("Hiện chưa có danh mục nào nên không thể hiển thị sản phẩm");
            return;
        }
        String categoryName = getCatalogName(categoryList);
        //Kiểm tra status
        String categoryStatus = this.productStatus == STATUS.ACTIVE ? "Đang bán" : (this.productStatus == STATUS.BLOCK) ?
                "Hết hàng" : "Không bán";
        System.out.printf("Mã sản phẩm: %s | Tên sản phẩm: %s | Giá bán: %.1f | Mô tả: %s\n",
                this.productId, this.productName, this.price, this.description);
        System.out.printf("Ngày nhập sản phẩm: %s | Danh mục sản phẩm: %s | Trạng thái: %s\n",
                this.created.toString(), categoryName, categoryStatus);
        System.out.println("-----------------------------------------------------------------------------------");
    }

    public void inputData(List<Category> categoryList, List<Product> productList, boolean isAdding)
    {
        if (categoryList.isEmpty())
        {
            System.out.println("Hiện không có danh mục nào nên không thể nhập sản phẩm. Vui lòng nhập danh mục trước");
            return;
        }
        if (isAdding)//Đang add thì gọi hàm thêm Id mới
        {
            this.productId = getInputId(productList);
        } else
        {//Đang update thì gọi hàm update Id
            this.productId = getUpdateId(productList);
        }
        if (isAdding)//Đang add thì gọi hàm thêm mới
        {
            this.productName = getInputName(productList);
        } else
        {//Đang update thì gọi hàm chỉnh sửa
            this.productName = getUpdateName(productList);
        }
        this.price = getInputPrice();
        this.description = getInputDescription();
        this.created = getInputDate();
        this.catalogId = getCatalogIdInput(categoryList);
        this.productStatus = getEnumStatus();
    }

    private String getInputId(List<Product> productList)
    {
        String regex = "^[CSA].{3}$";
        while (true)
        {
            System.out.println("Nhập mã sản phẩm, bắt đầu bằng C,S,A, và có tổng cộng 4 ký tự");
            String inputId = InputMethods.getString();
            if (inputId.matches(regex))
            {
                if (productList.isEmpty())//List rỗng thì không cần duyệt
                    return inputId;
                //Check trùng Id. true => không trùng. false => có trùng
                if (productList.stream().noneMatch(p -> p.productId.equals(inputId)))
                {
                    return inputId;
                } else
                {
                    System.out.println(CONSTANT.NAME_EXISTED + ". " + CONSTANT.INPUT_AGAIN);
                }
            } else
            {
                System.out.println(CONSTANT.INPUT_CORRECT_FORMAT);
            }
        }
    }

    public String getUpdateId(List<Product> productList)
    {
        String regex = "^[CSA].{3}$";
        while (true)
        {
            System.out.println("Nhập mã sản phẩm mới, bắt đầu bằng C,S,A, và có tổng cộng 4 ký tự");
            String updateId = InputMethods.getString();
            if (updateId.matches(regex))
            {
                //Kiểm tra xem có phải đang trùng với chính nó không
                //Nếu đúng, cho phép sử dụng lại Id này, vì đã đảm bảo không trùng các Id khác
                //ngay từ lúc thêm mơi sản phẩm
                if (this.productId.equals(updateId))
                {
                    return updateId;
                }
                //Nếu không dùng lại Id cũ, thì check xem có trùng Id khác không
                if (productList.stream().anyMatch(p -> p.productId.equals(updateId)))
                {
                    System.out.println(CONSTANT.NAME_EXISTED + ". " + CONSTANT.INPUT_AGAIN);
                } else return updateId;
            } else
            {   //Nhập sai định dạng
                System.out.println(CONSTANT.INPUT_CORRECT_FORMAT);
            }
        }
    }

    private String getInputName(List<Product> productList)
    {
        String regex = ".{10,50}";
        while (true)
        {
            System.out.println("Nhập tên sản phẩm, từ 10 đến 50 ký tự");
            String inputName = InputMethods.getString();
            if (inputName.matches(regex))
            {   //Check trùng tên
                if (productList.stream().noneMatch(p -> p.productName.equals(inputName)))
                {
                    return inputName;
                } else System.out.println(CONSTANT.NAME_EXISTED + ". " + CONSTANT.INPUT_AGAIN);
            } else
            {
                System.out.println(CONSTANT.INPUT_CORRECT_FORMAT);
            }
        }
    }

    public String getUpdateName(List<Product> productList)
    {
        String regex = "^.{10,50}$";
        while (true)
        {
            System.out.println("Nhập tên mới, từ 10 đến 50 ký tự");
            String updateName = InputMethods.getString();
            if (updateName.matches(regex))
            {
                //Kiểm tra xem có phải đang trùng tên với chính nó không. true => cho dùng
                //Vì khi nhập tên sản phẩm ngay từ đầu đã đảm bảo không thể trùng lặp
                //=>Check điều kiện trùng tên với chính bản thân trước
                if (this.productName.equals(updateName))
                {
                    return updateName;
                }
                //Nếu không dùng lại tên cũ => Check xem có bị trùng các sản phẩm khác không
                if (productList.stream().anyMatch(p -> p.productName.equals(updateName)))
                {
                    System.out.println(CONSTANT.NAME_EXISTED + ". " + CONSTANT.INPUT_AGAIN);
                } else return updateName;
            } else
            {   //Sai định dạng
                System.out.println(CONSTANT.INPUT_CORRECT_FORMAT);
            }
        }
    }

    private float getInputPrice()
    {
        while (true)
        {
            System.out.println("Nhập giá bán sản phẩm");
            float inputPrice = InputMethods.getFloat();
            if (inputPrice <= 0)
            {
                System.out.println("Giá sản phẩm phải lớn hơn 0");
            } else return inputPrice;
        }
    }

    private String getInputDescription()
    {
        System.out.println("Nhập mô tả sản phẩm");
        return InputMethods.getString();
    }

    public Date getInputDate()
    {
        System.out.println("Ngày nhập sản phẩm này là: (dd/MM/yyyy)");
        return InputMethods.getDate();
    }

    public int getCatalogIdInput(List<Category> categoryList)
    {
        while (true)
        {
            System.out.println("Danh sách các danh mục hiện có:");
            categoryList.forEach(System.out::println);
            System.out.println("Vui lòng nhập chọn mã danh mục");
            int idChoice = InputMethods.getInteger();
            for (Category category : categoryList)
            {
                if (idChoice == category.getCatalogId())
                    return idChoice;
            }
            //Chạy hết vòng for mà không return được => Nhập sai
            System.out.println("Mã danh mục không tồn tại. " + CONSTANT.INPUT_AGAIN);
        }
    }

    private String getCatalogName(List<Category> categoryList)
    {
        if (categoryList.isEmpty())
            return null;
        //Lấy tên danh mục dựa theo mã Id
        Optional<Category> category = categoryList.stream().filter(c -> c.getCatalogId() == this.catalogId).findFirst();
        String categoryName = category.get().getCatalogName();
        return categoryName;
    }

    public Enum<STATUS> getEnumStatus()
    {
        while (true)
        {
            System.out.println("Nhập trạng thái của sản phẩm: 1-Đang bán, 2-Hết hàng, 3-Không bán");
            int statusChoice = InputMethods.getInteger();
            switch (statusChoice)
            {
                case 1:
                    return STATUS.ACTIVE;
                case 2:
                    return STATUS.BLOCK;
                case 3:
                    return STATUS.INACTIVE;
                default:
                    System.out.println(CONSTANT.CHOICE_NOT_AVAI + ". " + CONSTANT.INPUT_AGAIN);
                    break;
            }
        }
    }

    @Override
    public String toString()
    {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                '}';
    }
}
