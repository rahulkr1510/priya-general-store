pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/rahulkr1510/priya-general-store.git'
            }
        }

        stage('Backend Build') {
            steps {
                dir('backend') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Frontend Build') {
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }
    }
}
