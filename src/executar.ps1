# Script PowerShell para compilar e executar o sistema
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host "  SISTEMA DE REDE SOCIAL - MVC" -ForegroundColor Cyan
Write-Host "  Compilando projeto..." -ForegroundColor Cyan
Write-Host "=========================================" -ForegroundColor Cyan

# Compilação
javac -encoding UTF-8 -cp ".;lib\sqlite-jdbc-3.45.2.0.jar" -d bin `
    src/Main.java `
    src/models/*.java `
    src/persistence/*.java `
    src/service/*.java `
    src/controllers/*.java `
    src/views/TelaAprovacaoAfiliacao.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "`nCompilacao concluida com sucesso!`n" -ForegroundColor Green
    Write-Host "Iniciando aplicacao..." -ForegroundColor Yellow
    Write-Host "=========================================`n" -ForegroundColor Cyan
    
    # Execução
    java -cp "bin;lib\sqlite-jdbc-3.45.2.0.jar;lib\slf4j-api-2.0.12.jar;lib\slf4j-simple-2.0.12.jar" Main
} else {
    Write-Host "`nERRO na compilacao!" -ForegroundColor Red
    exit 1
}
