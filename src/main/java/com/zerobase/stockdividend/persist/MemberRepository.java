package com.zerobase.stockdividend.persist;

import com.zerobase.stockdividend.persist.entity.MemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
