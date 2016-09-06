/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models.Book;

import LibData.Models.Book;
import LibData.Models.Factories.BookFactory;
import MainPackage.Models.IModelValidate;

/**
 *
 * @author Limited
 */
public class AddBookModel implements IModelValidate {

    public String ISBN;
    public String Price;

    public String Name;
    public String Author;
    public String Publisher;
    public String Type;
    public String Year;
    public String Month;
    public String Details;

    public String CreatedBy;

    public Book getBook() {
        if (this.IsValidate()) {
            return BookFactory.createBook(
                    this.ISBN, this.Price,
                    this.Name, this.Author,
                    this.Publisher, this.Type,
                    this.Year, this.Month, this.Details
            );
        }

        return null;
    }

    public AddBookModel() {
    }

    public AddBookModel(String ISBN, String Price, String Name, String Author, String Publisher, String Type, String Year, String Details) {
        this.ISBN = ISBN;
        this.Price = Price;
        this.Name = Name;
        this.Author = Author;
        this.Publisher = Publisher;
        this.Type = Type;
        this.Year = Year;
        this.Details = Details;
    }

    public AddBookModel(String ISBN, String Price, String Name, String Author, String Publisher, String Type, String Year, String Details, String CreatedBy) {
        this(ISBN, Price, Name, Author, Publisher, Type, Year, Details);
        this.CreatedBy = CreatedBy;
    }

    public AddBookModel(
            String ISBN, String Price,
            String Name, String Author,
            String Publisher, String Type,
            String Year, String Month,
            String Details, String CreatedBy) {
        this(ISBN, Price, Name, Author, Publisher, Type, Year, Details, CreatedBy);
        this.Month = Month;
    }

    @Override
    public boolean IsValidate() {
        if (!this.Price.isEmpty()) {
            try {
                Integer.parseInt(this.Price);
            } catch (Exception e) {
                return false;
            }
        }

        if (this.Name.isEmpty()) {
            return false;
        }
        if (this.Author.isEmpty()) {
            return false;
        }
        if (this.Publisher.isEmpty()) {
            return false;
        }
        if (this.Type.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(this.Year);
        } catch (Exception e) {
            return false;
        }

        if (!this.Month.isEmpty()) {
            try {
                Integer.parseInt(this.Month);
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String MessageValidate() {
        if (this.IsValidate()) {
            return "OK";
        }

        String message = "";
        if (!this.Price.isEmpty()) {
            try {
                Integer.parseInt(this.Price);
            } catch (Exception e) {
                message += "Giá tiền không hợp lệ;" + '\n';
            }
        }

        if (this.Name.isEmpty()) {
            message += "Vui lòng nhập Tên sách;" + '\n';
        }
        if (this.Author.isEmpty()) {
            message += "Vui lòng nhập Tên tác giả;" + '\n';
        }
        if (this.Publisher.isEmpty()) {
            message += "Vui lòng nhập Nhà xuất bản;" + '\n';
        }
        if (this.Type.isEmpty()) {
            message += "Vui lòng nhập Thể loại;" + '\n';
        }

        if (!this.Year.isEmpty()) {
            try {
                Integer.parseInt(this.Year);
            } catch (Exception e) {
                message += "Năm xuất bản không hợp lệ;" + '\n';
            }
        } else {
            message += "Vui lòng nhập Năm phát hành;" + '\n';
        }

        if (!this.Month.isEmpty()) {
            try {
                int month = Integer.parseInt(this.Month);
                if (!(month >= 1 && month <= 12)) {
                    message += "Tháng nằm trong khoảng từ 1 đến 12;" + '\n';
                }
            } catch (Exception e) {
                message += "Tháng không hợp lệ";
            }
        }

        return message;
    }

    @Override
    public String ToLogString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
