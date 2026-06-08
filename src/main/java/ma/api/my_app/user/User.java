package ma.api.my_app.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, length = 30, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String password;

    @Column
    private String avatarUrl;

    // We will create WorkspaceMember and LinkedAccount classes later.
    // Uncomment these relationships once those classes exist!
    /*
    @OneToMany(mappedBy = "user")
    private List<WorkspaceMember> workspaceMembers;

    @OneToMany(mappedBy = "user")
    private List<LinkedAccount> linkedAccounts;
    */

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
