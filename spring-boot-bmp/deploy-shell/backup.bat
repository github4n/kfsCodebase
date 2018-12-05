::c:
::cd C:\development_environment\repository\myself\spring-boot-bmp\springboot-bmp-parent

::打包
::mvn package -Dmaven.test.skip=true


::c:
cd C:\development_environment\repository\myself\spring-boot-bmp\deploy-shell
::上传到FTP
ftp -s:springboot-ftp.txt
 
pause 