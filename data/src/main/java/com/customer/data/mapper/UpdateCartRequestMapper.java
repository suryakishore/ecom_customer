package com.customer.data.mapper;

import com.customer.domain.model.common.ImageData;
import com.customer.domain.model.pdp.PdpOfferData;
import com.customer.remote.http.model.response.common.ImagesDetails;
import com.customer.remote.http.model.response.pdp.OfferName;
import com.customer.remote.http.model.response.pdp.PdpOfferDetails;

import org.jetbrains.annotations.NotNull;

public class UpdateCartRequestMapper {

    @NotNull
    public PdpOfferDetails getOffer(PdpOfferData offer) {
        try {
            return new PdpOfferDetails(offer.getApplicableOnStatus(), convertToImages(offer.getImages()),
                    convertToOfferName(offer.getOfferName()), offer.getEndDateTimeISO(), offer.getEndDateTime(), offer.getGlobalClaimCount(),
                    offer.getStartDateTimeISO(), convertToWebImages(offer.getWebimages()), offer.getStartDateTime(), offer.getPerUserLimit(),
                    offer.getMinimumPurchaseQty(), offer.getStatusString(), offer.getOfferId(), offer.getDiscountType(), offer.getOfferFor(),
                    offer.getDiscountValue(), offer.getApplicableOn(), offer.getStatus());
        } catch (NullPointerException e) {
            return new PdpOfferDetails();
        }
    }

    private ImagesDetails convertToImages(ImageData images) {
        return new ImagesDetails(images.getImageText(), images.getImage(), images.getThumbnail(),
                images.getMobile(), images.getDescription(), images.getMedium(), images.getKeyword());
    }

    private OfferName convertToOfferName(String offerName) {
        return new OfferName(offerName);
    }

    private ImagesDetails convertToWebImages(ImageData webimages) {
        return new ImagesDetails(webimages.getImageText(), webimages.getImage(), webimages.getThumbnail(),
                webimages.getMobile(), webimages.getDescription(), webimages.getMedium(), webimages.getKeyword());
    }
}
