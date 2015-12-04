package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import org.mindrot.jbcrypt.BCrypt;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Constraint;
import java.util.List;

/**
 * Created by rebeca on 11/18/2015.
 */
@Entity
public class Users extends Model{
    @Id
    public Long id;

    public String email;

    @Constraints.Required
    @Column(unique = true)
    public String username;

    @Constraints.Required
    public String password_hash;

    public Boolean admin;

    public boolean authenticate (String password){
        return BCrypt.checkpw(password, this.password_hash);
    }

    public static Users createUser(String username,String password,String email,Boolean admin){
        if(password==null ||username==null ||email==null || password.length()<8){
            return null;
        }

        String pwHashed = BCrypt.hashpw(password, BCrypt.gensalt());

        Users nUser = new Users();
        nUser.username=username;
        nUser.password_hash=pwHashed;
        nUser.email=email;
        nUser.admin=admin;

        return nUser;
    }

    @OneToMany
    public List<Tools> toolList;

    @OneToMany(mappedBy = "receiver")
    public List<PM> messages;

    @OneToMany(mappedBy = "sender")
    public List<PM> sentMess;
}
