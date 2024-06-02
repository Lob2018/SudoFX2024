@echo off
	chcp 65001
	color 0A
	:: JPACKAGE
	title JPACKAGE CREATE THE MSI
    echo.
    echo          ▒▒▒▒▒
    echo         ▒▒▒▒▒▒▒
    echo         ▒▒▒▒▒▒▒▒
    echo         ▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒
    echo         ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
    echo    ▒▒▒▒▒▒▒▒▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒    ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓  ▓▓▓▓▓▓    ▓▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓▓
    echo ▒▒▒▒▒▒▒▒▒▒▓▓▓███▓▓▓▒▒▒▒▒▒▒     ▓▓▓     ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓      ▓▓▓▓▓     ▓▓▓    ▓▓▓ ▓▓▓
    echo ▒▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒      ▓▓▓▓▓▓  ▓▓    ▓▓ ▓▓▓▓▓▓    ▓▓▓   ▓▓▓▓▓▓▓ ▓▓▓ ▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓
    echo  ▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒         ▓▓▓▓ ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓ ▓▓▓ ▓▓▓▓▓▓▓▓    ▓▓▓    ▓▓▓ ▓▓▓
    echo    ▒▒▒▒▒▒▒▓▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒     ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓▓▓▓     ▓▓▓  ▓▓ ▓▓▓    ▓▓▓  ▓▓▓
    echo        ▒▒▒▒▒▒▓▓▓▒▒▒▒▒▒▒▒▒▒
    echo       ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
    echo       ▒▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒▒
    echo       ▒▒▒▒▒▒▒▒▒    ▒▒▒▒▒▒
    echo        ▒▒▒▒▒
    echo.
	echo ###################################################################################################
	echo #                                     JPACKAGE CREATE THE MSI                                     #
	echo ###################################################################################################
	echo.
	echo # WiX Toolset is installed
	echo # WiX's bin path is added to PATH environment variable
    echo.
    echo # Arguments from pom.xml :
    echo # This batch's path   :"%0"
	echo # App's name          :"%1"
	echo # Current version     :"%2"
	echo # Organization's name :"%3"
	echo # Main's path         :"%4"

    echo.
	set "jarName=%1-%2.jar"
	set "year=%date:~6,4%"
	echo # OUTPUT   : DELETE OLD MSI	
	rmdir /s /q output 2>nul
	cd ./target
	echo # TARGET   : DELETE ALL BUT CURRENT JAR
	for %%I in (*.jar) do (
	    if /I not "%%~nxI"=="%jarName%" (
	        del "%%I"
	    )
	)	
	echo # OUTPUT   : CREATING THE MSI...
	cd ../
    jpackage --input ./target  --dest output --name %1 --type msi --main-jar %jarName% --main-class %4 --win-shortcut --win-menu --win-menu-group %1 --java-options "-Xmx3072m -Dapp.name=%1 -Dapp.version=%2" --vendor %3 --copyright "Copyright © %year% %3" --icon src/main/resources/fr/softsf/sudofx2024/images/icon.ico --app-version %2 --description "%1 %year%" --license-file LICENSE.txt --verbose
	echo # HASH.TXT : MD5 AND SHA256
	set "msiFile=%1-%2.msi"
	cd ./output
	(
		echo.
		CertUtil -hashfile %msiFile% MD5
	    echo.
	    CertUtil -hashfile %msiFile% SHA256
	    echo.
	) > hash.txt
	echo.
    echo ###################################################################################################
	echo #                                             DONE !                                              #
	echo ###################################################################################################
	echo.
exit