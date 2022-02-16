package mz.co.kr.mzoauth2security.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class SignInRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
