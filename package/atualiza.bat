@REM ATUALIZADOR v1.1
@REM Este arquivo é utilizado para atualizar a versao do projeto
@REM Ele automaticamente baixa a versão mais recente do projeto

@echo off
echo  ########################################
echo  # Atualizando ...

setlocal enabledelayedexpansion


echo  # Iniciando variaveis
set download_url=https://github.com/victordalosto/consolida-pdf/releases/download/v1.0/consolidador.jar
set build_directory=./build
set jar_file=%build_directory%\consolidador.jar



if not exist "%build_directory%" (
    echo  # Criando diretorio: %build_directory%
    mkdir "%build_directory%"
)



if exist "%jar_file%" (
    echo  # Deletando executavel antigo
    del "%jar_file%"
)



echo  # Baixando executavel novo
cd "%build_directory%"
curl -LOJ "%download_url%"



echo  ########################################
if %errorlevel% neq 0 (
    echo  # ERRO
    echo  # ERRO AO TENTAR ATUALIZAR ARQUIVO
) else (
    ren consolidador.jar app.jar
    echo  # ARQUIVOS ATUALIZADOS COM SUCESSO
)
echo  ########################################

endlocal
@pause