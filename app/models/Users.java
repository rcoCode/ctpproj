package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rebeca on 11/18/2015.
 */
@Table(name="Users")
@Entity
public class Users extends Model{
    @Id
    public Long id;

    public String email;

    @Constraints.Required
    @Column(unique = true)
    public String username;

    public String password_hash;

    public Boolean admin = false;

    public static Finder<Long, Users> find=new Finder<Long, Users>(Users.class);

    public boolean authenticate (String password){
        return BCrypt.checkpw(password, this.password_hash);
    }

    public static Users createUser(String username,String password,String email){
        if(password==null || username==null || email==null || password.length()<8){
            return null;
        }
        //If return null it means there's no user by that username
        if (Users.find.where().eq("username", username).findUnique()!=null){
            Users user = new Users();
            user.username = null;
            user.password_hash = null;
            user.email = null;
            return user;
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        Users User = new Users();
        User.username=username;
        User.password_hash=passwordHash;
        User.email=email;
        //User.admin=false;

        return User;
    }

    @OneToMany(mappedBy = "owner")
    public List<Tools> toolList;

    @OneToMany(mappedBy = "receiver")
    public List<PM> messages;

    @OneToMany(mappedBy = "sender")
    public List<PM> sentMess;
}
