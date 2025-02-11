package bio.service;

import bio.dto.BioInvReceivingDTO;
import bio.dto.BioInvReceivingPageRequestDTO;
import bio.dto.BioInvReceivingPageResponseDTO;

public interface BioInvReceivingService {
    Long register(BioInvReceivingDTO bioInvReceivingDTO);
    BioInvReceivingDTO readOne(Long ReceivingId);
    void modify(BioInvReceivingDTO bioInvReceivingDTO);
    void remove(Long ReceivingId);
    BioInvReceivingPageResponseDTO<BioInvReceivingDTO> list(BioInvReceivingPageRequestDTO bioInvReceivingPageRequestDTO);
}
