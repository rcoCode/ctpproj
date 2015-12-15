package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rebeca on 11/18/2015.
 */
@Table(name="Users")
@Entity
public class Users extends Model{

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{4,8}$";
    public static final Pattern pattern = Pattern.compile(USERNAME_PATTERN);


    public static boolean validate(String username){
        return pattern.matcher(username).matches();
    }

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
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        Users User = new Users();
        if (Users.validate(username)) {
            User.username = username;
            User.password_hash = passwordHash;
            User.email = email;
            //User.admin=false;

            return User;
        }
        else {
            return null;
        }
    }

    @OneToMany(mappedBy = "owner")
    public List<Tools> toolList;

    @OneToMany(mappedBy = "receiver")
    public List<PM> messages;

    @OneToMany(mappedBy = "sender")
    public List<PM> sentMess;

    @OneToMany(mappedBy = "poster")
    public List<Comments> commented;

    @OneToMany(mappedBy = "lender")
    public List<Request> lend;

    @OneToMany(mappedBy = "borrower")
    public List<Request> borrow;
}
