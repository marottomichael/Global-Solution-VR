$base = "http://localhost:8081"
$endpoints = @(
    @{ Method = "GET"; Path = "/dashboard/amazonia" },
    @{ Method = "GET"; Path = "/estatisticas" },
    @{ Method = "GET"; Path = "/solicitacoes" },
    @{ Method = "GET"; Path = "/avaliacoes" }
)

Write-Host "`n=== Teste de endpoints MEMORA ===" -ForegroundColor Cyan
Write-Host "Base: $base`n"

foreach ($ep in $endpoints) {
    $url = "$base$($ep.Path)"
    try {
        $response = Invoke-WebRequest -Uri $url -Method $ep.Method -UseBasicParsing -TimeoutSec 10
        Write-Host "[OK $($response.StatusCode)] $($ep.Method) $($ep.Path)" -ForegroundColor Green
        if ($ep.Path -eq "/dashboard/amazonia") {
            $json = $response.Content | ConvertFrom-Json
            Write-Host "       titulo: $($json.titulo)"
            Write-Host "       metricas: $($json.metricas.Count) registros"
        }
    } catch {
        $status = $_.Exception.Response.StatusCode.value__
        if ($status) {
            Write-Host "[ERRO $status] $($ep.Method) $($ep.Path)" -ForegroundColor Red
        } else {
            Write-Host "[FALHA] $($ep.Method) $($ep.Path) - backend inacessivel" -ForegroundColor Red
            Write-Host "        $($_.Exception.Message)"
        }
    }
}

Write-Host ""
