package net.sardor.manitoring_ai_auksion.repository;

import net.sardor.manitoring_ai_auksion.model.entity.Auction;
import net.sardor.manitoring_ai_auksion.model.enums.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByStatus(AuctionStatus status);
}
