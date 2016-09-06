/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models.Book;

import LibData.Models.Book;
import LibData.Models.Factories.BookFactory;
import static LimitedSolution.Utilities.CurrencyHelper.*;
import MainPackage.Models.IModelValidate;

/**
 *
 * @author Limited
 */
public class UpdateBookModel extends BookViewModel implements IModelValidate {

    private UpdateBookModel OriginalModel;

    public String Id;

    public Book getBook() {
        if (this.IsValidate()) {
            return BookFactory.createBook(
                    this.Id,
                    this.ISBN, CurrencyToInt(this.Price, "VND") + "",
                    this.Name, this.Author,
                    this.Publisher, this.Type,
                    this.PublishYear, this.PublishMonth, this.Details
            );
        }

        return null;
    }

    public UpdateBookModel() {
        super();
        this.OriginalModel = null;
    }

    public UpdateBookModel(Book book) {
        super(book);
        this.Id = book.getId();
        this.OriginalModel = new UpdateBookModel(new BookViewModel(book));
    }

    private UpdateBookModel(BookViewModel model) {
        this.Author = model.Author;
        this.CreateTime = model.CreateTime;
        this.CreatedBy = model.CreatedBy;
        this.Details = model.Details;
        this.ISBN = model.ISBN;
        this.IdCode = model.IdCode;
        this.Name = model.Name;
        this.Price = model.Price;
        this.PublishMonth = model.PublishMonth;
        this.PublishYear = model.PublishYear;
        this.Publisher = model.Publisher;
        this.Status = model.Status;
        this.Type = model.Type;

        this.OriginalModel = null;
    }

    public UpdateBookModel(String ISBN, String Price,
            String Name, String Author,
            String Publisher, String Type,
            String Year, String Month,
            String Details, String CreatedBy) {

        this.ISBN = ISBN;
        this.Price = Price;
        this.Name = Name;
        this.Author = Author;
        this.Publisher = Publisher;
        this.Type = Type;
        this.PublishYear = Year;
        this.PublishMonth = Month;
        this.Details = Details;
        this.CreatedBy = CreatedBy;

        this.OriginalModel = null;

    }

    public UpdateBookModel(String Id,
            String ISBN, String Price,
            String Name, String Author,
            String Publisher, String Type,
            String Year, String Month,
            String Details, String CreatedBy) {
        this(ISBN, Price, Name, Author, Publisher, Type, Year, Month, Details, CreatedBy);
        this.Id = Id;
    }

    public UpdateBookModel getOriginalModel() {
        return OriginalModel;
    }

    @Override
    public boolean IsValidate() {
        if (!this.Price.isEmpty()) {
            try {
                Integer.parseInt(CurrencyToInt(this.Price, "VND") + "");
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
            Integer.parseInt(this.PublishYear);
        } catch (Exception e) {
            return false;
        }

        if (!this.PublishMonth.isEmpty()) {
            try {
                Integer.parseInt(this.PublishMonth);
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
                Integer.parseInt(CurrencyToInt(this.Price, "VND") + "");
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

        if (!this.PublishYear.isEmpty()) {
            try {
                Integer.parseInt(this.PublishYear);
            } catch (Exception e) {
                message += "Năm xuất bản không hợp lệ;" + '\n';
            }
        } else {
            message += "Vui lòng nhập Năm phát hành;" + '\n';
        }

        if (!this.PublishMonth.isEmpty()) {
            try {
                int month = Integer.parseInt(this.PublishMonth);
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
