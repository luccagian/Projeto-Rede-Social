@echo off
REM Script para executar o sistema de aprovação de afiliação
REM Compilação e execução do projeto MVC com SQLite

echo =========================================
echo  SISTEMA DE REDE SOCIAL - MVC
echo  Compilando projeto...
echo =========================================

javac -encoding UTF-8 -cp ".;lib\sqlite-jdbc-3.45.2.0.jar" -d bin src\Main.java src\models\*.java src\persistence\*.java src\service\*.java src\controllers\*.java src\views\TelaAprovacaoAfiliacao.java

if %errorlevel% neq 0 (
    echo ERRO na compilacao!
    pause
    exit /b 1
)

echo.
echo Compilacao concluida com sucesso!
echo.
echo Iniciando aplicacao...
echo =========================================
echo.

java -cp "bin;lib\sqlite-jdbc-3.45.2.0.jar;lib\slf4j-api-2.0.12.jar;lib\slf4j-simple-2.0.12.jar" Main

pause
