package com.terasoft.userprofile.query.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LawyerHistoryViewRepository extends JpaRepository<LawyerHistoryView, String> {
    @Query(value = "SELECT * FROM lawyer_history_view WHERE lawyer_history_id = (SELECT MAX(lawyer_history_id) FROM lawyer_history_view WHERE lawyer_id = : lawyerId)", nativeQuery = true)
    Optional<LawyerHistoryView> getLastByLawyerId(String lawyerId);
    @Query(value = "SELECT * FROM lawyer_history_view WHERE lawyer_id =:lawyerId ORDER BY created_at", nativeQuery = true)
    List<LawyerHistoryView> getHistoryByLawyerId(String lawyerId);
}
