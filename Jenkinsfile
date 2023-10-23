#!/usr/bin/env groovy
@Library('jenkins-shared-library')
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
