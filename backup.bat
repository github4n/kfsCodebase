::获取当前时间
set d=%date:~0,10%
set t=%time:~0,8%
set upload_time=%d% %t%
::echo %upload_time%

c:
cd C:\development_environment\repository\myself

::上传到git
git add .
git commit -m "newest modify time = %upload_time%"
git pull 
git push 
git status
 
::pause 