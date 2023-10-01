@REM ATUALIZADOR
@REM Este arquivo é utilizado para atualizar a versao do consolidador
@REM Assim, consigo fazer atualizacoes sem precisar no computador

@echo off
echo  Atualizando consolidador..

setlocal enabledelayedexpansion


echo  Iniciando variaveis
set download_url=https://github.com/victordalosto/consolida-pdf/releases/download/v1.0/consolidador.jar
set build_directory=./build
set jar_file=%build_directory%\consolidador.jar



echo  Criando diretorio: %build_directory%
if not exist "%build_directory%" (
    mkdir "%build_directory%"
)


echo  Deletando executavel antigo
if exist "%jar_file%" (
    del "%jar_file%"
)


echo  Baixando executavel novo
cd "%build_directory%"
curl -LOJ "%download_url%"


if %errorlevel% equ 0 (
    echo  Arquivos atualizados...
) else (
    echo  Houve um erro ao tentar baixar a atualizacao...
)

endlocal
@pause