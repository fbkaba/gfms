pipeline {
    agent any
    tools {
        maven 'Maven3'
    }
    environment {
        registry = "949214307611.dkr.ecr.eu-west-3.amazonaws.com/my-docker-repo"
    }    
    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'GIT_HUB_CREDENTIALS', url: 'https://github.com/fbkaba/gfms.git']]])
            }
        }
        
        stage('Build Jar') {
            steps {
                sh 'mvn clean install'            
            }
        }
    // Building Docker images        
        stage('Building image') {
            steps {
                script {
                    docker.build registry
                }
            }
        }  
        
        stage('push into ECR') {
            steps {
                script {
                    sh 'aws ecr get-login-password --region eu-west-3 | docker login --username AWS --password-stdin 949214307611.dkr.ecr.eu-west-3.amazonaws.com'
                    sh 'docker push 949214307611.dkr.ecr.eu-west-3.amazonaws.com/my-docker-repo:latest'
                }
            }
        }                   
        stage('K8S Deploy') {
            steps {
                withKubeConfig(caCertificate: '', clusterName: '', contextName: '', credentialsId: 'K8S', namespace: '', serverUrl: '') {
                     sh ('kubectl apply -f  eks-gfms-tresorkey-deploy-k8s.yaml')
}
                
            }
        }  
       
    }
}
