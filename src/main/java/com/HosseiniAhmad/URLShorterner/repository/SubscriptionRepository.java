package com.HosseiniAhmad.URLShorterner.repository;

import com.HosseiniAhmad.URLShorterner.model.entity.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByUser_Id(Long userId);

    @Query("SELECT s FROM Subscription s WHERE s.user.id = :userId AND s.expiryDate >= CURRENT_DATE")
    Set<Subscription> findUserActiveSubscriptions(Long userId);
}
