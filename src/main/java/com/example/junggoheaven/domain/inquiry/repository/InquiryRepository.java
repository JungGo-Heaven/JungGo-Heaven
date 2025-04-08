package com.example.junggoheaven.domain.inquiry.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.junggoheaven.domain.inquiry.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryCustomRepository {

	@EntityGraph(attributePaths = {"writer", "respondent"})
	@Query("SELECT i FROM Inquiry i WHERE i.id = :inquiryId")
	Optional<Inquiry> findById(Long inquiryId);

	Boolean existsByIdAndWriterId(Long inquiryId, Long userId);

	@EntityGraph(attributePaths = {"writer", "respondent"})
	@Query("SELECT i FROM Inquiry i WHERE i.writer.id = :writerId")
	Page<Inquiry> findAllByWriterId(Long writerId, Pageable pageable);

	@EntityGraph(attributePaths = {"writer", "respondent"})
	@Query("SELECT i FROM Inquiry i WHERE i.id = :inquiryId AND i.status <> 'DELETED'")
	Inquiry findByIdAndStatusIsNotDeleted(Long inquiryId);

	@EntityGraph(attributePaths = {"writer", "respondent"})
	@Query("SELECT i FROM Inquiry i WHERE i.id = :inquiryId AND i.status <> 'DELETED'")
	Page<Inquiry> findAllByWriterIdAndStatusIsNotDeleted(Long writerId, Pageable pageable);
}
