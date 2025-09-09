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
                <html>
                <body style="font-family: Arial, sans-serif; font-size: 14px; color: #333;">
                    <p>Hello Team,</p>
                    <p>The latest Jenkins build has completed. Please find the details below:</p>

                    <table style="border-collapse: collapse; width: 100%; max-width: 600px;">
                        <tr>
                            <td style="padding: 8px; font-weight: bold;">🛠 Project Name</td>
                            <td style="padding: 8px;">${env.JOB_NAME}</td>
                        </tr>
                        <tr style="background-color: #f9f9f9;">
                            <td style="padding: 8px; font-weight: bold;">Build Number</td>
                            <td style="padding: 8px;">#${env.BUILD_NUMBER}</td>
                        </tr>
                        <tr>
                            <td style="padding: 8px; font-weight: bold;">Build Status</td>
                            <td style="padding: 8px; color: ${currentBuild.currentResult == 'SUCCESS' ? 'green' : 'red'};">
                                <b>${currentBuild.currentResult}</b>
                            </td>
                        </tr>
                        <tr style="background-color: #f9f9f9;">
                            <td style="padding: 8px; font-weight: bold;">Build URL</td>
                            <td style="padding: 8px;">
                                <a href="${env.BUILD_URL}" style="color: #1a73e8;">Click here</a>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 8px; font-weight: bold;">Last Commit</td>
                            <td style="padding: 8px;">${env.GIT_COMMIT}</td>
                        </tr>
                        <tr style="background-color: #f9f9f9;">
                            <td style="padding: 8px; font-weight: bold;">Branch</td>
                            <td style="padding: 8px;">${env.GIT_BRANCH}</td>
                        </tr>
                        <tr>
                            <td style="padding: 8px; font-weight: bold;">Extent Report</td>
                            <td style="padding: 8px;">
                                <a href="http://localhost:8080/job/OrangeHRMProject/allure/" style="color: #1a73e8;">Click here</a>
                            </td>
                        </tr>
                    </table>

                    <p>📎 <b>Build log is attached.</b></p>

                    <br>
                    <p>Best regards,<br>
                    <b>Automation Team</b></p>
                </body>
                </html>
            """,
            mimeType: 'text/html',
            to: 'divyaranjan1995@gmail.com',
            attachLog: true
        )
    }
}

}
