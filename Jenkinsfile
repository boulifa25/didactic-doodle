pipeline {
    agent any

    tools {
        maven 'M2_HOME'
    }

    environment {
        IMAGE_NAME = 'boulifa25/didactic-doodle:latest'
        DOCKER_CREDENTIALS_ID = 'dockerhub-aziz'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/boulifa25/didactic-doodle.git'
            }
        }

        stage('Build & Test & Coverage') {
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
                chmod +x mvnw
                ./mvnw sonar:sonar \
                  -Dsonar.projectKey=didactic-doodle \
                  -Dsonar.projectName="Didactic Doodle"
            '''
        }
    }
}
        stage('Setup Docker') {
    steps {
            sh '''
      # Installer Docker si non présent
      if ! command -v docker &> /dev/null; then
        echo "Installing Docker..."
        apt-get update
        apt-get install -y docker.io
      fi
      
      # Vérifier l'installation
      docker --version
    '''
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
                    usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}",
                                     usernameVariable: 'USER',
                                     passwordVariable: 'PASS')
                ]) {
                    sh 'echo "${PASS}" | docker login -u "${USER}" --password-stdin'
                    sh "docker push ${IMAGE_NAME}"
                }
            }
        }
    }
}
