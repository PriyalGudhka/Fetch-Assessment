package com.fetch.assessment.dao;

import com.fetch.assessment.entity.Receipts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReceiptsRepo extends JpaRepository<Receipts, UUID> {
}
