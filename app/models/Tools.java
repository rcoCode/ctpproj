package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by rebeca on 11/18/2015.
 */
@Entity
public class Tools extends Model{
    @Id
    public Long id;

    public String name;

    public String description;

    @ManyToOne
    public Users owner;

    @ManyToOne
    public ToolType category;

    @OneToMany
    public List<Comments> commentList;

    public static Find<Long,Tools> find=new Finder<Long, Tools>(Tools.class);

}
