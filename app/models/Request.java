package models;

import com.avaje.ebean.Model;

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
    public Date start;
    public Date end;
    //yes or no from user
    public Boolean status;

    @ManyToOne
    public Tools wanted;

    @ManyToMany(cascade = CascadeType.ALL)
    public Users borrower;

    @ManyToMany(cascade = CascadeType.ALL)
    public Users lender;

    public static Request create(String text,Date begin,Date due,Tools want,Users b, Users l){
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
