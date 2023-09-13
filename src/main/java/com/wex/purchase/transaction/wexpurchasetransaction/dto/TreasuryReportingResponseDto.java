package com.wex.purchase.transaction.wexpurchasetransaction.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TreasuryReportingResponseDto {
    private List<TreasuryReportingResponseData> data;
}
