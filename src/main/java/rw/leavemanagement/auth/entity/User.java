package rw.leavemanagement.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import rw.leavemanagement.auth.enumerations.EStatus;
import rw.leavemanagement.auth.enumerations.EUserRole;

import java.util.Set;

/**
 * Represents a user in the system.
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@OnDelete(action = OnDeleteAction.CASCADE)
@NoArgsConstructor
@AllArgsConstructor
public class User extends Base {

    /**
     * The email address of the user.
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * The first name of the user.
     */
    @Column(name = "firstname")
    private String firstname;

    /**
     * The last name of the user.
     */
    @Column(name = "lastname")
    private String lastname;

    @Column(name = "department")
    private String department;

    @Column(name = "profile_picture")
    private String profilePicture;
    /**
     * The password of the user (JSON ignored for security reasons).
     */
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * The status of the user.
     */
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EStatus status = EStatus.ACTIVE;
    /**
     * The Role of the user.
     */

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private EUserRole role = EUserRole.USER;

}
