package io.github.arasdenizhan.payment.demo.dto.authentication;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private List<String> roles;
}
