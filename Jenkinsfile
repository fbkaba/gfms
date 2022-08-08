node {
    stage("Git Clone"){
            cleanWs()
        git branch: 'main', credentialsId: 'GIT_HUB_CREDENTIALS', url: 'https://github.com/dv-sharma/DevOps_Project-1.git'
    }
    stage('Maven Build') {

     sh '''mvn clean install'''
  }

    stage("Docker build"){

        sh 'docker version'
        sh 'docker build -t pipeline-gfms .'
        sh 'docker image list'
        sh 'docker tag pipeline-gfms toumtou/pipelinegfms:v1'
    }
    stage("Docker Login"){
        withCredentials([string(credentialsId: 'DOCKER_PASSWORD', variable: 'PASSWORD')]) {
            sh 'docker login -u toumtou -p $PASSWORD'
        }
    }
    stage("Pushing Image to Docker Hub"){
        sh 'docker push  toumtou/pipelinegfms:v1'
    }
   
    }