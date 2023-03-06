package com.flexpag.paymentscheduler.repositories;

import com.flexpag.paymentscheduler.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    Boolean existsUserByRegistrationNumber(String registrationNumber);
}
