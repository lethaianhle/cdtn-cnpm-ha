package app.pizzahut.request;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class UserModelRequest {

    private String mobile;

    private String password;

}
