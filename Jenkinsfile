pipeline {
    agent any
    parameters {
        string(name: 'NEW_VERSION', defaultValue: '1.0.0', description: 'New version to build')
        choice(name: 'ENVIRONMENT', choices: ['DEV', 'QA', 'PROD'], description: 'Environment to deploy to')
        booleanParam(name: 'DEPLOY_TO_PROD', defaultValue: false, description: 'Should we deploy to production?')
    }
    environment {
        NEW_VERSION = '1.0.0'
        SERVER_CREDENTIALS = credentials('nexus-creds')
    }
    tools {
        maven 'maven-3.9.5'
    }
    stages {
        stage('Build') {
            steps {
                echo "Building version ${NEW_VERSION}"
            }
        }
        stage('Test') {
            when {
                expression { params.ENVIRONMENT == 'DEV' }
            }
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying version ${NEW_VERSION} to ${ENVIRONMENT}"
                withCredentials([
                    usernamePassword(credentialsId: 'nexus-creds', usernameVariable: USERNAME, passwordVariable: PASSWORD)]) {
                    sh "echo ${USERNAME}"
                    sh "echo ${PASSWORD}"
                }
            }
        }
    }
    post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}
