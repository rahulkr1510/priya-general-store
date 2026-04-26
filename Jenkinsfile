pipeline {
    agent none

    environment {
        DOCKERHUB_USER = 'rahulkr1510'
        BACKEND_IMAGE = "${DOCKERHUB_USER}/priya-backend"
        FRONTEND_IMAGE = "${DOCKERHUB_USER}/priya-frontend"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            agent any
            steps {
                checkout scm
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
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Backend Test') {
            agent {
                docker {
                    image 'maven:3.9.9-eclipse-temurin-17'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                dir('backend') {
                    sh 'mvn test -Dspring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration'
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
                    sh 'npm config set cache /tmp/.npm'
                    sh 'rm -rf node_modules'
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Docker Build') {
            when {
                branch 'main'
            }
            agent any
            steps {
                sh 'docker build -t $BACKEND_IMAGE:$IMAGE_TAG backend'
                sh 'docker build -t $FRONTEND_IMAGE:$IMAGE_TAG frontend'
            }
        }

        stage('Docker Push') {
            when {
                branch 'main'
            }
            agent any
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-cred',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker push $BACKEND_IMAGE:$IMAGE_TAG'
                    sh 'docker push $FRONTEND_IMAGE:$IMAGE_TAG'
                }
            }
        }

        stage('Deploy to Kubernetes') {
            when {
                branch 'main'
            }
            agent any
            steps {
                echo 'Deploy stage will run only after merge to main'
                sh 'kubectl set image deployment/priya-backend priya-backend=$BACKEND_IMAGE:$IMAGE_TAG || true'
                sh 'kubectl set image deployment/priya-frontend priya-frontend=$FRONTEND_IMAGE:$IMAGE_TAG || true'
            }
        }
    }

    post {
        always {
            echo "Pipeline finished for branch: ${env.BRANCH_NAME}"
        }
    }
}
