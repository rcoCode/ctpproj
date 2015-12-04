package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by rebeca on 12/2/2015.
 */
@Entity
public class ToolType extends Model{
    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @OneToMany
    public List<Tools> toolList;
}
