pipeline {
    agent any

    environment {
        GIT_REPO = 'https://github.com/VoElenaS/Automation_Java.git'
        MAVEN_HOME = 'C:\\Java\\apache-maven-3.9.9'
        ALLURE_RESULTS = 'target\\allure-results'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: "${env.GIT_REPO}"
            }
        }


        stage('Build and Test') {
             steps {
                 withCredentials([file(credentialsId: 'test_env.properties', variable: 'SECRET_FILE')]) {
                  bat "copy %SECRET_FILE% src\\test\\resources\\test_env.properties"
                  bat "dir src\\test\\resources\\ "
                  bat "mvn clean test site"
                  }
             }
        }

        stage('Generate Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: "${env.ALLURE_RESULTS}"]],
                    reportBuildPolicy: 'ALWAYS'
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/site/**/*.*', allowEmptyArchive: true
        }
        failure {
            mail to: 'your.email@example.com',
                 subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Check Jenkins for details: ${env.BUILD_URL}"
        }
    }
}