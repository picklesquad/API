package com.pickle.Repository;

import com.pickle.Domain.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository interface for table "user"
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer>{

    UserEntity findByEmail(String email);
    UserEntity findById(int id);
    UserEntity findByApiToken(String apiToken);
    UserEntity findByFbToken(String fbToken);
    UserEntity findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT iscomplete from user where email = :email",
            nativeQuery = true)
    int getIsComplete(@Param("email") String email);
}
