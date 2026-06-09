package br.com.globalsolution.dto.response;

import java.util.List;

public record DashboardResponse(String titulo, String descricao, List<MetricaData> metricas) {
}
