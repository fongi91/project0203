package bio.service;

import bio.domain.BioInvShipping;
import bio.dto.BioInvShippingDTO;
import bio.dto.BioInvShippingPageRequestDTO;
import bio.dto.BioInvShippingPageResponseDTO;

public interface BioInvShippingService {
    Long register(BioInvShippingDTO bioInvShippingDTO);
    BioInvShippingDTO readOne(Long shippingId);
    void modify(BioInvShippingDTO bioInvShippingDTO);
    void remove(Long shippingId);
    BioInvShippingPageResponseDTO<BioInvShippingDTO> list(BioInvShippingPageRequestDTO bioInvShippingPageRequestDTO);
}
