package com.pickle.Repository;

import com.pickle.Domain.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by andrikurniawan.id@gmail.com on 3/17/2016.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer>{

    public UserEntity findByEmail(String email);
    public UserEntity findById(int id);
    public UserEntity findByApiToken(String apiToken);
    public UserEntity findByFbToken(String fbToken);

    @Query(value = "SELECT iscomplete from user where email = :email",
            nativeQuery = true)
    public int getIsComplete(@Param("email") String email);

}
