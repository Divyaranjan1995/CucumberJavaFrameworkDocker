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
        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
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
}