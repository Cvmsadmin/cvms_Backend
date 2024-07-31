package org.ss.vendorapi.modal;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfitLossRequestDTO {
    private List<ProfitLossMasterDTO> profitLoss;
    private String clientName;
    private String projectName;
    
    public List<ProfitLossMasterDTO> getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(List<ProfitLossMasterDTO> profitLoss) {
        this.profitLoss = profitLoss;
    }
    
    // Any other fields and methods
}


