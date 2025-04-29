package com.example.junggoheaven.domain.bespokeinfo.repository;


import com.example.junggoheaven.domain.bespokeinfo.entity.UserBespokeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserBespokeInfoRepository extends JpaRepository<UserBespokeInfo, Long> {

	// 수정
	@Query("SELECT u FROM UserBespokeInfo u WHERE u.user = :usersId")
	UserBespokeInfo findByUsersId(@Param("usersId") Long usersId);



}
