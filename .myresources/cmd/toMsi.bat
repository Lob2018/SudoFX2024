@echo off
	chcp 65001
	color 0A
	:: JPACKAGE CREATE THE MSI
	title JPACKAGE CREATE THE MSI
	echo.
	echo #########################################
	echo #        JPACKAGE CREATE THE MSI        #
	echo #########################################
	echo.

    echo # Arguments from pom.xml :
    echo # 0   :"%0"
	echo # 1   :"%1"
	echo # 2   :"%2"
	echo # 3   :"%3"
	echo # 4   :"%4"
	echo # 5   :"%5"

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
	jpackage --dest output --name %1 --type msi --module-path "%PATH_TO_FX_JMODS%;./target/%jarName%" --add-modules %4 --module %5 --win-shortcut --win-menu --win-menu-group %1 --java-options -Xmx2048m --vendor %3 --copyright "Copyright © %year% %3" --icon src/main/resources/fr/softsf/sudofx2024/images/icon.ico --app-version %2
	echo # HASH.TXT : MD5 AND SHA256
	set "exeFile=%1-%2.msi"
	cd ./output
	(
		echo.
		CertUtil -hashfile %exeFile% MD5
	    echo.
	    CertUtil -hashfile %exeFile% SHA256
	    echo.
	) > hash.txt
	echo # DONE
	echo.
exit