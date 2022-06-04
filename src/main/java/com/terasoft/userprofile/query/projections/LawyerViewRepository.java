package com.terasoft.userprofile.query.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LawyerViewRepository extends JpaRepository<LawyerView, String> {

    /*@Query(value = "SELECT * FROM lawyer_view WHERE lawyer_id <> :lawyerId AND email = :email", nativeQuery = true)
    Optional<CustomerView> getByCustomerIdAndEmail(String lawyerId, String email);*/
}
