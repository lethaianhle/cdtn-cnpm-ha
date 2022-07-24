package app.pizzahut.response;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserModelRes {

    private Long id;

    @NotNull(message = "Trường họ tên không được để trống")
    @NotEmpty(message = "Trường họ tên không được để trống")
    @Size(max = 255, min = 1, message = "Độ dài trường họ tên không hợp lệ")
    private String hoTen;

    @Pattern(regexp="^0[0-9]{1,10}", message = "Trường số điện thoại không đúng định dạng")
    private String mobile;

    @Size(max = 255, message = "Độ dài trường địa chỉ giao hàng 1 không hợp lệ")
    private String shippingAddress1;

    @Size(max = 255, message = "Độ dài trường địa chỉ giao hàng 2 không hợp lệ")
    private String shippingAddress2;

    @Size(max = 255, message = "Độ dài trường địa chỉ giao hàng 3 không hợp lệ")
    private String shippingAddress3;

    @NotNull
    @NotEmpty(message = "Trường địa chỉ không được để trống")
    @Size(max = 255, min = 1, message = "Độ dài trường địa chỉ không hợp lệ")
    private String address;

    @NotNull
    @Max(30000)
    @Min(0)
    private int point;

    @NotNull(message = "Trường rank không được để trống")
    @NotEmpty(message = "Trường rank tên không được để trống")
    private String rankName;

    @NotNull(message = "Trường role không được để trống")
    @NotEmpty(message = "Trường role không được để trống")
    private String roleName;

    @Size(max = 255, min = 1)
    private String password;

}
