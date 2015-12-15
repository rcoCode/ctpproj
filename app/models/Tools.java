package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "wanted")
    public List<Request> reqList;

    @ManyToOne
    public Users owner;

    @ManyToOne
    public ToolType category;

    @OneToMany(mappedBy = "topic")
    public List<Comments> commentList;

    public static Find<Long,Tools> find=new Finder<Long, Tools>(Tools.class);

}
