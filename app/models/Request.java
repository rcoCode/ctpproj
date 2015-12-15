package models;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import play.data.format.Formats;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rebeca on 12/4/2015.
 */
@Entity
public class Request extends Model{
    @Id
    public Long id;
    //message
    public String message;
    //time of request
    @Formats.DateTime(pattern = "MM/dd/yy")
    public DateTime start;
    @Formats.DateTime(pattern = "MM/dd/yy")
    public DateTime end;
    //yes or no from user
    public Boolean status;

    @ManyToOne
    public Tools wanted;

    @ManyToOne
    public Users borrower;

    @ManyToOne
    public Users lender;

    public static Request create(String text,DateTime begin,DateTime due,Tools want,Users b, Users l){
        Request req = new Request();
        req.message = text;
        req.start = begin;
        req.end = due;
        req.status = false;
        req.borrower = b;
        req.lender = l;
        req.wanted = want;

        return req;
    }

}
