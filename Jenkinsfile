pipeline {
    agent any

    environment {
        IMAGE_NAME = "boulifa25/student-management:latest"
        DOCKER_CREDENTIALS_ID = "dockerhub-creds"
        SONARQUBE_ENV = "sonar-server"
        K8S_NAMESPACE = "devops"
        KUBECONFIG = "${WORKSPACE}/kubeconfig.yaml"
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

        stage('Check Kubernetes Access') {
            steps {
                sh '''
                    echo "🔎 Kubernetes access test"
                    kubectl config current-context
                    kubectl get nodes
                '''
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    echo "📦 Déploiement Kubernetes..."

                    kubectl create namespace ${K8S_NAMESPACE} \
                      --dry-run=client -o yaml | kubectl apply -f -

                    kubectl apply -f k8s/ -n ${K8S_NAMESPACE}

                    kubectl set image deployment/spring-app \
                      spring-app=${IMAGE_NAME} \
                      -n ${K8S_NAMESPACE}
                '''
            }
        }

        stage('Verify Kubernetes Deployment') {
            steps {
                sh '''
                    echo "🔍 Vérification du déploiement..."

                    kubectl rollout status deployment/spring-app -n ${K8S_NAMESPACE}
                    kubectl get pods -n ${K8S_NAMESPACE}
                    kubectl get svc -n ${K8S_NAMESPACE}
                '''
            }
        }
    }

    post {
        success {
            echo "✅ CI/CD + Docker + Kubernetes exécuté avec succès"
        }
        failure {
            echo "❌ Échec du pipeline CI/CD"
        }
    }
}
