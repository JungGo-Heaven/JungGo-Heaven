package com.example.junggoheaven.domain.chatRoom.repository;

import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByProductIdAndBuyerId(Long productId, Long buyerId);

    @Query("""
        SELECT r FROM ChatRoom r
        LEFT JOIN FETCH r.product p
        WHERE (r.buyer.id = :userId AND (r.buyerExited IS NULL OR r.buyerExited != :userId))
            OR
                (r.product.user.id = :userId AND (r.sellerExited IS NULL OR r.sellerExited != :userId))
    """)
    Page<ChatRoom> findPagingChatRooms(@Param("userId") Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"product", "buyer"})
    @Query("""
    SELECT r FROM ChatRoom r
    WHERE r.id = :id
""")
    ChatRoom findByChatRoomId(Long id);

}
