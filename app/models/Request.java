package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
    public Date timer;
    //yes or no from user
    public Boolean status;

    @ManyToOne
    public Tools wanted;

    @ManyToOne
    public Users borrower;

    @ManyToMany
    public Users lender;
}
