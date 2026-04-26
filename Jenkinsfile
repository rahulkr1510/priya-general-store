pipeline {
    agent none

    stages {
        stage('Checkout') {
            agent any
            steps {
                deleteDir()
                git branch: 'main',
                    url: 'https://github.com/rahulkr1510/priya-general-store.git'
            }
        }

        stage('Backend Build') {
            agent {
                docker {
                    image 'maven:3.9.9-eclipse-temurin-17'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                dir('backend') {
                    sh 'java -version'
                    sh 'mvn -v'
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Frontend Build') {
            agent {
                docker {
                    image 'node:20-alpine'
                    args '-u root'
                }
            }
            steps {
                dir('frontend') {
                    sh 'rm -rf node_modules package-lock.json'
                    sh 'npm cache clean --force'
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }
    }
}
