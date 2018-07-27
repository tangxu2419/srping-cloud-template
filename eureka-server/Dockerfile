# 基础镜像：
FROM java:8
# 将打包好的程序拷贝到容器的指定位置
ARG JAR_FILE
ADD $JAR_FILE app.jar
RUN chmod +x app.jar
RUN bash -c 'touch /app.jar'
# 容器启动后执行的命令
ENV PROFILE=""
ENTRYPOINT ["sh","-c","java -Dspring.profiles.active=$PROFILE -jar app.jar"]
#ENTRYPOINT /app.jar $ARGS --spring.profiles.active=$PROFILE
