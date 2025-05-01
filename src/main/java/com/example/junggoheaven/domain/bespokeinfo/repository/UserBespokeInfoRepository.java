package com.example.junggoheaven.domain.bespokeinfo.repository;


import com.example.junggoheaven.domain.bespokeinfo.entity.UserBespokeInfo;
import com.example.junggoheaven.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserBespokeInfoRepository extends JpaRepository<UserBespokeInfo, Long> {

	@Query("SELECT u FROM UserBespokeInfo u WHERE u.user = :user")
	UserBespokeInfo findByUsersId(@Param("user") User user);

}
