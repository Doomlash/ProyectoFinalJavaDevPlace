package otakus_de_la_costa.grupo3.database;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "my_users")
@Getter
@Setter
@NoArgsConstructor
public class MyUserJPA extends MessengerJPA{

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "language")
    private String language;

    @Column(name = "profile_image", nullable = false)
    private String profileImage;

    @Column(name = "birth_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany()
    @JoinTable(name = "contacts",
                joinColumns = {@JoinColumn(name = "contact_owner")}, 
                inverseJoinColumns = {@JoinColumn(name = "contacted")})
    private List<MyUserJPA> contacts;

    @ManyToMany()
    @JoinTable(name = "blocks",
                joinColumns = {@JoinColumn(name = "block_owner")}, 
                inverseJoinColumns = {@JoinColumn(name = "blocked")})
    private List<MyUserJPA> blocks;

    @OneToMany(mappedBy = "user")
    private List<GroupMembersJPA> groups;
}
