package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * Created by rebeca on 12/3/2015.
 */
@Entity
public class Comments extends Model{
    @Id
    public Long id;

    public String body;

    @ManyToOne
    public Users poster;

    @ManyToOne
    public Tools topic;

    public Date postTime;
}
