pipeline {
    agent any

    tools{
        maven 'MyMaven'
    }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Select the browser to run tests'
        )
    }

    environment{
        COMPOSE_PATH = "${WORKSPACE}"
    }
    stages {
        stage('Start Selenium Grid via Docker'){
            steps{
                script{
                    echo "Starting selenium grid with docker compose"
                    bat "docker-compose -f ${COMPOSE_PATH}/docker-compose.yml up -d"
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
                bat 'mvn clean test -Dbrowser=${params.BROWSER}'
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
                    bat "docker-compose down"
                    sleep 10
                }
            }
        }

    }
    post {
        always {
            emailext (
            subject: "Project: ${env.JOB_NAME} | Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
            body: """
                Hello Team,<br><br>
                The latest Jenkins build has completed.<br><br>

                🛠 <b>Project Name</b>: ${env.JOB_NAME}<br>
                🔢 <b>Build Number</b>: #${env.BUILD_NUMBER}<br>
                📜 <b>Build Status</b>: ${currentBuild.currentResult}<br>
                🔗 <b>Build URL</b>: <a href="${env.BUILD_URL}">Click here</a><br>
                📜 <b>Last Commit:</b><br><br>
                ${env.GIT_COMMIT}<br>
                📂 <b>Branch</b>: ${env.GIT_BRANCH}<br>
                📎 <b>Build log is attached.</b><br>
                📎 <b>Extent Report:</b> <a href="http://localhost:8080/job/OrangeHRMProject/allure/">Click here</a><br><br>

                Best regards,<br>
                Automation Team
            """,
            mimeType: 'text/html',
            to: 'divyaranjan1995@gmail.com',
            attachLog: true
        )
    }
}
}