/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models.Book;

import static LibData.Business.Configs.BookConfigs.getBookStatus;
import LibData.Models.Book;
import static LimitedSolution.Utilities.DateTimeHelper.getDateTimeString;
import static LimitedSolution.Utilities.CurrencyHelper.*;

/**
 *
 * @author Limited
 */
public class BookViewModel {

    public String IdCode;
    public String ISBN;
    public String CreatedBy;
    public String CreateTime;
    public String Price;
    public String Status;

    public String Name;
    public String Author;
    public String Publisher;
    public String Type;
    public String PublishYear;
    public String PublishMonth;
    public String Details;

    public BookViewModel() {
    }

    public BookViewModel(Book book) {
        this.IdCode = book.getIdCode();
        this.ISBN = book.getIsbn() != null ? book.getIsbn() : "";
        this.CreatedBy = book.getCreatedBy().getUsername();
        this.CreateTime = getDateTimeString(book.getCreateTime());
        this.Price = book.getPrice() != null ? IntToVND(book.getPrice()) : ""; // TODO: To VND
        this.Status = getBookStatus(book.getStatus());
        
        this.Name = book.getName();
        this.Author = book.getAuthor();
        this.Publisher = book.getPublisher();
        this.Type = book.getType();
        this.PublishYear = book.getPublishYear() + "";
        this.PublishMonth = book.getPublishMonth() != null ? book.getPublishMonth().toString() : "";
        this.Details = book.getDetails() != null ? book.getDetails() : "";
    }
}
