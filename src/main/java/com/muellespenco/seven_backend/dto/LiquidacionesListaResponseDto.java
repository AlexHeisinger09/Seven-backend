package com.muellespenco.seven_backend.dto;

import java.io.Serializable;
import java.util.List;

public class LiquidacionesListaResponseDto implements Serializable {
    
    private List<LiquidacionResponseDto> liquidaciones;
    private long total;
    private boolean hasMore;

    public LiquidacionesListaResponseDto() {}

    public LiquidacionesListaResponseDto(List<LiquidacionResponseDto> liquidaciones, long total, boolean hasMore) {
        this.liquidaciones = liquidaciones;
        this.total = total;
        this.hasMore = hasMore;
    }

    public List<LiquidacionResponseDto> getLiquidaciones() {
        return liquidaciones;
    }

    public void setLiquidaciones(List<LiquidacionResponseDto> liquidaciones) {
        this.liquidaciones = liquidaciones;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}