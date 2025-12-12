pipeline {
    agent any

    tools {
        maven 'M2_HOME'
    }

    environment {
        IMAGE_NAME = 'boulifa25/student-management:latest'
        DOCKER_CREDENTIALS_ID = 'c85ad107-c988-416f-b3d7-7d25ce9599e0'
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
                withSonarQubeEnv('sonar-server') {
                    sh '''
                        ./mvnw sonar:sonar \
                          -Dsonar.projectKey=didactic-doodle \
                          -Dsonar.projectName="Didactic Doodle"
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: "${DOCKER_CREDENTIALS_ID}",
                        usernameVariable: 'USER',
                        passwordVariable: 'PASS'
                    )
                ]) {
                    sh '''
                        echo "$PASS" | docker login -u "$USER" --password-stdin
                        docker push ${IMAGE_NAME}
                        docker logout
                    '''
                }
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline terminé avec succès"
        }
        failure {
            echo "❌ Pipeline échoué"
        }
    }
}
