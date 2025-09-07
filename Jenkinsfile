pipeline {
    agent any

    tools{
        maven 'MyMaven'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url:'https://github.com/Divyaranjan1995/CucumberJavaFrameworkDocker.git'
            }
        }
        stage('Build & Test') {
            steps {
                bat 'mvn clean test'
            }
        }
        stage('Report') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        results: [[path: 'allure-results']]  // or 'target/allure-results' if using Maven
                    ])
                }
            }
        }

    }
    post {
        always {
            emailext (
                subject: "Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                body: """
                    <p>Hi Team,</p>
                    <p>The Jenkins build <b>#${env.BUILD_NUMBER}</b> for job
                    <b>${env.JOB_NAME}</b> has finished with status:
                    <b style="color:red">${currentBuild.currentResult}</b>.</p>

                    <p><a href="${env.BUILD_URL}">Click here</a> to see full build details.</p>
                """,
                mimeType: 'text/html'
            )
        }
    }
}