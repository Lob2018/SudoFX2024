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
	echo # JRE version         :"%5"

    echo.
	set "jarName=%1-%2.jar"
	set "year=%date:~6,4%"
	echo.
	echo # OUTPUT   : CLEAN
	rmdir /s /q output 2>nul
	cd ./target
	echo.
	echo # TARGET   : DELETE ALL BUT UBERJAR
	for %%I in (*.jar) do (
	    if /I not "%%~nxI"=="%jarName%" (
	        del "%%I"
	    )
	)
	echo.
	echo # OUTPUT   : CREATING THE MSI...
	cd ../
    jpackage --input ./target  --dest output --name %1 --type msi --main-jar %jarName% --main-class %4 --win-shortcut --win-menu --win-menu-group %1 --java-options "-Xmx2048m -Dapp.name=%1 -Dapp.version=%2" --vendor %3 --copyright "Copyright © %year% %3" --icon src/main/resources/fr/softsf/sudofx2024/images/icon.ico --app-version %2 --description "%1 %year%" --license-file LICENSE.txt --verbose
    echo.
    echo # TARGET   : THE BATCH TO LAUNCH THE UBERJAR
	cd ./target
    (
        echo @echo off
        echo     chcp 65001
        echo     color 0A
        echo     echo.
        echo     echo           ▒▒▒▒▒
        echo     echo          ▒▒▒▒▒▒▒
        echo     echo          ▒▒▒▒▒▒▒▒
        echo     echo          ▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒
        echo     echo           ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
        echo     echo     ▒▒▒▒▒▒▒▒▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒    ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓  ▓▓▓▓▓▓    ▓▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓▓
        echo     echo  ▒▒▒▒▒▒▒▒▒▒▓▓▓███▓▓▓▒▒▒▒▒▒▒     ▓▓▓     ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓      ▓▓▓▓▓     ▓▓▓    ▓▓▓ ▓▓▓
        echo     echo  ▒▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒      ▓▓▓▓▓▓  ▓▓    ▓▓ ▓▓▓▓▓▓    ▓▓▓   ▓▓▓▓▓▓▓ ▓▓▓ ▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓
        echo     echo   ▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒         ▓▓▓▓ ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓ ▓▓▓ ▓▓▓▓▓▓▓▓    ▓▓▓    ▓▓▓ ▓▓▓
        echo     echo     ▒▒▒▒▒▒▒▓▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒     ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓▓▓▓     ▓▓▓  ▓▓ ▓▓▓    ▓▓▓  ▓▓▓
        echo     echo         ▒▒▒▒▒▒▓▓▓▒▒▒▒▒▒▒▒▒▒
        echo     echo        ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
        echo     echo        ▒▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒▒
        echo     echo        ▒▒▒▒▒▒▒▒▒    ▒▒▒▒▒▒
        echo     echo         ▒▒▒▒▒
        echo     echo.
        echo     echo.
        echo     timeout /t 1 /nobreak ^>nul
        echo     set JAVA_VERSION=0
        echo     for /f "tokens=3" %%%%g in ('java -version 2^^^>^^^&1 ^^^| findstr /i "version"'^) do (
        echo         set JAVA_VERSION=%%%%g
        echo     ^)
        echo     set JAVA_VERSION=%%JAVA_VERSION:"=%%
        echo     set /a JAVA_REQUIRED=%5
        echo     if %%JAVA_VERSION%% EQU 0 (
        echo         echo.
        echo         echo  ██ Java minimum version %%JAVA_REQUIRED%% is required to run this application.
        echo         echo  ██ Please install the latest Java JRE available at ▒▒ https://adoptium.net ▒▒.
        echo         echo.
        echo         pause
        echo         exit /b 1
        echo     ^)
        echo     if %%JAVA_VERSION%% LSS %%JAVA_REQUIRED%% (
        echo         echo.
        echo         echo  ██ A newer version of Java is required to run this application.
        echo         echo  ██ Your Java version is %%JAVA_VERSION%%, and requires version %%JAVA_REQUIRED%%.
        echo         echo  ██ Please install the latest Java JRE available at ▒▒ https://adoptium.net ▒▒.
        echo         echo.
        echo         pause
        echo         exit /b 1
        echo     ^)
        echo     start /min cmd /c "java -Xmx2048m -Dapp.name=%1 -Dapp.version=%2 -jar %1-%2.jar & exit"
        echo exit
    ) > %1-%2.bat
    echo.
    echo # TARGET   : COPY THE BATCH AND THE UBERJAR TO OUTPUT AS A ZIP FILE
    set "zipName=%1-%2.zip"

    powershell -command "& {Compress-Archive -Path '%1-%2.bat', '%jarName%' -DestinationPath '..\output\%zipName%'}"

    echo.
    echo # OUTPUT   : THE HASH FILE
    set "msiFile=%1-%2.msi"
	cd ../
	cd ./output
	(
		echo.
		CertUtil -hashfile %msiFile% MD5
	    echo.
	    CertUtil -hashfile %msiFile% SHA256
	    echo.
        CertUtil -hashfile %zipName% MD5
        echo.
	    CertUtil -hashfile %zipName% SHA256
        echo.
	) > hash.txt
	cd ../
	echo.
    echo ###################################################################################################
	echo #                                             DONE !                                              #
	echo ###################################################################################################
	echo.

exit