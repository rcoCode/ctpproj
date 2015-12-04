package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by rebeca on 12/4/2015.
 */
@Entity
public class PM extends Model{
    @Id
    public Long id;

    public String message;

    @ManyToOne
    public Users sender;

    @ManyToOne
    public Users receiver;
}
