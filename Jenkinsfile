#!/usr/bin/env groovy
// @Library('jenkins-shared-library')

library identifier: 'jenkins-shared-library@master', retriever: modernSCM(
    [$class: 'GitSCMSource',
     remote: 'git@github.com:sidor2/devops-module8-jenkins-shared-lib.git',
        credentialsId: '2c40c606-3564-4fc4-8fc2-3a89a016f089',
    ]
)

def gv

pipeline {
    agent any
    tools {
        maven 'maven-3.9'
    }

    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("test") {
            steps {
                script {
                    gv.testApp()
                }
            }
        }
        stage('build jar') {
            when {
                expression {
                    BRANCH_NAME == 'jenkins-shared-lib'
                }
            }
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage('build and push image') {
            when {
                expression {
                    BRANCH_NAME == 'jenkins-shared-lib'
                }
            }
            steps {
                script {
                    buildImage 'ilsoldier/devops:jma-3.0'
                    dockerLogin()
                    dockerPush 'ilsoldier/devops:jma-3.0'
                }
            }
        }
        stage('Deploy') {
            when {
                expression {
                    BRANCH_NAME == 'jenkins-shared-lib'
                }
            }
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}
