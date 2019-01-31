set myDIR=src\main\resources\war
IF not exist %myDIR% (
    mkdir %myDIR%
)
xcopy /y /i ..\GaragemApi\target\GaragemApi-*.war %myDIR%\GaragemApi.war*
xcopy /y /i ..\GaragemWeb\target\GaragemWeb-*.war %myDIR%\GaragemWeb.war*
