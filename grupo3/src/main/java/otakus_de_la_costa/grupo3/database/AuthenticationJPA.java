package otakus_de_la_costa.grupo3.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_authentications")
@Setter
@Getter
@NoArgsConstructor
public class AuthenticationJPA {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    private RoleJPA role;
}
