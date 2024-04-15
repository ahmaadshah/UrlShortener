package com.HosseiniAhmad.URLShorterner.repository;

import com.HosseiniAhmad.URLShorterner.model.entity.url.UrlBinding;
import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UrlBindingRepository extends JpaRepository<UrlBinding, Long> {
    Optional<UrlBinding> findByShortUrl_Uid(String uid);
    @Query("SELECT u FROM UrlBinding u WHERE u.user.id = :userId")
    Set<UrlBinding> findUrlBindings(Long userId);

    @Query("SELECT u FROM UrlBinding u WHERE u.user.id = :userId AND u.isClosed = false AND u.expiryDate >= CURRENT_DATE")
    Set<UrlBinding> findActiveUrlBindings(Long userId);
}
