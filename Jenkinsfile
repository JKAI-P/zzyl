pipeline {
    agent any
    options {
        timestamps()
    }
    tools {
        maven 'maven'
        jdk 'jdk11'
    }
    stages {
        stage('清除工作空间') {
            steps {
                cleanWs()
            }
        }
        stage('拉取Git代码') {
            steps {
                echo "正在拉取代码..."
                echo "当前分支:${GIT_TAG},当前服务:${services}"
                checkout([$class: 'GitSCM',
                          branches: [[name: GIT_TAG]],
                          doGenerateSubmoduleConfigurations: false,
                          extensions: [],
                          submoduleCfg: [],
                          userRemoteConfigs: [[credentialsId: 'Gitee_ID', url: GIT_URL]]
                ])
                sh "pwd"
            }
        }
        stage('重新Maven打包') {
            steps {
                script {
                    echo "正在执行maven打包...."
                    sh "mvn clean install -DskipTests"
                }
            }
        }
        stage('存在容器处理'){
          steps {
             script {
                for (ws in services.tokenize(",")) {
                   def containerExists = sh(script: "docker ps -a --filter 'name=${ws}' --format '{{.Names}}' | grep -w '${ws}'", returnStatus: true) == 0
                   if (containerExists) {
                      echo "容器${ws}存在"
                      sh "docker rm -f ${ws}"
                      echo "容器${ws}已删除"
                   } else {
                      echo "容器${ws}不存在"
                   }
                }
             }
          }
       }
        stage('重新构建镜像') {
            steps {
                echo "当前打镜像tag:${DOCKER_TAG}"
                script {
                    for (ds in services.tokenize(",")) {
                        def imageExists = sh(script: "docker images ${ds}:${DOCKER_TAG} --format '{{.ID}}' | grep -q .", returnStatus: true) == 0
                        if (imageExists) {
                            echo "镜像${ds}:${DOCKER_TAG}存在"
                            def imageId = sh(script: "docker images ${ds}:${DOCKER_TAG} --format '{{.ID}}'", returnStdout: true).trim()
                            echo "获取镜像ID：${imageId}"
                            sh "docker rmi -f ${imageId}"
                            echo "镜像${ds}:${DOCKER_TAG}已删除"
                        } else {
                            echo "镜像${ds}:${DOCKER_TAG}不存在"
                        }
                        sh "pwd"
                        echo "进入target目录执行镜像打包......"
                        sh "cd ./${ds}/target/ && docker build -t ${ds}:${DOCKER_TAG} -f ../Dockerfile ."
                    }
                }
            }
        }
        stage('部署服务'){
            steps {
                script {
                    for (ws in services.tokenize(",")) {
                        sh "pwd"
                        sh "cd `pwd`"
                        echo "部署升级:${ws}服务"
                        sh "chmod +x ./${ws}/deploy.sh && sh ./${ws}/deploy.sh ${ws} ${DOCKER_TAG}"
                    }
                }
            }
        }

    }
    post {
        always {
            echo '任务构建完毕'
        }
    }
}