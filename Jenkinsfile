pipeline {
    agent any
    tools {
        maven 'maven-3.9'
    }

    stages {
        stage('build jar') {
            steps {
                script {
                    echo "Building the jar file"
                    sh "mvn package"
                }
            }
        }
        stage('build image') {
            steps {
                script {
                    echo "Building the docker image"
                    withCredentials([usernamePassword(credentialsId: 'devops-token', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                        sh "docker build -t $USER/devops:jma-2.0 ."
                        sh "echo $PASS | docker login -u $USER --password-stdin"
                        sh "docker push $USER/devops:jma-2.0"
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying the docker image"
            }
        }
    }
}
