package net.sardor.manitoring_ai_auksion.web.mapper;

import net.sardor.manitoring_ai_auksion.model.entity.Auction;
import net.sardor.manitoring_ai_auksion.web.dto.AuctionRequest;
import net.sardor.manitoring_ai_auksion.web.dto.AuctionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuctionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "currentPrice", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "winner", ignore = true)
    Auction toEntity(AuctionRequest request);

    @Mapping(target = "sellerId", source = "seller.id")
    @Mapping(target = "winnerId", source = "winner.id")
    AuctionResponse toResponse(Auction auction);
}
