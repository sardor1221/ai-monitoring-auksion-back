package net.sardor.manitoring_ai_auksion.service;

import lombok.RequiredArgsConstructor;
import net.sardor.manitoring_ai_auksion.model.entity.Auction;
import net.sardor.manitoring_ai_auksion.model.entity.User;
import net.sardor.manitoring_ai_auksion.model.enums.AuctionStatus;
import net.sardor.manitoring_ai_auksion.repository.AuctionRepository;
import net.sardor.manitoring_ai_auksion.repository.UserRepository;
import net.sardor.manitoring_ai_auksion.web.dto.AuctionRequest;
import net.sardor.manitoring_ai_auksion.web.dto.AuctionResponse;
import net.sardor.manitoring_ai_auksion.web.mapper.AuctionMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final AuctionMapper auctionMapper;

    @Transactional
    public AuctionResponse createAuction(AuctionRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User seller = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Auction auction = auctionMapper.toEntity(request);
        auction.setSeller(seller);
        auction.setStatus(AuctionStatus.CREATED);
        auction.setCurrentPrice(request.getStartPrice());

        Auction savedAuction = auctionRepository.save(auction);
        return auctionMapper.toResponse(savedAuction);
    }

    public List<AuctionResponse> getAllAuctions() {
        return auctionRepository.findAll().stream()
                .map(auctionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AuctionResponse getAuctionById(Long id) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        return auctionMapper.toResponse(auction);
    }

    @Transactional
    public void deleteAuction(Long id) {
        auctionRepository.deleteById(id);
    }
}
