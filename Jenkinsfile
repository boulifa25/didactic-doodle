pipeline {
    agent any

    tools {
        maven 'M2_HOME'
    }

    environment {
        IMAGE_NAME = 'zehim/devops-project:latest'
        DOCKER_CREDENTIALS_ID = 'c85ad107-c988-416f-b3d7-7d25ce9599e0'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/MZahii/Devops.git'
            }
        }

        stage('Build & Test & Coverage') {
            steps {
                // This builds the app and generates the JaCoCo coverage report
                sh 'mvn clean verify -DskipTests=false'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // We use the "sonar-server" name you configured in Manage Jenkins -> System
                withSonarQubeEnv('sonar-server') {
                    // We use Maven directly. No need for the external tool.
                    sh 'mvn sonar:sonar -Dsonar.projectKey=student-management -Dsonar.projectName="Student Management"'
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
                withCredentials([usernamePassword(credentialsId: "$DOCKER_CREDENTIALS_ID", usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh "docker login -u ${USER} -p ${PASS}"
                    sh "docker push ${IMAGE_NAME}"
                }
            }
        }
    }
}