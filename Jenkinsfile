node {
    stage ("checkout repo") {
        git branch: 'master',
        credentialsId: '6bc223c3-6152-40ee-8574-702551dd8b4d',
        url: 'https://github.com/SudakovAndrey/Api-test.git'
    }

    stage ("build") {
        sh "mvn -B -DskipTests clean package"
    }

    stage ("run api tests") {
            sh "mvn test"
        }
}