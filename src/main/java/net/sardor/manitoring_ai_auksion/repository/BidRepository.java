package net.sardor.manitoring_ai_auksion.repository;

import net.sardor.manitoring_ai_auksion.model.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByAuctionIdOrderByTimestampDesc(Long auctionId);
}
