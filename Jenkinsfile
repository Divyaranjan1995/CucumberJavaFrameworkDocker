pipeline {
    agent any

    tools{
        maven 'MyMaven'
    }

    environment{
        COMPOSE_PATH = "${WORKSPACE}"
    }
    stages {
        stage('Start Selenium Grid via Docker'){
            steps{
                script{
                    echo "Starting selenium grid with docker compose"
                    sh "docker-compose -f ${COMPOSE_PATH}/docker-compose.yml up -d"
                    echo "Waiting for Selenium Grid to be Ready"
                    sleep 30
                }
            }
        }
        stage('Checkout') {
            steps {
                git branch: 'main', url:'https://github.com/Divyaranjan1995/CucumberJavaFrameworkDocker.git'
            }
        }
        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
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
        stage('End Selenium Grid via Docker'){
            steps{
                script{
                    echo "Closing selenium grid with docker compose"
                    sh "docker-compose down"
                    sleep 10
                }
            }
        }

    }
    post {
        always {
            emailext (
                subject: "$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!",
                body: """
                    <p>Hello Team,</p>
<p>The latest Jenkins build has completed.</p>
<p>🛠 **Project Name**: $PROJECT_NAME <br />🔢 **Build Number**: #$BUILD_NUMBER <br />📜 **Build Status**: $BUILD_STATUS <br />🔗 **Build URL**: [Click here]($BUILD_URL) <br />📜 **Last Commit:**</p>
<p>$GIT_COMMIT <br />📂 **Branch**: $GIT_BRANCH <br />📎 **Build log is attached.**<br />📎 **Extent Report:** [Click here] http://3.86.12.128:8080/job/OrangeHRMPipeline/allure/</p>
<p>Best regards, <br />Automation Team</p>
                """,
                mimeType: 'text/html',
                to: 'divyaranjan1995@gmail.com'
            )
        }
    }
}