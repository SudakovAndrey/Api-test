pipeline {
    agent any
    tools {
        maven 'Maven 3.3.9'
        jdk 'jdk8'
    }
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage ('checkout repo') {
                git branch: 'master',
                credentialsId: '6bc223c3-6152-40ee-8574-702551dd8b4d',
                url: 'https://github.com/SudakovAndrey/Api-test.git'
            }
        stage ('Build') {
              sh 'mvn -Dmaven.test.failure.ignore=true install'
        }
        stage ('run api tests') {
                sh 'mvn test'
            }
}