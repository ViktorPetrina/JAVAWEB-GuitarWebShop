package hr.vpetrina.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class UserLogin {
    private Integer id;
    private Integer userId;
    private Date loginDate;
    private String ipAddress;

    public UserLogin() {/* empty because of row mapper */}

    public UserLogin(Integer userId, Date loginDate, String ipAddress) {
        this.userId = userId;
        this.loginDate = loginDate;
        this.ipAddress = ipAddress;
    }
}
