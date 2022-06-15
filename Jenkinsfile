pipeline {
    agent any
    tools {
            maven 'Maven 3.8.6'
            jdk 'Java 17'
    }
    stages {
        stage ('checkout repo') {
            steps {
                git branch: 'master',
                credentialsId: '6bc223c3-6152-40ee-8574-702551dd8b4d',
                url: 'https://github.com/SudakovAndrey/Api-test.git'
            }
        }

        stage ('build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage ('run api tests') {
            steps {
                sh 'mvn test -Dtest=TestSuiteRegisterUserSuccessfully'
            }
        }
    }
    post {
        always {
            script {
                allure ([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'allure-results']]
                ])
            }
        }
    }
}