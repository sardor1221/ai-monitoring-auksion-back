package net.sardor.manitoring_ai_auksion.web.mapper;

import net.sardor.manitoring_ai_auksion.model.entity.Bid;
import net.sardor.manitoring_ai_auksion.web.dto.BidResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BidMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "auctionId", source = "auction.id")
    BidResponse toResponse(Bid bid);
}
