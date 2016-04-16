package com.pickle.Repository;

import com.pickle.Domain.UserEntity;
import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by andrikurniawan.id@gmail.com on 3/17/2016.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer>{

    UserEntity findByEmail(String email);
    UserEntity findById(int id);

    @Query(value = "SELECT iscomplete from user where email = :email",
            nativeQuery = true)
    int getIsComplete(@Param("email") String email);

}
