package rw.leavemanagement.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.leavemanagement.auth.entity.User;


import java.util.Optional;


/**
 * The IUserRepository interface is responsible for managing User entities in the database.
 * It extends the JpaRepository interface to inherit basic CRUD operations.
 */
@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    /**
     * Finds a user by email.
     *
     * @param email The email of the user.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findUserByEmail(String email);

    /**
     * Finds a user by id.
     *
     * @param id The id of the user.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findUserById(String id);

}