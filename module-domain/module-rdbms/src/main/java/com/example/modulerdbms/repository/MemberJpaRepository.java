package com.example.modulerdbms.repository;

import com.example.modulerdbms.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
}
