package ra.business.entity;

import ra.business.config.CONSTANT;
import ra.business.config.InputMethods;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Category
{
    private int catalogId;
    private String catalogName;
    private String description;
    private Boolean catalogStatus;
    private final static int MAX_NAME_LENGTH = 50;

    public Category()
    {
    }

    public Category(int catalogId, String catalogName, String description, Boolean catalogStatus)
    {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.description = description;
        this.catalogStatus = catalogStatus;
    }

    public int getCatalogId()
    {
        return catalogId;
    }

    public void setCatalogId(int catalogId)
    {
        this.catalogId = catalogId;
    }

    public String getCatalogName()
    {
        return catalogName;
    }

    public void setCatalogName(String catalogName)
    {
        this.catalogName = catalogName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Boolean getCatalogStatus()
    {
        return catalogStatus;
    }

    public void setCatalogStatus(Boolean catalogStatus)
    {
        this.catalogStatus = catalogStatus;
    }

    public void displayData()
    {
        System.out.printf("Mã danh mục: %d | Tên danh mục: %s | Mô tả: %s | Trạng thái: %s\n",
                this.catalogId, this.catalogName, this.description, this.catalogStatus ? "Hoạt động" : "Không hoạt động");
        System.out.println("-----------------------------------------------------------------------------------");
    }

    public void inputData(List<Category> categoryList, boolean isAdding)
    {
        if (isAdding)//Nếu là add thì gọi hàm thêm tên mới
        {
            this.catalogName = getInputName(categoryList);
        } else//Nếu là update thì gọi hàm cập nhật tên
        {
            this.catalogName = getUpdateName(categoryList);
        }
        this.description = getInputDescription();
        this.catalogStatus = getInputStatus();
        if (isAdding)//Nếu là add thì mới set Id
        {
            this.catalogId = getMaxId(categoryList);
        }
    }

    private String getInputName(List<Category> categoryList)
    {
        while (true)
        {
            System.out.println("Nhập tên danh mục:");
            String inputName = InputMethods.getString();
            if (checkInputName(inputName, categoryList))//Trả về false thì không return
            {
                return inputName;
            } else
            {
                System.out.println(CONSTANT.NAME_EXISTED + ". " + CONSTANT.INPUT_AGAIN);
            }
        }
    }

    public String getUpdateName(List<Category> categoryList)
    {
        while (true)
        {
            System.out.println("Nhập tên mới");
            String updateName = InputMethods.getString();
            if (!checkInputName(updateName, categoryList))//Nếu trả về false => Tên bị trùng
            {   //Kiểm tra xem có phải đang trùng với chính nó không
                if (this.catalogName.equals(updateName))
                {
                    return updateName;//Nếu trùng với chính nó thì cho phép sử dụng
                } else
                {   //Nếu không phải => Trùng tên khác => Nhập lại
                    System.out.println(CONSTANT.NAME_EXISTED + ". " + CONSTANT.INPUT_AGAIN);
                }
            }
        }
    }

    private boolean checkInputName(String inputName, List<Category> categoryList)
    {
        if (inputName.length() > MAX_NAME_LENGTH)
        {
            System.out.println("Độ dài của tên danh mục chỉ được tối đa 50 ký tự");
            return false;
        }
        //Check trùng lặp: true => Không trùng. false => Có trùng lặp
        return categoryList.stream().noneMatch(c -> c.catalogName.equals(inputName));
    }

    private String getInputDescription()
    {
        System.out.println("Mời nhập mô tả cho danh mục");
        return InputMethods.getString();
    }

    private boolean getInputStatus()
    {
        while (true)
        {
            System.out.println("Nhập trạng thái của danh mục: true - Hoạt động, false - Không hoạt động");
            String inputStatus = InputMethods.getString();
            if (inputStatus.equals("true") || inputStatus.equals("false"))
                return Boolean.parseBoolean(inputStatus);
            else
            {
                System.out.println("Vui lòng nhập chính xác true hoặc false");
            }
        }
    }

    private int getMaxId(List<Category> categoryList)
    {
        if (categoryList.isEmpty())
            return 1;
        //Lấy ra object có giá trị Id lớn nhất
        Optional<Category> maxIdCategory = categoryList.stream().max(Comparator.comparingInt(c -> c.catalogId));
        return maxIdCategory.get().catalogId + 1;//Cộng thêm 1 để tăng Id
    }

    @Override
    public String toString()
    {
        return "Category{" +
                "catalogId=" + catalogId +
                ", catalogName='" + catalogName + '\'' +
                '}';
    }
}
