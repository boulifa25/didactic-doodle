pipeline {
    agent any

    environment {
        IMAGE_NAME = "boulifa25/student-management:latest"
        DOCKER_CREDENTIALS_ID = "dockerhub-creds"
        SONARQUBE_ENV = "sonar-server"
        K8S_NAMESPACE = "devops"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/boulifa25/didactic-doodle.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh '''
                    chmod +x mvnw
                    ./mvnw clean verify
                '''
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE_ENV}") {
                    sh '''
                        ./mvnw sonar:sonar \
                          -Dsonar.projectKey=didactic-doodle \
                          -Dsonar.projectName="Didactic Doodle"
                    '''
                }
            }
        }


        stage('Docker Build') {
            steps {
                sh '''
                    docker build -t ${IMAGE_NAME} .
                '''
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: "${DOCKER_CREDENTIALS_ID}",
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push ${IMAGE_NAME}
                        docker logout
                    '''
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    kubectl apply -f k8s/deployment.yaml
                    kubectl apply -f k8s/service.yaml
                '''
            }
        }
    }

    post {
        success {
            echo "✅ CI/CD + Kubernetes exécuté avec succès"
        }
        failure {
            echo "❌ Échec du pipeline"
        }
    }
}
