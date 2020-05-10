package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Builder
public class User {
    @Id
    @Column(name = "USR_LOGIN",unique=true,columnDefinition="VARCHAR(64)")
    private String login;
    @Column(name = "USR_NAME")
    private String name;
    @Column(name = "USR_PASSWORD")
    private String password;


    public User() {
    }
}

