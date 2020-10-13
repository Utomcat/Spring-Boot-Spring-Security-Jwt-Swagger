package com.ranyk.security.model;

import lombok.*;

/**
 * ClassName:User
 * Description:
 *
 * @author ranyi
 * @date 2020-10-13 0:14
 * Version: V1.0
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;

    private String password;

}
