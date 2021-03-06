@REM ----------------------------------------------------------------------------
@REM Copyright 2001-2004 The Apache Software Foundation.
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM      http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM ----------------------------------------------------------------------------
@REM

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\repo

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\org\apache\maven\plugins\maven-compiler-plugin\3.8.1\maven-compiler-plugin-3.8.1.jar;"%REPO%"\org\apache\maven\maven-plugin-api\3.0\maven-plugin-api-3.0.jar;"%REPO%"\org\apache\maven\maven-model\3.0\maven-model-3.0.jar;"%REPO%"\org\sonatype\sisu\sisu-inject-plexus\1.4.2\sisu-inject-plexus-1.4.2.jar;"%REPO%"\org\sonatype\sisu\sisu-inject-bean\1.4.2\sisu-inject-bean-1.4.2.jar;"%REPO%"\org\sonatype\sisu\sisu-guice\2.1.7\sisu-guice-2.1.7-noaop.jar;"%REPO%"\org\apache\maven\maven-artifact\3.0\maven-artifact-3.0.jar;"%REPO%"\org\codehaus\plexus\plexus-utils\2.0.4\plexus-utils-2.0.4.jar;"%REPO%"\org\apache\maven\maven-core\3.0\maven-core-3.0.jar;"%REPO%"\org\apache\maven\maven-settings\3.0\maven-settings-3.0.jar;"%REPO%"\org\apache\maven\maven-settings-builder\3.0\maven-settings-builder-3.0.jar;"%REPO%"\org\apache\maven\maven-repository-metadata\3.0\maven-repository-metadata-3.0.jar;"%REPO%"\org\apache\maven\maven-model-builder\3.0\maven-model-builder-3.0.jar;"%REPO%"\org\apache\maven\maven-aether-provider\3.0\maven-aether-provider-3.0.jar;"%REPO%"\org\sonatype\aether\aether-impl\1.7\aether-impl-1.7.jar;"%REPO%"\org\sonatype\aether\aether-spi\1.7\aether-spi-1.7.jar;"%REPO%"\org\sonatype\aether\aether-api\1.7\aether-api-1.7.jar;"%REPO%"\org\sonatype\aether\aether-util\1.7\aether-util-1.7.jar;"%REPO%"\org\codehaus\plexus\plexus-interpolation\1.14\plexus-interpolation-1.14.jar;"%REPO%"\org\codehaus\plexus\plexus-classworlds\2.2.3\plexus-classworlds-2.2.3.jar;"%REPO%"\org\codehaus\plexus\plexus-component-annotations\1.5.5\plexus-component-annotations-1.5.5.jar;"%REPO%"\org\sonatype\plexus\plexus-sec-dispatcher\1.3\plexus-sec-dispatcher-1.3.jar;"%REPO%"\org\sonatype\plexus\plexus-cipher\1.4\plexus-cipher-1.4.jar;"%REPO%"\org\apache\maven\shared\maven-shared-utils\3.2.1\maven-shared-utils-3.2.1.jar;"%REPO%"\org\apache\maven\shared\maven-shared-incremental\1.1\maven-shared-incremental-1.1.jar;"%REPO%"\org\codehaus\plexus\plexus-java\0.9.10\plexus-java-0.9.10.jar;"%REPO%"\org\ow2\asm\asm\6.2\asm-6.2.jar;"%REPO%"\com\thoughtworks\qdox\qdox\2.0-M8\qdox-2.0-M8.jar;"%REPO%"\org\codehaus\plexus\plexus-compiler-api\2.8.4\plexus-compiler-api-2.8.4.jar;"%REPO%"\org\codehaus\plexus\plexus-compiler-manager\2.8.4\plexus-compiler-manager-2.8.4.jar;"%REPO%"\org\codehaus\plexus\plexus-compiler-javac\2.8.4\plexus-compiler-javac-2.8.4.jar;"%REPO%"\org\slf4j\slf4j-api\1.7.5\slf4j-api-1.7.5.jar;"%REPO%"\org\slf4j\slf4j-log4j12\1.7.5\slf4j-log4j12-1.7.5.jar;"%REPO%"\log4j\log4j\1.2.17\log4j-1.2.17.jar;"%REPO%"\org\telegram\telegrambots\4.4.0.2\telegrambots-4.4.0.2.jar;"%REPO%"\org\telegram\telegrambots-meta\4.4.0.2\telegrambots-meta-4.4.0.2.jar;"%REPO%"\com\google\inject\guice\4.2.2\guice-4.2.2.jar;"%REPO%"\javax\inject\javax.inject\1\javax.inject-1.jar;"%REPO%"\aopalliance\aopalliance\1.0\aopalliance-1.0.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-annotations\2.10.1\jackson-annotations-2.10.1.jar;"%REPO%"\com\fasterxml\jackson\jaxrs\jackson-jaxrs-json-provider\2.10.1\jackson-jaxrs-json-provider-2.10.1.jar;"%REPO%"\com\fasterxml\jackson\jaxrs\jackson-jaxrs-base\2.10.1\jackson-jaxrs-base-2.10.1.jar;"%REPO%"\com\fasterxml\jackson\module\jackson-module-jaxb-annotations\2.10.1\jackson-module-jaxb-annotations-2.10.1.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-core\2.10.1\jackson-core-2.10.1.jar;"%REPO%"\jakarta\xml\bind\jakarta.xml.bind-api\2.3.2\jakarta.xml.bind-api-2.3.2.jar;"%REPO%"\jakarta\activation\jakarta.activation-api\1.2.1\jakarta.activation-api-1.2.1.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-databind\2.10.1\jackson-databind-2.10.1.jar;"%REPO%"\org\glassfish\jersey\inject\jersey-hk2\2.29.1\jersey-hk2-2.29.1.jar;"%REPO%"\org\glassfish\jersey\core\jersey-common\2.29.1\jersey-common-2.29.1.jar;"%REPO%"\org\glassfish\hk2\osgi-resource-locator\1.0.3\osgi-resource-locator-1.0.3.jar;"%REPO%"\com\sun\activation\jakarta.activation\1.2.1\jakarta.activation-1.2.1.jar;"%REPO%"\org\glassfish\hk2\hk2-locator\2.6.1\hk2-locator-2.6.1.jar;"%REPO%"\org\glassfish\hk2\external\aopalliance-repackaged\2.6.1\aopalliance-repackaged-2.6.1.jar;"%REPO%"\org\glassfish\hk2\hk2-api\2.6.1\hk2-api-2.6.1.jar;"%REPO%"\org\glassfish\hk2\hk2-utils\2.6.1\hk2-utils-2.6.1.jar;"%REPO%"\org\javassist\javassist\3.22.0-CR2\javassist-3.22.0-CR2.jar;"%REPO%"\org\glassfish\jersey\media\jersey-media-json-jackson\2.29.1\jersey-media-json-jackson-2.29.1.jar;"%REPO%"\org\glassfish\jersey\ext\jersey-entity-filtering\2.29.1\jersey-entity-filtering-2.29.1.jar;"%REPO%"\org\glassfish\jersey\containers\jersey-container-grizzly2-http\2.29.1\jersey-container-grizzly2-http-2.29.1.jar;"%REPO%"\org\glassfish\hk2\external\jakarta.inject\2.6.1\jakarta.inject-2.6.1.jar;"%REPO%"\org\glassfish\grizzly\grizzly-http-server\2.4.4\grizzly-http-server-2.4.4.jar;"%REPO%"\org\glassfish\grizzly\grizzly-http\2.4.4\grizzly-http-2.4.4.jar;"%REPO%"\org\glassfish\grizzly\grizzly-framework\2.4.4\grizzly-framework-2.4.4.jar;"%REPO%"\jakarta\ws\rs\jakarta.ws.rs-api\2.1.6\jakarta.ws.rs-api-2.1.6.jar;"%REPO%"\org\glassfish\jersey\core\jersey-server\2.29.1\jersey-server-2.29.1.jar;"%REPO%"\org\glassfish\jersey\core\jersey-client\2.29.1\jersey-client-2.29.1.jar;"%REPO%"\org\glassfish\jersey\media\jersey-media-jaxb\2.29.1\jersey-media-jaxb-2.29.1.jar;"%REPO%"\jakarta\annotation\jakarta.annotation-api\1.3.5\jakarta.annotation-api-1.3.5.jar;"%REPO%"\jakarta\validation\jakarta.validation-api\2.0.2\jakarta.validation-api-2.0.2.jar;"%REPO%"\org\json\json\20180813\json-20180813.jar;"%REPO%"\org\apache\httpcomponents\httpclient\4.5.10\httpclient-4.5.10.jar;"%REPO%"\org\apache\httpcomponents\httpcore\4.4.12\httpcore-4.4.12.jar;"%REPO%"\commons-logging\commons-logging\1.2\commons-logging-1.2.jar;"%REPO%"\commons-codec\commons-codec\1.11\commons-codec-1.11.jar;"%REPO%"\org\apache\httpcomponents\httpmime\4.5.10\httpmime-4.5.10.jar;"%REPO%"\commons-io\commons-io\2.6\commons-io-2.6.jar;"%REPO%"\com\google\api-client\google-api-client\1.30.4\google-api-client-1.30.4.jar;"%REPO%"\com\google\oauth-client\google-oauth-client\1.30.3\google-oauth-client-1.30.3.jar;"%REPO%"\com\google\http-client\google-http-client\1.32.1\google-http-client-1.32.1.jar;"%REPO%"\io\opencensus\opencensus-api\0.24.0\opencensus-api-0.24.0.jar;"%REPO%"\io\grpc\grpc-context\1.22.1\grpc-context-1.22.1.jar;"%REPO%"\io\opencensus\opencensus-contrib-http-util\0.24.0\opencensus-contrib-http-util-0.24.0.jar;"%REPO%"\com\google\code\findbugs\jsr305\3.0.2\jsr305-3.0.2.jar;"%REPO%"\com\google\http-client\google-http-client-jackson2\1.32.1\google-http-client-jackson2-1.32.1.jar;"%REPO%"\com\google\guava\guava\28.0-android\guava-28.0-android.jar;"%REPO%"\com\google\guava\failureaccess\1.0.1\failureaccess-1.0.1.jar;"%REPO%"\com\google\guava\listenablefuture\9999.0-empty-to-avoid-conflict-with-guava\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;"%REPO%"\org\checkerframework\checker-compat-qual\2.5.5\checker-compat-qual-2.5.5.jar;"%REPO%"\com\google\errorprone\error_prone_annotations\2.3.2\error_prone_annotations-2.3.2.jar;"%REPO%"\com\google\j2objc\j2objc-annotations\1.3\j2objc-annotations-1.3.jar;"%REPO%"\org\codehaus\mojo\animal-sniffer-annotations\1.17\animal-sniffer-annotations-1.17.jar;"%REPO%"\com\google\oauth-client\google-oauth-client-jetty\1.30.4\google-oauth-client-jetty-1.30.4.jar;"%REPO%"\com\google\oauth-client\google-oauth-client-java6\1.30.4\google-oauth-client-java6-1.30.4.jar;"%REPO%"\org\eclipse\jetty\jetty-server\8.2.0.v20160908\jetty-server-8.2.0.v20160908.jar;"%REPO%"\org\eclipse\jetty\orbit\javax.servlet\3.0.0.v201112011016\javax.servlet-3.0.0.v201112011016.jar;"%REPO%"\org\eclipse\jetty\jetty-continuation\8.2.0.v20160908\jetty-continuation-8.2.0.v20160908.jar;"%REPO%"\org\eclipse\jetty\jetty-http\8.2.0.v20160908\jetty-http-8.2.0.v20160908.jar;"%REPO%"\org\eclipse\jetty\jetty-io\8.2.0.v20160908\jetty-io-8.2.0.v20160908.jar;"%REPO%"\org\eclipse\jetty\jetty-util\8.2.0.v20160908\jetty-util-8.2.0.v20160908.jar;"%REPO%"\com\google\apis\google-api-services-sheets\v4-rev581-1.25.0\google-api-services-sheets-v4-rev581-1.25.0.jar;"%REPO%"\org\jetbrains\kotlin\kotlin-stdlib-jdk8\1.3.72\kotlin-stdlib-jdk8-1.3.72.jar;"%REPO%"\org\jetbrains\kotlin\kotlin-stdlib\1.3.72\kotlin-stdlib-1.3.72.jar;"%REPO%"\org\jetbrains\kotlin\kotlin-stdlib-common\1.3.72\kotlin-stdlib-common-1.3.72.jar;"%REPO%"\org\jetbrains\annotations\13.0\annotations-13.0.jar;"%REPO%"\org\jetbrains\kotlin\kotlin-stdlib-jdk7\1.3.72\kotlin-stdlib-jdk7-1.3.72.jar;"%REPO%"\org\example\TelegramBot\1.0-SNAPSHOT\TelegramBot-1.0-SNAPSHOT.jar
set EXTRA_JVM_ARGUMENTS=
goto endInit

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS% %EXTRA_JVM_ARGUMENTS% -classpath %CLASSPATH_PREFIX%;%CLASSPATH% -Dapp.name="TelegramBot" -Dapp.repo="%REPO%" -Dbasedir="%BASEDIR%" com.telegram.bot.MainMethod.kt %CMD_LINE_ARGS%
if ERRORLEVEL 1 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=1

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@endlocal

:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
