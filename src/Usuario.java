import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
    private String username;
    private String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
