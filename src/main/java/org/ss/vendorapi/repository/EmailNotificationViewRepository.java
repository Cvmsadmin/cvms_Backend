package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.EmailNotificationView;

@Repository
public interface EmailNotificationViewRepository extends JpaRepository<EmailNotificationView, Long> {

    @Query("SELECT m FROM EmailNotificationView m WHERE TO_DATE(m.mail_date, 'DD-MM-YY') <= CURRENT_DATE")  
    List<EmailNotificationView> findPendingEmailRecipients();
}